/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfControleDetailComponent } from 'app/entities/reinf-controle/reinf-controle-detail.component';
import { ReinfControle } from 'app/shared/model/reinf-controle.model';

describe('Component Tests', () => {
    describe('ReinfControle Management Detail Component', () => {
        let comp: ReinfControleDetailComponent;
        let fixture: ComponentFixture<ReinfControleDetailComponent>;
        const route = ({ data: of({ reinfControle: new ReinfControle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfControleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReinfControleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfControleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reinfControle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
