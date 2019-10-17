import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoggedInGuard} from "./login/logged-in.guard";
import {LoginComponent} from "./login/login.component";
import {EmpresaListComponent} from "./administracion/empresa/empresa-list.component";
import {EmpresaEditComponent} from "./administracion/empresa/empresa-edit.component";
import {RolListComponent} from "./administracion/roles/rol-list.component";
import {RolEditComponent} from "./administracion/roles/rol-edit.component";
import {TipoOrdenPagoListComponent} from "./orden-pago/tipo-orden-pago/tipo-orden-pago-list.component";
import {TipoOrdenPagoEditComponent} from "./orden-pago/tipo-orden-pago/tipo-orden-pago-edit.component";
import {WorkflowListComponent} from "./administracion/workflow/workflow-list.component";
import {WorkflowEditComponent} from "./administracion/workflow/workflow-edit.component";

import {UsuarioListComponent} from "./administracion/usuario/usuario-list.component";
import {UsuarioEditComponent} from "./administracion/usuario/usuario-edit.component";
import {ProcesoListComponent} from "./system/procesos/proceso-list.component";
import {ProcesoEditComponent} from "./system/procesos/proceso-edit-component";
import {SystemParameterListComponent} from "./system/parameter/parameter-list.component";
import {SystemparameterEditComponent} from "./system/parameter/systemparameter-edit.component";
import {CodigoeventosListComponent} from "./system/codigoeventos/codigoeventos-list.component";
import {CodigoeventosEditComponent} from "./system/codigoeventos/codigoeventos-edit.component";
import {BorradorListComponent} from "./orden-pago/borrador/borrador-list.component";
import {BorradorEditComponent} from "./orden-pago/borrador/borrador-edit.component";
import {BeneficiariosListComponent} from './orden-pago/beneficiarios/beneficiarios-list.component';
import {PaymentordersListComponent} from './orden-pago/ordenes-de-pago/paymentorders-list.component';
import {PaymentorderEditComponent} from './orden-pago/ordenes-de-pago/paymentorders-edit.component';
import {PerfilComponent} from "./perfil/perfil.component";
import {BorradorRapidoComponent} from "./orden-pago/borrador-rapido/borrador-rapido.component";
import {LogMtsComponent} from "./consultas/logmts/log-mts.component";
import {LogPaymentComponent} from "./consultas/logpayment/log-payment.component";
import {LogAuditComponent} from "./administracion/log-audit/log-audit.component";
import {LogDraftopComponent} from "./consultas/log-draftop/log-draftop.component";
import {ComprobantePagoComponent} from "./consultas/comprobante-pago/comprobante-pago.component";
import {LogMessageComponent} from "./consultas/log-notificaciones/log-message.component";
import {LogSesionComponent} from "./consultas/log-sesion/log-sesion.component";
import {ImportacionPlantillaComponent} from "./system/plantillas/importacion-plantilla.component";
import {LogMensajesRecibidosComponent} from "./consultas/log-mensajes-recibidos/log-mensajes-recibidos.component";
import {RecuperarContrasenhaComponent} from "./recuperacion/recuperar-contrasenha/recuperar-contrasenha.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  //PANTALLA PRINCIPAL
  {
    path: 'home',
    children: [
      {
        path: '',
        component: HomeComponent,
        data: {permisos: []},
        canActivate: [LoggedInGuard],
      }
    ]
  },
  //LOGIN
  {
    path: 'login',
    children: [
      {
        path: '',
        component: LoginComponent
      }
    ]
  },
  //EMPRESAS
  {
    path: 'companyList',
    children: [
      {
        path: '',
        component: EmpresaListComponent,
        data: {permisos: ["ROLE_CONSULTAR_EMPRESA"]},
        canActivate: [LoggedInGuard]
      }
    ],

  },
  //CREACION Y EDICION DE EMPRESAS
  {
    path: 'companyEdit',
    children: [
      {
        path: '',
        component: EmpresaEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_EMPRESA"]},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'companyId/:id',
        component: EmpresaEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_EMPRESA"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  //ROLES
  {
    path: 'rolList',
    children: [
      {
        path: '',
        component: RolListComponent,
        data: {permisos: ["ROLE_CONSULTAR_ROL"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  // CREACION Y EDICION DE ROLES
  {
    path: 'rolEdit',
    children: [
      {
        path: '',
        component: RolEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_ROL"]},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'rolId/:id',
        component: RolEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_ROL"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  //TIPO DE ORDEN DE PAGO
  {
    path: 'paymentOrderTypeList',
    children: [
      {
        path: '',
        component: TipoOrdenPagoListComponent,
        data: {permisos: ["ROLE_CONSULTAR_TIPO_ORDEN_DE_PAGO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  // CREACION Y EDICION DE ROLES
  {
    path: 'paymentOrderTypeEdit',
    children: [
      {
        path: '',
        component: TipoOrdenPagoEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_TIPO_ORDEN_DE_PAGO"]},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'paymentOrderTypeId/:id',
        component: TipoOrdenPagoEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_TIPO_ORDEN_DE_PAGO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'userList',
    children: [
      {
        path: '',
        component: UsuarioListComponent,
        data: {permisos: ['ROLE_CONSULTAR_USUARIO']},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'userEdit',
    children: [
      {
        path: '',
        component: UsuarioEditComponent,
        data: {permisos: ['ROLE_MODIFICAR_USUARIO']},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'userId/:id',
        component: UsuarioEditComponent,
        data: {permisos: ['ROLE_MODIFICAR_USUARIO']},
        canActivate: [LoggedInGuard]
      }
    ]
  },//WORKFLOWS
  {
    path: 'workflowList',
    children: [
      {
        path: '',
        component: WorkflowListComponent,
        data: {permisos: ["ROLE_CONSULTAR_WORKFLOW"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'workflowEdit',
    children: [
      {
        path: '',
        component: WorkflowEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_WORKFLOW"]},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'workflowId/:id',
        component: WorkflowEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_WORKFLOW"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  //Procesos
  {
    path: 'processList',
    children: [
      {
        path: '',
        component: ProcesoListComponent,
        data: {permisos: ["ROLE_CONSULTAR_PROCESO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  }, {
    path: 'processEdit',
    children: [
      {
        path: '',
        component: ProcesoEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_PROCESO"]},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'processId/:id',
        component: ProcesoEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_PROCESO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'systemparameterList',
    children: [
      {
        path: '',
        component: SystemParameterListComponent,
        data: {permisos: ["ROLE_CONSULTAR_SYSTEMPARAMETER"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'systemparameterEdit',
    children: [
      {
        path: '',
        component:SystemparameterEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_SYSTEMPARAMETER"]},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'systemparameterId/:idProcess/:parametro',
        component: SystemparameterEditComponent,
        data: {permisos: ["ROLE_MODIFICAR_SYSTEMPARAMETER"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'eventCodeList',
    children: [
      {
        path: '',
        component:CodigoeventosListComponent,
        data: {permisos: ["ROLE_CONSULTAR_COD_EVENTO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  // {
  //   path: 'eventCodeId/:idProcess/:parametro',
  //   children: [
  //     {
  //       path: '',
  //       component: CodigoeventosEditComponent,
  //       data: {permisos: ["ROLE_MODIFICAR_COD_EVENTO"]},
  //       canActivate: [LoggedInGuard]
  //     }
    {
    path: 'eventCodeEdit',
    children: [
    {
      path: '',
      component:CodigoeventosEditComponent,
      data: {permisos: ["ROLE_MODIFICAR_COD_EVENTO"]},
      canActivate: [LoggedInGuard]
    },
    {
      path: 'eventCodeId/:idProcess/:parametro',
      component: CodigoeventosEditComponent,
      data: {permisos: ["ROLE_MODIFICAR_COD_EVENTO"]},
      canActivate: [LoggedInGuard]
    }
  ]
  },
  {
    path: 'beneficiariosList',
    children: [
      {
        path: '',
        component:BeneficiariosListComponent ,
        data: {permisos: ["ROLE_CONSULTAR_BENEFICIARIO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  //BORRADOR
  {
    path: 'draftList',
    children: [
      {
        path: '',
        component:BorradorListComponent,
        data: {permisos: ["ROLE_CONSULTAR_BORRADOR"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'draftEdit',
    children: [
      {
        path: '',
        component: BorradorEditComponent,
        data: {permisos: ['ROLE_MODIFICAR_BORRADOR']},
        canActivate: [LoggedInGuard]
      },
      {
        path: 'draftId/:id',
        component: BorradorEditComponent,
        data: {permisos: ['ROLE_MODIFICAR_BORRADOR']},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'quickdraftEdit',
    children: [
      {
        path: '',
        component: BorradorRapidoComponent,
        data: {permisos: ['ROLE_MODIFICAR_BORRADOR']},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'paymentOrderList',
    children: [
      {
        path: '',
        component:PaymentordersListComponent,
        data: {permisos: ["ROLE_CONSULTAR_ORDEN_DE_PAGO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path: 'paymentorderEdit',
    children: [
      {
        path: 'paymentorderId/:id',
        component: PaymentorderEditComponent,
        data: {permisos: ['ROLE_MODIFICAR_ORDEN_DE_PAGO']},
        canActivate: [LoggedInGuard]
      }
    ]
  },

  {
    path:'perfil',
    children: [
      {
        path:'',
        component: PerfilComponent,
        canActivate: [LoggedInGuard]

      }

    ]
  },
  {
    path:'logMts',
    children: [
      {
        path: '',
        component: LogMtsComponent,
        data: {permisos: ["ROLE_CONSULTAR_REGISTRO_MTS"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'logPayment',
    children: [
      {
        path: '',
        component: LogPaymentComponent,
        data: {permisos: ["ROLE_CONSULTAR_REGISTRO_DE_PAGO"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'audit',
    children: [
      {
        path: '',
        component: LogAuditComponent,
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'logDraftop',
    children: [
      {
        path: '',
        component: LogDraftopComponent,
        data: {permisos: ["ROLE_CONSULTAR_REGISTRO_DE_GEN_OP"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'paymentVoucher',
    children: [
      {
        path: '',
        component: ComprobantePagoComponent,
        data: {permisos: ["ROLE_CONSULTAR_COMPROBANTE_DE_PAGOS"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'logNotification',
    children: [
      {
        path: '',
        component: LogMessageComponent,
        data: {permisos: ["ROLE_CONSULTAR_NOTIFICACIONES"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'logSession',
    children: [
      {
        path: '',
        component: LogSesionComponent,
        data: {permisos: ["ROLE_CONSULTAR_REGISTRO_SESION"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'smsLogMessage',
    children: [
      {
        path: '',
        component: LogMensajesRecibidosComponent,
        data: {permisos: ["ROLE_CONSULTAR_REGISTRO_DE_MSJ_RECEIVED"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'templateDocument',
    children: [
      {
        path: '',
        component: ImportacionPlantillaComponent,
        data: {permisos: ["ROLE_MODIFICAR_EJEMPLO_DE_PLANTILLA"]},
        canActivate: [LoggedInGuard]
      }
    ]
  },
  {
    path:'changePassword',
    children: [
      {
        path: '',
        component: RecuperarContrasenhaComponent,
      }
    ]
  }
  // {
  //   path: '**',
  //   component: PageNotFoundComponent
  // }
];

export const routing = RouterModule.forRoot(routes, {useHash: true});
