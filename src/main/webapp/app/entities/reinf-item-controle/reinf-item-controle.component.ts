import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReinfItemControle } from 'app/shared/model/reinf-item-controle.model';
import { Principal } from 'app/core';
import { ReinfItemControleService } from './reinf-item-controle.service';

@Component({
    selector: 'jhi-reinf-item-controle',
    templateUrl: './reinf-item-controle.component.html'
})
export class ReinfItemControleComponent implements OnInit, OnDestroy {
    reinfItemControles: IReinfItemControle[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private reinfItemControleService: ReinfItemControleService,
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
            this.reinfItemControleService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IReinfItemControle[]>) => (this.reinfItemControles = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.reinfItemControleService.query().subscribe(
            (res: HttpResponse<IReinfItemControle[]>) => {
                this.reinfItemControles = res.body;
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
        this.registerChangeInReinfItemControles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReinfItemControle) {
        return item.id;
    }

    registerChangeInReinfItemControles() {
        this.eventSubscriber = this.eventManager.subscribe('reinfItemControleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
