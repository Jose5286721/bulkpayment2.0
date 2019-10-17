export class WorkflowListRequestDto{
  stateChr: string;
  workflownameChr: string;
  descriptionChr: string;
  constructor() {
    this.stateChr= 'AC';
  }
}
