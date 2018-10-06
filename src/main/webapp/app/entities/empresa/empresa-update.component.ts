import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';

@Component({
    selector: 'jhi-empresa-update',
    templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
    private _empresa: IEmpresa;
    isSaving: boolean;

    empresas: IEmpresa[];
    dtInclusao: string;
    dtUltAlteracao: string;
    usuUltAlteracao: string;

    constructor(private jhiAlertService: JhiAlertService, private empresaService: EmpresaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empresa }) => {
            this.empresa = empresa;
        });
        this.empresaService.query().subscribe(
            (res: HttpResponse<IEmpresa[]>) => {
                this.empresas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.empresa.dtInclusao = moment(this.dtInclusao, DATE_TIME_FORMAT);
        this.empresa.dtUltAlteracao = moment(this.dtUltAlteracao, DATE_TIME_FORMAT);
        this.empresa.usuUltAlteracao = moment(this.usuUltAlteracao, DATE_TIME_FORMAT);
        if (this.empresa.id !== undefined) {
            this.subscribeToSaveResponse(this.empresaService.update(this.empresa));
        } else {
            this.subscribeToSaveResponse(this.empresaService.create(this.empresa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
        result.subscribe((res: HttpResponse<IEmpresa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }
    get empresa() {
        return this._empresa;
    }

    set empresa(empresa: IEmpresa) {
        this._empresa = empresa;
        this.dtInclusao = moment(empresa.dtInclusao).format(DATE_TIME_FORMAT);
        this.dtUltAlteracao = moment(empresa.dtUltAlteracao).format(DATE_TIME_FORMAT);
        this.usuUltAlteracao = moment(empresa.usuUltAlteracao).format(DATE_TIME_FORMAT);
    }
}
