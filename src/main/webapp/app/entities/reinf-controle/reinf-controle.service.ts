import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReinfControle } from 'app/shared/model/reinf-controle.model';

type EntityResponseType = HttpResponse<IReinfControle>;
type EntityArrayResponseType = HttpResponse<IReinfControle[]>;

@Injectable({ providedIn: 'root' })
export class ReinfControleService {
    private resourceUrl = SERVER_API_URL + 'api/reinf-controles';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reinf-controles';

    constructor(private http: HttpClient) {}

    create(reinfControle: IReinfControle): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reinfControle);
        return this.http
            .post<IReinfControle>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reinfControle: IReinfControle): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reinfControle);
        return this.http
            .put<IReinfControle>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReinfControle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReinfControle[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReinfControle[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(reinfControle: IReinfControle): IReinfControle {
        const copy: IReinfControle = Object.assign({}, reinfControle, {
            dtIni: reinfControle.dtIni != null && reinfControle.dtIni.isValid() ? reinfControle.dtIni.toJSON() : null,
            dtFim: reinfControle.dtFim != null && reinfControle.dtFim.isValid() ? reinfControle.dtFim.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dtIni = res.body.dtIni != null ? moment(res.body.dtIni) : null;
        res.body.dtFim = res.body.dtFim != null ? moment(res.body.dtFim) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((reinfControle: IReinfControle) => {
            reinfControle.dtIni = reinfControle.dtIni != null ? moment(reinfControle.dtIni) : null;
            reinfControle.dtFim = reinfControle.dtFim != null ? moment(reinfControle.dtFim) : null;
        });
        return res;
    }
}
