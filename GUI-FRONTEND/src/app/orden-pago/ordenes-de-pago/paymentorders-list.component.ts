import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";

import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";
import {EmpresaService} from '../../administracion/empresa/empresa.service';
import {Empresa} from '../../model/empresa';
import {PaymentOrderService} from './ordenes-pago-service';
import {PaymentOrderFilterDto} from '../../dto/payment-order-dto/paymentorder.filtro.dto';
import {PaymentOrderResponseDto} from '../../dto/payment-order-dto/paymentorder.dto';
import {TipoOrdenPagoService} from '../tipo-orden-pago/tipo-orden-pago.service';
import {WorkflowService} from '../../administracion/workflow/workflow-service';
import {Workflow} from '../../model/workflow';
import {TipoOrdenPago} from '../../model/tipo-orden-pago';
import {PaymentorderRequestFiltroDto} from '../../dto/payment-order-dto/paymentorder-request.filtro.dto';
import {DateUtilService} from '../../utils/date-util.service';
import {PAYMENT_ORDER_STATES} from "../../utils/paymentorders-states";


@Component({
  selector: 'app-paymentorders-list',
  templateUrl: './paymentorders-list.component.html',
  styleUrls: ['./paymentorders-list.component.css']
})
export class PaymentordersListComponent implements OnInit {
  //Referencia a componentes
  @ViewChild('dt') dt: DataTable;

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
  filteredWorkflow: any[];
  filteredPaymentOrderType: any[];
  // Message
  msgs: Message[] = [];

  estadoList = PAYMENT_ORDER_STATES;

  filtroBusquedaPaymentOrder: PaymentOrderFilterDto;
  //listas
  listaEmpresa: Empresa[];
  paymentOrderDt: PaymentOrderResponseDto[] = [];
  listaWorkflow: Workflow[];
  listaTipoOrdenPago: TipoOrdenPago[];

  constructor(private _permisoService: PermisosService,
              private _messageUtilService: MessageUtilService,
              private _paymentOrderservice: PaymentOrderService,
              private _workFlowService: WorkflowService,
              private _empresaService: EmpresaService,
              private _tipoOrdenPagoService: TipoOrdenPagoService,
              private _dateService: DateUtilService,
              private _router: Router) {
  }

  ngOnInit() {
    this.loading = false;
    this.init();
    this.rendererCompany();
    this.onBusqueda();
    this.mostrarMensaje();
    this.obtenerWorkflows();
    this.obtenerPaymentOrderType();
  }
  init(){
    this.filtroBusquedaPaymentOrder = new PaymentOrderFilterDto();
    this.paymentOrderDt = [];
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }
  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }
  rendererCompany(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
    }else if(this.isSuperCompany()){
      this.obtenerEmpresas();
    }
  }

  /**
   * Obtiene lista de todos los workflows
   * @returns {Subscription}
   */
  obtenerWorkflows(){
    return this._workFlowService.getAllWorkflows().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaWorkflow = res.data.body.workflows;
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  /**
   * Obtiene lista de todos los tipo de ordenes de pago
   * @returns {Subscription}
   */
  obtenerPaymentOrderType(){
    return this._tipoOrdenPagoService.getAllTipoOrdenPago().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaTipoOrdenPago = res.data.body.tipoOrdenPago;
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }
  cargarCriterio(): PaymentorderRequestFiltroDto {
    let filtroPaymentOrderTemp = new PaymentorderRequestFiltroDto();
    if(this.filtroBusquedaPaymentOrder) {
      if(this.filtroBusquedaPaymentOrder.empresa) {
        filtroPaymentOrderTemp.idEmpresa = this.filtroBusquedaPaymentOrder.empresa.idcompanyPk;
      }
      if(this.filtroBusquedaPaymentOrder.tipoOrdenPago) {
        filtroPaymentOrderTemp.idTipoOrdenPago = this.filtroBusquedaPaymentOrder.tipoOrdenPago.idorderpaymenttypePk;
      }
      if(this.filtroBusquedaPaymentOrder.workflow) {
        filtroPaymentOrderTemp.idWorkflow = this.filtroBusquedaPaymentOrder.workflow.idworkflowPk;
      }
      filtroPaymentOrderTemp.estado = this.filtroBusquedaPaymentOrder.estado;
      filtroPaymentOrderTemp.fechaDesde =DateUtilService.convertStringDateUsToEs(this.filtroBusquedaPaymentOrder.fechaDesde);
      filtroPaymentOrderTemp.fechaHasta =DateUtilService.convertStringDateUsToEs(this.filtroBusquedaPaymentOrder.fechaHasta);
      filtroPaymentOrderTemp.idOrdenPago =this.filtroBusquedaPaymentOrder.idOrdenPago;
    }
    return filtroPaymentOrderTemp;
  }

  filterWorkflow(event) {
    this.filteredWorkflow = [];
    if(this.listaWorkflow){
      for(let i = 0; i < this.listaWorkflow.length; i++) {
        let workflow = this.listaWorkflow[i];
        if(workflow.workflownameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredWorkflow.push(workflow);
        }
      }
    }
  }
  filterPaymentOrderType(event) {
    this.filteredPaymentOrderType = [];
    if (this.listaTipoOrdenPago) {
      for (let i = 0; i < this.listaTipoOrdenPago.length; i++) {
        let tipoOrdenPago = this.listaTipoOrdenPago[i];
        if (tipoOrdenPago.nameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredPaymentOrderType.push(tipoOrdenPago);
        }
      }
    }
  }
  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusquedaPaymentOrder != null) {
      this._paymentOrderservice.getPaymentOrdersByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.paymentOrderDt = res.data.body.ordenpago;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.paymentOrderDt = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }

  navigatePaymentOrderEdit(paymentorder: PaymentOrderResponseDto){
    if(paymentorder != null && paymentorder.idpaymentorderPk != null){
      this._router.navigate(['/paymentorderEdit/paymentorderId', paymentorder.idpaymentorderPk]);
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

  mysort(event) {
    this.properties = event.field;
    if (event.order === 1) {
      this.direction = 'ASC';
    } else {
      this.direction = 'DESC';
    }
    //   Si ordena en la primera página, si ordena en las siguientes, ya
    //   se debe haber cambiado el valor de las variables page y size.
    if (this.page === 0) {
      this.page = 1;
    }
    if (this.size === 0) {
      this.size = 10;
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
    this.loading = true;
    this._paymentOrderservice.getPaymentOrdersByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.paymentOrderDt = res.data.body.ordenpago;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.paymentOrderDt = null;
          this.totalRecords = 0;
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  reset() {
    this.filtroBusquedaPaymentOrder = new PaymentOrderFilterDto();
    this.paymentOrderDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusquedaPaymentOrder != null) {
      return this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
    }
  }

  showSuccess(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'success', summary: mensaje, detail: detail});
  }

  showInfo(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'info', summary: mensaje, detail: detail});
  }

  showWarn(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'warn', summary: mensaje, detail: detail});
  }

  showError(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'error', summary: mensaje, detail: detail});
  }
  modificarOrdenPerm():boolean{
    return this._permisoService.modificarOrdenPago;
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
        this.showError(error, MESSAGEERROR);
      });
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

}
