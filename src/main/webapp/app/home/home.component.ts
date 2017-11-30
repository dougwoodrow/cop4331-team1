import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiEventManager} from 'ng-jhipster';

import {Account, LoginService, Principal} from '../shared';
import {Subscription} from 'rxjs/Subscription';
import {RouteService} from '../shared/route/route.service';
import {Route} from '../shared/route/route.model';
import {StationService} from '../shared/station/station.service';
import {Observable} from 'rxjs/Observable';

declare var jquery: any;
declare var $: any;

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit, OnDestroy {
    account: Account;
    subscription: Subscription;
    route: Route = new Route();
    isTracking = false;
    routeSet = false;
    isStationSaved = false;
    iconUrl = 'https://i.imgur.com/Wls8QkC.png';
    timer: Subscription;

    constructor(private principal: Principal,
                private loginService: LoginService,
                private routeService: RouteService,
                private stationService: StationService,
                private eventManager: JhiEventManager) {
        this.route.startLocation = this.route.endLocation = {
            lat: 28.5354336,
            lng: -81.4034221
        };
        $('.jh-card').css('padding', '0');
    }

    ngOnInit() {
        $('#tracker').removeClass();
        $('#tracker').addClass('yellow');

        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.subscription = this.routeService.getRoute().subscribe((route) => {
            this.route = route;
            this.routeSet = true;
            this.isTracking = false;
            this.isStationSaved = false;
            this.route.busLinePath.map((pathPoint) => {
                pathPoint.isVisible = false;
            });
            try {
                this.timer.unsubscribe();
            } catch (te) {
               console.debug('Not subscribed yet...waiting.');
            }

            $('#tracker').removeClass();
            $('#tracker').addClass('blue');
        });
        this.routeService.query({to: '12348 Golden Knight Circle', from: '12986 Mallory Circle'});
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.loginService.login();
    }

    startTracking() {
        $('#tracker').removeClass();
        $('#tracker').addClass('green');
        const departureTime = this.route.departureDateTime;
        this.isTracking = true;
        this.startTicking(departureTime, this.route);
    }

    stopTracking() {
        $('#tracker').removeClass();
        $('#tracker').addClass('red');
        this.route = new Route();
        this.routeSet = false;
        this.isTracking = false;
        this.isStationSaved = true;
        this.timer.unsubscribe();
    }

    private startTicking(departureTime: Date, route: any) {
        let step: number;
        let hours: number;
        let minutes: number;
        let seconds: number;
        let timeElapsed: number;
        let timeDifference: number;
        let lengthOfPath: number;
        let currentIndex = 0;
        let originalTimeEstimate = 0;

        let fakeDate = new Date();
        fakeDate.setSeconds(fakeDate.getSeconds() + 30);
        departureTime = fakeDate;

        timeDifference = originalTimeEstimate = Date.parse(departureTime.toString()) - Date.parse(new Date().toString());
        hours = Math.floor((timeDifference / (1000 * 60 * 60)) % 24);
        minutes = Math.floor((timeDifference / 1000 / 60) % 60);
        seconds = Math.floor((timeDifference / 1000) % 60);
        timeElapsed = (hours * 3600) + (minutes * 60) + seconds;
        lengthOfPath = route.busLinePath.length;
        step = lengthOfPath / timeElapsed;

        this.timer = Observable.interval(1000).map((x) => {
            if (timeElapsed === 1) {
                this.timer.unsubscribe();
            }

            timeDifference = Date.parse(departureTime.toString()) - Date.parse(new Date().toString());
            timeElapsed = (hours * 3600) + (minutes * 60) + seconds;
            route.busLinePath.forEach((pathPoint, key) => {
                if (key > 0 && key === Number.parseInt(currentIndex.toFixed(0))) {
                    pathPoint.isVisible = true;
                } else if (pathPoint.isVisible = true) {
                    pathPoint.isVisible = false;
                }
            });

            if (hours === undefined || minutes === undefined || seconds === undefined) {
                $('#countdown').html('Calculating time remaining');
            } else if(hours === 0 && minutes === 0 && seconds === 0) {
                $('#countdown').html('Your bus has arrived!');
            } else {
                $('#countdown').html(hours + ' hrs ' + minutes + ' mins ' + seconds + ' secs');
            }
            currentIndex += step;
        }).subscribe((x) => {
            hours = Math.floor((timeDifference / (1000 * 60 * 60)) % 24);
            minutes = Math.floor((timeDifference / 1000 / 60) % 60);
            seconds = Math.floor((timeDifference / 1000) % 60);
        });
    }

    saveStation() {
        const stationAddress = this.route.startAddress;
        this.stationService.createForUser(stationAddress).subscribe((station) => {
            this.isStationSaved = true;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
