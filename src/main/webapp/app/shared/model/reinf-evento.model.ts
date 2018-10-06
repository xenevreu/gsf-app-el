export interface IReinfEvento {
    id?: number;
    codEvento?: string;
    descEvento?: string;
}

export class ReinfEvento implements IReinfEvento {
    constructor(public id?: number, public codEvento?: string, public descEvento?: string) {}
}
