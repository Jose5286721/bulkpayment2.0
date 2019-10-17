import {Beneficiario} from "../../model/beneficiario";

export class DraftRequest {
  iddraftPk: number;
  idWorkflow: number;
  idCompany: number;
  idPaymentordertype: number;
  stateChr: string;
  descriptionChr: string;
  paiddateTim: string;
  recurrentNum: boolean;
  fromdateTim: string;
  todateTim: string;
  notifybenficiaryNum: boolean;
  notifysignerNum: boolean;
  notifygenNum: boolean;
  notifycancelNum: boolean;
  beneficiariesToAdd: Beneficiario[];
  beneficiariesToDelete: Beneficiario[];
  generateDayNum: number;
  fastDraft : boolean;
  constructor(){

  }
}
