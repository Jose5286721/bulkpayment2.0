import { Component, OnInit } from '@angular/core';
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {PermisosService} from "../../security/permiso.service";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA, PROCESS_PO_MANAGER} from "../../utils/gop-constantes";
import {Message} from "primeng/primeng";
import {Empresa} from "../../model/empresa";
import {DateUtilService} from "../../utils/date-util.service";
import {DatePipe} from "@angular/common";
import {LogDraftopFiltroDto} from "../../dto/log-draftop-dto/log-draftop-filtro-dto";
import {CodigoeventosResponseDto} from "../../dto/systemparameter-dto/codigoeventos-response.dto";
import {LogDraftopService} from "./log-draftop.service";
import {LogDraftopDto} from "../../dto/log-draftop-dto/log-draftop-dto";
import {CodigoeventosService} from "../../system/codigoeventos/codigoeventos.service";
import {LogDraftopRequestFiltroDto} from "../../dto/log-draftop-dto/log-draftop-request-filtro-dto";
import {CodigoeventosFiltroDto} from "../../dto/systemparameter-dto/codigoeventos-filtro.dto";
import {saveAs} from "file-saver";
import {UtilService} from "../../utils/util.service";

@Component({
  selector: 'app-log-draftop',
  templateUrl: './log-draftop.component.html',
  styleUrls: ['./log-draftop.component.css']
})
export class LogDraftopComponent implements OnInit {

  //spinner
  loading = true;

  //DataTable
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';

  //Objetos Filtrados
  filteredCompany: any[];
  // Message
  msgs: Message[] = [];

  filtroBusqueda: LogDraftopFiltroDto;
  listaEmpresa: Empresa[];
  listaEventCode : CodigoeventosResponseDto[];
  logDraftopDto: LogDraftopDto;
  constructor( private _empresaService : EmpresaService, private _permisoService : PermisosService, private _logDraftopService : LogDraftopService,
               private datepipe: DatePipe, private _codigoEventosService: CodigoeventosService, private _utilService: UtilService
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new LogDraftopFiltroDto();
    this.isSuperCompanyService();
    this.onBusqueda();
    this.obtenerEvenCodes();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logDraftopService.getLogGeneracionOpByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logDraftopDto = res.data.body.logdraftop;

              this.totalRecords = res.meta.totalCount;
            } else {
              this.logDraftopDto = null;
              this.totalRecords = 0;}

            this.loading = false;

          },
          error => {
            this.loading =false;
            console.log(error);
          });
    }
  }

  /**
   * Metodo que realiza una busqueda de empresas de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logDraftopService.getLogGeneracionOpByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logDraftopDto = res.data.body.logdraftop;
              this.totalRecords = res.meta.totalCount;
            } else {
              this.logDraftopDto = null;
              this.totalRecords = 0;
            }
            this.loading = false;

          },
          error => {
            this.loading = false;
            console.log(error);
          });
    }
  }

  cargarCriterio(): LogDraftopRequestFiltroDto {
    let logDraftopRequestFiltroDto = new LogDraftopRequestFiltroDto();
    if(this.filtroBusqueda) {
      if(this.filtroBusqueda.company) {
        logDraftopRequestFiltroDto.idCompany = this.filtroBusqueda.company.idcompanyPk;
      }
      if(this.filtroBusqueda.eventCode) {
        logDraftopRequestFiltroDto.idEventCode = this.filtroBusqueda.eventCode.ideventcodeNum;
      }
      logDraftopRequestFiltroDto.idDraft = this.filtroBusqueda.idDraft;
      logDraftopRequestFiltroDto.idDraftOp = this.filtroBusqueda.idDraftOp;

      logDraftopRequestFiltroDto.sinceDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
      logDraftopRequestFiltroDto.toDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
    }
    return logDraftopRequestFiltroDto;
  }
  reset() {
    this.filtroBusqueda = new LogDraftopFiltroDto();
    this.logDraftopDto = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusqueda != null) {
      return this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
    }
  }

  /**
   * Metodo que hace una peticion para obtener otra pagina de la tabla
   * @param event
   */
  paginate(event) {
    this.page = this.size = 0;
    this.page = event.page + 1;
    this.size = event.rows;
    this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
  }

  filterCompany(event) {
    this.filteredCompany = [];
    if(this.listaEmpresa){
      for(let i = 0; i < this.listaEmpresa.length; i++) {
        let empresa = this.listaEmpresa[i];
        if(empresa.companynameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredCompany.push(empresa);
        }
      }
    }
  }

  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }

  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
          if(res.data.body.superCompany){
            this.obtenerEmpresas();
          }
        } else {
          this._permisoService.superCompany = false;
        }
      },
      error => {
        console.log(error);
        this._permisoService.superCompany = false;
      });
  }

  /**
   * Obtiene lista de todas las empresas
   * @returns {Subscription}
   */
  obtenerEmpresas(){
    this.loading = true;
    return this._empresaService.getAllEmpresas().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaEmpresa = res.data.body.empresas;
        } else {
          console.log(res.errors.message.message);
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
        this.showMessage('error',error, MESSAGEERROR);
      });
  }
  obtenerEvenCodes(){
    this.loading = true;
    let criterios = new CodigoeventosFiltroDto();
    criterios.allByPage = false;
    criterios.idProceso = PROCESS_PO_MANAGER;
    return this._codigoEventosService.getCodigoEventosByCriPag(criterios,'','',PRIMERA_PAGINA, CANT_FILAS).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaEventCode = res.data.body.eventcodes;
        } else {
          console.log(res.errors.message.message);
        }
        this.loading = false;
      },

      error => {
        console.log(error);
        this.loading = false;
        this.showMessage('error',error, MESSAGEERROR);
      });
  }
  showMessage(severity: string, detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: severity, summary: mensaje, detail: detail});
  }

  generarExcel(){
    this.loading = true;
    this._logDraftopService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
      (response : any) =>{
        const file = new Blob([response.blob()], {type:'application/vnd.ms-excel'});
        const fileName = this._utilService.getFileNameFromResponse(response);
        saveAs(file, fileName);
        this.loading = false;
      },error => {
        console.log(error);
        this.loading = false;
      }
    )
  }


}
