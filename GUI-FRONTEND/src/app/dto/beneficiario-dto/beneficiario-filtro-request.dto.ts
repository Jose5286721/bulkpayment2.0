export class BeneficiarioFiltroRequestDto {
  idCompany: number;
  phonenumberChr: string;
  stateChr: string;
  constructor() {
    this.phonenumberChr = null;
    this.idCompany = null;
    this.stateChr = 'AC';
  }
}
