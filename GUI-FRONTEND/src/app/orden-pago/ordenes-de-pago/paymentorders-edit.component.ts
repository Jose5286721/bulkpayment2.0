import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {PermisosService} from "../../security/permiso.service";
import {EmpresaService} from '../../administracion/empresa/empresa.service';
import {Empresa} from '../../model/empresa';
import {PaymentOrderService} from './ordenes-pago-service';
import {DateUtilService} from '../../utils/date-util.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Paymentoder} from '../../model/paymentoder';
import {BeneficiarioDto} from '../../dto/beneficiario-dto/beneficiario.dto';
import {PaymentOrderCancelDto} from '../../dto/payment-order-dto/paymentorder.cancel.dto';
import {FirmantesDto} from '../../dto/firmantes-dto/firmantes.dto';
import {MESSAGEERROR} from "../../utils/gop-constantes";
import {
  CANCELADA,
  ERROR,
  FIRMA_EN_PROCESO, NO_REVERTIDA, PAGO_EN_PROCESO,
  PARCIALMENTE_PAGADA, PARCIALMENTE_REVERTIDA, PAYMENT_ORDER_STATES,
  PENDIENTE_PAGO, REVERSION_EN_PROCESO, REVERTIDA, SATISFACTORIO
} from "../../utils/paymentorders-states";

@Component({
  selector: 'app-paymentorders-edit',
  templateUrl: './paymentorders-edit.component.html',
  styleUrls: ['./paymentorders-edit.component.css']
})
export class PaymentorderEditComponent implements OnInit {
  //Referencia a componentes
  @ViewChild('dt') dt: DataTable;

  //spinner
  loading = true;

  isNewPaymentOrder: boolean;

  //DataTable
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';

  idPaymentOrderUrl: number;

  //Objetos Filtrados
  filteredCompany: any[];

  paymentOrderFormGroup: FormGroup;

  // Message
  msgs: Message[] = [];

  // Lista Firmantes
  paymentOrderDt: FirmantesDto[] = [];

  //Es Turno del Firmante Logueado
  isCurrentUsrTurn: boolean;

  //EsFirmante
  isSigner: boolean;

  //Disable Buttons
  disableButtons: boolean;

  estadoList = PAYMENT_ORDER_STATES;

  //listas
  listaEmpresa: Empresa[];
  beneficiariosDt: BeneficiarioDto[] = [];
  estadoActualOP: string;

  constructor(private _permisoService: PermisosService,
              private _messageUtilService: MessageUtilService,
              private _paymentOrderservice: PaymentOrderService,
              private _empresaService: EmpresaService,
              private _dateService: DateUtilService,
              private _formBuilder: FormBuilder,
              private _activatedRoute: ActivatedRoute,
              private _router: Router) {
  }

  ngOnInit() {
    this.loading = false;
    this.isNewPaymentOrder = true;
    this.estadoActualOP = null;
    this.disableButtons = false;
    this.buildForm(null);
    this.init();
    this.mostrarMensaje();
    this.isEditOrNew();
  }

  init() {
    this.beneficiariosDt = [];
    this.isCurrentUsrTurn = false;
    this.isSigner = false;
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un workflow,
   * sino un nuevo workflow
   */
  isEditOrNew() {
    this._activatedRoute.params.subscribe(params => {
      if (params['id']) {
        this.idPaymentOrderUrl = params['id'];
        this.isNewPaymentOrder = false;
        this.getPaymentOrderById(params['id']);
      } else {
        this.isNewPaymentOrder = true;
      }
    });
  }

  /**
   * Firma una Orden de Pago
   * @returns {Subscription}
   */
  firmarOrdenPago() {
    return this._paymentOrderservice.signPaymentOrder(this.getPaymentOrderId()).subscribe(
      (res: any) => {
        if (res.data !== null && res.data.body !== null) {
          this.showSuccess(res.data.message.detail, res.data.body.mensaje);
          this.disableButtons = true;
          this.getPaymentOrderById(this.getPaymentOrderIdNumber());
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  /**
   * Reintenta una Orden de Pago
   * @returns {Subscription}
   */
  reintentarOrdenPago() {
    return this._paymentOrderservice.retryPaymentOrder(this.getPaymentOrderId()).subscribe(
      (res: any) => {
        if (res.data !== null && res.data.body !== null) {
          this.showSuccess(res.data.message.detail, res.data.body.mensaje);
          this.disableButtons = true;
          this.getPaymentOrderById(this.getPaymentOrderIdNumber());
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  /**
   * Cancela una Orden de Pago
   * @returns {Subscription}
   */
  cancelarOrdenPago() {
    return this._paymentOrderservice.cancelPaymentOrder(this.loadPaymentOrdertoCancel()).subscribe(
      (res: any) => {
        if (res.data !== null && res.data.body !== null) {
          this.showSuccess(res.data.message.detail, res.data.body.mensaje);
          this.disableButtons = true;
          this.getPaymentOrderById(this.getPaymentOrderIdNumber());
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  getPaymentOrderId(): string {
    return this.paymentOrderFormGroup.value.idpaymentOrderPk;
  }
  getPaymentOrderIdNumber(): number{
    return parseInt(this.paymentOrderFormGroup.value.idpaymentOrderPk,10 );
  }

  loadPaymentOrdertoCancel(): PaymentOrderCancelDto {
    let cancelDtTemp = new PaymentOrderCancelDto();
    cancelDtTemp.idPaymentOrder = this.paymentOrderFormGroup.value.idpaymentOrderPk;
    return cancelDtTemp;
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  isSuperCompany(): boolean {
    return this._permisoService.superCompany;
  }

  buildForm(paymentOrderform: Paymentoder) {

    if (this.isNewPaymentOrder) {
      this.paymentOrderFormGroup = this._formBuilder.group({
        idpaymentOrderPk: [],
        company: [],
        descriptionChr: [],
        stateChr: [],
        creationdate: [],
        lastmodification: [],
        beneficiarioslength: [0],
        paymentlength: [0],
        paymenterrorlength: [0],
        signerslength: [0],
        totalammount: [],
        paymentammount: []
      });
    } else {
      paymentOrderform = paymentOrderform ? paymentOrderform : new Paymentoder();
      const state = this.verificarEstadoOrdenPago(paymentOrderform.stateChr) ?
        this.verificarEstadoOrdenPago(paymentOrderform.stateChr).label : '';
      this.paymentOrderFormGroup = this._formBuilder.group({
        idpaymentOrderPk: [paymentOrderform.idpaymentorderPk],
        company: [paymentOrderform.nombreEmpresa],
        descriptionChr: [paymentOrderform.descripcion],
        stateChr: [state],
        creationdate: [paymentOrderform.creationdateTim],
        lastmodification: [paymentOrderform.updatedateTim],
        beneficiarioslength: [paymentOrderform.beneficiarieslength],
        paymentlength: [paymentOrderform.paymentsuccessNum],
        paymenterrorlength: [paymentOrderform.paymenterrorNum],
        signerslength: [paymentOrderform.signerslength],
        totalammount: [paymentOrderform.montoTotal],
        paymentammount: [paymentOrderform.amountpaidChr]
      });
    }
    this.loading = false;
    this.paymentOrderFormGroup.valueChanges
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
   * Obtiene una Orden de Pago por su id
   * @returns {Subscription}
   */
  getPaymentOrderById(paymentOrderId: number) {
    return this._paymentOrderservice.getPaymentOrderById(paymentOrderId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.ordenpago);
          if (res.data.body.ordenpago) {
            this.estadoActualOP = res.data.body.ordenpago.stateChr;
            this.paymentOrderDt = res.data.body.ordenpago.firmantes;
            this.isCurrentUsrTurn = res.data.body.ordenpago.isCurrentUserTurn;
            this.isSigner = res.data.body.ordenpago.isSigner;
          }
        } else {
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
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

  navigateToPaymentOrderList() {
    this._router.navigate(['/paymentOrderList']);
  }

  ocultarOperaciones() {
    return (((this.estadoActualOP == FIRMA_EN_PROCESO.value)) ||
      ((this.estadoActualOP == PARCIALMENTE_PAGADA.value || this.estadoActualOP == ERROR.value))
      || ((this.estadoActualOP == FIRMA_EN_PROCESO.value || this.estadoActualOP == PENDIENTE_PAGO.value)));
  }

  mostrarBotonFirma() {
    return (this.isSigner && this.estadoActualOP == FIRMA_EN_PROCESO.value && this.isCurrentUsrTurn);
  }

  mostrarBotonReintentar() {
    return (this.isSigner && (this.estadoActualOP == PARCIALMENTE_PAGADA.value || this.estadoActualOP == ERROR.value || this.estadoActualOP == PENDIENTE_PAGO.value));
  }

  mostrarBotonCancelar() {
    return (this.isSigner && (this.estadoActualOP == FIRMA_EN_PROCESO.value || this.estadoActualOP == PENDIENTE_PAGO.value));
  }

  verificarEstadoOrdenPago(state) {
    let stateCurrent = null;
    switch (state) {
      case FIRMA_EN_PROCESO.value : {
        stateCurrent = FIRMA_EN_PROCESO;
        break;
      }
      case CANCELADA.value : {
        stateCurrent = CANCELADA;
        break;
      }
      case PENDIENTE_PAGO.value : {
        stateCurrent = PENDIENTE_PAGO;
        break;
      }
      case PAGO_EN_PROCESO.value : {
        stateCurrent = PAGO_EN_PROCESO;
        break;
      }
      case SATISFACTORIO.value : {
        stateCurrent = SATISFACTORIO;
        break;
      }
      case PARCIALMENTE_PAGADA.value : {
        stateCurrent = PARCIALMENTE_PAGADA;
        break;
      }
      case ERROR.value : {
        stateCurrent = ERROR;
        break;
      }
      case REVERSION_EN_PROCESO.value : {
        stateCurrent = REVERSION_EN_PROCESO;
        break;
      }
      case REVERTIDA.value : {
        stateCurrent = REVERTIDA;
        break;
      }
      case PARCIALMENTE_REVERTIDA.value : {
        stateCurrent = PARCIALMENTE_REVERTIDA;
        break;
      }
      case NO_REVERTIDA.value : {
        stateCurrent = NO_REVERTIDA;
        break;
      }
      case null : {
        stateCurrent = {value: 0, label: 'Error'};
        break;
      }
    }
    return stateCurrent;
  }
}
