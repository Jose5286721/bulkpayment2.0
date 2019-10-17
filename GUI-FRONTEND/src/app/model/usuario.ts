import { Empresa } from "./empresa";
import {Rol} from "./rol";
import {EmpresaShortDto} from "../dto/empresa-dto/empresa-short.dto";

export class Usuario {
  iduserPk: number;
  emailChr: string;
  usernameChr: string;
  userlastnameChr: string;
  userjobtitleChr: string;
  phonenumberChr: string;
  smssignNum: boolean;
  smspinChr: string;
  codigoequipohumanoNum: number;
  notifysmsNum: boolean;
  notifyemailNum: boolean;
  canviewcompanycreditNum: boolean;
  company: Empresa;
  companies: EmpresaShortDto[];
  stateChr: string;
  creationdateTim: string;
  rol: Rol;
  esFirmante:boolean;

  constructor() {
    this.stateChr ='AC';
    this.esFirmante=false;
  }
}
