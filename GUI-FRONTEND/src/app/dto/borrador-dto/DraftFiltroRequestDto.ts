
export class DraftFiltroRequestDto{

  iddraftPk: number;
  recurrentNum: Boolean;
  idcompanyPk: number;
  idworkflowPk: number;
  idorderpaymenttypePk: number;
  creationdateTimStart: string;
  creationdateTimEnd: string;
  descriptionChr: string;

  constructor() {
    this.iddraftPk = null;
    this.recurrentNum = false;
    this.idcompanyPk = null;
    this.idworkflowPk = null;
    this.idorderpaymenttypePk = null;
    this.creationdateTimEnd = null;
    this.creationdateTimStart = null;
    this.descriptionChr = null;
  }
}
