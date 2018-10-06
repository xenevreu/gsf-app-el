export interface IReinfStatusControle {
    id?: number;
    codStatus?: string;
    descr?: string;
}

export class ReinfStatusControle implements IReinfStatusControle {
    constructor(public id?: number, public codStatus?: string, public descr?: string) {}
}
