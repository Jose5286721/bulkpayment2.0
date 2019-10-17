export class CompanyInfoDTO {
  totalPayment: string;
  totalAmount: string;
  lastPaymentDate : Date;

  constructor(){
    this.totalPayment = 'Sin datos';
    this.totalAmount = 'Sin datos';
  }
}
