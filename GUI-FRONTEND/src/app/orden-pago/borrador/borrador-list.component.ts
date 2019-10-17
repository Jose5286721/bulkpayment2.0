import { Component, OnInit } from '@angular/core';
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";
import {MessageUtilService} from "../../utils/message-util.service";
import {Router} from "@angular/router";
import {Message} from "primeng/primeng";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA, STATES} from "../../utils/gop-constantes";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {Empresa} from "../../model/empresa";
import {Workflow} from "../../model/workflow";
import {WorkflowService} from "../../administracion/workflow/workflow-service";
import {TipoOrdenPago} from "../../model/tipo-orden-pago";
import {TipoOrdenPagoService} from "../tipo-orden-pago/tipo-orden-pago.service";
import {DraftResponseListDto} from "../../dto/borrador-dto/DraftResponseListDto";
import {DraftFiltroDto} from "../../dto/borrador-dto/DraftFiltroDto";
import {BorradorService} from "./borrador.service";
import {RolFiltroRequestDto} from "../../dto/rol-dto/rol-filtro-request.dto";
import {DraftFiltroRequestDto} from "../../dto/borrador-dto/DraftFiltroRequestDto";
import {DateUtilService} from "../../utils/date-util.service";

@Component({
  selector: 'app-borrador-list',
  templateUrl: './borrador-list.component.html',
  styleUrls: ['./borrador-list.component.css']
})
export class BorradorListComponent implements OnInit {

  //Constantes
  recurrenciaOptions: any[] = [{ label: "Si", value: true },{ label: "No", value: false } ];

  //spinner
  loading: boolean;

  //DataTable
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';
  borradoresDt: DraftResponseListDto[];

  // Message
  msgs: Message[] = [];

  //
  filteredCompany: any[];
  filteredWorkflow: any[];
  filteredPaymentOrderType: any[];
  listaEmpresa: Empresa[];
  listaWorkflow: Workflow[];
  listaTipoOrdenPago: TipoOrdenPago[];

  filtroBusqueda: DraftFiltroDto;
  states = {
    AC: 'Activo',
    IN: 'Inactivo',
    ER: 'Error'
  };

  constructor(private _permisoService: PermisosService, private _router: Router, private _messageUtilService: MessageUtilService,
              private _empresaService: EmpresaService, private _workFlowService: WorkflowService, private _dateService: DateUtilService,
              private _tipoOrdenPagoService: TipoOrdenPagoService, private _borradorService: BorradorService) {
  }

  ngOnInit() {
    this.init();
    this.rendererCompany();
    this.onBusqueda();
    this.mostrarMensaje();
    this.obtenerWorkflows();
    this.obtenerPaymentOrderType();
  }

  init(){
    this.filtroBusqueda = new DraftFiltroDto();
    this.borradoresDt = [];
  }
  mostrarMensaje(){
    if(this._messageUtilService.getMsgs()){
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  navigateDraftEdit(borrador: any){
    if(borrador != null && borrador.iddraftPk != null){
      this._router.navigate(['/draftEdit/draftId', borrador.iddraftPk]);
    }else{
      this._router.navigate(['/draftEdit']);
    }
  }

  onBusqueda() {
    this.msgs = null;
    if (this.filtroBusqueda != null) {
      this.loading = true;
      this._borradorService.getDraftByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.borradoresDt = res.data.body.borradores;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.borradoresDt = null;
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


  reset(){
    this.filtroBusqueda = new DraftFiltroDto();
    this.borradoresDt = null;
    this.totalRecords = 0;
    this.msgs = null;
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
        this.showError(error, MESSAGEERROR);
      });
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
  modificarBorradorPerm():boolean{
    return this._permisoService.modificarBorrador;
  }
  showSuccess(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:mensaje, detail:detail});
  }

  showInfo(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:mensaje, detail:detail});
  }

  showWarn(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'warn', summary:mensaje, detail:detail});
  }

  showError(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:mensaje, detail:detail});
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
    if(this.listaTipoOrdenPago){
      for(let i = 0; i < this.listaTipoOrdenPago.length; i++) {
        let tipoOrdenPago = this.listaTipoOrdenPago[i];
        if(tipoOrdenPago.nameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredPaymentOrderType.push(tipoOrdenPago);
        }
      }
    }
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

  /**
   * Metodo que realiza una busqueda de roles de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._borradorService.getDraftByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.borradoresDt = res.data.body.borradores;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.borradoresDt = null;
          this.totalRecords = 0;
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }


  cargarCriterio(): DraftFiltroRequestDto{
    let filtroTemp = new DraftFiltroRequestDto();
    filtroTemp.iddraftPk = this.filtroBusqueda.iddraftPk;
    if(this.filtroBusqueda.company) {
      filtroTemp.idcompanyPk = this.filtroBusqueda.company.idcompanyPk;
    }
    if(this.filtroBusqueda.workflow) {
      filtroTemp.idworkflowPk = this.filtroBusqueda.workflow.idworkflowPk;
    }
    if(this.filtroBusqueda.orderpaymenttype) {
      filtroTemp.idorderpaymenttypePk = this.filtroBusqueda.orderpaymenttype.idorderpaymenttypePk;
    }
    filtroTemp.recurrentNum = this.filtroBusqueda.recurrentNum;
    filtroTemp.creationdateTimStart = DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.creationdateTimStart);
    filtroTemp.creationdateTimEnd =DateUtilService.convertStringDateUsToEs(this.filtroBusqueda.creationdateTimEnd);
    filtroTemp.descriptionChr = this.filtroBusqueda.descriptionChr;
    return filtroTemp;
  }



}
