import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import { AppComponent } from './app.component';
import { CabeceraComponent } from "./cabecera/cabecera.component";
import { FormsModule, ReactiveFormsModule} from "@angular/forms";
import { HomeComponent} from "./home/home.component";
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { MaterialModule} from "./utils/material.module";
import { LoginComponent} from "./login/login.component";
import {AutenticacionService} from "./autenticacion/autenticacion.service";
import { LoggedInGuard} from "./login/logged-in.guard";
import { HttpModule} from "@angular/http";
import { routing} from "./app.routing";
import { CovalentLayoutModule} from "@covalent/core";
import { FlexLayoutModule} from "@angular/flex-layout";
import { BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { EmpresaListComponent } from './administracion/empresa/empresa-list.component';
import { EmpresaService} from "./administracion/empresa/empresa.service";
import { EmpresaEditComponent } from './administracion/empresa/empresa-edit.component';
import { RouterModule} from "@angular/router";
import { DateUtilService} from "./utils/date-util.service";
import { MessageUtilService} from "./utils/message-util.service";
import { RolListComponent } from './administracion/roles/rol-list.component';
import { RolEditComponent } from './administracion/roles/rol-edit.component';
import { RolService} from "./administracion/roles/rol.service";
import { PrimengModule} from "./utils/primeng.module";
import { TipoOrdenPagoListComponent } from './orden-pago/tipo-orden-pago/tipo-orden-pago-list.component';
import {TipoOrdenPagoService} from "./orden-pago/tipo-orden-pago/tipo-orden-pago.service";
import { TipoOrdenPagoEditComponent } from './orden-pago/tipo-orden-pago/tipo-orden-pago-edit.component';
import { UsuarioListComponent } from './administracion/usuario/usuario-list.component';
import { UsuarioEditComponent } from './administracion/usuario/usuario-edit.component';
import {UsuarioService} from "./administracion/usuario/usuario.service";
import {PermisosService} from "./security/permiso.service";
import {WorkflowService} from "./administracion/workflow/workflow-service";
import {WorkflowListComponent} from "./administracion/workflow/workflow-list.component";
import {WorkflowEditComponent} from "./administracion/workflow/workflow-edit.component";
import {ProcesoListComponent} from "./system/procesos/proceso-list.component";
import {ProcesoService} from "./system/procesos/proceso.service";
import {ProcesoEditComponent} from "./system/procesos/proceso-edit-component";
import {SystemParameterListComponent} from "./system/parameter/parameter-list.component";
import {ParameterService} from "./system/parameter/parameter.service";
import {SystemparameterEditComponent} from "./system/parameter/systemparameter-edit.component";
import {CodigoeventosService} from "./system/codigoeventos/codigoeventos.service";
import {CodigoeventosListComponent} from "./system/codigoeventos/codigoeventos-list.component";
import {CodigoeventosEditComponent} from "./system/codigoeventos/codigoeventos-edit.component";
import {ConfirmationService} from "primeng/primeng";
import {GeneralHelperService} from "./utils/general-helper.service";
import { BorradorListComponent } from './orden-pago/borrador/borrador-list.component';
import {BorradorEditComponent} from "./orden-pago/borrador/borrador-edit.component";
import {BorradorService} from "./orden-pago/borrador/borrador.service";
import {BeneficiariosListComponent} from './orden-pago/beneficiarios/beneficiarios-list.component';
import {BeneficiariosService} from './orden-pago/beneficiarios/beneficiarios-service';
import {PaymentordersListComponent} from './orden-pago/ordenes-de-pago/paymentorders-list.component';
import {PaymentOrderService} from './orden-pago/ordenes-de-pago/ordenes-pago-service';
import {PaymentorderEditComponent} from './orden-pago/ordenes-de-pago/paymentorders-edit.component';
import {BorradorRapidoComponent} from "./orden-pago/borrador-rapido/borrador-rapido.component";
import { PerfilComponent } from './perfil/perfil.component';
import {PerfilService} from "./perfil/perfil.service";
import {UtilService} from "./utils/util.service";
import {HomeService} from "./home/home.service";
import {MatDialogModule} from "@angular/material";
import {CompanyDialogComponent} from "./login/company-dialog/company-dialog.component";
import { LogPaymentComponent } from './consultas/logpayment/log-payment.component';
import { LogMtsComponent } from './consultas/logmts/log-mts.component';
import {LogMtsService} from "./consultas/logmts/log-mts.service";
import {DatePipe} from "@angular/common";
import { LogAuditComponent } from './administracion/log-audit/log-audit.component';
import {LogAuditService} from "./administracion/log-audit/log-audit.service";
import { LogMessageComponent } from './consultas/log-notificaciones/log-message.component';
import { LogDraftopComponent } from './consultas/log-draftop/log-draftop.component';
import { LogSesionComponent } from './consultas/log-sesion/log-sesion.component';
import {LogDraftopService} from "./consultas/log-draftop/log-draftop.service";
import { ComprobantePagoComponent } from './consultas/comprobante-pago/comprobante-pago.component';
import {ComprobantePagoService} from "./consultas/comprobante-pago/comprobante-pago.service";
import {LogMessageService} from "./consultas/log-notificaciones/log-message.service";
import {LogSesionService} from "./consultas/log-sesion/log-sesion.service";
import {LogPaymentService} from "./consultas/logpayment/log-payment.service";
import {TemplatesService} from "./system/plantillas/templates.service";
import {ImportacionPlantillaComponent} from "./system/plantillas/importacion-plantilla.component";
import {LogMensajesRecibidosComponent} from "./consultas/log-mensajes-recibidos/log-mensajes-recibidos.component";
import {LogMensajesRecibidosService} from "./consultas/log-mensajes-recibidos/log-mensajes-recibidos.service";
import {RecuperarContrasenhaComponent} from "./recuperacion/recuperar-contrasenha/recuperar-contrasenha.component";
import {AppConfig} from "./app.config";
import { BotDetectCaptchaModule } from 'angular-captcha';
import {CaptchaService} from "./login/captcha-service";


export function initConfig(config: AppConfig) {
  return () => config.load();
}
@NgModule({
  declarations: [
    AppComponent,
    CabeceraComponent,
    HomeComponent,
    LoginComponent,
    EmpresaListComponent,
    EmpresaEditComponent,
    RolListComponent,
    RolEditComponent,
    TipoOrdenPagoListComponent,
    TipoOrdenPagoEditComponent,
    WorkflowListComponent,
    WorkflowEditComponent,
    TipoOrdenPagoEditComponent,
    TipoOrdenPagoListComponent,
    UsuarioListComponent,
    UsuarioEditComponent,
    ProcesoListComponent,
    ProcesoEditComponent,
    SystemParameterListComponent,
    SystemparameterEditComponent,
    CodigoeventosListComponent,
    CodigoeventosEditComponent,
    BorradorListComponent,
    BorradorEditComponent,
    BeneficiariosListComponent,
    PaymentordersListComponent,
    PaymentorderEditComponent,
    BorradorRapidoComponent,
    PaymentorderEditComponent,
    PerfilComponent,
    CompanyDialogComponent,
    LogPaymentComponent,
    LogMtsComponent,
    LogAuditComponent,
    LogMessageComponent,
    LogDraftopComponent,
    LogSesionComponent,
    LogPaymentComponent,
    LogMensajesRecibidosComponent,
    ComprobantePagoComponent,
    ImportacionPlantillaComponent,
    RecuperarContrasenhaComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    routing,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    MaterialModule,
    FlexLayoutModule,
    CovalentLayoutModule,
    CarouselModule.forRoot(),
    RouterModule,
    PrimengModule,
    ChartsModule,
    MatDialogModule,
    BotDetectCaptchaModule,

  ],
  providers: [
    UsuarioService,
    LoggedInGuard,
    EmpresaService,
    DateUtilService,
    MessageUtilService,
    RolService,
    TipoOrdenPagoService,
    UsuarioService,
    TipoOrdenPagoService,
    WorkflowService,
    AutenticacionService,
    UsuarioService,
    PermisosService,
    ProcesoService,
    ParameterService,
    CodigoeventosService,
    ConfirmationService,
    GeneralHelperService,
    BorradorService,
    BeneficiariosService,
    PaymentOrderService,
    PaymentOrderService,
    PerfilService,
    UtilService,
    HomeService,
    LogMtsService,
    LogAuditService,
    LogDraftopService,
    ComprobantePagoService,
    LogMessageService,
    LogSesionService,
    LogPaymentService,
    LogMensajesRecibidosService,
    TemplatesService,
    DatePipe,
    CaptchaService,
    AppConfig,
    {
      provide: APP_INITIALIZER,
      useFactory: initConfig,
      deps: [AppConfig],
      multi: true
    }

  ],

  bootstrap: [AppComponent],
  entryComponents: [CompanyDialogComponent]
})
export class AppModule { }
