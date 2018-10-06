import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GsfappelReinfControleModule } from './reinf-controle/reinf-controle.module';
import { GsfappelEmpresaModule } from './empresa/empresa.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GsfappelReinfControleModule,
        GsfappelEmpresaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GsfappelEntityModule {}
