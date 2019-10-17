import {Empresa} from "./empresa";

export class Rol {
  idrolPk: number;
  rolnameChr: string;
  company: Empresa;
  descriptionChr: string;
  defaultrolNum: boolean;
  stateNum: boolean;

  constructor() {

  }
}
