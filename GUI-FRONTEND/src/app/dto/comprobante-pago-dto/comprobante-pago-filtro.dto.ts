import {Empresa} from "../../model/empresa";

export class ComprobantePagoFiltroDto {
  paymentOrderId : number;
  beneficiaryCI: string;
  phonenumberChr: string;
  company: Empresa;
  sinceDate: string;
  toDate:string;
}
