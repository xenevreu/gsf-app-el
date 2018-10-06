import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';
import { ReinfStatusControleService } from './reinf-status-controle.service';

@Component({
    selector: 'jhi-reinf-status-controle-update',
    templateUrl: './reinf-status-controle-update.component.html'
})
export class ReinfStatusControleUpdateComponent implements OnInit {
    private _reinfStatusControle: IReinfStatusControle;
    isSaving: boolean;

    constructor(private reinfStatusControleService: ReinfStatusControleService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reinfStatusControle }) => {
            this.reinfStatusControle = reinfStatusControle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reinfStatusControle.id !== undefined) {
            this.subscribeToSaveResponse(this.reinfStatusControleService.update(this.reinfStatusControle));
        } else {
            this.subscribeToSaveResponse(this.reinfStatusControleService.create(this.reinfStatusControle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReinfStatusControle>>) {
        result.subscribe((res: HttpResponse<IReinfStatusControle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get reinfStatusControle() {
        return this._reinfStatusControle;
    }

    set reinfStatusControle(reinfStatusControle: IReinfStatusControle) {
        this._reinfStatusControle = reinfStatusControle;
    }
}
