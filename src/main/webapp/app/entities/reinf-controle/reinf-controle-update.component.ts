import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IReinfControle } from 'app/shared/model/reinf-controle.model';
import { ReinfControleService } from './reinf-controle.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';
import { ReinfStatusControleService } from 'app/entities/reinf-status-controle';

@Component({
    selector: 'jhi-reinf-controle-update',
    templateUrl: './reinf-controle-update.component.html'
})
export class ReinfControleUpdateComponent implements OnInit {
    private _reinfControle: IReinfControle;
    isSaving: boolean;

    empresas: IEmpresa[];

    reinfstatuscontroles: IReinfStatusControle[];
    dtIni: string;
    dtFim: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private reinfControleService: ReinfControleService,
        private empresaService: EmpresaService,
        private reinfStatusControleService: ReinfStatusControleService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reinfControle }) => {
            this.reinfControle = reinfControle;
        });
        this.empresaService.query().subscribe(
            (res: HttpResponse<IEmpresa[]>) => {
                this.empresas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reinfStatusControleService.query().subscribe(
            (res: HttpResponse<IReinfStatusControle[]>) => {
                this.reinfstatuscontroles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.reinfControle.dtIni = moment(this.dtIni, DATE_TIME_FORMAT);
        this.reinfControle.dtFim = moment(this.dtFim, DATE_TIME_FORMAT);
        if (this.reinfControle.id !== undefined) {
            this.subscribeToSaveResponse(this.reinfControleService.update(this.reinfControle));
        } else {
            this.subscribeToSaveResponse(this.reinfControleService.create(this.reinfControle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReinfControle>>) {
        result.subscribe((res: HttpResponse<IReinfControle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackReinfStatusControleById(index: number, item: IReinfStatusControle) {
        return item.id;
    }
    get reinfControle() {
        return this._reinfControle;
    }

    set reinfControle(reinfControle: IReinfControle) {
        this._reinfControle = reinfControle;
        this.dtIni = moment(reinfControle.dtIni).format(DATE_TIME_FORMAT);
        this.dtFim = moment(reinfControle.dtFim).format(DATE_TIME_FORMAT);
    }
}
