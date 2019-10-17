import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {TipoOrdenPagoFiltroDto} from "../../dto/tipo-orden-pago-dto/tipo-orden-pago-filtro.dto";
import {Empresa} from "../../model/empresa";
import {TipoOrdenPago} from "../../model/tipo-orden-pago";
import {TipoOrdenPagoService} from "./tipo-orden-pago.service";
import {CANT_FILAS, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Router, RouterModule} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {PermisosService} from "../../security/permiso.service";

@Component({
  selector: 'app-tipo-orden-pago-list',
  templateUrl: './tipo-orden-pago-list.component.html',
  styleUrls: ['./tipo-orden-pago-list.component.css']
})
export class TipoOrdenPagoListComponent implements OnInit {

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

  //
  filtroBusqueda: TipoOrdenPagoFiltroDto;
  tipoOrdenPagoDt: TipoOrdenPago[] = [];

  constructor(private _tipoOrdenPagoService: TipoOrdenPagoService, private _router: Router, private _messageUtilService: MessageUtilService,
              private _permisoService: PermisosService) { }

  ngOnInit() {
    this.loading = false;
    this.filtroBusqueda = new TipoOrdenPagoFiltroDto();
    this.onBusqueda();
    this.mostrarMensaje();
  }

  mostrarMensaje(){
    if(this._messageUtilService.getMsgs()){
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  /**
   * Obtiene la lista de tipos de ordenes de pago
   */
  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._tipoOrdenPagoService.getTipoOrdenPagoByCriPag(this.filtroBusqueda, '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.tipoOrdenPagoDt = res.data.body.tipoOrdenPago;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.tipoOrdenPagoDt = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }


  reset(){
    this.filtroBusqueda = new TipoOrdenPagoFiltroDto();
    this.tipoOrdenPagoDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  navigatePaymentOrderTypeEdit(t: TipoOrdenPago){
    if(t != null && t.idorderpaymenttypePk != null){
      this._router.navigate(['/paymentOrderTypeEdit/paymentOrderTypeId/', t.idorderpaymenttypePk]);
    }else{
      this._router.navigate(['/paymentOrderTypeEdit']);
    }
  }

  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusqueda != null) {
      return this.onBusquedaByCriPag(this.filtroBusqueda, this.direction, this.properties, this.page.toString(), this.size.toString());
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
    this.onBusquedaByCriPag(this.filtroBusqueda, this.direction, this.properties, this.page.toString(), this.size.toString());
  }

  /**
   * Metodo que realiza una busqueda de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._tipoOrdenPagoService.getTipoOrdenPagoByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.tipoOrdenPagoDt = res.data.body.tipoOrdenPago;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.tipoOrdenPagoDt = null;
          this.totalRecords = 0;
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  modificarTipoOrdePagoPerm(): boolean {
    return this._permisoService.modificarTipoOrdenPago;
  }


}
