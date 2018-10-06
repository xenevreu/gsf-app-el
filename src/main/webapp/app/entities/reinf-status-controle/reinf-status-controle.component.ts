import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';
import { Principal } from 'app/core';
import { ReinfStatusControleService } from './reinf-status-controle.service';

@Component({
    selector: 'jhi-reinf-status-controle',
    templateUrl: './reinf-status-controle.component.html'
})
export class ReinfStatusControleComponent implements OnInit, OnDestroy {
    reinfStatusControles: IReinfStatusControle[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private reinfStatusControleService: ReinfStatusControleService,
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
            this.reinfStatusControleService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IReinfStatusControle[]>) => (this.reinfStatusControles = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.reinfStatusControleService.query().subscribe(
            (res: HttpResponse<IReinfStatusControle[]>) => {
                this.reinfStatusControles = res.body;
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
        this.registerChangeInReinfStatusControles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReinfStatusControle) {
        return item.id;
    }

    registerChangeInReinfStatusControles() {
        this.eventSubscriber = this.eventManager.subscribe('reinfStatusControleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
