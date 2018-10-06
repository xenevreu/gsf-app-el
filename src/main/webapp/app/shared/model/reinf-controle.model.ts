export interface IReinfControle {
    id?: number;
    empresa?: string;
}

export class ReinfControle implements IReinfControle {
    constructor(public id?: number, public empresa?: string) {}
}
