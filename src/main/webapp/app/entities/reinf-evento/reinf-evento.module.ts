import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GsfappelSharedModule } from 'app/shared';
import {
    ReinfEventoComponent,
    ReinfEventoDetailComponent,
    ReinfEventoUpdateComponent,
    ReinfEventoDeletePopupComponent,
    ReinfEventoDeleteDialogComponent,
    reinfEventoRoute,
    reinfEventoPopupRoute
} from './';

const ENTITY_STATES = [...reinfEventoRoute, ...reinfEventoPopupRoute];

@NgModule({
    imports: [GsfappelSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReinfEventoComponent,
        ReinfEventoDetailComponent,
        ReinfEventoUpdateComponent,
        ReinfEventoDeleteDialogComponent,
        ReinfEventoDeletePopupComponent
    ],
    entryComponents: [ReinfEventoComponent, ReinfEventoUpdateComponent, ReinfEventoDeleteDialogComponent, ReinfEventoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfappelReinfEventoModule {}
