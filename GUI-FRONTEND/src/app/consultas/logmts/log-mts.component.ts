import { Component, OnInit } from '@angular/core';
import {LogMtsFiltroDto} from "../../dto/log-mts-dto/log-mts-filtro-dto";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {PermisosService} from "../../security/permiso.service";
import {CANT_FILAS, LOG_MTS_STATES, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Message} from "primeng/primeng";
import {Empresa} from "../../model/empresa";
import {LogMtsService} from "./log-mts.service";
import {LogMtsDto} from "../../dto/log-mts-dto/log-mts-dto";
import {LogMtsRequestFiltroDto} from "../../dto/log-mts-dto/log-mts-request-filtro-dto";
import {DateUtilService} from "../../utils/date-util.service";
import {saveAs} from 'file-saver';
import {DatePipe} from "@angular/common";
import {UtilService} from "../../utils/util.service";

@Component({
  selector: 'app-log-mts',
  templateUrl: './log-mts.component.html',
  styleUrls: ['./log-mts.component.css']
})
export class LogMtsComponent implements OnInit {

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

  filtroBusqueda: LogMtsFiltroDto;
  listaEmpresa: Empresa[];
  logMtsDto: LogMtsDto;
  estadoList = LOG_MTS_STATES;

  constructor( private _empresaService : EmpresaService, private _permisoService : PermisosService, private _logMtsService : LogMtsService,
               private datepipe: DatePipe, private _utilService : UtilService
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new LogMtsFiltroDto();
    this.isSuperCompanyService();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logMtsService.getLogMtsByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logMtsDto = res.data.body.logmts;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logMtsDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
            this.loading = false;
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
      this._logMtsService.getLogMtsByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logMtsDto = res.data.body.logmts;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logMtsDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
            this.loading = false;

          });
    }
  }

  cargarCriterio(): LogMtsRequestFiltroDto {
    let logMtsRequestFiltroDto = new LogMtsRequestFiltroDto();
    if(this.filtroBusqueda) {
      if(this.filtroBusqueda.company) {
        logMtsRequestFiltroDto.idcompanyPk = this.filtroBusqueda.company.idcompanyPk;
      }
      if(this.filtroBusqueda.emailChr) {
        logMtsRequestFiltroDto.emailChr = this.filtroBusqueda.emailChr;
      }
      if(this.filtroBusqueda.phonenumberChr) {
        logMtsRequestFiltroDto.phonenumberChr = this.filtroBusqueda.phonenumberChr;
      }
      if(this.filtroBusqueda.subscriberciChr) {
        logMtsRequestFiltroDto.subscriberciChr = this.filtroBusqueda.subscriberciChr;
      }
      if(this.filtroBusqueda.stateChr) {
        logMtsRequestFiltroDto.stateChr = this.filtroBusqueda.stateChr;
      }
      logMtsRequestFiltroDto.fechaDesde =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.fechaDesde);
      logMtsRequestFiltroDto.fechaHasta =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.fechaHasta);
    }
    return logMtsRequestFiltroDto;
  }
  reset() {
    this.filtroBusqueda = new LogMtsFiltroDto();
    this.logMtsDto = null;
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
    return this._empresaService.getAllEmpresas().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaEmpresa = res.data.body.empresas;
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showMessage('error',error, MESSAGEERROR);
      });
  }
  showMessage(severity: string, detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: severity, summary: mensaje, detail: detail});
  }

  generarCsv(){
    this.loading = true;
    this._logMtsService.getReportCsv(this.cargarCriterio(),'','').subscribe(
      (response : any) =>{
        console.log(response);
        const file = new Blob([response.blob()], { type: 'text/csv;charset=utf-8' });
        const fileName = this._utilService.getFileNameFromResponse(response);
        saveAs(file, fileName);
        this.loading = false;
      },error => {
        console.log(error);
        this.loading = false;
      }
    )
  }


  generarExcel(){
    this.loading = true;
    this._logMtsService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
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

  generarPdf(){
    this.loading = true;
    this._logMtsService.getReportPdf(this.cargarCriterio(),'','').subscribe(
      (response : any) =>{
        const file = new Blob([response.blob()], {type:'application/pdf'});
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
