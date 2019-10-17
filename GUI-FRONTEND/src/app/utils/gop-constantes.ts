/*Opciones de colores para los mensajes*/
import {ExpandOperator} from "rxjs/operators/expand";

export const WARN = 'primary';
export const ERROR = 'warn';
export const INFO = 'accent';

/*Opciones de iconos*/
export const WARNICON = 'warning';
export const INFOICON = 'info';
export const ERRORICON = 'error';

/*Mensajes*/
export const SERVER_ERROR = '¡Error inesperado en el servidor!';
export const OPERACION_CANCELADA = 'La operación ha sido cancelada';
export const SESIONEXP = "La sesión ha expirado. Deberá autenticarse nuevamente.";
export const MESSAGEERROR = "ERROR";
export const SIN_CONEXION_INTERNET = 'Sin Conexión a Internet.';
export const CANT_FILAS = '10';
export const PRIMERA_PAGINA = '1';

//CODIGOS INTERNOS
export const USUARIO_SIN_ROL = "0024";

//EXPRESIONES REGULARES
export const REGEX_DECIMAL = /^\d+$|^\d+\.\d{1,2}$/;

//MESSAGES
export const MESSAGES = {
  'MTSUSR': 'Ingrese nro de telefono. Ej: (0981)123-123',
  'MTSBAND': 'Marca, valor por defecto 1 (Tigo)',
  'MTSROLBIND': 'Rol con el cual se conecta la cuenta origen (mtsusr), valor por defecto 1 (agente)',
  'MTSPASSWORD': 'PIN de la cuenta origen',
  'TRXDAYLIMIT': 'Cantidad maxima de transacciones diarias que se permite realizar una empresa. Cero (0) no tiene limite',
  'TRXMONTLIMIT': 'Cantidad maxima de transacciones mensuales que se permite realizar una empresa. Cero (0) no tiene limite',
  'TRXTOTALLIMIT': 'Cantidad maxima total de transacciones que se permite realizar una empresa. Cero (0) no tiene limite',
  'SIN_EMPRESA': 'Para seleccionar un worflow debe seleccionar una compañia...',
  'SIN_WORKFLOW': 'Seleccione un workflow con el botón...',
  'CUENTA': 'Ingrese Nro de Cuenta'
};

export const NOTIFICATIONS = [
  {value: 'PAYMENT_ORDER_CREATED', label: 'OP creada'},
  {value: 'SIGNER_EMAIL', label: 'Firmante notificado por e-mail'},
  {value: 'SIGNER_SMS', label: 'Firmante notificado por SMS'},
  {value: 'PAYMENT_BENEFICIARY', label: 'Pago Beneficiario'},
  {value: 'PAYMENT_ORDER_CANCELLED', label: 'OP cancelada'},
  {value: 'INSUFFICIENT_MONEY', label: 'Saldo insuficiente'},
  {value: 'PAYMENT_ORDER_REVERSION', label: 'OP revertida'},
  {value: 'PO_PARTIAL_REVERSION', label: 'PO revertida parcialmente'},
  {value: 'PO_UNREVERSION', label: 'OP no revertida'},
  {value: 'PAYMENT_ORDER_UNPAID', label: 'OP no pagada'},
  {value: 'PO_SIGNATURE_SUCCESS', label: 'Firma OP correcta'},
  {value: 'SIGNER_BLOCK', label: 'Firmante bloqueado'},
  {value: 'VALID_TIME', label: 'Tiempo valido de firma'},
  {value: 'PAYMENT_ORDER_GENERATION_FAILED', label: 'Generacion OP fallida'},
  {value: 'INCORRECT_PIN', label: 'Pin incorrecto'},
  {value: 'PASS_RECOVERY', label: 'Recuperacion contraseña'},
  {value: 'USER_CREATED', label: 'Usuario creado'},
  {value: 'OUTTIME_APPROVAL', label: 'Tiempo aprovacion expirado'},
  {value: 'PAYMENT_ORDER_NOT_FOUND', label: 'OP no encontrada'},
  {value: 'INVALID_PAYMENT_ORDER_STATE', label: 'OP estado invalido'},
  {value: 'NOT_SIGNER_TURN', label: 'No es su turno para aprobar'},
  {value: 'INCORRECT_PIN_LAST_CHANCE', label: 'Pin incorrecto, ultimo intento'},
  {value: 'INCORRECT_PIN_USER_BLOCKED', label: 'Pin incorrecta, firmante bloqueado'},
  {value: 'INVALID_MESSAGE', label: 'Mensaje invalido'},
  {value: 'UNKNOW_USER', label: 'Usuario desconocido'},
  {value: 'UNKNOW_DESTINATION', label: 'Destino desconocido'},
  {value: 'UNKNOW_USER_PREFIX', label: 'Prefijo de usuario desconocido'},
  {value: 'INVALID_ID_PAYMENT_ORDER', label: 'Id OP invalido'},
  {value: 'PAYMENT_SIGNERS', label: 'Firmantes notificados del pago'},
  {value: 'OTHER_ERROR', label: 'Otro error'},
  {value: 'SIGNEVENT', label: 'Creador draft notificado'},
  {value: 'UNNOTIFIED', label: 'No notificado'}
];


//CODIGO DE PROCESOS\
export const PROCESS_PO_MANAGER = 1001;

//EJEMPLOS CSV
export const SHORT_EXAMPLE_TEMPLATE = 'short.example';
export const COMPLETE_EXAMPLE_TEMPLATE = 'complete.example';

export const STATES = [
  {label: 'Activo', value: 'AC'},
  {label: 'Inactivo', value: 'IN'},
];
export const MESSAGE_STATES = [
  {label: "Satisfactorio", value: "SU"},
  {label: "Error", value: "ER"}
  ];

export const LOG_MTS_STATES = [
  {label: "Satisfactorio", value: "SU"},
  {label: "Revertido", value: "RE"},
  {label: "Error", value: "ER"}
];

export const PRIORITY_STATES = [
  {label: "Muy Baja",value:"1"},
  {label: "Media Baja",value:"2"},
  {label: "Baja",value:"3"},
  {label: "Media",value:"4"},
  {label: "Media Alta",value:"5"},
  {label: "Alta",value:"6"},
  {label: "Muy Alta",value:"7"}
  ];

export const LOG_PAYMENT_STATES =[
  {label: "Pendiente de Pago", value: "EP"},
  {label: "Pago en Proceso", value: "PX"},
  {label: "Satisfactorio", value: "SU"},
  {label: "Parcialmente Pagada", value: "SP"},
  {label: "Revertido", value:"RE"},
  {label: "Parcialmente Revertido", value:"RP"},
  {label: "Error", value:"ER"}];


