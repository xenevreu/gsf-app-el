/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GsfappelTestModule } from '../../../test.module';
import { ReinfItemControleDetailComponent } from 'app/entities/reinf-item-controle/reinf-item-controle-detail.component';
import { ReinfItemControle } from 'app/shared/model/reinf-item-controle.model';

describe('Component Tests', () => {
    describe('ReinfItemControle Management Detail Component', () => {
        let comp: ReinfItemControleDetailComponent;
        let fixture: ComponentFixture<ReinfItemControleDetailComponent>;
        const route = ({ data: of({ reinfItemControle: new ReinfItemControle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GsfappelTestModule],
                declarations: [ReinfItemControleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReinfItemControleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReinfItemControleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reinfItemControle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
