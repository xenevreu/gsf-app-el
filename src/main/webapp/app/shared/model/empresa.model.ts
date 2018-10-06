import { Moment } from 'moment';
import { IEmpresa } from 'app/shared/model//empresa.model';

export interface IEmpresa {
    id?: number;
    nomeEmpresa?: string;
    dtInclusao?: Moment;
    usuInclusao?: string;
    dtUltAlteracao?: Moment;
    usuUltAlteracao?: Moment;
    cnpj?: string;
    siglaLa?: string;
    conteudoCodigo?: string;
    idUf?: number;
    nomeFantasia?: string;
    idLocalCentral?: number;
    dsLogo?: string;
    caminhoLog?: string;
    empresaPai?: IEmpresa;
}

export class Empresa implements IEmpresa {
    constructor(
        public id?: number,
        public nomeEmpresa?: string,
        public dtInclusao?: Moment,
        public usuInclusao?: string,
        public dtUltAlteracao?: Moment,
        public usuUltAlteracao?: Moment,
        public cnpj?: string,
        public siglaLa?: string,
        public conteudoCodigo?: string,
        public idUf?: number,
        public nomeFantasia?: string,
        public idLocalCentral?: number,
        public dsLogo?: string,
        public caminhoLog?: string,
        public empresaPai?: IEmpresa
    ) {}
}
