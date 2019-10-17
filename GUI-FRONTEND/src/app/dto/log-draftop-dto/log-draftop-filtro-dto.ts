import {Empresa} from "../../model/empresa";
import {CodigoeventosResponseDto} from "../systemparameter-dto/codigoeventos-response.dto";

export class LogDraftopFiltroDto {
  company : Empresa;
  idDraftOp : number;
  idDraft : number;
  eventCode : CodigoeventosResponseDto;
  sinceDate : string;
  toDate : string;
}
