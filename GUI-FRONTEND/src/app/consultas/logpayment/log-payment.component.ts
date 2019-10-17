import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {LogPaymentFiltroDto} from "../../dto/log-payment-dto/log-payment-filtro-dto";
import {Empresa} from "../../model/empresa";
import {LogPaymentDto} from "../../dto/log-payment-dto/log-payment-dto";
import {LogPaymentRequestFiltroDto} from "../../dto/log-payment-dto/log-payment-request-filtro-dto";
import {CANT_FILAS, LOG_PAYMENT_STATES, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {PermisosService} from "../../security/permiso.service";
import {DateUtilService} from "../../utils/date-util.service";
import {LogPaymentService} from "./log-payment.service";
import {UtilService} from "../../utils/util.service";
import {saveAs} from "file-saver";

@Component({
  selector: 'app-log-payment',
  templateUrl: './log-payment.component.html',
  styleUrls: ['./log-payment.component.css']
})
export class LogPaymentComponent implements OnInit {

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

  filtroBusqueda: LogPaymentFiltroDto;
  listaEmpresa: Empresa[];
  logPaymentDto: LogPaymentDto[];
  estadoList = LOG_PAYMENT_STATES;
  constructor( private _empresaService : EmpresaService, private _permisoService : PermisosService,
               private _logPaymentService : LogPaymentService,
               private _utilService: UtilService
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new LogPaymentFiltroDto();
    this.isSuperCompanyService();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._logPaymentService.getLogPaymentByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logPaymentDto = res.data.body.logpayment;
              this.mapValues();
              this.totalRecords = res.meta.totalCount;
            } else {
              this.logPaymentDto = null;
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
      this._logPaymentService.getLogPaymentByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.logPaymentDto = res.data.body.logpayment;
              this.mapValues();
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.logPaymentDto = null;
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

  mapValues(){
    if(this.logPaymentDto!=null)
      this.logPaymentDto.forEach(value => {
        value.stateChr = UtilService.objectContains(this.estadoList,value.stateChr);
      })


  }



  cargarCriterio(): LogPaymentRequestFiltroDto {
    let requestFiltroDto = new LogPaymentRequestFiltroDto();
    if(this.filtroBusqueda) {
      if(this.filtroBusqueda.company) {
        requestFiltroDto.idcompanyPk = this.filtroBusqueda.company.idcompanyPk;
      }
      requestFiltroDto.idpaymentNum = this.filtroBusqueda.idpaymentNum;
      requestFiltroDto.idpaymentorderPk = this.filtroBusqueda.idpaymentorderPk;
      requestFiltroDto.stateChr = this.filtroBusqueda.stateChr;
      requestFiltroDto.sinceDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
      requestFiltroDto.toDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
    }
    return requestFiltroDto;
  }
  reset() {
    this.filtroBusqueda = new LogPaymentFiltroDto();
    this.logPaymentDto = null;
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

  generarExcel(){
    this.loading = true;
    this._logPaymentService.getReportXlsx(this.cargarCriterio(),'','').subscribe(
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
