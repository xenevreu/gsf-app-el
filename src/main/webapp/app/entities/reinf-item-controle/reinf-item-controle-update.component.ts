import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReinfItemControle } from 'app/shared/model/reinf-item-controle.model';
import { ReinfItemControleService } from './reinf-item-controle.service';
import { IReinfEvento } from 'app/shared/model/reinf-evento.model';
import { ReinfEventoService } from 'app/entities/reinf-evento';
import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';
import { ReinfStatusControleService } from 'app/entities/reinf-status-controle';
import { IReinfControle } from 'app/shared/model/reinf-controle.model';
import { ReinfControleService } from 'app/entities/reinf-controle';

@Component({
    selector: 'jhi-reinf-item-controle-update',
    templateUrl: './reinf-item-controle-update.component.html'
})
export class ReinfItemControleUpdateComponent implements OnInit {
    private _reinfItemControle: IReinfItemControle;
    isSaving: boolean;

    reinfeventos: IReinfEvento[];

    reinfstatuscontroles: IReinfStatusControle[];

    reinfcontroles: IReinfControle[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private reinfItemControleService: ReinfItemControleService,
        private reinfEventoService: ReinfEventoService,
        private reinfStatusControleService: ReinfStatusControleService,
        private reinfControleService: ReinfControleService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reinfItemControle }) => {
            this.reinfItemControle = reinfItemControle;
        });
        this.reinfEventoService.query().subscribe(
            (res: HttpResponse<IReinfEvento[]>) => {
                this.reinfeventos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reinfStatusControleService.query().subscribe(
            (res: HttpResponse<IReinfStatusControle[]>) => {
                this.reinfstatuscontroles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reinfControleService.query().subscribe(
            (res: HttpResponse<IReinfControle[]>) => {
                this.reinfcontroles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reinfItemControle.id !== undefined) {
            this.subscribeToSaveResponse(this.reinfItemControleService.update(this.reinfItemControle));
        } else {
            this.subscribeToSaveResponse(this.reinfItemControleService.create(this.reinfItemControle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReinfItemControle>>) {
        result.subscribe((res: HttpResponse<IReinfItemControle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackReinfEventoById(index: number, item: IReinfEvento) {
        return item.id;
    }

    trackReinfStatusControleById(index: number, item: IReinfStatusControle) {
        return item.id;
    }

    trackReinfControleById(index: number, item: IReinfControle) {
        return item.id;
    }
    get reinfItemControle() {
        return this._reinfItemControle;
    }

    set reinfItemControle(reinfItemControle: IReinfItemControle) {
        this._reinfItemControle = reinfItemControle;
    }
}
