import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {TipoOrdenPagoService} from "./tipo-orden-pago.service";
import {MESSAGEERROR} from "../../utils/gop-constantes";
import {TipoOrdenPago} from "../../model/tipo-orden-pago";

@Component({
  selector: 'app-tipo-orden-pago-edit',
  templateUrl: './tipo-orden-pago-edit.component.html',
  styleUrls: ['./tipo-orden-pago-edit.component.css']
})
export class TipoOrdenPagoEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';

  //
  isNewTipoOrdenPago: boolean;
  tipoOrdenPagoFormGroup: FormGroup;
  idTipoOrdenPagoUrl: number;

  constructor(private _activatedRoute: ActivatedRoute, private _router: Router, private _tipoOrdenPagoService: TipoOrdenPagoService,
              private _formBuilder: FormBuilder, private _messageUtilService: MessageUtilService) { }

  ngOnInit() {
    this.isNewTipoOrdenPago = true;
    this.buildForm(null);
    this.isEditOrNew();
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un tipo de orden de pago,
   * sino uno nuevo
   */
  isEditOrNew(){
    this._activatedRoute.params.subscribe( params => {
      if (params['id']) {
        this.idTipoOrdenPagoUrl = params['id'];
        this.accionTitle = 'Editar Tipo Orden Pago';
        this.isNewTipoOrdenPago = false;
        this.getTipoOrdenPagoById(params['id']);
      }else{
        this.accionTitle = 'Agregar Tipo Orden Pago';
        this.isNewTipoOrdenPago = true;
      }
    });
  }

  /**
   * Obtiene un tipo orden pago por su id
   * @returns {Subscription}
   */
  getTipoOrdenPagoById(topId: number) {
    return this._tipoOrdenPagoService.getTipoOrdenPagoById(topId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.tipoOrdenPago);
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error,MESSAGEERROR);
      });
  }

  /**
   * Arma e inicializa la estructura del formulario para creacion o edicion de
   * tipo de orden de pago
   */
  buildForm(topForm: TipoOrdenPago){
    if(topForm == null){
      topForm = new TipoOrdenPago();
    }
    //Se pone requerido los campos de acuerdo a si es nuevo o una edicion
    if(this.isNewTipoOrdenPago){
      this.tipoOrdenPagoFormGroup = this._formBuilder.group({
        idorderpaymenttypePk: [null],
        nameChr: [null, Validators.required],
        descriptionChr: [null]
      });
    }else{
      this.tipoOrdenPagoFormGroup = this._formBuilder.group({
        idorderpaymenttypePk: [topForm.idorderpaymenttypePk],
        nameChr: [topForm.nameChr, Validators.required],
        descriptionChr: [topForm.descriptionChr]
      });
    }
    this.loading = false;
    this.tipoOrdenPagoFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
  }

  /**
   * Submit, se encarga de redirigir al metodo correcto para la creacion o edicion
   */
  saveTipoOrdenPago(){
    if(this.tipoOrdenPagoFormGroup.valid && this.isNewTipoOrdenPago){
      this.crearTipoOrdenPago(this.cargarTipoOrdenPagoForNew());
    }else if(this.tipoOrdenPagoFormGroup.valid && !this.isNewTipoOrdenPago){
      this.editarTipoOrdenPago(this.cargarTipoOrdenPagoForEdit());
    }
  }


  crearTipoOrdenPago(top: TipoOrdenPago){
    this.loading = true;
    return this._tipoOrdenPagoService.crearTipoOrdenPago(top).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/paymentOrderTypeList']);
        } else {
          setTimeout(() => {
            this.loading = false;
            this.showWarn(res.errors.message.detail,res.errors.message.message);
          },500);
        }
      },
      error => {
        this.loading = false;
        console.log(error);
        this.showError(error,MESSAGEERROR);
      });
  }

  /**
   * Editar tipo de orden de pago
   * @param {TipoOrdenPago} top
   * @returns {Subscription}
   */
  editarTipoOrdenPago(top: TipoOrdenPago){
    this.loading = true;
    return this._tipoOrdenPagoService.editarTipoOrdenPago(top).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/paymentOrderTypeList']);
        } else {
          setTimeout(() => {
            this.loading = false;
            this.showWarn(res.errors.message.detail,res.errors.message.message);
          },500);
        }
      },
      error => {
        this.loading = false;
        console.log(error);
        this.showError(error,MESSAGEERROR);
      });
  }

  cargarTipoOrdenPagoForNew() : TipoOrdenPago{
    let topTemp = new TipoOrdenPago();
    topTemp.nameChr = this.tipoOrdenPagoFormGroup.value.nameChr;
    topTemp.descriptionChr = this.tipoOrdenPagoFormGroup.value.descriptionChr;
    return topTemp;
  }

  cargarTipoOrdenPagoForEdit() : TipoOrdenPago{
    let topTemp = new TipoOrdenPago();
    topTemp.idorderpaymenttypePk = this.tipoOrdenPagoFormGroup.value.idorderpaymenttypePk;
    topTemp.nameChr = this.tipoOrdenPagoFormGroup.value.nameChr;
    topTemp.descriptionChr = this.tipoOrdenPagoFormGroup.value.descriptionChr;
    return topTemp;
  }

  /**
   * Navega al listado de roles
   */
  navigateTipoOrdenPagoList() {
    this._router.navigate(['/paymentOrderTypeList']);
  }


  showWarn(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'warn', summary:mensaje, detail:detail});
  }

  showError(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:mensaje, detail:detail});
  }

}
