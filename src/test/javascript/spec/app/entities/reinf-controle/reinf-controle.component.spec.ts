/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfControleComponent } from 'app/entities/reinf-controle/reinf-controle.component';
import { ReinfControleService } from 'app/entities/reinf-controle/reinf-controle.service';
import { ReinfControle } from 'app/shared/model/reinf-controle.model';

describe('Component Tests', () => {
    describe('ReinfControle Management Component', () => {
        let comp: ReinfControleComponent;
        let fixture: ComponentFixture<ReinfControleComponent>;
        let service: ReinfControleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfControleComponent],
                providers: []
            })
                .overrideTemplate(ReinfControleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfControleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfControleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReinfControle(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reinfControles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
