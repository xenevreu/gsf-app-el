/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfEventoDeleteDialogComponent } from 'app/entities/reinf-evento/reinf-evento-delete-dialog.component';
import { ReinfEventoService } from 'app/entities/reinf-evento/reinf-evento.service';

describe('Component Tests', () => {
    describe('ReinfEvento Management Delete Component', () => {
        let comp: ReinfEventoDeleteDialogComponent;
        let fixture: ComponentFixture<ReinfEventoDeleteDialogComponent>;
        let service: ReinfEventoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfEventoDeleteDialogComponent]
            })
                .overrideTemplate(ReinfEventoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfEventoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfEventoService);
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
