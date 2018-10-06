import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GsfappelSharedModule } from 'app/shared';
import {
    ReinfStatusControleComponent,
    ReinfStatusControleDetailComponent,
    ReinfStatusControleUpdateComponent,
    ReinfStatusControleDeletePopupComponent,
    ReinfStatusControleDeleteDialogComponent,
    reinfStatusControleRoute,
    reinfStatusControlePopupRoute
} from './';

const ENTITY_STATES = [...reinfStatusControleRoute, ...reinfStatusControlePopupRoute];

@NgModule({
    imports: [GsfappelSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReinfStatusControleComponent,
        ReinfStatusControleDetailComponent,
        ReinfStatusControleUpdateComponent,
        ReinfStatusControleDeleteDialogComponent,
        ReinfStatusControleDeletePopupComponent
    ],
    entryComponents: [
        ReinfStatusControleComponent,
        ReinfStatusControleUpdateComponent,
        ReinfStatusControleDeleteDialogComponent,
        ReinfStatusControleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfappelReinfStatusControleModule {}
