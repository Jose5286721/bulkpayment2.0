
import {Proceso} from "../../model/Proceso";
import {ProcesoListResponseDto} from "./proceso-response.dto";

export class SystemparameterResponseDto{
  idProcess: number;
  parametro: string;
  proceso:ProcesoListResponseDto;
  valor: string;
  processnameChr:string;
  descripcion:string;
  constructor(){
  }
}
