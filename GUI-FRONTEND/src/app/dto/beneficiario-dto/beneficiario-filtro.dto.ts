import {Empresa} from '../../model/empresa';

export class BeneficiarioFiltroDto {
  empresa: Empresa;
  phonenumberChr: string;
  stateChr: string;
  constructor() {
    this.phonenumberChr = null;
    this.empresa = null;
    this.stateChr = 'AC';
  }
}
