import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Message} from "primeng/primeng";
import {Empresa} from "../../model/empresa";
import {Location} from '@angular/common';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EmpresaService} from "./empresa.service";
import {MESSAGEERROR, MESSAGES, PRIORITY_STATES, REGEX_DECIMAL, STATES} from "../../utils/gop-constantes";
import {DateUtilService} from "../../utils/date-util.service";
import {MessageUtilService} from "../../utils/message-util.service";
import {GeneralHelperService} from "../../utils/general-helper.service";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";

@Component({
  selector: 'app-empresa-edit',
  templateUrl: './empresa-edit.component.html',
  styleUrls: ['./empresa-edit.component.css']
})
export class EmpresaEditComponent implements OnInit {

  readonly MESSAGES = MESSAGES;

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';

  //
  isNewCompany: boolean;
  empresaFormGroup: FormGroup;
  estadoList = STATES;
  priorityList = PRIORITY_STATES;
  superCompany: boolean;


  constructor(private _activatedRoute: ActivatedRoute, private _location: Location, private _router: Router,
              private _formBuilder: FormBuilder, private _empresaService: EmpresaService, private _dateUtilService: DateUtilService,
              private _messageUtilService: MessageUtilService, private _generalHelper: GeneralHelperService,
              private _permisoService:PermisosService)
  { }

  ngOnInit() {
    this.isNewCompany = true;
    this.callSuperCompany();
    this.buildForm(new Empresa);
    this.isEditOrNew();
  }



  /**
   * Verifica la url, si tiene id significa que es una edicion de empresa-dto,
   * sino una nueva empresa-dto
   */
  isEditOrNew(){
    this._activatedRoute.params.subscribe( params => {
      if (params['id']) {
        this.accionTitle = 'Editar Empresa';
        this.isNewCompany = false;
        this.getEmpresaById(params['id']);
      }else{
        this.accionTitle = 'Agregar Empresa';
        this.isNewCompany = true;
      }
    });
  }

  /**
   * Navega a la pagina anterior
   */
  goBack() {
    this._location.back();
  }

  /**
   * Navega al listado de empresas
   */
  navigateCompanyList() {
    this._router.navigate(['/companyList']);
  }

  /**
   * Arma e inicializa la estructura del formulario para creacion o edicion de
   * empresas
   */
  buildForm(empresaForm: Empresa){
    if(empresaForm == null){
      empresaForm = new Empresa();
    }
    //Se pone requerido los campos de acuerdo a si es nueva empresa o una edicion
    if(this.isNewCompany){
      this.empresaFormGroup = this._formBuilder.group({
        idcompanyPk: [empresaForm.idcompanyPk],
        companynameChr: [empresaForm.companynameChr, [Validators.required, Validators.maxLength(30)]],
        creationdateTim: [this._dateUtilService.convertStringDate(empresaForm.creationdateTim)],
        descriptionChr: [empresaForm.descriptionChr],
        stateChr: [empresaForm.stateChr],
        contactnameChr: [empresaForm.contactnameChr, [Validators.required, Validators.maxLength(50)]],
        emailChr: [empresaForm.emailChr , [Validators.email, Validators.maxLength(50)]],
        phonenumberChr: [empresaForm.phonenumberChr, Validators.required],
        lastopdateTim: [this._dateUtilService.convertStringDate(empresaForm.lastopdateTim)],
        trxdaycountNum:[empresaForm.trxdaycountNum],
        trxmonthcountNum:[empresaForm.trxmonthcountNum],
        trxtotalcountNum:[empresaForm.trxtotalcountNum],
        percentcommissionNum: [empresaForm.percentcommissionNum, [Validators.required, Validators.maxLength(4),
          Validators.pattern(REGEX_DECIMAL)]],
        mtsusrChr: [empresaForm.mtsusrChr, Validators.required],
        // mtsbandChr: [empresaForm.mtsbandChr, [Validators.required, Validators.maxLength(15)]],
        // mtsrolbindChr: [empresaForm.mtsrolbindChr, [Validators.required, Validators.maxLength(15)]],
        mtspasswordChr:[empresaForm.mtspasswordChr, Validators.required],
        trxdaylimitNum: [empresaForm.trxdaylimitNum, Validators.required],
        trxmonthlimitNum: [empresaForm.trxmonthlimitNum, Validators.required],
        trxtotallimitNum: [empresaForm.trxtotallimitNum, Validators.required],
        priority: [empresaForm.priority]
      });
    }else{
      this.empresaFormGroup = this._formBuilder.group({
        idcompanyPk: [empresaForm.idcompanyPk],
        companynameChr: [empresaForm.companynameChr, [Validators.required,  Validators.maxLength(30)]],
        creationdateTim: [this._dateUtilService.convertStringDate(empresaForm.creationdateTim)],
        descriptionChr: [empresaForm.descriptionChr],
        stateChr: [empresaForm.stateChr, Validators.required],
        contactnameChr: [empresaForm.contactnameChr, [Validators.required, Validators.maxLength(50)]],
        emailChr: [empresaForm.emailChr, [Validators.email, Validators.maxLength(50)]],
        phonenumberChr: [empresaForm.phonenumberChr, Validators.required],
        lastopdateTim: [this._dateUtilService.convertStringDate(empresaForm.lastopdateTim)],
        trxdaycountNum:[empresaForm.trxdaycountNum, Validators.required],
        trxmonthcountNum:[empresaForm.trxmonthcountNum, Validators.required],
        trxtotalcountNum:[empresaForm.trxtotalcountNum, Validators.required],
        percentcommissionNum: [empresaForm.percentcommissionNum, [Validators.required, Validators.maxLength(4),
          Validators.pattern(REGEX_DECIMAL)]],
        mtsusrChr: [empresaForm.mtsusrChr, Validators.required],//this._generalHelper.convertNumberToMask(empresaForm.mtsusrChr)
        // mtsbandChr: [empresaForm.mtsbandChr, [Validators.required, Validators.maxLength(15)]],
        // mtsrolbindChr: [empresaForm.mtsrolbindChr, [Validators.required, Validators.maxLength(15)]],
        mtspasswordChr:[empresaForm.mtspasswordChr],
        trxdaylimitNum: [empresaForm.trxdaylimitNum, Validators.required],
        trxmonthlimitNum: [empresaForm.trxmonthlimitNum, Validators.required],
        trxtotallimitNum: [empresaForm.trxtotallimitNum, Validators.required],
        priority: [empresaForm.priority]
      });
    }
    this.loading = false;
    this.empresaFormGroup.valueChanges
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
   * Obtiene una empresa-dto por su id
   * @returns {Subscription}
   */
  getEmpresaById(empresaId: number) {
    return this._empresaService.getEmpresaById(empresaId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.empresa);
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
   * Carga campos requeridos para la creacion de una empresa-dto
   * @returns {Empresa}
   */
  cargarEmpresaForNew() : Empresa{
      let empresaTemp = new Empresa();
      empresaTemp.companynameChr = this.empresaFormGroup.value.companynameChr;
      empresaTemp.descriptionChr = this.empresaFormGroup.value.descriptionChr;
      empresaTemp.contactnameChr = this.empresaFormGroup.value.contactnameChr;
      empresaTemp.emailChr = this.empresaFormGroup.value.emailChr;
      empresaTemp.phonenumberChr = this.empresaFormGroup.value.phonenumberChr;
      empresaTemp.trxdaylimitNum = this.empresaFormGroup.value.trxdaylimitNum;
      empresaTemp.trxmonthlimitNum = this.empresaFormGroup.value.trxmonthlimitNum;
      empresaTemp.trxtotallimitNum = this.empresaFormGroup.value.trxtotallimitNum;
      empresaTemp.percentcommissionNum = this.empresaFormGroup.value.percentcommissionNum;
      empresaTemp.mtsusrChr = this.empresaFormGroup.value.mtsusrChr;//this._generalHelper.convertMaskToNumber(this.empresaFormGroup.value.mtsusrChr);
      empresaTemp.mtsbandChr = "";//this.empresaFormGroup.value.mtsbandChr;
      empresaTemp.mtsrolbindChr = "";//this.empresaFormGroup.value.mtsrolbindChr;
      empresaTemp.mtspasswordChr = this.empresaFormGroup.value.mtspasswordChr;
      empresaTemp.priority = this.empresaFormGroup.value.priority;
      return empresaTemp;
  }

  /**
   * Carga campos para la edicion de una empresa-dto
   * @returns {Empresa}
   */
  cargarEmpresaForEdit() : Empresa{
    let empresaTemp = new Empresa();
    empresaTemp.idcompanyPk = this.empresaFormGroup.value.idcompanyPk;
    empresaTemp.companynameChr = this.empresaFormGroup.value.companynameChr;
    empresaTemp.descriptionChr = this.empresaFormGroup.value.descriptionChr;
    empresaTemp.stateChr = this.empresaFormGroup.value.stateChr;
    empresaTemp.contactnameChr = this.empresaFormGroup.value.contactnameChr;
    empresaTemp.emailChr = this.empresaFormGroup.value.emailChr;
    empresaTemp.phonenumberChr = this.empresaFormGroup.value.phonenumberChr;
    empresaTemp.trxdaycountNum = this.empresaFormGroup.value.trxdaycountNum;
    empresaTemp.trxmonthcountNum = this.empresaFormGroup.value.trxmonthcountNum;
    empresaTemp.trxtotalcountNum = this.empresaFormGroup.value.trxtotalcountNum;
    empresaTemp.trxdaylimitNum = this.empresaFormGroup.value.trxdaylimitNum;
    empresaTemp.trxmonthlimitNum = this.empresaFormGroup.value.trxmonthlimitNum;
    empresaTemp.trxtotallimitNum = this.empresaFormGroup.value.trxtotallimitNum;
    empresaTemp.percentcommissionNum = this.empresaFormGroup.value.percentcommissionNum;
    empresaTemp.mtsusrChr = this.empresaFormGroup.value.mtsusrChr;//this._generalHelper.convertMaskToNumber(this.empresaFormGroup.value.mtsusrChr);
    empresaTemp.mtsbandChr = "";//this.empresaFormGroup.value.mtsbandChr;
    empresaTemp.mtsrolbindChr = "";//this.empresaFormGroup.value.mtsrolbindChr;
    empresaTemp.mtspasswordChr = this.empresaFormGroup.value.mtspasswordChr;
    empresaTemp.priority = this.empresaFormGroup.value.priority;
    return empresaTemp;
  }

  /**
   * Submit, se encarga de redirigir al metodo correcto para la creacion o edicion de una empresa-dto
   */
  saveCompany(){
      if(this.empresaFormGroup.valid && this.isNewCompany){
        this.crearEmpresa(this.cargarEmpresaForNew());
      }else if(this.empresaFormGroup.valid && !this.isNewCompany){
        this.editarEmpresa(this.cargarEmpresaForEdit());
      }
  }

  /**
   * Creacion de una nueva empresa-dto
   * @param {Empresa} empresa
   * @returns {Subscription}
   */
  crearEmpresa(empresa: Empresa){
    this.loading = true;
    return this._empresaService.crearEmpresa(empresa).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/companyList']);
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
   * Procesar la edicion de los campos de una empresa-dto.
   * @param {Empresa} empresa
   * @returns {Subscription}
   */
  editarEmpresa(empresa: Empresa){
    this.loading = true;
    return this._empresaService.editarEmpresa(empresa).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/companyList']);
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

  isSuperCompany(): boolean{
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
  }


  callSuperCompany(){
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
