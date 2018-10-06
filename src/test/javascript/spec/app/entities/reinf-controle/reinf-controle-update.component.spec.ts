/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfControleUpdateComponent } from 'app/entities/reinf-controle/reinf-controle-update.component';
import { ReinfControleService } from 'app/entities/reinf-controle/reinf-controle.service';
import { ReinfControle } from 'app/shared/model/reinf-controle.model';

describe('Component Tests', () => {
    describe('ReinfControle Management Update Component', () => {
        let comp: ReinfControleUpdateComponent;
        let fixture: ComponentFixture<ReinfControleUpdateComponent>;
        let service: ReinfControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfControleUpdateComponent]
            })
                .overrideTemplate(ReinfControleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfControleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfControleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReinfControle(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfControle = entity;
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
                    const entity = new ReinfControle();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfControle = entity;
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
