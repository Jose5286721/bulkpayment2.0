import {Empresa} from "../../model/empresa";

export class LogPaymentFiltroDto {
  idpaymentNum: string;
  idpaymentorderPk: string;
  sinceDate: string;
  toDate: string;
  stateChr: string;
  company: Empresa;
}
