import {Proceso} from "../../model/Proceso";

export class CodigoeventosFiltroDto{
  idProceso:number;
  proceso:Proceso;
  allByPage:boolean;
  evento:number;
  constructor() {
  }
}
