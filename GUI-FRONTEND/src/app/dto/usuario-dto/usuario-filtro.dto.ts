import {Empresa} from "../../model/empresa";

export class UsuarioFiltroDto{

  emailChr: string;
  usernameChr: string;
  stateChr: string;
  company: Empresa;

  constructor() {
    this.stateChr = 'AC';
  }
}
