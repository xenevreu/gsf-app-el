import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GsfappelSharedModule } from 'app/shared';
import {
    ReinfControleComponent,
    ReinfControleDetailComponent,
    ReinfControleUpdateComponent,
    ReinfControleDeletePopupComponent,
    ReinfControleDeleteDialogComponent,
    reinfControleRoute,
    reinfControlePopupRoute
} from './';

const ENTITY_STATES = [...reinfControleRoute, ...reinfControlePopupRoute];

@NgModule({
    imports: [GsfappelSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReinfControleComponent,
        ReinfControleDetailComponent,
        ReinfControleUpdateComponent,
        ReinfControleDeleteDialogComponent,
        ReinfControleDeletePopupComponent
    ],
    entryComponents: [
        ReinfControleComponent,
        ReinfControleUpdateComponent,
        ReinfControleDeleteDialogComponent,
        ReinfControleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfappelReinfControleModule {}
