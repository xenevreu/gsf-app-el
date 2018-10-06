/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfEventoComponent } from 'app/entities/reinf-evento/reinf-evento.component';
import { ReinfEventoService } from 'app/entities/reinf-evento/reinf-evento.service';
import { ReinfEvento } from 'app/shared/model/reinf-evento.model';

describe('Component Tests', () => {
    describe('ReinfEvento Management Component', () => {
        let comp: ReinfEventoComponent;
        let fixture: ComponentFixture<ReinfEventoComponent>;
        let service: ReinfEventoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfEventoComponent],
                providers: []
            })
                .overrideTemplate(ReinfEventoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReinfEventoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReinfEventoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReinfEvento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reinfEventos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
