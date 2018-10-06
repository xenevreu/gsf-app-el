/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfControleDeleteDialogComponent } from 'app/entities/reinf-controle/reinf-controle-delete-dialog.component';
import { ReinfControleService } from 'app/entities/reinf-controle/reinf-controle.service';

describe('Component Tests', () => {
    describe('ReinfControle Management Delete Component', () => {
        let comp: ReinfControleDeleteDialogComponent;
        let fixture: ComponentFixture<ReinfControleDeleteDialogComponent>;
        let service: ReinfControleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfControleDeleteDialogComponent]
            })
                .overrideTemplate(ReinfControleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfControleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfControleService);
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
