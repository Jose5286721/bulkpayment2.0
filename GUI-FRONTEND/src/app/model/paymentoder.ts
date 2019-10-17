import {Empresa} from './empresa';

export class Paymentoder {
  idpaymentorderPk: number;
  idDraft: number;
  idWorkflow: number;
  nombreWorkflow: string;
  idCompany: number;
  nombreEmpresa: string;
  idPaymentordertype: number;
  nombreTipoOrdenPago: string;
  stateChr: string;
  descripcion: string;
  montoTotal: string;
  creationdateTim: string;
  updatedateTim: string;
  amountpaidChr:string;
  totalpaymentNum:number;
  paymentsuccessNum:number;
  paymenterrorNum:number;
  paymentpartsucNum:number;
  beneficiarieslength:number;
  signerslength:number;
  isCurrentUserTurn:boolean;
  isSigner:boolean;

  constructor(){
    this.idpaymentorderPk = null;
    this.idDraft= null;
    this.idWorkflow = null;
    this.nombreWorkflow= null;
    this.idCompany= null;
    this.nombreEmpresa= null;
    this.idPaymentordertype= null;
    this.nombreTipoOrdenPago = null;
    this.stateChr = null;
    this.descripcion= null;
    this.montoTotal= null;
    this.creationdateTim= null;
    this.updatedateTim= null;
    this.amountpaidChr= null;
    this.totalpaymentNum= null;
    this.paymentsuccessNum= null;
    this.paymenterrorNum= null;
    this.paymentpartsucNum= null;
    this.beneficiarieslength= null;
    this.signerslength= null;
    this.isCurrentUserTurn=false;
    this.isSigner=false;
  }
}
