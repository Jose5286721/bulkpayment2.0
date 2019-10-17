import {Empresa} from "../../model/empresa";
import {Workflow} from "../../model/workflow";
import {TipoOrdenPago} from "../../model/tipo-orden-pago";

export class DraftFiltroDto{

  iddraftPk: number;
  recurrentNum: Boolean;
  company: Empresa;
  workflow: Workflow;
  orderpaymenttype: TipoOrdenPago;
  descriptionChr: string;
  creationdateTimStart: string;
  creationdateTimEnd: string;

  constructor() {

  }
}
