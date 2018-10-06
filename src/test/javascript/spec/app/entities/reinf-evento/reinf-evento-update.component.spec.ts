/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfEventoUpdateComponent } from 'app/entities/reinf-evento/reinf-evento-update.component';
import { ReinfEventoService } from 'app/entities/reinf-evento/reinf-evento.service';
import { ReinfEvento } from 'app/shared/model/reinf-evento.model';

describe('Component Tests', () => {
    describe('ReinfEvento Management Update Component', () => {
        let comp: ReinfEventoUpdateComponent;
        let fixture: ComponentFixture<ReinfEventoUpdateComponent>;
        let service: ReinfEventoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfEventoUpdateComponent]
            })
                .overrideTemplate(ReinfEventoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfEventoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfEventoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReinfEvento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfEvento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReinfEvento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfEvento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
