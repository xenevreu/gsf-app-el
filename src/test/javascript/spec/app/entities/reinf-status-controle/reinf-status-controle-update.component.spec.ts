/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfStatusControleUpdateComponent } from 'app/entities/reinf-status-controle/reinf-status-controle-update.component';
import { ReinfStatusControleService } from 'app/entities/reinf-status-controle/reinf-status-controle.service';
import { ReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';

describe('Component Tests', () => {
    describe('ReinfStatusControle Management Update Component', () => {
        let comp: ReinfStatusControleUpdateComponent;
        let fixture: ComponentFixture<ReinfStatusControleUpdateComponent>;
        let service: ReinfStatusControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfStatusControleUpdateComponent]
            })
                .overrideTemplate(ReinfStatusControleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfStatusControleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfStatusControleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReinfStatusControle(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfStatusControle = entity;
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
                    const entity = new ReinfStatusControle();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfStatusControle = entity;
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
