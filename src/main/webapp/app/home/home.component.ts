import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiEventManager} from 'ng-jhipster';

import {Account, LoginService, Principal} from '../shared';
import {Subscription} from 'rxjs/Subscription';
import {RouteService} from '../shared/route/route.service';
import {Route} from '../shared/route/route.model';

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
    point: any;

    constructor(private principal: Principal,
                private loginService: LoginService,
                private routeService: RouteService,
                private eventManager: JhiEventManager) {
        this.route.startLocation = this.route.endLocation = {
            lat: 28.5354336,
            lng: -81.4034221
        };
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.subscription = this.routeService.getRoute().subscribe(route => {
                this.route = route;
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
