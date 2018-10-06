import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReinfEvento } from 'app/shared/model/reinf-evento.model';
import { Principal } from 'app/core';
import { ReinfEventoService } from './reinf-evento.service';

@Component({
    selector: 'jhi-reinf-evento',
    templateUrl: './reinf-evento.component.html'
})
export class ReinfEventoComponent implements OnInit, OnDestroy {
    reinfEventos: IReinfEvento[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private reinfEventoService: ReinfEventoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.reinfEventoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IReinfEvento[]>) => (this.reinfEventos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.reinfEventoService.query().subscribe(
            (res: HttpResponse<IReinfEvento[]>) => {
                this.reinfEventos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReinfEventos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReinfEvento) {
        return item.id;
    }

    registerChangeInReinfEventos() {
        this.eventSubscriber = this.eventManager.subscribe('reinfEventoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
