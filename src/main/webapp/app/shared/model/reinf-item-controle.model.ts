import { IReinfEvento } from 'app/shared/model//reinf-evento.model';
import { IReinfStatusControle } from 'app/shared/model//reinf-status-controle.model';
import { IReinfControle } from 'app/shared/model//reinf-controle.model';

export interface IReinfItemControle {
    id?: number;
    evento?: IReinfEvento;
    status?: IReinfStatusControle;
    controle?: IReinfControle;
}

export class ReinfItemControle implements IReinfItemControle {
    constructor(public id?: number, public evento?: IReinfEvento, public status?: IReinfStatusControle, public controle?: IReinfControle) {}
}
