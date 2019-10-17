
import {Proceso} from "../../model/Proceso";
import {ProcesoListResponseDto} from "./proceso-response.dto";

export class CodigoeventosResponseDto{
  idprocessPk: number;
  ideventcodeNum: number;
  proceso:ProcesoListResponseDto;
  descriptionChr: string;
  constructor(){
  }
}
