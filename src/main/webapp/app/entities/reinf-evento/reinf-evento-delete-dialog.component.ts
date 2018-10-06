import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReinfEvento } from 'app/shared/model/reinf-evento.model';
import { ReinfEventoService } from './reinf-evento.service';

@Component({
    selector: 'jhi-reinf-evento-delete-dialog',
    templateUrl: './reinf-evento-delete-dialog.component.html'
})
export class ReinfEventoDeleteDialogComponent {
    reinfEvento: IReinfEvento;

    constructor(
        private reinfEventoService: ReinfEventoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reinfEventoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reinfEventoListModification',
                content: 'Deleted an reinfEvento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reinf-evento-delete-popup',
    template: ''
})
export class ReinfEventoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reinfEvento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReinfEventoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reinfEvento = reinfEvento;
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
