/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfItemControleUpdateComponent } from 'app/entities/reinf-item-controle/reinf-item-controle-update.component';
import { ReinfItemControleService } from 'app/entities/reinf-item-controle/reinf-item-controle.service';
import { ReinfItemControle } from 'app/shared/model/reinf-item-controle.model';

describe('Component Tests', () => {
    describe('ReinfItemControle Management Update Component', () => {
        let comp: ReinfItemControleUpdateComponent;
        let fixture: ComponentFixture<ReinfItemControleUpdateComponent>;
        let service: ReinfItemControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfItemControleUpdateComponent]
            })
                .overrideTemplate(ReinfItemControleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfItemControleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfItemControleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReinfItemControle(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfItemControle = entity;
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
                    const entity = new ReinfItemControle();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reinfItemControle = entity;
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
