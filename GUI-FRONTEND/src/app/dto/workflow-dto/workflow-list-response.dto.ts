import {Empresa} from "../../model/empresa";

export class WorkflowListResponseDto {
  idworkflowPk: number;
  company: Empresa;
  descriptionChr: string;
  workflownameChr: string;
  stateChr: string;
  companyNameChr:string;


  constructor() {

  }
}
