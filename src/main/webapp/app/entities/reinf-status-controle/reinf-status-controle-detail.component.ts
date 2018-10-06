import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';

@Component({
    selector: 'jhi-reinf-status-controle-detail',
    templateUrl: './reinf-status-controle-detail.component.html'
})
export class ReinfStatusControleDetailComponent implements OnInit {
    reinfStatusControle: IReinfStatusControle;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfStatusControle }) => {
            this.reinfStatusControle = reinfStatusControle;
        });
    }

    previousState() {
        window.history.back();
    }
}
