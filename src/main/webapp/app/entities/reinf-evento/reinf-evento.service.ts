import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReinfEvento } from 'app/shared/model/reinf-evento.model';

type EntityResponseType = HttpResponse<IReinfEvento>;
type EntityArrayResponseType = HttpResponse<IReinfEvento[]>;

@Injectable({ providedIn: 'root' })
export class ReinfEventoService {
    private resourceUrl = SERVER_API_URL + 'api/reinf-eventos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reinf-eventos';

    constructor(private http: HttpClient) {}

    create(reinfEvento: IReinfEvento): Observable<EntityResponseType> {
        return this.http.post<IReinfEvento>(this.resourceUrl, reinfEvento, { observe: 'response' });
    }

    update(reinfEvento: IReinfEvento): Observable<EntityResponseType> {
        return this.http.put<IReinfEvento>(this.resourceUrl, reinfEvento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReinfEvento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfEvento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReinfEvento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
