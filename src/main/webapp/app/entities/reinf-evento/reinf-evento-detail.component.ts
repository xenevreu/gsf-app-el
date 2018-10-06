import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReinfEvento } from 'app/shared/model/reinf-evento.model';

@Component({
    selector: 'jhi-reinf-evento-detail',
    templateUrl: './reinf-evento-detail.component.html'
})
export class ReinfEventoDetailComponent implements OnInit {
    reinfEvento: IReinfEvento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfEvento }) => {
            this.reinfEvento = reinfEvento;
        });
    }

    previousState() {
        window.history.back();
    }
}
