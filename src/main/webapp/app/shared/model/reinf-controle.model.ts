import { Moment } from 'moment';
import { IReinfItemControle } from 'app/shared/model//reinf-item-controle.model';
import { IEmpresa } from 'app/shared/model//empresa.model';
import { IReinfStatusControle } from 'app/shared/model//reinf-status-controle.model';

export interface IReinfControle {
    id?: number;
    dtIni?: Moment;
    dtFim?: Moment;
    tpAmbiente?: string;
    itens?: IReinfItemControle[];
    empresa?: IEmpresa;
    status?: IReinfStatusControle;
}

export class ReinfControle implements IReinfControle {
    constructor(
        public id?: number,
        public dtIni?: Moment,
        public dtFim?: Moment,
        public tpAmbiente?: string,
        public itens?: IReinfItemControle[],
        public empresa?: IEmpresa,
        public status?: IReinfStatusControle
    ) {}
}
