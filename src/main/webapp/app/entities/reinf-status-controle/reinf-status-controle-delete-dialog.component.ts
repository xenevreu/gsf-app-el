import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';
import { ReinfStatusControleService } from './reinf-status-controle.service';

@Component({
    selector: 'jhi-reinf-status-controle-delete-dialog',
    templateUrl: './reinf-status-controle-delete-dialog.component.html'
})
export class ReinfStatusControleDeleteDialogComponent {
    reinfStatusControle: IReinfStatusControle;

    constructor(
        private reinfStatusControleService: ReinfStatusControleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reinfStatusControleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reinfStatusControleListModification',
                content: 'Deleted an reinfStatusControle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reinf-status-controle-delete-popup',
    template: ''
})
export class ReinfStatusControleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfStatusControle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReinfStatusControleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reinfStatusControle = reinfStatusControle;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
