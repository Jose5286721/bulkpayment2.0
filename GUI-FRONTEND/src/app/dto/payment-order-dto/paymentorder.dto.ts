export class PaymentOrderResponseDto {
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
  creationhour:string;
  updatedatehour:string;
}
