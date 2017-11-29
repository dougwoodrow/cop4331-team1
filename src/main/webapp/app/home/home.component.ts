import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiEventManager} from 'ng-jhipster';

import {Account, LoginService, Principal} from '../shared';
import {Subscription} from 'rxjs/Subscription';
import {RouteService} from '../shared/route/route.service';
import {Route} from '../shared/route/route.model';

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
    isTracking: boolean = false;
    routeSet: boolean = true;

    constructor(private principal: Principal,
                private loginService: LoginService,
                private routeService: RouteService,
                private eventManager: JhiEventManager) {
        this.route.startLocation = this.route.endLocation = {
            lat: 28.5354336,
            lng: -81.4034221
        };
        this.routeService.query({to: "12348 Golden Knight Circle", from: "12986 Mallory Circle"});
        $('.jh-card').css("padding", "0");
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;

            $("#tracker").removeClass();
            $("#tracker").addClass("yellow");

            this.subscription = this.routeService.getRoute().subscribe(route => {
                this.route = route;
                this.routeSet = true;

                $("#tracker").removeClass();
                $("#tracker").addClass("blue");
            });
        });
        this.registerAuthenticationSuccess();
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
        $("#tracker").removeClass();
        $("#tracker").addClass("green");

        var end = this.route.departureDateTime as any;
        var _second = 1000;
        var _minute = _second * 60;
        var _hour = _minute * 60;
        var _day = _hour * 24;
        var timer;

        function showRemaining() {
            var now = new Date() as any;
            var distance = end - now;
            if (distance < 0) {
                clearInterval(timer);
                $('#countdown').html('Your bus is arriving!');
                return;
            }
            var hours = Math.floor((distance % _day) / _hour);
            var minutes = Math.floor((distance % _hour) / _minute);
            var seconds = Math.floor((distance % _minute) / _second);

            $('#countdown').html(hours + ' hrs ');
            $('#countdown').html(minutes + ' mins ');
            $('#countdown').html(seconds + ' secs');
        }

        timer = setInterval(showRemaining, 1000);

        this.isTracking = true;
    }

    stopTracking() {
        $("#tracker").removeClass();
        $("#tracker").addClass("red");
        this.route = new Route();
        this.routeSet = false;
        this.isTracking = false;
    }

    saveStation() {

    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
