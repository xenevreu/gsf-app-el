import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IReinfControle>(this.resourceUrl, reinfControle, { observe: 'response' });
    }

    update(reinfControle: IReinfControle): Observable<EntityResponseType> {
        return this.http.put<IReinfControle>(this.resourceUrl, reinfControle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReinfControle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfControle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfControle[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
