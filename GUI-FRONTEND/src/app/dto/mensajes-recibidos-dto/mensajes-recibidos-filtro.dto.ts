import {Empresa} from "../../model/empresa";

export class MensajesRecibidosFiltroDto {
  sourcenumberChr: string;
  company: Empresa;
  stateChr: string;
  sinceDate: string;
  toDate: string;
}
