import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {SERVER_API_URL} from '../../app.constants';
import {ResponseWrapper} from '../model/response-wrapper.model';
import {Station} from './station.model';
import {Subject} from "rxjs/Subject";

@Injectable()
export class StationService {
    private resourceUrl = SERVER_API_URL + 'api/stations';
    private currentStation: Station;
    private subject = new Subject<any>();

    constructor(private http: Http) { }

    queryForUser() {
        const requestUrl = this.resourceUrl;
        return this.http.get(requestUrl).map((res: Response) => this.convertResponse(res));
    }

    createForUser(stationAddress: string, req?: any): Observable<ResponseWrapper> {
        const requestUrl = this.resourceUrl;
        const station: Station = new Station();
        station.address = stationAddress;
        return this.http.post(requestUrl, station).map((res: Response) => this.convertResponse(res));
    }

    getStation(): Observable<any> {
        return this.subject.asObservable();
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        this.currentStation = jsonResponse.data as Station;
        this.subject.next(this.currentStation);
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
}
