export class EmpresaShortDto {
  idcompanyPk: number;
  companynameChr:string;

  constructor(idcompanyPk: number, companynameChr:string ) {
    this.idcompanyPk=idcompanyPk;
    this.companynameChr = companynameChr;
  }
}

