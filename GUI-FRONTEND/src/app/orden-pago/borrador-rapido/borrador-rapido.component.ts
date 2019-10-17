import {AutoComplete, ConfirmationService, DataTable, Message} from "primeng/primeng";
import {ChangeDetectorRef, Component, OnInit, ViewChild} from "@angular/core";
import {MESSAGEERROR, MESSAGES} from "../../utils/gop-constantes";
import {Firmante} from "../../model/Firmante";
import {Empresa} from "../../model/empresa";
import {TipoOrdenPago} from "../../model/tipo-orden-pago";
import {Workflow} from "../../model/workflow";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PermisosService} from "../../security/permiso.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {TipoOrdenPagoService} from "../tipo-orden-pago/tipo-orden-pago.service";
import {WorkflowService} from "../../administracion/workflow/workflow-service";
import {BorradorService} from "../borrador/borrador.service";
import {DateUtilService} from "../../utils/date-util.service";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {Draft} from "../../model/draft";
import {isUndefined} from "util";
import {Beneficiario} from "../../model/beneficiario";
import {DraftRequest} from "../../dto/borrador-dto/DraftRequest";
import {UtilService} from "../../utils/util.service";
import {DateValidators} from "../../utils/date.validator";
@Component({
  selector: 'app-borrador-rapido',
  templateUrl: './borrador-rapido.component.html',
  styleUrls: ['./borrador-rapido.component.css'],
  providers: [ConfirmationService]
})
export class BorradorRapidoComponent implements OnInit {
  @ViewChild('workflowAutocomplete') workflowAutocomplete: AutoComplete;
  //spinner
  loading: boolean;
  // Message
  msgs: Message[] = [];
  readonly MESSAGES = MESSAGES;
  // FIRMANTES
  firmantesDt: Firmante[];
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';
  filteredCompany: any[];
  filteredPaymentOrderType: any[];
  listaEmpresa: Empresa[];
  listaTipoOrdenPago: TipoOrdenPago[];
  accionTitle: string;
  workflow: Workflow;
  draftFormGroup: FormGroup;
  montoTotal:number =0;
  isNewDraft: boolean;
  superCompany: boolean;
  workflowDisplay: boolean;
  filteredWorkflow: any[];
  listaWorkflow: Workflow[];
  montoTotalBeneficiarios: number;
  recurrenciaNum: boolean;
  //BENEFICIARIOS
  beneficiariosDt: Beneficiario[];
  accountRegExpr: RegExp;
  constructor(private _permisoService: PermisosService, private _router: Router, private _messageUtilService: MessageUtilService,
              private _empresaService: EmpresaService, private _tipoOrdenPagoService: TipoOrdenPagoService, private _formBuilder: FormBuilder,
              private _activatedRoute: ActivatedRoute, private _workFlowService: WorkflowService, private _borradorService: BorradorService,
              private _dateUtilService: DateUtilService, private _confirmationService: ConfirmationService, private cdRef: ChangeDetectorRef,
              private _utilService: UtilService) {
  }
  ngOnInit() {
    this.loading = true;
    this.isNewDraft = true;
    this.workflow = null;
    this.recurrenciaNum = false;
    this.montoTotalBeneficiarios = 0;
    this.beneficiariosDt=[];
    this.beneficiariosDt.push(new Beneficiario());
    this.getAccountRegExp();
    this.buildForm(null);
    this.rendererCompany();
    this.obtenerPaymentOrderType();
    this.isEditOrNew();
  }

  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
        this.accionTitle = "Agregar Borrador";
        this.isNewDraft = true;
    });
  }

  buildForm(draft: Draft) {
    if (draft == null) {
      draft = new Draft();
    }
    this.draftFormGroup = this._formBuilder.group({
      iddraftPk: [draft.iddraftPk],
      company: [draft.company],
      paymentordertype: [draft.paymentordertype, Validators.required],
      descriptionChr: [draft.descriptionChr],
      stateChr: ['AC'],
      creationdateTim: [this._dateUtilService.convertStringDate(draft.creationdateTim)],
      totalAmount: [],
      recurrentNum: [draft.recurrentNum],
      workflow: [draft.workflow, Validators.required],
      notifybenficiaryNum: [true],
      notifysignerNum: [true],
      notifygenNum: [true],
      notifycancelNum: [true],
      saldo: [0],
      montoTotal: [this.montoTotal],
      paiddateTim: [this._dateUtilService.convertStringDate(draft.paiddateTim),DateValidators.dateGreaterToday()],
      generateDayNum: [null],
      totalBeneficiary: [],
      beneficiaries: [this.beneficiariosDt],
    });
    this.loading = false;
    this.draftFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }

  /**
   * Obtiene lista de todos los workflows
   * @returns {Subscription}
   */
  obtenerWorkflows() {
    let company = this.draftFormGroup.value.company;
    //Si no esta seteado un workflow limpiamos el componente autocomplete
    if (this.draftFormGroup.value.workflow == null) {
      this.firmantesDt = null;
      this.workflowAutocomplete.value = null;
      this.workflowAutocomplete.inputEL.nativeElement.value = null;
    }
    if (this.isSuperCompany() && company != null) {
      return this._workFlowService.getAllWorkflowsByCompany(company.idcompanyPk).subscribe(
        (res: any) => {
          if (res.data != null && res.data.body != null) {
            this.listaWorkflow = res.data.body.workflows;
            this.workflowDisplay = true;
          } else {
            console.log(res.errors.message.message);
          }
        },
        error => {
          console.log(error);
          this.showError(error, MESSAGEERROR);
        });
    } else {
      return this._workFlowService.getAllWorkflows().subscribe(
        (res: any) => {
          if (res.data != null && res.data.body != null) {
            this.listaWorkflow = res.data.body.workflows;
            this.workflowDisplay = true;
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
  saveDraft() {
    let request = this.cargarRequest();
    if (request !== null) {
      this.loading = true;
      let subscribe = this.isNewDraft ? this._borradorService.crearBorrador(request) :
        this._borradorService.editarBorrador(this.draftFormGroup.value.iddraftPk, request);
      this.loading = false;
      subscribe.subscribe((res: any) => {
          if (res.data != null && res.data.body != null) {
            this._messageUtilService.setMessagesComponent('success', res.data.body.mensaje, res.data.message.detail);
            this.navigateDraftList();
          } else {
            setTimeout(() => {
              this.loading = false;
              this.showWarn(res.errors.message.detail, res.errors.message.message);
            }, 500);
          }
        },
        error => {
          console.log(error);
          this.loading = false;
        });
    }
  }
  cargarRequest(): DraftRequest {
    if (this.draftFormGroup.valid) {
      let request: DraftRequest = new DraftRequest();
      request.stateChr = this.draftFormGroup.value.stateChr;
      request.descriptionChr = this.draftFormGroup.value.descriptionChr;
      request.idCompany = this.draftFormGroup.value.company ? this.draftFormGroup.value.company.idcompanyPk : null;
      request.idPaymentordertype = this.draftFormGroup.value.paymentordertype ?
        this.draftFormGroup.value.paymentordertype.idorderpaymenttypePk : null;
      request.recurrentNum = this.draftFormGroup.value.recurrentNum;
      request.idWorkflow = this.draftFormGroup.value.workflow ? this.draftFormGroup.value.workflow.idworkflowPk : null;
      request.notifybenficiaryNum = this.draftFormGroup.value.notifybenficiaryNum;
      request.notifysignerNum = this.draftFormGroup.value.notifysignerNum;
      request.notifygenNum = this.draftFormGroup.value.notifygenNum;
      request.notifycancelNum = this.draftFormGroup.value.notifycancelNum;
      request.fromdateTim = DateUtilService.convertStringDateUsToEs(this.draftFormGroup.value.fromdateTim);
      request.todateTim = DateUtilService.convertStringDateUsToEs(this.draftFormGroup.value.todateTim);
      request.paiddateTim = DateUtilService.convertStringDateUsToEs(this.draftFormGroup.value.paiddateTim);
      request.beneficiariesToAdd = this.validarBeneficiarios(this.beneficiariosDt);
      request.fastDraft = true;
      request.generateDayNum = this.draftFormGroup.value.generateDayNum;
      //      let newFile = new new File([this.newBeneficiarios.join('\n')], {type: "text/plain", endings: 'native'});
      /*this._borradorService.procesarArchivo(new File([this.newBeneficiarios.join('\n')],"beneficiarios.csv",{type: "text/csv"}), request.idCompany.toString(), request.idWorkflow.toString()).subscribe((res: any) => {
          if (res.data != null && res.data.body != null) {
            this.newBeneficiarios = res.data.body.beneficiarios;
          } else {
            this.newBeneficiarios = null;
          }
          this.loading = false;
        },
        error => {
          console.log(error);
          this.newBeneficiarios = null;
          this.loading = false;
        });*/
      return request;
    }
  }
  validarBeneficiarios(beneficiario:Beneficiario[]):Beneficiario[]{
    if(beneficiario){
      var i = beneficiario.length
      while (i--) {
        if (beneficiario[i] == null || beneficiario[i].phonenumberChr == null || beneficiario[i].phonenumberChr.length == 0) {

          beneficiario.splice(i, 1);
        }
      }
      return beneficiario;
    }
  }
  navigateDraftList() {
    this._router.navigate(['/draftList']);
  }
  seleccionarWorkflow(workflow: any) {
    if (workflow != null && workflow.value != null) {
      this.draftFormGroup.controls['workflow'].setValue(workflow.value);
    }
    this.workflowDisplay = false;
  }
  filterWorkflow(event) {
    this.filteredWorkflow = [];
    if (this.listaWorkflow) {
      for (let i = 0; i < this.listaWorkflow.length; i++) {
        let workflow = this.listaWorkflow[i];
        if (workflow.workflownameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredWorkflow.push(workflow);
        }
      }
    }
  }
  cancelarSeleccionWorkflow() {
    this.draftFormGroup.value.workflow = null;
    this.workflowDisplay = false;
    this.firmantesDt = null;
  }
  obtenerFirmantes(event: any) {
    this.loading = true;
    if (event != null && event.value != null) {
      this._workFlowService.getFirmantesByWorkflowId(event.value.idworkflowPk).subscribe(
        (res: any) => {
          if (res.data != null && res.data.body != null) {
            this.firmantesDt = res.data.body.firmantes;
            this.loading = false;
          } else {
            console.log(res.errors.message.message);
            this.loading = false;
          }
        },
        error => {
          console.log(error);
          this.showError(error, MESSAGEERROR);
          this.loading = false;
        });
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
   * Obtiene lista de todos los tipo de ordenes de pago
   * @returns {Subscription}
   */
  obtenerPaymentOrderType() {
    this.loading = true;
    return this._tipoOrdenPagoService.getAllTipoOrdenPago().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaTipoOrdenPago = res.data.body.tipoOrdenPago;
        } else {
          console.log(res.errors.message.message);
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
        this.showError(error, MESSAGEERROR);
      });
  }

  /**
   * Obtiene lista de todas las empresas
   * @returns {Subscription}
   */
  obtenerEmpresas() {
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
  canviewCompanyCredit(): boolean {
    return this._permisoService.canviewcompanycredit;
  }
  agregarBeneficiario(beneficiario: Beneficiario){
    if(this.beneficiariosDt.length<10) {
      this.beneficiariosDt.push(new Beneficiario());
      this.draftFormGroup.patchValue({
        totalBeneficiary: [this.beneficiariosDt.length]
        }, false
      );
    }
  }
  borrarBeneficiario(beneficiario:any){
    if(beneficiario!=null && this.beneficiariosDt.length>1) {
      if(beneficiario.phonenumberChr) {

        let index = this.beneficiariosDt.findIndex(d => d.phonenumberChr.includes(beneficiario.phonenumberChr, 0));
        this.beneficiariosDt.splice(index, 1);
      }else{
        if(this.beneficiariosDt.length>0){
          this.beneficiariosDt.splice(this.beneficiariosDt.length-1, 1);
        }
      }

      this.updateValues();
    }

  }
  updateValues(){
    this.montoTotal =0;
    this.beneficiariosDt.forEach(beneficiary =>{if(!isNaN(beneficiary.amount)) this.montoTotal+=Number(beneficiary.amount)});
    this.draftFormGroup.patchValue({
        montoTotal: [this.montoTotal]
      }, false
    );
    this.draftFormGroup.patchValue({
        totalBeneficiary: [this.beneficiariosDt.length]
      }, false
    );
  }

  rendererCompany() {
    if (isUndefined(this.isSuperCompany())) {
      this.isSuperCompanyService();
    } else if (this.isSuperCompany()) {
      this.obtenerEmpresas();
    }
  }
  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService() {
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
          if (res.data.body.superCompany) {
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
  empresaSeleccionada(): boolean {
    if (this.superCompany && this.draftFormGroup && this.draftFormGroup.value.company) {
      return true;
    } else if (!this.superCompany && this.draftFormGroup) {
      return true;
    }
    return false;
  }

  getAccountRegExp(){
    return this._utilService.getPhoneNumberRegExpr().subscribe(
      (res: any) =>{
        if(res.data && res.data.body){
          this.accountRegExpr = new RegExp(res.data.body.NumberPhoneregExp);
        }else{
          console.log(res.errors);
        }
      }
    )
  }

  /**
   * Valida si los datos introducidos en la carga de beneficiarios son validos
   * @returns {boolean}
   */
  isValidBeneficiaries(): boolean {
    return this.draftFormGroup.value.beneficiaries
      .every(value =>
        this.isValidAccount(value.phonenumberChr) && this.isValidAmount(value.amount)) && this.hasRepeatedValues();
  }
  hasRepeatedValues() : boolean{
    let benef: Beneficiario[] = [];
    benef = benef.concat(this.draftFormGroup.value.beneficiaries);
    benef.sort(((a, b) => (a.phonenumberChr > b.phonenumberChr) ? 1 : ((b.phonenumberChr > a.phonenumberChr) ? -1 : 0)));
    for (let i = 0; i < benef.length-1; i++) {
      if(benef[i].phonenumberChr===benef[i+1].phonenumberChr){
        return false;
      }
    }
    benef.sort(((a, b) => (a.subscriberciChr > b.subscriberciChr) ? 1 : ((b.subscriberciChr > a.subscriberciChr) ? -1 : 0)));
    for (let i = 0; i < benef.length-1; i++) {
      if(benef[i].subscriberciChr===benef[i+1].subscriberciChr){
        return false;
      }
    }
    return true;
  }


  /**
   * Metodo para determinar si el numero de cuenta es valido
   * @param {string} value
   * @returns {boolean}
   */
  isValidAccount(value: string): boolean{
    return this.accountRegExpr.test(value);

  }

  /**
   * Verifica si el monto introducido en la carga de beneficiarios es valido
   * @param value
   * @returns {boolean}
   */
  isValidAmount(value: any): boolean{
    return value && !isNaN(value) ;
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
  }
  clearWorkflow(event: any) {
    this.draftFormGroup.controls['workflow'].setValue(null);
  }
  isSuperCompany(): boolean {
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
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
