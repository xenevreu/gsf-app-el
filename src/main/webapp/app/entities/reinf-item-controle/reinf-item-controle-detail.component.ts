import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReinfItemControle } from 'app/shared/model/reinf-item-controle.model';

@Component({
    selector: 'jhi-reinf-item-controle-detail',
    templateUrl: './reinf-item-controle-detail.component.html'
})
export class ReinfItemControleDetailComponent implements OnInit {
    reinfItemControle: IReinfItemControle;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfItemControle }) => {
            this.reinfItemControle = reinfItemControle;
        });
    }

    previousState() {
        window.history.back();
    }
}
