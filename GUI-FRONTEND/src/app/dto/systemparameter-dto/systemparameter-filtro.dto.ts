import {Proceso} from "../../model/Proceso";

export class SystemparameterFiltroDto{

  parametro:string;
  idProceso:number;
  proceso:Proceso;
  allByPage:boolean;
  valor:string
  constructor() {
  }
}
