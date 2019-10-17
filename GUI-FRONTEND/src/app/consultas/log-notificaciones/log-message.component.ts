import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {LogMessageService} from "./log-message.service";
import {PermisosService} from "../../security/permiso.service";
import {Empresa} from "../../model/empresa";
import {DateUtilService} from "../../utils/date-util.service";
import {LogMessageFiltroDto} from "../../dto/log-notification-dto/log-message-filtro-dto";
import {LogMessageDto} from "../../dto/log-notification-dto/log-message-dto";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {CANT_FILAS, MESSAGE_STATES, MESSAGEERROR, NOTIFICATIONS, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {LogMessageRequestFiltroDto} from "../../dto/log-notification-dto/log-message-request-filtro-dto";
import {saveAs} from "file-saver";
import {UtilService} from "../../utils/util.service";

@Component({
  selector: 'app-log-notificaciones',
  templateUrl: './log-message.component.html',
  styleUrls: ['./log-message.component.css']
})
export class LogMessageComponent implements OnInit {

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

  filtroBusqueda: LogMessageFiltroDto;
  listaEmpresa: Empresa[];
  logNotifDto: LogMessageDto[];
  notificationList = NOTIFICATIONS;
  estadoList = MESSAGE_STATES;
  constructor(private _empresaService : EmpresaService, private _permisoService : PermisosService,
              private _logNotifService : LogMessageService,
              private _utilService: UtilService
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new LogMessageFiltroDto();
    this.isSuperCompanyService();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logNotifService.getLogNotifByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logNotifDto = res.data.body.logmessage;
              this.mapValues();
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logNotifDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }
  mapValues(){
    if(this.logNotifDto!=null)
      this.logNotifDto.forEach(value => {
        value.notificationeventChr = LogMessageComponent.objectContains(this.notificationList,value.notificationeventChr);
        value.stateChr = LogMessageComponent.objectContains(this.estadoList,value.stateChr);

      })
  }

  static objectContains( obj: any,term: string): string {
    for (let key in obj){
      if(obj[key].value.indexOf(term) != -1) return obj[key].label;
    }
    return null;
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
      this._logNotifService.getLogNotifByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logNotifDto = res.data.body.logmessage;
              this.mapValues();
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logNotifDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }

  cargarCriterio(): LogMessageRequestFiltroDto {
    let logMtsRequestFiltroDto = new LogMessageRequestFiltroDto();
    if(this.filtroBusqueda) {
      if(this.filtroBusqueda.company) {
        logMtsRequestFiltroDto.companyId = this.filtroBusqueda.company.idcompanyPk;
      }
      logMtsRequestFiltroDto.messageId = this.filtroBusqueda.messageId;
      logMtsRequestFiltroDto.notificationEventChr = this.filtroBusqueda.notificationEventChr;
      logMtsRequestFiltroDto.paymentOrderId = this.filtroBusqueda.paymentOrderId;
      logMtsRequestFiltroDto.stateChr = this.filtroBusqueda.stateChr;
      logMtsRequestFiltroDto.sinceDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
      logMtsRequestFiltroDto.toDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
    }
    return logMtsRequestFiltroDto;
  }
  reset() {
    this.filtroBusqueda = new LogMessageFiltroDto();
    this.logNotifDto = null;
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
        this.loading = false;
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

  generarExcel(){
    this.loading = true;
    this._logNotifService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
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
