import {DetalleWorkflow} from "./detalle-workflow"
import {Empresa} from "./empresa";
export class Aprovals {
  idapprovalPk: number;
  workflowdet: number;
  company:Empresa;
  signature: any;
  smssignedNum:boolean;
  creationdateTim: string;
  signedNum:boolean;
  signdateTim:string;
}
