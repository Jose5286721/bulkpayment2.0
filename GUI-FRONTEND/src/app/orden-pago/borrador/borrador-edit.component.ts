import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AutoComplete, Button, ConfirmationService, DataTable, FileUpload, Message} from "primeng/primeng";
import {
  CANT_FILAS,
  COMPLETE_EXAMPLE_TEMPLATE,
  MESSAGEERROR,
  MESSAGES,
  PRIMERA_PAGINA,
  SHORT_EXAMPLE_TEMPLATE
} from "../../utils/gop-constantes";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TipoOrdenPagoService} from "../tipo-orden-pago/tipo-orden-pago.service";
import {EmpresaService} from "../../administracion/empresa/empresa.service";
import {WorkflowService} from "../../administracion/workflow/workflow-service";
import {MessageUtilService} from "../../utils/message-util.service";
import {TipoOrdenPago} from "../../model/tipo-orden-pago";
import {Workflow} from "../../model/workflow";
import {Empresa} from "../../model/empresa";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Draft} from "../../model/draft";
import {Firmante} from "../../model/Firmante";
import {Beneficiario} from "../../model/beneficiario";
import {BorradorService} from "./borrador.service";
import {DraftRequest} from "../../dto/borrador-dto/DraftRequest";
import {DateUtilService} from "../../utils/date-util.service";
import {TemplatesService} from "../../system/plantillas/templates.service";
import {UtilService} from "../../utils/util.service";
import {DateValidators} from "../../utils/date.validator";

@Component({
  selector: 'app-borrador-edit',
  templateUrl: './borrador-edit.component.html',
  styleUrls: ['./borrador-edit.component.css'],
  providers: [ConfirmationService]
})
export class BorradorEditComponent implements OnInit {

  @ViewChild('workflowAutocomplete') workflowAutocomplete: AutoComplete;
  @ViewChild('fileUpload') fileUpload: FileUpload;
  //spinner
  loading: boolean;

  //File
  private fileList: FileList[];
  selectedFile: File;
  uploadedFiles: any[] = [];
  csvmimetype = 'application/csv, application/x-csv,text/csv, text/comma-separated-values, text/x-comma-separated-values';
  stateDraft: any[] = [{label: "Activo", value: "AC"}, {label: "Inactivo", value: "IN"}];

  // Message
  msgs: Message[] = [];
  readonly MESSAGES = MESSAGES;
  beneficiariesToDelete: Beneficiario[] =[];

  //DataTable
  //BENEFICIARIOS
  actualBeneficiaries: Beneficiario[] = [];

  preBeneficiaries: Beneficiario[] = [];
  selectBeneficiaries: Beneficiario[] = [];

  idCompany: string;
  idWorkflow: string;
  // FIRMANTES
  firmantesDt: Firmante[];
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = 'beneficiary.phonenumberChr';


  shortExample = SHORT_EXAMPLE_TEMPLATE;
  completeExample = COMPLETE_EXAMPLE_TEMPLATE;

  //
  filteredCompany: any[];
  filteredPaymentOrderType: any[];
  filteredState: any[];
  listaEmpresa: Empresa[];
  listaTipoOrdenPago: TipoOrdenPago[];
  accionTitle: string;
  workflow: Workflow;
  draftFormGroup: FormGroup;
  isNewDraft: boolean;
  superCompany: boolean;
  recurrenciaNum: boolean;
  workflowDisplay: boolean;
  templateDisplay: boolean;
  filteredWorkflow: any[];
  minDate = new Date();
  listaWorkflow: Workflow[];
  hayBeneficiarios:boolean;
  constructor(private _permisoService: PermisosService, private _router: Router, private _messageUtilService: MessageUtilService,
              private _empresaService: EmpresaService, private _tipoOrdenPagoService: TipoOrdenPagoService, private _formBuilder: FormBuilder,
              private _activatedRoute: ActivatedRoute, private _workFlowService: WorkflowService, private _borradorService: BorradorService,
              private _dateUtilService: DateUtilService, private _confirmationService: ConfirmationService, private cdRef: ChangeDetectorRef,
              private _templateService: TemplatesService, private _utilService : UtilService) {
  }

  ngOnInit() {
    this.isNewDraft = true;
    this.workflow = null;
    this.recurrenciaNum = false;
    this.hayBeneficiarios=false;
    this.buildForm(null);
    this.rendererCompany();
    this.obtenerPaymentOrderType();
    this.isEditOrNew();
    this.getFileMaxSize();
  }
  getFileMaxSize(){
    this._utilService.getMaxFileSize().subscribe( (res:any) =>{
      if(res && res.data){
        this.fileUpload.maxFileSize = res.data.body.csvSizeValue;

      }
    },error => {
      console.log(error);
    });
  }
  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
      if (params['id']) {
        this.accionTitle = 'Editar Borrador';
        this.isNewDraft = false;
        this.getDraftById(params['id']);
      } else {
        this.accionTitle = "Agregar Borrador";
        this.isNewDraft = true;
      }
    });
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


  getDraftById(id) {
    this.loading = true;
    return this._borradorService.getDraftById(id).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.borrador);
          this.getBeneficiariesByCriPagFirstTime(res.data.body.borrador.iddraftPk, '', this.properties,  PRIMERA_PAGINA, CANT_FILAS);
          this.recurrenciaNum = (res.data.body.borrador) ? res.data.body.borrador.recurrentNum : false;
          this.cdRef.detectChanges();
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
        this.loading=false;
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
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

  /**
   * Obtiene lista de todos los tipo de ordenes de pago
   * @returns {Subscription}
   */
  obtenerPaymentOrderType() {
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

  isSuperCompany(): boolean {
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
  }

  canviewCompanyCredit(): boolean {
    return this._permisoService.canviewcompanycredit;
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

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs()) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  navigateDraftList() {
    this._router.navigate(['/draftList']);
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
      stateChr: [draft.stateChr],
      creationdateTim: [this._dateUtilService.convertStringDate(draft.creationdateTim)],
      recurrentNum: [draft.recurrentNum],
      workflow: [draft.workflow, Validators.required],
      notifybenficiaryNum: [draft.notifybenficiaryNum],
      notifysignerNum: [draft.notifysignerNum],
      notifygenNum: [draft.notifygenNum],
      notifycancelNum: [draft.notifycancelNum],
      saldo: [0],
      montoTotal: [draft.amount],
      fromdateTim: [this._dateUtilService.convertStringDate(draft.fromdateTim)],
      todateTim: [this._dateUtilService.convertStringDate(draft.todateTim)],
      paiddateTim: [this._dateUtilService.convertStringDate(draft.paiddateTim)],
      generateDayNum: [draft.generatedayNum, Validators.pattern("^(3[01]|[12][0-9]|[1-9])$")],
      totalBeneficiary: [0],
      beneficiaries: [[]],
    });
    if(this.isNewDraft){
      this.draftFormGroup.controls['beneficiaries'].setValidators([Validators.required]);
      this.draftFormGroup.controls['paiddateTim'].setValidators([DateValidators.dateGreaterToday()]);

    }
    this.draftFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
  }

  onChangeRecurrentNum(event: any) {
    this.recurrenciaNum = event;
  }

  clearWorkflow(event: any) {
    this.draftFormGroup.controls['workflow'].setValue(null);
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
      request.beneficiariesToAdd = this.draftFormGroup.value.beneficiaries;
      request.beneficiariesToDelete = this.beneficiariesToDelete;
      request.generateDayNum = this.draftFormGroup.value.generateDayNum;
      request.fastDraft = false;
      return request;
    }
    return null;
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

  agregarBeneficiarios() {
    let beneficiaries = this.draftFormGroup.value.beneficiaries;
    let montoTotalBeneficiarios = this.draftFormGroup.value.montoTotal;
    if(beneficiaries.length>0){
      let actualMonto = beneficiaries.map(b => b.amount).reduce((a,b)=>a+b);
      montoTotalBeneficiarios = montoTotalBeneficiarios - actualMonto;
    }
    let newAmount = this.selectBeneficiaries.map(b => b.amount).reduce((a,b)=>a+b);
    montoTotalBeneficiarios = montoTotalBeneficiarios + newAmount;

    let totalBeneficiary = this.draftFormGroup.value.totalBeneficiary;
    if(totalBeneficiary!=0){
      totalBeneficiary = totalBeneficiary - beneficiaries.length;
    }
    totalBeneficiary = totalBeneficiary+ this.selectBeneficiaries.length;
    this.draftFormGroup.controls['montoTotal'].setValue(montoTotalBeneficiarios);
    this.draftFormGroup.controls['totalBeneficiary'].setValue(totalBeneficiary);
    this.draftFormGroup.controls['beneficiaries'].setValue(this.selectBeneficiaries);
    this.templateDisplay = false;
  }

  // /**
  //  * Metodo que compara objetos de tipo beneficiarios
  //  * Si coincide, entonces se resta lo asignado al beneficiario
  //  * @see reduceAmount
  //  * @param obj
  //  * @param {string} term
  //  * @returns {boolean}
  //  */
  // objectContains(obj, term: string): boolean {
  //   for (let key in obj) {
  //     if (obj[key].phonenumberChr.indexOf(term) != -1) {
  //       this.reduceAmount(obj[key].amount)
  //       return true;
  //     }
  //   }
  //   return false;
  // }
  //
  // /**
  //  * Reduce el monto del FormGroup
  //  * @param amount
  //  */
  // reduceAmount(amount: number){
  //   this.draftFormGroup.patchValue({
  //       montoTotal: [this.montoTotalBeneficiarios = this.montoTotalBeneficiarios - amount]
  //     }, false
  //   );
  // }


  selectFile(event) {
    let files = event.files;
    this.idCompany = this.draftFormGroup.value.company ? this.draftFormGroup.value.company.idcompanyPk : null;
    this.idWorkflow = this.draftFormGroup.value.workflow ? this.draftFormGroup.value.workflow.idworkflowPk : null;
    if (files && files.length > 0 && this.idWorkflow) {
      this.loading = true;
      this.selectedFile = <File>(files[0]).valueOf();
      this.procesarArchivo();

    }
  }
  procesarArchivo(){
    this._borradorService.procesarArchivo(this.selectedFile, this.idCompany, this.idWorkflow).subscribe((res: any) => {
        if (res.data != null && res.data.body != null && res.data.body.mensaje != null) {
          this.preBeneficiaries =res.data.body.beneficiarios;
          this.showInfo('',res.data.body.mensaje);
        } else {
          this.selectedFile = null;
          this.templateDisplay = false;
          this.showWarn(res.errors.message.detail,res.errors.message.message);

        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }
  isDeleted(beneficiario: Beneficiario):string {
   return beneficiario.isDeleted ? 'benef-deleted':'';
  }
  deleteBeneficiarioService(beneficiario: Beneficiario) {

     let montoTotalBeneficiarios = this.draftFormGroup.value.montoTotal;
    this.draftFormGroup.controls['montoTotal'].setValue(montoTotalBeneficiarios - beneficiario.amount);
    this.draftFormGroup.controls['totalBeneficiary'].setValue(this.draftFormGroup.value.totalBeneficiary-1);
    if(beneficiario.idbeneficiaryPk!=null){
      this.beneficiariesToDelete.push(beneficiario);
      this.actualBeneficiaries.find(x=>x.idbeneficiaryPk==beneficiario.idbeneficiaryPk).isDeleted = true;
    }else{
      this.draftFormGroup.controls['beneficiaries'].setValue(this.draftFormGroup.value.beneficiaries
        .filter((val) => val != beneficiario));
    }
    // this.draftFormGroup.controls['totalBeneficiary'].setValue(this.draftFormGroup.controls['beneficiaries'].value.length);
  }

  deleteBeneficiario(beneficiario: any) {
      this._confirmationService.confirm({
        message: '¿Está seguro de eliminar al beneficiario con número ' + beneficiario.phonenumberChr + ' del borrador?',
        header: 'Confirmar Eliminación',
        icon: 'fa fa-trash',
        accept: () => {
          this.deleteBeneficiarioService(beneficiario);
        },
        reject: () => {
        }
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

  paginate(event) {
    this.page = this.size = 0;
    this.page = event.page + 1;
    this.size = event.rows;
    this.getBeneficiariesByCriPag(this.draftFormGroup.value.iddraftPk, this.direction, this.properties, this.page.toString(), this.size.toString());
  }

  getBeneficiariesByCriPag(iddraftPk: any, direction: string, properties: string, page: string, size: string) {
    this.msgs = null;
    this.loading = true;
      this._borradorService.getBeneficiariesByCriPag(iddraftPk, direction, properties, page, size)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.actualBeneficiaries = res.data.body.beneficiarios;
              this.beneficiariesToDelete.forEach( x => {
                let toDelete = this.actualBeneficiaries.find(b => b.idbeneficiaryPk== x.idbeneficiaryPk);
                if(toDelete) toDelete.isDeleted= true;
              });
              this.totalRecords = res.meta.totalCount;

            } else {
              this.actualBeneficiaries =[];
              this.totalRecords = 0;
            }
            this.loading = false;

          },
          error => {
            this.loading = false;
            console.log(error);
          });
  }

  getBeneficiariesByCriPagFirstTime(iddraftPk: any, direction: string, properties: string, page: string, size: string) {
    this.msgs = null;
    this.loading = true;
    this._borradorService.getBeneficiariesByCriPag(iddraftPk, direction, properties, page, size)
      .subscribe((res: any) => {
          if (res.data != null && res.data.body != null) {
            this.actualBeneficiaries = res.data.body.beneficiarios;
            this.beneficiariesToDelete.forEach( x => {
              let toDelete = this.actualBeneficiaries.find(b => b.idbeneficiaryPk== x.idbeneficiaryPk);
              if(toDelete) toDelete.isDeleted= true;
            });
            this.totalRecords = res.meta.totalCount;
            this.draftFormGroup.controls['totalBeneficiary'].setValue(this.totalRecords);

          } else {
            this.actualBeneficiaries =[];
            this.totalRecords = 0;
          }
          this.loading = false;

        },
        error => {
          this.loading = false;
          console.log(error);
        });
  }
  // getActualTotalAmount(iddraftPk: any){
  //   this.msgs = null;
  //   this.loading = true;
  //   this._borradorService.getActualTotalAmount(iddraftPk)
  //     .subscribe((res: any) => {
  //         if (res.data != null && res.data.body != null) {
  //           this.draftFormGroup.controls['montoTotal'].setValue(res.data.body.montoTotal);
  //         }
  //         this.loading = false;
  //       },
  //       error => {
  //         this.loading = false;
  //         console.log(error);
  //       });
  // }
  downloadExample(example:string){
    this._templateService.getCsvExample(example).subscribe(
      (res : Response) =>{
        console.log(res);
        const file = new Blob([res.blob()], {type:'text/csv;charset=utf-8' });
        const fileName = this._utilService.getFileNameFromResponse(res);
        saveAs(file, fileName);
      },error =>{
        console.log(error);
      });
  }

}
