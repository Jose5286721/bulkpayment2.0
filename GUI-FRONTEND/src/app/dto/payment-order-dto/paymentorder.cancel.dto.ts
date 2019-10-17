export class PaymentOrderCancelDto {
  idPaymentOrder:number;
  motivo:string;

  constructor() {
   this.idPaymentOrder=0;
   this.motivo="Cancelado Por Usuario";
  }
}
