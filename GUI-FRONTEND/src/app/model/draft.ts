import {Usuario} from "./usuario";
import {Workflow} from "./workflow";
import {Empresa} from "./empresa";
import {TipoOrdenPago} from "./tipo-orden-pago";
import {Beneficiario} from "./beneficiario";

export class Draft {
  iddraftPk: number;
  user: Usuario;
  workflow: Workflow;
  company: Empresa;
  paymentordertype: TipoOrdenPago;
  stateChr: string;
  descriptionChr: string;
  paiddateTim: string;
  creationdateTim: string;
  recurrentNum: boolean;
  generatedayNum: number;
  fromtemplateNum: boolean;
  template: any;
  templatenameChr: string;
  notifybenficiaryNum: boolean;
  notifysignerNum: boolean;
  notifygenNum: boolean;
  notifycancelNum: boolean;
  fromdateTim: string;
  todateTim: string;
  amount: number;
  constructor(){
    this.iddraftPk = null;
    this.user= null;
    this.workflow= null;
    this.company= null;
    this.paymentordertype= null;
    this.stateChr= null;
    this.descriptionChr= null;
    this.paiddateTim= null;
    this.creationdateTim= null;
    this.recurrentNum= false;
    this.generatedayNum= 1;
    this.fromtemplateNum= false;
    this.template= null;
    this.templatenameChr= null;
    this.notifybenficiaryNum= false;
    this.notifysignerNum= false;
    this.notifygenNum= false;
    this.notifycancelNum= false;
    this.amount = null;
  }
}
