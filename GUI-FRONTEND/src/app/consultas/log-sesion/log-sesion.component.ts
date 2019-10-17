import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {CANT_FILAS, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {LogSesionFiltroDto} from "../../dto/log-sesion-dto/log-sesion-filtro-dto";
import {LogSesionDto} from "../../dto/log-sesion-dto/log-sesion-dto";
import {LogSesionService} from "./log-sesion.service";
import {saveAs} from "file-saver";
import {UtilService} from "../../utils/util.service";
import {DateUtilService} from "../../utils/date-util.service";

@Component({
  selector: 'app-log-sesion',
  templateUrl: './log-sesion.component.html',
  styleUrls: ['./log-sesion.component.css']
})
export class LogSesionComponent implements OnInit {


//spinner
  loading = false;

  //DataTable
  totalRecords = 0;
  size = 10;
  page = 1;
  direction = '';
  properties = '';

  //Objetos Filtrados
  // Message
  msgs: Message[] = [];

  filtroBusqueda: LogSesionFiltroDto;
  logSesionDto: LogSesionDto[];
  constructor( private _logSesionService : LogSesionService, private _utilService : UtilService,
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new LogSesionFiltroDto();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logSesionService.getLogSesionByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logSesionDto = res.data.body.logsession;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logSesionDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
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
      this._logSesionService.getLogSesionByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logSesionDto = res.data.body.logsession;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logSesionDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }

  cargarCriterio(): LogSesionFiltroDto {
    let criterio = new LogSesionFiltroDto();
    criterio.toDate = DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
    criterio.sinceDate = DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
    criterio.userNameChr = this.filtroBusqueda.userNameChr;
    return criterio;

  }
  reset() {
    this.filtroBusqueda = new LogSesionFiltroDto();
    this.logSesionDto = null;
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


  showMessage(severity: string, detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: severity, summary: mensaje, detail: detail});
  }
  generarExcel(){
    this.loading = true;
    this._logSesionService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
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
