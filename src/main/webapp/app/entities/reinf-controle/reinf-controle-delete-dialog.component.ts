import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReinfControle } from 'app/shared/model/reinf-controle.model';
import { ReinfControleService } from './reinf-controle.service';

@Component({
    selector: 'jhi-reinf-controle-delete-dialog',
    templateUrl: './reinf-controle-delete-dialog.component.html'
})
export class ReinfControleDeleteDialogComponent {
    reinfControle: IReinfControle;

    constructor(
        private reinfControleService: ReinfControleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reinfControleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reinfControleListModification',
                content: 'Deleted an reinfControle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reinf-controle-delete-popup',
    template: ''
})
export class ReinfControleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfControle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReinfControleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reinfControle = reinfControle;
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
