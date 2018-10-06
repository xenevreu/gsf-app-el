/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfItemControleDeleteDialogComponent } from 'app/entities/reinf-item-controle/reinf-item-controle-delete-dialog.component';
import { ReinfItemControleService } from 'app/entities/reinf-item-controle/reinf-item-controle.service';

describe('Component Tests', () => {
    describe('ReinfItemControle Management Delete Component', () => {
        let comp: ReinfItemControleDeleteDialogComponent;
        let fixture: ComponentFixture<ReinfItemControleDeleteDialogComponent>;
        let service: ReinfItemControleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfItemControleDeleteDialogComponent]
            })
                .overrideTemplate(ReinfItemControleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfItemControleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfItemControleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
