import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {DateUtilService} from "../../utils/date-util.service";
import {DatePipe} from "@angular/common";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {saveAs} from "file-saver";
import {ComprobantePagoDto} from "../../dto/comprobante-pago-dto/comprobante-pago.dto";
import {ComprobantePagoFiltroDto} from "../../dto/comprobante-pago-dto/comprobante-pago-filtro.dto";
import {ComprobantePagoService} from "./comprobante-pago.service";
import {ComprobantePagoRequestFiltroDto} from "../../dto/comprobante-pago-dto/comprobante-pago-request-filtro.dto";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {PermisosService} from "../../security/permiso.service";
import {Empresa} from "../../model/empresa";
import {UtilService} from "../../utils/util.service";

@Component({
  selector: 'app-comprobante-pago',
  templateUrl: './comprobante-pago.component.html',
  styleUrls: ['./comprobante-pago.component.css']
})
export class ComprobantePagoComponent implements OnInit {

//spinner
  loading = true;

  //DataTable
  totalRecords = 0;
  size = 10;
  page = 1;
  direction = '';
  properties = '';

  //Objetos Filtrados
  filteredCompany: any;
  // Message
  msgs: Message[] = [];

  listaEmpresa: Empresa[];
  filtroBusqueda: ComprobantePagoFiltroDto;
  comprobantePagoDto: ComprobantePagoDto[];
  constructor( private _empresaService : EmpresaService, private _permisoService : PermisosService,private _comprobantePagoService : ComprobantePagoService,
               private _utilService: UtilService
  ) { }

  ngOnInit() {
    this.filtroBusqueda = new ComprobantePagoFiltroDto();
    this.isSuperCompanyService();
    this.onBusqueda();
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._comprobantePagoService.getComprobantePagoByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.comprobantePagoDto = res.data.body.paymentVouchers;
              this.totalRecords = res.meta.totalCount;
            } else {
              this.comprobantePagoDto = null;
              this.totalRecords = 0;
            }
            this.loading = false;
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
      this._comprobantePagoService.getComprobantePagoByCriPag(criterio, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.comprobantePagoDto = res.data.body.paymentVouchers;

              this.totalRecords = res.meta.totalCount;
            } else {
              this.comprobantePagoDto = null;
              this.totalRecords = 0;
            }
            this.loading = false;
          },
          error => {
            console.log(error);
            this.loading = false;
          });
    }
  }

  cargarCriterio(): ComprobantePagoRequestFiltroDto {
    let request = new ComprobantePagoRequestFiltroDto();
    if(this.filtroBusqueda.company)
      request.companyId = this.filtroBusqueda.company.idcompanyPk;
    request.beneficiaryCI = this.filtroBusqueda.beneficiaryCI;
    request.paymentOrderId = this.filtroBusqueda.paymentOrderId;
    request.phonenumberChr = this.filtroBusqueda.phonenumberChr;
    request.sinceDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.sinceDate);
    request.toDate =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.toDate);
    return request;
  }
  reset() {
    this.filtroBusqueda = new ComprobantePagoFiltroDto();
    this.comprobantePagoDto = null;
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
        this.loading = false;
        console.log(error);
        this.showMessage('error',error, MESSAGEERROR);
      });
  }


  showMessage(severity: string, detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: severity, summary: mensaje, detail: detail});
  }

  generarPdf(){
    this.loading = true;
    this._comprobantePagoService.getReportPdf(this.cargarCriterio(),this.direction,this.properties).subscribe(
      (response : any) =>{
        const file = new Blob([response.blob()], {type:'application/pdf'});
        const fileName = this._utilService.getFileNameFromResponse(response);
        saveAs(file, fileName);
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });

  }

}
