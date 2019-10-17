import { Empresa } from "../../model/empresa";

export class UsuarioRequestDto{
  id: number;
  nombre: string;
  apellido: string;
  empresa: Empresa;
  email: string;
  password: string;
  password_2: string;
  cargo: string;
  telefono: string;
  firmar_via_sms: boolean;
  pin_sms: number;
  notificar_via_sms: boolean;
  notificar_via_email: boolean;
  ver_saldo_empresa: boolean;
  listRol: number[];

  constructor() {
    this.listRol = [];
  }
}
