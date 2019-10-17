import {Empresa} from "../../model/empresa";

export class LogMessageFiltroDto {
  messageId: number;
  paymentOrderId: number;
  notificationEventChr: string;
  company: Empresa;
  stateChr: string;
  sinceDate: string;
  toDate: string;


}
