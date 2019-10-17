import {Empresa} from "../../model/empresa";

export class LogMtsFiltroDto {
  subscriberciChr: string;
  phonenumberChr: string;
  fechaDesde: string;
  fechaHasta: string;
  emailChr: string;
  company: Empresa;
  stateChr: string;

}
