import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReinfItemControle } from 'app/shared/model/reinf-item-controle.model';
import { ReinfItemControleService } from './reinf-item-controle.service';

@Component({
    selector: 'jhi-reinf-item-controle-delete-dialog',
    templateUrl: './reinf-item-controle-delete-dialog.component.html'
})
export class ReinfItemControleDeleteDialogComponent {
    reinfItemControle: IReinfItemControle;

    constructor(
        private reinfItemControleService: ReinfItemControleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reinfItemControleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reinfItemControleListModification',
                content: 'Deleted an reinfItemControle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reinf-item-controle-delete-popup',
    template: ''
})
export class ReinfItemControleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfItemControle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReinfItemControleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reinfItemControle = reinfItemControle;
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
