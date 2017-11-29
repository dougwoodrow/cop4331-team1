import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {SERVER_API_URL} from '../../app.constants';
import {ResponseWrapper} from '../model/response-wrapper.model';
import {Route} from './route.model';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class RouteService {
    private resourceUrl = SERVER_API_URL + 'api/routes';
    private currentRoute: Route;
    private subject = new Subject<any>();

    constructor(private http: Http) { }

    query(routeRequest: any, req?: any): Observable<ResponseWrapper> {
        const requestUrl: string = this.resourceUrl + '?to=' + routeRequest.to + '&from=' + routeRequest.from;
        return this.http.get(requestUrl).map((res: Response) => this.convertResponse(res));
    }

    getRoute(): Observable<any> {
        return this.subject.asObservable();
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        this.currentRoute = jsonResponse.data;
        this.currentRoute.arrivalDateTime = new Date(this.currentRoute.arrivalDateTime);
        this.currentRoute.departureDateTime = new Date(this.currentRoute.departureDateTime);
        this.subject.next(this.currentRoute);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
