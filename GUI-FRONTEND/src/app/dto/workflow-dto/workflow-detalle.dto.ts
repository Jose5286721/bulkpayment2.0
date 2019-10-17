import {ApprovalDto} from "./workflow-edit-approvals";

export class WorkflowDetalleDto{
  userId:number;
  workflowId:number;
  approvals: ApprovalDto[] ;
  step:number;
  constructor() {
    this.approvals = [];
  }
}
