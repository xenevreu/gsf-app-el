import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmpresa } from 'app/shared/model/empresa.model';

type EntityResponseType = HttpResponse<IEmpresa>;
type EntityArrayResponseType = HttpResponse<IEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaService {
    private resourceUrl = SERVER_API_URL + 'api/empresas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/empresas';

    constructor(private http: HttpClient) {}

    create(empresa: IEmpresa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(empresa);
        return this.http
            .post<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(empresa: IEmpresa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(empresa);
        return this.http
            .put<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmpresa[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(empresa: IEmpresa): IEmpresa {
        const copy: IEmpresa = Object.assign({}, empresa, {
            dtInclusao: empresa.dtInclusao != null && empresa.dtInclusao.isValid() ? empresa.dtInclusao.toJSON() : null,
            dtUltAlteracao: empresa.dtUltAlteracao != null && empresa.dtUltAlteracao.isValid() ? empresa.dtUltAlteracao.toJSON() : null,
            usuUltAlteracao: empresa.usuUltAlteracao != null && empresa.usuUltAlteracao.isValid() ? empresa.usuUltAlteracao.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dtInclusao = res.body.dtInclusao != null ? moment(res.body.dtInclusao) : null;
        res.body.dtUltAlteracao = res.body.dtUltAlteracao != null ? moment(res.body.dtUltAlteracao) : null;
        res.body.usuUltAlteracao = res.body.usuUltAlteracao != null ? moment(res.body.usuUltAlteracao) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((empresa: IEmpresa) => {
            empresa.dtInclusao = empresa.dtInclusao != null ? moment(empresa.dtInclusao) : null;
            empresa.dtUltAlteracao = empresa.dtUltAlteracao != null ? moment(empresa.dtUltAlteracao) : null;
            empresa.usuUltAlteracao = empresa.usuUltAlteracao != null ? moment(empresa.usuUltAlteracao) : null;
        });
        return res;
    }
}
