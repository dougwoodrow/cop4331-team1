import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {ProfileService} from '../profiles/profile.service';
import {Principal, LoginService, RouteService} from '../../shared';

import {VERSION} from '../../app.constants';
import {Route} from '../../shared/route/route.model';
import {Subscription} from 'rxjs/Subscription';
import {StationService} from "../../shared/station/station.service";

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    version: string;
    formData: any = {
        to: '12348 Golden Knight Circle',
        from: '1 Orange Ave'
    };
    route: Route;
    subscription: Subscription;
    stations: any[] = [];

    constructor(private loginService: LoginService,
                private principal: Principal,
                private profileService: ProfileService,
                private routeService: RouteService,
                private stationService: StationService,
                private router: Router) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        this.stationService.queryForUser().subscribe((stations) => {
            this.stations = stations.json.data;
        });

        this.subscription = this.stationService.getStation().subscribe((station) => {
            this.stations.push(station);
        });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    searchRoute() {
        this.routeService.query(this.formData).subscribe((routeInfo) => {
            this.route = routeInfo.json.data as Route;
        });
    }

    login() {
        this.loginService.login();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    setStation(station: any) {
        this.formData.from = station.address;
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
