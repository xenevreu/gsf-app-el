/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfEventoDetailComponent } from 'app/entities/reinf-evento/reinf-evento-detail.component';
import { ReinfEvento } from 'app/shared/model/reinf-evento.model';

describe('Component Tests', () => {
    describe('ReinfEvento Management Detail Component', () => {
        let comp: ReinfEventoDetailComponent;
        let fixture: ComponentFixture<ReinfEventoDetailComponent>;
        const route = ({ data: of({ reinfEvento: new ReinfEvento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfEventoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReinfEventoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfEventoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reinfEvento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
