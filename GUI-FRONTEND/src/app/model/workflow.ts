import {DetalleWorkflow} from "./detalle-workflow";
import {Empresa} from "./empresa";

export class Workflow{
  idworkflowPk:number;
  idcompany:number;
  company: Empresa;
  workflownameChr: string;
  descriptionChr:string;
  stateChr:string;
  walletChr:string;
  detalle: DetalleWorkflow[];
  isLoggedSuperCompany: boolean;
  constructor() {}
}
