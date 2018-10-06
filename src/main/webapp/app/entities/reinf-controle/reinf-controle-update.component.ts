import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IReinfControle } from 'app/shared/model/reinf-controle.model';
import { ReinfControleService } from './reinf-controle.service';

@Component({
    selector: 'jhi-reinf-controle-update',
    templateUrl: './reinf-controle-update.component.html'
})
export class ReinfControleUpdateComponent implements OnInit {
    private _reinfControle: IReinfControle;
    isSaving: boolean;

    constructor(private reinfControleService: ReinfControleService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reinfControle }) => {
            this.reinfControle = reinfControle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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
    get reinfControle() {
        return this._reinfControle;
    }

    set reinfControle(reinfControle: IReinfControle) {
        this._reinfControle = reinfControle;
    }
}
