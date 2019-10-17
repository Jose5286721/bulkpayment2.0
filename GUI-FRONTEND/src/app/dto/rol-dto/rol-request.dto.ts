export class RolRequestDto{
  idrolPk: number;
  rolnameChr: string;
  companyId: number;
  descriptionChr: string;
  defaultrolNum: boolean;
  listPermisos: Map<string,number>;
  stateNum: boolean;

  constructor() {
    this.listPermisos = new Map<string, number>();
  }
}
