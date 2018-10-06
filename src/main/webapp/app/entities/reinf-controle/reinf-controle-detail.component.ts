import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReinfControle } from 'app/shared/model/reinf-controle.model';

@Component({
    selector: 'jhi-reinf-controle-detail',
    templateUrl: './reinf-controle-detail.component.html'
})
export class ReinfControleDetailComponent implements OnInit {
    reinfControle: IReinfControle;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfControle }) => {
            this.reinfControle = reinfControle;
        });
    }

    previousState() {
        window.history.back();
    }
}
