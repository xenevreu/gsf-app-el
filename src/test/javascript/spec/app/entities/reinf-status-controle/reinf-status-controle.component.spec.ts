/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfStatusControleComponent } from 'app/entities/reinf-status-controle/reinf-status-controle.component';
import { ReinfStatusControleService } from 'app/entities/reinf-status-controle/reinf-status-controle.service';
import { ReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';

describe('Component Tests', () => {
    describe('ReinfStatusControle Management Component', () => {
        let comp: ReinfStatusControleComponent;
        let fixture: ComponentFixture<ReinfStatusControleComponent>;
        let service: ReinfStatusControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfStatusControleComponent],
                providers: []
            })
                .overrideTemplate(ReinfStatusControleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfStatusControleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfStatusControleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReinfStatusControle(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reinfStatusControles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
