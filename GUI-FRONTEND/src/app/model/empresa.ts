export class Empresa{
  idcompanyPk: number;
  companynameChr: string;
  creationdateTim: any;
  descriptionChr: string;
  stateChr: string;
  contactnameChr: string;
  emailChr: string;
  phonenumberChr: string;
  lastopdateTim: any;
  trxdaycountNum: number;
  trxmonthcountNum: number;
  trxtotalcountNum: number;
  percentcommissionNum: number;
  mtsusrChr: string;
  mtsbandChr: string;
  mtsrolbindChr: string;
  mtspasswordChr: string;
  trxdaylimitNum: number;
  trxmonthlimitNum: number;
  trxtotallimitNum: number;
  priority:number;

  constructor() {
    this.trxdaylimitNum=0;
    this.trxmonthlimitNum=0;
    this.trxtotallimitNum=0;
    this.priority=4;
  }
}
