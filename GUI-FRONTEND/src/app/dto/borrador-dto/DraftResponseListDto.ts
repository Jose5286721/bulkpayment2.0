export class DraftResponseListDto {
  iddraftPk: number;
  user: string;
  workflow: string;
  company: string;
  paymentordertype: string;
  stateChr: string;
  descriptionChr: string;
  paiddateTim: string;
  creationdateTim: string;
  recurrentNum: boolean;
  fromdateTim: string;
  todateTim: string;
  generatedayNum:number;
  constructor(){

  }
}
