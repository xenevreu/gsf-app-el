import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GsfappelReinfControleModule } from './reinf-controle/reinf-controle.module';
import { GsfappelEmpresaModule } from './empresa/empresa.module';
import { GsfappelReinfStatusControleModule } from './reinf-status-controle/reinf-status-controle.module';
import { GsfappelReinfItemControleModule } from './reinf-item-controle/reinf-item-controle.module';
import { GsfappelReinfEventoModule } from './reinf-evento/reinf-evento.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GsfappelReinfControleModule,
        GsfappelEmpresaModule,
        GsfappelReinfStatusControleModule,
        GsfappelReinfItemControleModule,
        GsfappelReinfEventoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfappelEntityModule {}
