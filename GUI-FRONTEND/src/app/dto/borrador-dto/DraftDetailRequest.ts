export class DraftDetailRequest {

  iddraftPk: number;
  idbeneficiaryPk: number;
  amountNum: number;

  constructor(iddraftPk, idbeneficiaryPk, amountNum){
    this.idbeneficiaryPk = idbeneficiaryPk;
    this.iddraftPk = iddraftPk;
    this.amountNum = amountNum;
  }
}
