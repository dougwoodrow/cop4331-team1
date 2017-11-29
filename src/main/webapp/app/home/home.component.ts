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
    routeSet = true;
    isStationSaved = false;
    iconUrl = 'http://svgshare.com/i/45E.svg';
    private _diff: number;
    private _days: number;
    private _hours: number;
    private _minutes: number;
    private _seconds: number;

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

        const end = this.route.departureDateTime;
        const _second = 1000;
        const _minute = _second * 60;
        const _hour = _minute * 60;
        const _day = _hour * 24;
        let timer;

        function showRemaining() {

            function getHours(t) {
                return Math.floor((t / (1000 * 60 * 60)) % 24);
            }

            function getMinutes(t) {
                return Math.floor((t / 1000 / 60) % 60);
            }

            function getSeconds(t) {
                return Math.floor((t / 1000) % 60);
            }

            Observable.interval(1000).map((x) => {
                this._diff = Date.parse(end.toDateString()) - Date.parse(new Date().toString());
                console.log(this._diff);
            }).subscribe((x) => {
                this._hours = getHours(this._diff);
                this._minutes = getMinutes(this._diff);
                this._seconds = getSeconds(this._diff);
                console.log(this._hours, this._minutes, this._seconds);
            });

            $('#countdown').html(this._hours + ' hrs ');
            $('#countdown').html(this._minutes + ' mins ');
            $('#countdown').html(this._seconds + ' secs');
        }

        timer = setInterval(showRemaining, 1000);

        this.isTracking = true;
    }

    stopTracking() {
        $('#tracker').removeClass();
        $('#tracker').addClass('red');
        this.route = new Route();
        this.routeSet = false;
        this.isTracking = false;
        this.isStationSaved = true;
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
