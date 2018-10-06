import { Moment } from 'moment';
import { IEmpresa } from 'app/shared/model//empresa.model';
import { IReinfStatusControle } from 'app/shared/model//reinf-status-controle.model';

export interface IReinfControle {
    id?: number;
    dtIni?: Moment;
    dtFim?: Moment;
    tpAmbiente?: string;
    empresa?: IEmpresa;
    status?: IReinfStatusControle;
}

export class ReinfControle implements IReinfControle {
    constructor(
        public id?: number,
        public dtIni?: Moment,
        public dtFim?: Moment,
        public tpAmbiente?: string,
        public empresa?: IEmpresa,
        public status?: IReinfStatusControle
    ) {}
}
