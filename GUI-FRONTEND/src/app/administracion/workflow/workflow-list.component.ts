import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {WorkflowFiltroDto} from "../../dto/workflow-dto/workflow-filtro.dto";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {WorkflowListResponseDto} from "../../dto/workflow-dto/workflow-list-response.dto";
import {WorkflowService} from "./workflow-service";
import {WorkflowListRequestDto} from "../../dto/workflow-dto/workflow-list-request.dto";
import {CANT_FILAS, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Workflow} from "../../model/workflow";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";
import {EmpresaService} from "../empresa/empresa.service";
import {STATES} from "../../utils/gop-constantes";


@Component({
  selector: 'app-workflow-list',
  templateUrl: './workflow-list.component.html',
  styleUrls: ['./workflow-list.component.css']
})
export class WorkflowListComponent implements OnInit {
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

  // Message
  msgs: Message[] = [];
  estadoList = STATES;
  filtroBusquedaWorkflow: WorkflowFiltroDto;
  workflowDt: WorkflowListResponseDto[] = [];

  constructor(private _router: Router, private _activedRoute: ActivatedRoute, private _permisoService: PermisosService,
              private _messageUtilService: MessageUtilService, private _workflowservice: WorkflowService,
              private _empresaService: EmpresaService) {
  }

  ngOnInit() {
    this.loading = false;
    this.filtroBusquedaWorkflow = new WorkflowFiltroDto();
    this.onBusqueda();
    this.mostrarMensaje();
    this.callSuperCompanyService();
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  navigateWorkflowEdit(workflow: Workflow) {
    if (workflow != null && workflow.idworkflowPk != null) {
      this._router.navigate(['/workflowEdit/workflowId', workflow.idworkflowPk]);
    } else {
      this._router.navigate(['/workflowEdit']);
    }
  }

  cargarCriterio(): WorkflowListRequestDto {
    let filtroWorflowTemp = new WorkflowListRequestDto();
    filtroWorflowTemp.stateChr = this.filtroBusquedaWorkflow.stateChr;
    filtroWorflowTemp.descriptionChr = this.filtroBusquedaWorkflow.descriptionChr;
    filtroWorflowTemp.workflownameChr = this.filtroBusquedaWorkflow.workflownameChr;
    return filtroWorflowTemp;
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusquedaWorkflow != null) {
      this._workflowservice.getWorkflowByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.workflowDt = res.data.body.workflows;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.workflowDt = null;
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
    this._workflowservice.getWorkflowByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.workflowDt = res.data.body.workflows;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.workflowDt = null;
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
    this.filtroBusquedaWorkflow = new WorkflowFiltroDto();
    this.workflowDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusquedaWorkflow != null) {
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

  modificarWorkflowPerm(): boolean {
    return this._permisoService.modificarWorkflow;
  }

  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }

  callSuperCompanyService(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
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
