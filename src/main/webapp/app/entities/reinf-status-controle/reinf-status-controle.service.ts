import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';

type EntityResponseType = HttpResponse<IReinfStatusControle>;
type EntityArrayResponseType = HttpResponse<IReinfStatusControle[]>;

@Injectable({ providedIn: 'root' })
export class ReinfStatusControleService {
    private resourceUrl = SERVER_API_URL + 'api/reinf-status-controles';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reinf-status-controles';

    constructor(private http: HttpClient) {}

    create(reinfStatusControle: IReinfStatusControle): Observable<EntityResponseType> {
        return this.http.post<IReinfStatusControle>(this.resourceUrl, reinfStatusControle, { observe: 'response' });
    }

    update(reinfStatusControle: IReinfStatusControle): Observable<EntityResponseType> {
        return this.http.put<IReinfStatusControle>(this.resourceUrl, reinfStatusControle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReinfStatusControle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfStatusControle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfStatusControle[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
