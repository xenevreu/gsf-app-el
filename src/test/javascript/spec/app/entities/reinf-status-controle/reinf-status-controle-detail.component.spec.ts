/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfStatusControleDetailComponent } from 'app/entities/reinf-status-controle/reinf-status-controle-detail.component';
import { ReinfStatusControle } from 'app/shared/model/reinf-status-controle.model';

describe('Component Tests', () => {
    describe('ReinfStatusControle Management Detail Component', () => {
        let comp: ReinfStatusControleDetailComponent;
        let fixture: ComponentFixture<ReinfStatusControleDetailComponent>;
        const route = ({ data: of({ reinfStatusControle: new ReinfStatusControle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfStatusControleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReinfStatusControleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfStatusControleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reinfStatusControle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
