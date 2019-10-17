import {Component, OnInit} from "@angular/core";
import {Message} from "primeng/primeng";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {ProcesoService} from "../procesos/proceso.service";
import {PermisosService} from "../../security/permiso.service";
import {Proceso} from "../../model/Proceso";
import {ProcesoFiltroDto} from "../../dto/systemparameter-dto/proceso-filtro.dto";
import {ProcesoListResponseDto} from "../../dto/systemparameter-dto/proceso-response.dto";
import {CodigoeventosService} from "./codigoeventos.service";
import {CodigoeventosRequestDto} from "../../dto/systemparameter-dto/codigoeventos-request.dto";
import {CodigoeventosResponseDto} from "../../dto/systemparameter-dto/codigoeventos-response.dto";
import {MESSAGEERROR} from "../../utils/gop-constantes";


@Component({
  selector: 'app-codigoeventos-edit',
  templateUrl: './codigoeventos-edit.component.html',
  styleUrls: ['./codigoeventos-edit.component.css']
})
export class CodigoeventosEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';

  codigoeventoFormGroup: FormGroup;
  filteredProcesos: any[];
  listaProcesos: Proceso[];
  selectedProceso: Proceso;
  filtroBusqueda: ProcesoFiltroDto;
  procesoDt: ProcesoListResponseDto[];


  constructor(private _activatedRoute: ActivatedRoute, private _router: Router,
              private _formBuilder: FormBuilder,
              private _messageUtilService: MessageUtilService, private _codigoeventosservice: CodigoeventosService, private _procesoService: ProcesoService,
              private _permisoService: PermisosService) {
  }

  ngOnInit() {
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
   * Verifica la url, si tiene id significa para editar el Codigo Evento
   * sino un nuevo systemparameter
   */
  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
      if (params['idProcess']&&params['parametro']) {
        let id =new CodigoeventosRequestDto();
        id.idprocessPk=params['idProcess'];
        id.ideventcodeNum=params['parametro'];
        this.accionTitle = 'Editar Parametro de Sistema';
        this.getCodigoEventotById(id);
      }
    });
  }

  buildForm(codigoeventoForm: CodigoeventosResponseDto) {

   if(codigoeventoForm) {
     this.codigoeventoFormGroup = this._formBuilder.group({
       proceso: [codigoeventoForm.proceso, Validators.required],
       ideventcodeNum: [codigoeventoForm.ideventcodeNum, Validators.required],
       descripcionChr: [codigoeventoForm.descriptionChr],
     });

   }else{
     this.codigoeventoFormGroup = this._formBuilder.group({
       proceso: [{value: null}, Validators.required],
       ideventcodeNum: [null, Validators.required],
       descripcionChr: [null]
     });
   }
    this.loading = false;
    this.codigoeventoFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }



  /**
   * Carga campos para la edicion de un CodigoEvento
   * @returns {CodigoEvento}
   */
  cargarCodigoEventoForEdit(): CodigoeventosRequestDto {
    let systemparamterTemp = new CodigoeventosRequestDto();
    if(this.codigoeventoFormGroup.value.proceso) {
      systemparamterTemp.idprocessPk = this.codigoeventoFormGroup.value.proceso.idprocessPk;
    }
    systemparamterTemp.descriptionChr = this.codigoeventoFormGroup.value.descripcionChr;
    systemparamterTemp.ideventcodeNum = this.codigoeventoFormGroup.value.ideventcodeNum;
    return systemparamterTemp;
  }

  /**
   * Procesar la edicion de los campos de un CodigoEvento.
   * @param {CodigoeventosRequestDto} codigoevento
   * @returns {Subscription}
   */
  editarCodigoEvento(codigoevento:CodigoeventosRequestDto) {
    this.loading = true;
    return this._codigoeventosservice.editarCodigoEvento(codigoevento).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info', res.data.body.mensaje, res.data.message.detail);
          this._router.navigate(['/eventCodeList']);
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

  saveCodigoEvento() {
    if(this.codigoeventoFormGroup.valid) {
      this.editarCodigoEvento(this.cargarCodigoEventoForEdit());
    }
  }

  /**
   * Obtiene un CodigoEvento por su id
   * @returns {Subscription}
   */
  getCodigoEventotById(eventoId: CodigoeventosRequestDto) {
    return this._codigoeventosservice.getCodigoEventosById(eventoId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.eventcode);
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

  /**
   * Navega al listado de sytemparameters
   */
  navigateCodigoEventoList() {
    this._router.navigate(['/eventCodeList']);
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
