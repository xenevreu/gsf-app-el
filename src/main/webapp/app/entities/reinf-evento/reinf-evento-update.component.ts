import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IReinfEvento } from 'app/shared/model/reinf-evento.model';
import { ReinfEventoService } from './reinf-evento.service';

@Component({
    selector: 'jhi-reinf-evento-update',
    templateUrl: './reinf-evento-update.component.html'
})
export class ReinfEventoUpdateComponent implements OnInit {
    private _reinfEvento: IReinfEvento;
    isSaving: boolean;

    constructor(private reinfEventoService: ReinfEventoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reinfEvento }) => {
            this.reinfEvento = reinfEvento;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reinfEvento.id !== undefined) {
            this.subscribeToSaveResponse(this.reinfEventoService.update(this.reinfEvento));
        } else {
            this.subscribeToSaveResponse(this.reinfEventoService.create(this.reinfEvento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReinfEvento>>) {
        result.subscribe((res: HttpResponse<IReinfEvento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get reinfEvento() {
        return this._reinfEvento;
    }

    set reinfEvento(reinfEvento: IReinfEvento) {
        this._reinfEvento = reinfEvento;
    }
}
