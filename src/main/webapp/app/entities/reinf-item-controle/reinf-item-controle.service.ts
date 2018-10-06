import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReinfItemControle } from 'app/shared/model/reinf-item-controle.model';

type EntityResponseType = HttpResponse<IReinfItemControle>;
type EntityArrayResponseType = HttpResponse<IReinfItemControle[]>;

@Injectable({ providedIn: 'root' })
export class ReinfItemControleService {
    private resourceUrl = SERVER_API_URL + 'api/reinf-item-controles';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reinf-item-controles';

    constructor(private http: HttpClient) {}

    create(reinfItemControle: IReinfItemControle): Observable<EntityResponseType> {
        return this.http.post<IReinfItemControle>(this.resourceUrl, reinfItemControle, { observe: 'response' });
    }

    update(reinfItemControle: IReinfItemControle): Observable<EntityResponseType> {
        return this.http.put<IReinfItemControle>(this.resourceUrl, reinfItemControle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReinfItemControle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfItemControle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfItemControle[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
