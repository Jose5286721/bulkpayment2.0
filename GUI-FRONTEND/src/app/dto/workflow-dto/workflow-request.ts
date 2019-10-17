import {WorkflowDetalleDto} from "./workflow-detalle.dto";
import {Empresa} from "../../model/empresa";
import {UsuarioListResponseDto} from "../usuario-dto/usuario-list-response.dto";

export class WorkflowRequestDto {
  idworkflowPk:number;
  idcompany:number;
  company:Empresa;
  companyNameChr: string;
  workflownameChr:string;
  descriptionChr:string;
  stateChr:string;
  walletChr:string;
  listaFirmantes:UsuarioListResponseDto[];
  workflowDetalleDto:WorkflowDetalleDto[];
  firmantes:number[];
  isLoggedSuperCompany: boolean;

    constructor() {
      this.workflowDetalleDto = [];
      this.listaFirmantes=[];
      this.workflowDetalleDto.push(new WorkflowDetalleDto());
    }
  }

