/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfItemControleComponent } from 'app/entities/reinf-item-controle/reinf-item-controle.component';
import { ReinfItemControleService } from 'app/entities/reinf-item-controle/reinf-item-controle.service';
import { ReinfItemControle } from 'app/shared/model/reinf-item-controle.model';

describe('Component Tests', () => {
    describe('ReinfItemControle Management Component', () => {
        let comp: ReinfItemControleComponent;
        let fixture: ComponentFixture<ReinfItemControleComponent>;
        let service: ReinfItemControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfItemControleComponent],
                providers: []
            })
                .overrideTemplate(ReinfItemControleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfItemControleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfItemControleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReinfItemControle(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reinfItemControles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
