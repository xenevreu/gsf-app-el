import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GsfappelSharedModule } from 'app/shared';
import {
    ReinfItemControleComponent,
    ReinfItemControleDetailComponent,
    ReinfItemControleUpdateComponent,
    ReinfItemControleDeletePopupComponent,
    ReinfItemControleDeleteDialogComponent,
    reinfItemControleRoute,
    reinfItemControlePopupRoute
} from './';

const ENTITY_STATES = [...reinfItemControleRoute, ...reinfItemControlePopupRoute];

@NgModule({
    imports: [GsfappelSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReinfItemControleComponent,
        ReinfItemControleDetailComponent,
        ReinfItemControleUpdateComponent,
        ReinfItemControleDeleteDialogComponent,
        ReinfItemControleDeletePopupComponent
    ],
    entryComponents: [
        ReinfItemControleComponent,
        ReinfItemControleUpdateComponent,
        ReinfItemControleDeleteDialogComponent,
        ReinfItemControleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfappelReinfItemControleModule {}
