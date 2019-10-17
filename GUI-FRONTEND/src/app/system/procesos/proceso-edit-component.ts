import {Component, OnInit} from "@angular/core";
import {Message} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DateUtilService} from "../../utils/date-util.service";
import {MessageUtilService} from "../../utils/message-util.service";
import {UsuarioService} from "../../administracion/usuario/usuario.service";
import {PermisosService} from "../../security/permiso.service";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {ProcesoService} from "./proceso.service";
import {Proceso} from "../../model/Proceso";
import {ProcesoRequestDto} from "../../dto/systemparameter-dto/proceso-request.dto";
import {MESSAGEERROR} from "../../utils/gop-constantes";

@Component({
  selector: 'app-proceso-edit',
  templateUrl: './proceso-edit.component.html',
  styleUrls: ['./proceso-edit.component.css']
})
export class ProcesoEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];

  idproceso: number;
  accionTitle: string = '';
  isNewProcess: boolean;
  processFormGroup: FormGroup;

  ngOnInit() {
    this.isNewProcess = false;
    this.mostrarMensaje();
    this.buildForm(null);
    this.isEditOrNew();
  }


  constructor(private _activatedRoute: ActivatedRoute, private _router: Router,
              private _formBuilder: FormBuilder, private _dateUtilService: DateUtilService,
              private _messageUtilService: MessageUtilService, private _procesoService: ProcesoService, private _empresaService: EmpresaService,
              private _userServive: UsuarioService, private _permisoService: PermisosService) {
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un workflow,
   * sino un nuevo workflow
   */
  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
      if (params['id']) {
        this.idproceso = params['id'];
        this.accionTitle = 'Editar Proceso';
        this.isNewProcess = false;
        this.getProcesotById(params['id']);
      }
    });
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  /**
   * Carga campos para la edicion de una proceso-dto
   * @returns {workflow}
   */
  cargarProcesoForEdit(): ProcesoRequestDto {
    let procesoTemp = new ProcesoRequestDto();
    procesoTemp.idprocessPk = this.processFormGroup.value.idprocessPk;
    procesoTemp.descriptionChr = this.processFormGroup.value.descriptionChr;
    procesoTemp.processnameChr = this.processFormGroup.value.processnameChr;
    return procesoTemp;
  }

  saveProcess() {
    if (this.processFormGroup.valid && this.isNewProcess) {
      //this.crearWorkflow(this.cargarWorkflowForNew());
    } else if (this.processFormGroup.valid && !this.isNewProcess) {
      this.editarProceso(this.cargarProcesoForEdit());
    }
  }

  /**
   * Procesar la edicion de los campos de un Proceso.
   * @param {ProcesoRequestDto} proceso
   * @returns {Subscription}
   */
  editarProceso(proceso: ProcesoRequestDto) {
    this.loading = true;
    return this._procesoService.editarProceso(proceso).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/processList']);

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
   * Obtiene un Proceso por su id
   * @returns {Subscription}
   */
  getProcesotById(procesoId: number) {
    return this._procesoService.getProcessById(procesoId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.proceso);
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  buildForm(processForm: Proceso) {
    if (processForm == null) {
      processForm = new Proceso();
    }
    //Se pone requerido los campos de acuerdo a si es un nuevo workflow o una edicion
    if (!this.isNewProcess) {
      this.processFormGroup = this._formBuilder.group({
        idprocessPk: [processForm.idprocessPk, Validators.required],
        processnameChr: [processForm.processnameChr, Validators.required],
        descriptionChr: [processForm.descriptionChr],
      });
    }

    this.loading = false;
    this.processFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }

  /**
   * Navega al listado de workflows
   */
  navigateProcesoList() {
    this._router.navigate(['/processList']);
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
