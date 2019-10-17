import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {ConfirmationService, Message, TreeNode} from "primeng/primeng";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {ParameterService} from "./parameter.service";
import {ProcesoService} from "../procesos/proceso.service";
import {PermisosService} from "../../security/permiso.service";
import {Proceso} from "../../model/Proceso";
import {ProcesoFiltroDto} from "../../dto/systemparameter-dto/proceso-filtro.dto";
import {ProcesoListResponseDto} from "../../dto/systemparameter-dto/proceso-response.dto";
import {Systemparameter} from "../../model/systemparameter";
import {SystemparameterRequestDto} from "../../dto/systemparameter-dto/systemparameter-request.dto";
import {MESSAGEERROR} from "../../utils/gop-constantes";
import {SystemparameterId} from "../../model/systemParameterId";


@Component({
  selector: 'app-systemparameter-edit',
  templateUrl: './systemparameter-edit.component.html',
  styleUrls: ['./systemparameter-edit.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class SystemparameterEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';


  isNewSystemParameter: boolean;

  systemparameterFormGroup: FormGroup;
  filteredProcesos: any[];
  idProcesoUrl: string;
  listaProcesos: Proceso[];
  selectedProceso: Proceso;
  filtroBusqueda: ProcesoFiltroDto;
  procesoDt: ProcesoListResponseDto[];


  constructor(private _activatedRoute: ActivatedRoute, private _router: Router,
              private _formBuilder: FormBuilder,
              private _messageUtilService: MessageUtilService, private _systemparameterService: ParameterService, private _procesoService: ProcesoService,
              private _permisoService: PermisosService, private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.isNewSystemParameter = true;
    this.selectedProceso = null;
    this.mostrarMensaje();
    this.obtenerProcesos();
    this.buildForm(null);
    this.isEditOrNew();
    this.filtroBusqueda = new ProcesoFiltroDto();
    this.procesoDt = null;
  }

  /**
   * Obtiene lista de todos los Procesos
   * @returns {Subscription}
   */
  obtenerProcesos(){
    return this._procesoService.getAllProcesos().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaProcesos = res.data.body.procesos;
        } else {
          console.log(res.errors.message.message);
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
      console.log(this.msgs);
      this._messageUtilService.setMsgs(null);
    }
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un systemparameter,
   * sino un nuevo systemparameter
   */
  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
      if (params['idProcess']&&params['parametro']) {
        let id =new SystemparameterRequestDto();
        id.idProceso=params['idProcess'];
        id.parametro=params['parametro'];
        this.accionTitle = 'Editar Parametro de Sistema';
        this.isNewSystemParameter = false;
        this.getSystemParametertById(id);
      } else {
        this.accionTitle = 'Agregar Parametro de Sistema';
        this.isNewSystemParameter = true;
      }
    });
  }

  buildForm(systemparameterForm: Systemparameter) {
    if (systemparameterForm == null) {
      systemparameterForm = new Systemparameter();
    }
    //Se pone requerido los campos de acuerdo a si es un nuevo systemparamter o una edicion
    if (this.isNewSystemParameter) {
      this.systemparameterFormGroup = this._formBuilder.group({
        proceso: [{value: null, disabled: false}, Validators.required],
        parametro: [null, Validators.required],
        valor: [null,Validators.required],
        descripcion: [null],
      });
    } else {
      this.systemparameterFormGroup = this._formBuilder.group({
        proceso: [systemparameterForm.proceso, Validators.required],
        parametro: [systemparameterForm.parametro, Validators.required],
        valor: [systemparameterForm.valor, Validators.required],
        descripcion: [systemparameterForm.descripcion],
      });
    }
    this.loading = false;
    this.systemparameterFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }


  /**
   * Carga campos requeridos para la creacion de un SystemParmeter
   * @returns {SystemParameter}
   */
  cargarSystemParameterForNew(): SystemparameterRequestDto {
    let systemparamterTemp = new SystemparameterRequestDto();
    systemparamterTemp.idProceso = this.systemparameterFormGroup.value.proceso.idprocessPk;
    systemparamterTemp.descripcion = this.systemparameterFormGroup.value.descripcion;
    systemparamterTemp.valor = this.systemparameterFormGroup.value.valor;
    systemparamterTemp.parametro = this.systemparameterFormGroup.value.parametro;
    return systemparamterTemp;
  }

  /**
   * Carga campos para la edicion de un SystemParameter
   * @returns {SystemParameter}
   */
  cargarSystemParameterForEdit(): SystemparameterRequestDto {
    let systemparamterTemp = new SystemparameterRequestDto();
    systemparamterTemp.idProceso = this.systemparameterFormGroup.value.proceso.idprocessPk;
    systemparamterTemp.descripcion = this.systemparameterFormGroup.value.descripcion;
    systemparamterTemp.valor = this.systemparameterFormGroup.value.valor;
    systemparamterTemp.parametro = this.systemparameterFormGroup.value.parametro;
    return systemparamterTemp;
  }

  /**
   * Creacion de un nuevo SystemParameter
   * @param {SystemparameterRequestDto} systemparameter
   * @returns {Subscription}
   */
  crearSystemParameter(systemparameter: SystemparameterRequestDto) {
    this.loading = true;
    return this._systemparameterService.addSystemParameter(systemparameter).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/systemparameterList']);
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
   * Procesar la edicion de los campos de un SystemParameter.
   * @param {SystemParameterRequestDto} systemparamter
   * @returns {Subscription}
   */
  editarSystemParameter(systemparameter: SystemparameterRequestDto) {
    this.loading = true;
    return this._systemparameterService.editarSystemParameter(systemparameter).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/systemparameterList']);
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
   * Procesar la eliminacion de los campos de un SystemParameter.
   * @param {SystemParameterRequestDto} systemparamter
   * @returns {Subscription}
   */
  eliminarSystemParameter(systemparameter: SystemparameterRequestDto) {
    this.loading = true;
    return this._systemparameterService.eliminarSystemParameter(systemparameter).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/systemparameterList']);
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

  filterProcesos(event) {
    this.filteredProcesos = [];
    if(this.listaProcesos){
      for(let i = 0; i < this.listaProcesos.length; i++) {
        let proceso = this.listaProcesos[i];
        if(proceso.processnameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredProcesos.push(proceso);
        }
      }
    }
  }

  saveSystemparameter() {
    if (this.systemparameterFormGroup.valid && this.isNewSystemParameter) {
      this.crearSystemParameter(this.cargarSystemParameterForNew());
    } else if (this.systemparameterFormGroup.valid && !this.isNewSystemParameter) {
      this.editarSystemParameter(this.cargarSystemParameterForEdit());
    }
  }

  delete() {
    this.confirmationService.confirm({
      message: 'Está seguro de Eliminar?',
      header: 'Confirmación de Borrado',
      icon: 'fa fa-trash',
      accept: () => {
        this.eliminarSystemParameter(this.cargarSystemParameterForEdit());
        this.msgs = [{severity:'info', summary:'Confirmado', detail:'Parámetro Eliminado'}];
      },
      reject: () => {
        this.navigateSystemParameterList();
        this.msgs = [{severity:'info', summary:'Cancelado', detail:'Has Cancelado'}];
      }
    });
  }

  /**
   * Obtiene un Systemparameter por su id
   * @returns {Subscription}
   */
  getSystemParametertById(procesoId: SystemparameterRequestDto) {
    return this._systemparameterService.getSystemParameterById(procesoId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.systemparameter);
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  objectContains(obj, term: string): boolean {
    for (let key in obj) {
      if (obj[key].usernameChr.indexOf(term) != -1) return true;
    }
    return false;
  }
  modificarParameterPerm(): boolean {
    return this._permisoService.modificarParameter;
  }
  /**
   * Navega al listado de sytemparameters
   */
  navigateSystemParameterList() {
    this._router.navigate(['/systemparameterList']);
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
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
