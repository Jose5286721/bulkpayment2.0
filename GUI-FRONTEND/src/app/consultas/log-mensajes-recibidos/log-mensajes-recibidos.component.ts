import { Component, OnInit } from '@angular/core';
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {PermisosService} from "../../security/permiso.service";
import {CANT_FILAS, MESSAGE_STATES, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Message} from "primeng/primeng";
import {Empresa} from "../../model/empresa";
import {LogMensajesRecibidosService} from "./log-mensajes-recibidos.service";
import {DateUtilService} from "../../utils/date-util.service";
import {MensajesRecibidosDto} from "../../dto/mensajes-recibidos-dto/mensajes-recibidos.dto";
import {MensajesRecibidosFiltroDto} from "../../dto/mensajes-recibidos-dto/mensajes-recibidos-filtro.dto";
import {MensajesRecibidosRequestFiltroDto} from "../../dto/mensajes-recibidos-dto/mensajes-recibidos-request-filtro.dto";
import {saveAs} from "file-saver";
import {UtilService} from "../../utils/util.service";

@Component({
  selector: 'app-log-mensajes-recibidos',
  templateUrl: './log-mensajes-recibidos.component.html',
  styleUrls: ['./log-mensajes-recibidos.component.css']
})
export class LogMensajesRecibidosComponent implements OnInit {

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

  filtroBusqueda: MensajesRecibidosFiltroDto;
  listaEmpresa: Empresa[];
  mensajesRecibidosDto: MensajesRecibidosDto;
  estadoList = MESSAGE_STATES;
  constructor(private _empresaService : EmpresaService, private _permisoService : PermisosService,
              private _logMensajesRecibidosService : LogMensajesRecibidosService,
              private _utilService: UtilService) { }

  ngOnInit() {
    this.filtroBusqueda = new MensajesRecibidosFiltroDto();
    this.isSuperCompanyService();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logMensajesRecibidosService.getLogMensajesRecibidosByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.mensajesRecibidosDto = res.data.body.smslogmessage;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.mensajesRecibidosDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            this.loading = false;
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
      this._logMensajesRecibidosService.getLogMensajesRecibidosByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.mensajesRecibidosDto = res.data.body.smslogmessage;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.mensajesRecibidosDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }

  cargarCriterio(): MensajesRecibidosRequestFiltroDto {
    let request = new MensajesRecibidosRequestFiltroDto();
    if(this.filtroBusqueda) {
      if(this.filtroBusqueda.company) {
        request.idCompany = this.filtroBusqueda.company.idcompanyPk;
      }
      request.sourcenumberChr = this.filtroBusqueda.sourcenumberChr;
      request.stateChr = this.filtroBusqueda.stateChr;
      request.sinceDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
      request.toDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
    }
    return request;
  }
  reset() {
    this.filtroBusqueda = new MensajesRecibidosFiltroDto();
    this.mensajesRecibidosDto = null;
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
    this._logMensajesRecibidosService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
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
