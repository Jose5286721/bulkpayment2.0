import {Component, OnInit} from '@angular/core';
import {PaymentOrderService} from "../orden-pago/ordenes-de-pago/ordenes-pago-service";
import {MESSAGEERROR} from "../utils/gop-constantes";
import {Message} from "primeng/primeng";
import {HomeService} from "./home.service";
import {SaldoCuentaRequestDto} from "../dto/saldo-cuenta-dto/saldocuenta-request";
import {PermisosService} from "../security/permiso.service";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {CompanyInfoDTO} from "../dto/payment-order-dto/paymentorder.info.dto";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  // Message
   msgs: Message[] = [];
   inProcessPO: number;
   inPendingPO: number;
   payedPO: number;
   notPayedPO: number;
   saldoActual: string;
   loading : boolean;
   infoCompany:CompanyInfoDTO = new CompanyInfoDTO();
   lineChartData: Array<any>;
  constructor(private _paymentOrder: PaymentOrderService, private _homeService: HomeService,  private _permisoService: PermisosService,
              private _autenticacionService : AutenticacionService){
    this.inProcessPO = 0;
    this.inPendingPO = 0;
    this.payedPO = 0;
    this.notPayedPO = 0;
    this.lineChartData =  [
      {data: [this.payedPO, this.notPayedPO], label: 'Transacciones'}

    ];
  }

  ngOnInit(){
    this.msgs = null;
    this.getInfoCompany();
    this.getSaldoActual();
  }

  getSaldoActual(){
    if(this.canSeeCreditNum()){
      this.loading = true;
      let saldoactual = new SaldoCuentaRequestDto();
      saldoactual.code = "s";
      saldoactual.idCompany = this._autenticacionService.selectedCompany.idcompanyPk;
      return this._homeService.getSaldoActual(saldoactual).subscribe(
        (res : any) =>{
          if(res.data != null && res.data.body != null){
            this.saldoActual = (res.data.body.saldoA);
          }else{
            console.log(res.errors.message.message);
          }
          this.loading = false;
        }, error => {
          console.log(error);
          this.loading = false;
          this.showError(error, MESSAGEERROR);
        }
      )
    }
  }
  getInfoCompany(){
    this.loading = true;
    this._paymentOrder.getInfoCompany(this._autenticacionService.selectedCompany.idcompanyPk).subscribe(
      (res:any) => {
        if (res.data != null && res.data.body != null && res.data.body.infoCompany!=null ) {
          this.infoCompany = res.data.body.infoCompany
        }
        this.loading = false;
      }, error => {
        console.log(error);
        this.loading = false;
        this.showError(error, MESSAGEERROR);
      }
    )


  }
  canSeeCreditNum(){
    return this._permisoService.canviewcompanycredit;
  }

  countPayedPaymentOrder() {
    return this._paymentOrder.countPaymentOrderByState('TP').subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.payedPO = (res.data.body.cantidad);
          this.lineChartData  = [
            {data: [this.payedPO, this.notPayedPO], label: 'Transacciones'}

          ];
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error,MESSAGEERROR);
      });
  }

  showError(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:mensaje, detail:detail});
  }
}
