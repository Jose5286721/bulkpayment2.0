import {Component, OnInit} from "@angular/core";
import {Message, TreeNode} from "primeng/primeng";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA, STATES} from "../../utils/gop-constantes";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {DateUtilService} from "../../utils/date-util.service";
import {MessageUtilService} from "../../utils/message-util.service";
import {WorkflowService} from "./workflow-service";
import {WorkflowRequestDto} from "../../dto/workflow-dto/workflow-request";
import {Empresa} from "../../model/empresa"
import {EmpresaService} from "../empresa/empresa.service";
import {UsuarioService} from "../usuario/usuario.service";
import {UsuarioFiltroDto} from "../../dto/usuario-dto/usuario-filtro.dto";
import {UsuarioFiltroRequestDto} from "../../dto/usuario-dto/usuario-filtro-request.dto";
import {UsuarioListResponseDto} from "../../dto/usuario-dto/usuario-list-response.dto";
import {PermisosService} from "../../security/permiso.service";
import {Workflow} from "../../model/workflow";
import {isUndefined} from "util";


@Component({
  selector: 'app-workflow-edit',
  templateUrl: './workflow-edit.component.html',
  styleUrls: ['./workflow-edit.component.css']
})
export class WorkflowEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';
  firmantesList: UsuarioListResponseDto[];
  firmantesSelecionados: UsuarioListResponseDto[];

  isNewWorkflow: boolean;
  isLoggedSupercompany: boolean;
  workflowFormGroup: FormGroup;
  filteredCompany: any[];
  idworkflowUrl: number;
  listaEmpresa: Empresa[];
  selectedCompany: Empresa;
  filtroBusqueda: UsuarioFiltroDto;
  userDt: UsuarioListResponseDto[];
  firmantesResponse: UsuarioListResponseDto[];
  estadoList = STATES;
  superCompany: boolean;

  constructor(private _activatedRoute: ActivatedRoute, private _router: Router,
              private _formBuilder: FormBuilder, private _dateUtilService: DateUtilService,
              private _messageUtilService: MessageUtilService, private _workflowService: WorkflowService, private _empresaService: EmpresaService,
              private _userServive: UsuarioService, private _permisoService: PermisosService) {
  }

  ngOnInit() {
    this.isNewWorkflow = true;
    this.firmantesList = [];
    this.firmantesSelecionados = [];
    this.selectedCompany = null;
    this.filtroBusqueda = new UsuarioFiltroDto();
    this.userDt = null;
    this.firmantesResponse = [];
    this.isLoggedSupercompany = false;
    this.rendererCompany();
    this.mostrarMensaje();
    this.buildForm(null);
    this.isEditOrNew();
  }

  isSuperCompany(): boolean{
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
  }

  onSelectCompany(event) {
    this.firmantesSelecionados = [];
    this.firmantesList = [];
    this.getFirmatesByCompany(this.workflowFormGroup.value.company.idcompanyPk);
  }

  getFirmantesbyWorkflow(workflowId: number, companypk:number) {
    return this._workflowService.getFirmantesByWorkflowId(workflowId).subscribe(
      (res: any) => {
        if (res.data !== null && res.data.body !== null) {
          this.firmantesResponse = res.data.body.firmantes;
          if (this.firmantesResponse !== null) {
            for (let firm of this.firmantesResponse) {
              this.firmantesSelecionados.push(firm);
            }
          }
          this.getFirmatesByCompany(companypk);
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un workflow,
   * sino un nuevo workflow
   */
  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
      if (params['id']) {
        this.idworkflowUrl = params['id'];
        this.accionTitle = 'Editar Workflow';
        this.isNewWorkflow = false;
        this.getWorkflowtById(params['id']);
      } else {
        this.accionTitle = 'Agregar Workflow';
        this.isNewWorkflow = true;
        if(!this.superCompany){
          this.getFirmatesByCompany(null);
        }
      }
    });
  }

  buildForm(workflowForm: Workflow) {
    if (workflowForm == null) {
      workflowForm = new Workflow();
    }
    //Se pone requerido los campos de acuerdo a si es un nuevo workflow o una edicion
    if (this.isNewWorkflow) {
      this.workflowFormGroup = this._formBuilder.group({
        idworkflowPk: [null],
        workflownameChr: [null, Validators.required],
        descriptionChr: [null],
        stateChr: ["AC"],
        walletChr: [null],
        detalle: [null],
        company: [{value: null, disabled: false}]
      });
    } else {

      this.workflowFormGroup = this._formBuilder.group({
        idworkflowPk: [workflowForm.idworkflowPk],
        workflownameChr: [workflowForm.workflownameChr, Validators.required],
        descriptionChr: [workflowForm.descriptionChr],
        stateChr: [workflowForm.stateChr, Validators.required],
        walletChr: [workflowForm.walletChr,Validators.required],
        detalle: [workflowForm.detalle],
        company: [workflowForm.company],
      });
      this.getFirmantesbyWorkflow(workflowForm.idworkflowPk,this.workflowFormGroup.value.company.idcompanyPk);
    }

    this.isLoggedSupercompany = workflowForm.isLoggedSuperCompany;
    this.loading = false;
    this.workflowFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }


  /**
   * Carga campos requeridos para la creacion de un Worflow
   * @returns {Workflow}
   */
  cargarWorkflowForNew(): WorkflowRequestDto {
    let workflowTemp = new WorkflowRequestDto();
    workflowTemp.workflownameChr = this.workflowFormGroup.value.workflownameChr;
    workflowTemp.descriptionChr = this.workflowFormGroup.value.descriptionChr;
    workflowTemp.idcompany = this.workflowFormGroup.value.company ? this.workflowFormGroup.value.company.idcompanyPk : null;
    workflowTemp.walletChr = this.workflowFormGroup.value.walletChr;
    workflowTemp.stateChr = "AC";
    workflowTemp.company = this.workflowFormGroup.value.company;
    workflowTemp.workflowDetalleDto = this.workflowFormGroup.value.workflowDetalleDto;
    workflowTemp.isLoggedSuperCompany = this.workflowFormGroup.value.isLoggedSuperCompany;
    workflowTemp.listaFirmantes = this.firmantesSelecionados;
    return workflowTemp;
  }

  /**
   * Carga campos para la edicion de una workflow-dto
   * @returns {workflow}
   */
  cargarWorkflowForEdit(): WorkflowRequestDto {
    let workflowTemp = new WorkflowRequestDto();
    workflowTemp.idworkflowPk = this.workflowFormGroup.value.idworkflowPk;
    workflowTemp.idcompany = this.workflowFormGroup.value.company.idcompanyPk;
    workflowTemp.company = this.workflowFormGroup.value.company;
    workflowTemp.stateChr = this.workflowFormGroup.value.stateChr;
    workflowTemp.walletChr = this.workflowFormGroup.value.walletChr;
    workflowTemp.descriptionChr = this.workflowFormGroup.value.descriptionChr;
    workflowTemp.workflownameChr = this.workflowFormGroup.value.workflownameChr;
    workflowTemp.isLoggedSuperCompany = this.workflowFormGroup.value.isLoggedSuperCompany;
    workflowTemp.listaFirmantes = this.firmantesSelecionados;
    return workflowTemp;
  }

  /**
   * Creacion de un nuevo workflow
   * @param {WorkflowRequestDto} workflow
   * @returns {Subscription}
   */
  crearWorkflow(workflow: WorkflowRequestDto) {
    this.loading = true;
    return this._workflowService.crearWorkflow(workflow).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/workflowList']);
        } else {
          setTimeout(() => {
            this.loading = false;
            this.showWarn(res.errors.message.detail, res.errors.message.message);
          }, 500);
        }
      },
      error => {
        this.loading = false;
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  /**
   * Procesar la edicion de los campos de un workflow.
   * @param {Workflow} workflow
   * @returns {Subscription}
   */
  editarWorkflow(workflow: WorkflowRequestDto) {
    this.loading = true;
    return this._workflowService.editarWorkflow(workflow).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/workflowList']);
        } else {
          setTimeout(() => {
            this.loading = false;
            this.showWarn(res.errors.message.detail, res.errors.message.message);
          }, 500);
        }
      },
      error => {
        this.loading = false;
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  filterCompany(event) {
    this.filteredCompany = [];
    if (this.listaEmpresa) {
      for (let i = 0; i < this.listaEmpresa.length; i++) {
        let empresa = this.listaEmpresa[i];
        if (empresa.companynameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredCompany.push(empresa);
        }
      }
    }
  }

  /**
   * Obtiene lista de todas las empresas
   * @returns {Subscription}
   */
  obtenerEmpresas() {
    if(this.superCompany){
      this._empresaService.getAllEmpresas().subscribe(
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
  }


  saveWorkflow() {
    if (this.workflowFormGroup.valid && this.isNewWorkflow) {
      this.crearWorkflow(this.cargarWorkflowForNew());
    } else if (this.workflowFormGroup.valid && !this.isNewWorkflow) {
      this.editarWorkflow(this.cargarWorkflowForEdit());
    }
  }

  /**
   * Obtiene un Workflow por su id
   * @returns {Subscription}
   */
  getWorkflowtById(workflowId: number) {
    return this._workflowService.getWorkflowById(workflowId).subscribe(
      (res: any) => {
        if (res.data !== null && res.data.body !== null) {
          this.buildForm(res.data.body.workflow);
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  cargarCriterio(id: number): UsuarioFiltroRequestDto {
    let filtroTemp = new UsuarioFiltroRequestDto();
    filtroTemp.companyId = id;
    filtroTemp.stateChr = "AC";
    return filtroTemp;
  }

  getFirmatesByCompany(companyId?: number) {
    this.msgs = null;
    if (this.filtroBusqueda !== null) {
      this._userServive.getAllFirmantes(companyId)
        .subscribe((res: any) => {
            if (res.data !== null && res.data.body !== null) {
              this.userDt = res.data.body.usuarios;
              if (this.userDt !== null) {
                for (let user of this.userDt) {
                  if (user) {
                    if (!this.objectContains(this.firmantesSelecionados, user.iduserPk))
                      this.firmantesList.push(user);
                  }
                }
              }
            } else {
              this.userDt = null;
              this.showWarn(res.errors.message.detail, res.errors.message.message);
              this.loading = false;
            }
          },
          error => {
            console.log(error);
            this.loading = false;
          });
    }
  }

  objectContains(obj:UsuarioListResponseDto[], term: number): boolean {
    for (let key of obj) {
      if (key.iduserPk==term) return true;
    }
    return false;
  }

  /**
   * Navega al listado de workflows
   */
  navigateWorkflowList() {
    this._router.navigate(['/workflowList']);
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
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
}
