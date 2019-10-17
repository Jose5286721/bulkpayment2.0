import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {PermisosService} from "../../security/permiso.service";
import {DateUtilService} from "../../utils/date-util.service";
import {CANT_FILAS, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {LogAuditDto} from "../../dto/log-audit-dto/log-audit-dto";
import {LogAuditService} from "./log-audit.service";
import {LogAuditFiltroDto} from "../../dto/log-audit-dto/log-audit-filtro-dto";
import {saveAs} from "file-saver";
import {UtilService} from "../../utils/util.service";

@Component({
  selector: 'app-log-audit',
  templateUrl: './log-audit.component.html',
  styleUrls: ['./log-audit.component.css']
})
export class LogAuditComponent implements OnInit {

  //spinner
  loading = true;

  //DataTable
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';

  // Message
  msgs: Message[] = [];

  filtroBusqueda: LogAuditFiltroDto;
  auditDto: LogAuditDto;
  accesoList: any[] = [{label: "Creacion", value: "audit_persist"}, {label: "Modificacion", value: "audit_update"},
    {label: "Eliminacion", value: "audit_delete"}];
  constructor( private _permisoService : PermisosService, private _logAuditService : LogAuditService,
               private _utilService : UtilService
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new LogAuditFiltroDto();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logAuditService.getLogAuditByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.auditDto = res.data.body.auditoria;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.auditDto = null;
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
      this._logAuditService.getLogAuditByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.auditDto = res.data.body.auditoria;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.auditDto = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }

  cargarCriterio(): LogAuditFiltroDto {
    let criterio = new LogAuditFiltroDto();
    if(this.filtroBusqueda.toDate || this.filtroBusqueda.sinceDate || this.filtroBusqueda.accesstypeChr || this.filtroBusqueda.ipChr || this.filtroBusqueda.usernameChr ){
      criterio.sinceDate = DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
      criterio.toDate = DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
      criterio.usernameChr = this.filtroBusqueda.usernameChr;
      criterio.ipChr = this.filtroBusqueda.ipChr;
      criterio.accesstypeChr = this.filtroBusqueda.accesstypeChr;
      criterio.allByPage = false;
    }else
      criterio.allByPage = true;
    return criterio;
  }
  reset() {
    this.filtroBusqueda = new LogAuditFiltroDto();
    this.auditDto = null;
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
    this._logAuditService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
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
    this._logAuditService.getReportPdf(this.cargarCriterio(),'','').subscribe(
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
