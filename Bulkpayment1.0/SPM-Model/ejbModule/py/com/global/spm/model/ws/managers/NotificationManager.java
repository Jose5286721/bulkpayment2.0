package py.com.global.spm.model.ws.managers;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import py.com.global.spm.entities.Beneficiary;
import py.com.global.spm.entities.Company;
import py.com.global.spm.entities.Logmessage;
import py.com.global.spm.entities.Paymentordertype;
import py.com.global.spm.entities.User;
import py.com.global.spm.model.eventcodes.NotificationManagerEventCodes;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.interfaces.CompanyManagerLocal;
import py.com.global.spm.model.interfaces.EventCodeManagerLocal;
import py.com.global.spm.model.interfaces.LogmessageManagerLocal;
import py.com.global.spm.model.interfaces.NotificationCenterWsManagerLocal;
import py.com.global.spm.model.interfaces.NotificationManagerLocal;
import py.com.global.spm.model.interfaces.PaymentordertypeManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.interfaces.UserManagerLocal;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.model.messages.SPMGUIToNotificationManager;
import py.com.global.spm.model.systemparameters.NotificationCenterWsParameters;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.SpecialWordsDescripcion;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;
import py.com.global.spm.model.util.SpmUtil;

import com.tigo.sendnotificationresponse.v1.SendNotificationResponse;
import py.com.global.spm.domain.utils.NotificationEventEnum;

/**
 * Session Bean implementation class NotificationManager Representa al proceso
 * de Notificaciones.
 */
@Stateless
public class NotificationManager implements NotificationManagerLocal {

	@EJB
	private SystemparameterManagerLocal systemParametersManager;

	@EJB
	private LogmessageManagerLocal logMessageManager;

	@EJB
	private NotificationCenterWsManagerLocal notificationCenterWsManager;

	@EJB
	private BeneficiaryManagerLocal beneficiaryManager;

	@EJB
	private UserManagerLocal userManager;

	@EJB
	private CompanyManagerLocal companyManager;

	@EJB
	private PaymentordertypeManagerLocal paymentordertypeManager;

	@EJB
	private EventCodeManagerLocal eventCodeManager;

	private final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(NotificationManager.class);

	/**
	 * Default constructor.
	 */
	public NotificationManager() {
	}

	public void processRequest(Serializable msg) {
		/*
		 * Con el evento se debe traer la plantilla de la tabla systemparameter
		 */

		NotificationRequestMessage requestMessage = null;
		try {
			if (msg instanceof SPMGUIToNotificationManager) {
				processManuRequest((SPMGUIToNotificationManager) msg);
				return;
			}

			if (msg instanceof NotificationRequestMessage) {
				requestMessage = (NotificationRequestMessage) msg;
			}

			String eventValue = requestMessage.getEvent().getValue();
			// PatternPaymentBeneficiary
			NotificationEventEnum event = requestMessage.getEvent();

			// Validaciones previas a la notificacion.
			if (eventValue == null) {
				log.error(" Evento de notificacion null ");
				this.createLogmessageError(requestMessage,
						NotificationManagerEventCodes.EVENT_CODE_NULL);
				return;
			}

			String plantillaMensaje = systemParametersManager
					.getParameterValue(SpmProcesses.SPM_NOTIFICATION_MANAGER,
							eventValue, "Obteniendo -->" + eventValue);
			// Verifica plantilla para el mensaje.
			if (plantillaMensaje == null) {
				log.error("Plantilla de mensaje no existe para el evento-->"
						+ eventValue);
				this.createLogmessageError(requestMessage,
						NotificationManagerEventCodes.UNKWON_PATTERN);
				return;
			}
			// Casos especiales de notificacion.
			NotificationEventEnum sc = specialsCases(event);
			String mensaje = null;
			if (sc == null) {
				mensaje = createMessage(plantillaMensaje, requestMessage);
				if (mensaje == null) {
					log.error("Mensaje NULL --> Mensaje de notificacion desconocido");
					this.createLogmessageError(requestMessage,
							NotificationManagerEventCodes.MESSAGE_NULL);
					return;
				}
			}

			Boolean notificarViaSms = null;
			// Caso de notificacion a Beneficiarios
			if (eventValue
					.equals(NotificationEventEnum.NOTIFY_PAYMENT_BENEFICIARY
							.getValue())) {
				// Se notifica a los beneficiarios via sms.

				Long listaIdDestinatarios[] = null;

				/* Se obtiene el id de los destinatarios */
				listaIdDestinatarios = requestMessage.getIdDestinatarios();
				log.trace("Obteniendo lista de destinatarios");

				ArrayList<String> listaNroTelefonos = obtenerNroTelefonoBeneficiarios(listaIdDestinatarios);
				if (listaNroTelefonos == null || listaNroTelefonos.isEmpty()) {
					log.warn("telefonos de beneficiarios no existen");
				}
				notificarViaSms = true;

				for (String nroTelefono : listaNroTelefonos) {
					SendNotificationResponse respuesta = notificationCenterWsManager
							.sendNotificationSms(mensaje, nroTelefono);
					if (respuesta == null) {
						createLogmessageErrorWs(
								requestMessage,
								NotificationManagerEventCodes.WS_ERROR_CONNECTION,
								mensaje, nroTelefono, notificarViaSms);
						log.error(" Error de conexion WS notificaciones - No se envio sms --> "
								+ " nro. telefono= "
								+ nroTelefono
								+ ","
								+ " mensaje= "
								+ mensaje
								+ ","
								+ " evento = "
								+ requestMessage.getEvent());

					} else if (!respuesta.getResponseBody().isSuccess()) {
						createLogmessageErrorWs(
								requestMessage,
								NotificationManagerEventCodes.FAIL_NOTIFICATION,
								mensaje, nroTelefono, notificarViaSms);
					} else {
						createLogmessage(respuesta, requestMessage, mensaje,
								notificarViaSms, event);
					}
				}

				// Se notifica a los beneficiarios via email.
				ArrayList<String> listaEmails = obtenerEmailBeneficiarios(listaIdDestinatarios);
				if (listaEmails == null || listaEmails.isEmpty()) {
					log.warn("Email de beneficiarios no existen ");
				}
				notificarViaSms = false;
				for (String email : listaEmails) {
					SendNotificationResponse respuesta = notificationCenterWsManager
							.sendNotificationEmail(mensaje, email);
					if (respuesta == null) {
						createLogmessageErrorWs(
								requestMessage,
								NotificationManagerEventCodes.WS_ERROR_CONNECTION,
								mensaje, email, notificarViaSms);

						log.error(" Error de conexion WS notificaciones - No se envio email --> "
								+ " email= "
								+ email
								+ ","
								+ " mensaje= "
								+ mensaje
								+ ","
								+ " evento = "
								+ requestMessage.getEvent());

					} else if (!respuesta.getResponseBody().isSuccess()) {
						createLogmessageErrorWs(
								requestMessage,
								NotificationManagerEventCodes.FAIL_NOTIFICATION,
								mensaje, email, notificarViaSms);
					} else {
						createLogmessage(respuesta, requestMessage, mensaje,
								notificarViaSms, event);
					}
				}
			} else if (sc != null) {// Se notifican eventos speciales del SMS
									// manager
				String nroTelefono = requestMessage.getUserPhoneNumber();
				if (nroTelefono == null) {
					log.warn("telefono de usuario no existe ");
					return;
				}
				notificarViaSms = true;

				try {
					String specialMessage = systemParametersManager
							.getParameterValue(
									SpmProcesses.SPM_NOTIFICATION_MANAGER,
									sc.getValue(),
									"Obteniendo -->" + sc.getValue());
					SendNotificationResponse respuesta = notificationCenterWsManager
							.sendNotificationSms(specialMessage, nroTelefono);

					if (respuesta == null) {
						createLogmessageErrorWs(
								requestMessage,
								NotificationManagerEventCodes.WS_ERROR_CONNECTION,
								mensaje, nroTelefono, notificarViaSms);
						log.error(" Error de conexion WS notificaciones - No se envio sms --> "
								+ " nro telefono = "
								+ nroTelefono
								+ ","
								+ " mensaje= "
								+ mensaje
								+ ","
								+ " evento = "
								+ requestMessage.getEvent());

					} else if (!respuesta.getResponseBody().isSuccess()
							|| respuesta == null) {
						createLogmessageErrorWs(
								requestMessage,
								NotificationManagerEventCodes.FAIL_NOTIFICATION,
								mensaje, nroTelefono, notificarViaSms);
					} else {
						createLogmessage(respuesta, requestMessage,
								specialMessage, notificarViaSms, event);
					}
				} catch (Exception e) {
					log.error("Error. Special case notification--> "
							+ e.getMessage() + e.getStackTrace());
				}

			} else {
				Long listaIdDestinatarios[] = null;
				listaIdDestinatarios = requestMessage.getIdDestinatarios();
				if (!eventValue
						.equals(NotificationEventEnum.NOTIFY_SIGNER_EMAIL
								.getValue())) {
					/*
					 * Se notifica a los usuarios via sms. Los usuario pueden
					 * ser Firmantes, Creadores del draft...
					 */
					ArrayList<String> listaNroTelefonos = null;

					log.trace("Obteniendo lista de destinatarios");

					/*
					 * Se notifica al creador del draft cuando un usuario firma
					 * una OP.
					 */
					if (eventValue
							.equals(NotificationEventEnum.NOTIFY_SIGNEVENT
									.getValue())) {
						try {
							Long[] idCreador = new Long[1];

							/*
							 * La posicion 1 de listaIdDestinatarios contiene el
							 * id de creador del draft
							 */
							idCreador[0] = listaIdDestinatarios[1];
							listaNroTelefonos = obtenerNroTelefonoUsuarios(idCreador);

						} catch (Exception ex) {
							log.error(" Obteniendo ID destinatario - Creador del Draft Para los sms --> "
									+ " Evento: "
									+ NotificationEventEnum.NOTIFY_SIGNEVENT
											.getValue());
						}

					} else {
						listaNroTelefonos = obtenerNroTelefonoUsuarios(listaIdDestinatarios);
					}

					if (listaNroTelefonos == null
							|| listaNroTelefonos.isEmpty()) {
						log.warn("telefonos de usuarios no existen,  listanroTelefonos = [] ");
					}

					// Se notifica a los usuarios via sms.
					notificarViaSms = true;
					for (String nroTelefono : listaNroTelefonos) {
						SendNotificationResponse respuesta = notificationCenterWsManager
								.sendNotificationSms(mensaje, nroTelefono);

						if (respuesta == null) {
							createLogmessageErrorWs(
									requestMessage,
									NotificationManagerEventCodes.WS_ERROR_CONNECTION,
									mensaje, nroTelefono, notificarViaSms);
							log.error(" Error de conexion WS notificaciones - No se envio sms --> "
									+ " nro telefono= "
									+ nroTelefono
									+ ","
									+ " mensaje= "
									+ mensaje
									+ ","
									+ " evento = " + requestMessage.getEvent());

						} else if (!respuesta.getResponseBody().isSuccess()) {
							createLogmessageErrorWs(
									requestMessage,
									NotificationManagerEventCodes.FAIL_NOTIFICATION,
									mensaje, nroTelefono, notificarViaSms);
						} else {
							createLogmessage(respuesta, requestMessage,
									mensaje, notificarViaSms, event);
						}
					}
				}

				// Se notifica a los usuarios via email.
				if (!eventValue.equals(NotificationEventEnum.NOTIFY_SIGNER_SMS
						.getValue())) {

					ArrayList<String> listaEmails = null;
					/*
					 * Se notifica al creador del draft cuando un usuario firma
					 * una OP.
					 */
					if (eventValue
							.equals(NotificationEventEnum.NOTIFY_SIGNEVENT
									.getValue())) {
						try {
							Long[] idCreador = new Long[1];

							/*
							 * La posicion 1 de listaIdDestinatarios contiene el
							 * id de creador del draft
							 */
							idCreador[0] = listaIdDestinatarios[1];
							listaEmails = obtenerEmailUsuarios(idCreador);

						} catch (Exception ex) {
							log.error(" Obteniendo ID destinatario - Creador del Draft Para los emails --> "
									+ " Evento: "
									+ NotificationEventEnum.NOTIFY_SIGNEVENT
											.getValue());
						}

					} else {
						listaEmails = obtenerEmailUsuarios(listaIdDestinatarios);
					}

					if (listaEmails == null || listaEmails.isEmpty()) {
						log.warn("Emails de usuarios no existen ");
					}

					notificarViaSms = false;
					for (String email : listaEmails) {
						SendNotificationResponse respuesta = notificationCenterWsManager
								.sendNotificationEmail(mensaje, email);
						if (respuesta == null) {
							createLogmessageErrorWs(
									requestMessage,
									NotificationManagerEventCodes.WS_ERROR_CONNECTION,
									mensaje, email, notificarViaSms);
							log.error(" Error de conexion WS notificaciones - No se envio email --> "
									+ " email= "
									+ email
									+ ","
									+ " mensaje= "
									+ mensaje
									+ ","
									+ " evento = "
									+ requestMessage.getEvent());

						} else if (!respuesta.getResponseBody().isSuccess()) {
							createLogmessageErrorWs(
									requestMessage,
									NotificationManagerEventCodes.FAIL_NOTIFICATION,
									mensaje, email, notificarViaSms);
						} else {
							createLogmessage(respuesta, requestMessage,
									mensaje, notificarViaSms, event);
						}

					}
				}
			}
		} catch (Exception ex) {
			log.error("Error al generar mensaje" + ex.getMessage(), ex);
		}
	}

	private void processManuRequest(SPMGUIToNotificationManager msg) {
		String mensaje = msg.getMensaje();
		String emailDestino = msg.getUserEmail();
		// String subj = msg.getSubject();
		SendNotificationResponse respuesta = notificationCenterWsManager
				.SendNotificationPassRecovery(mensaje, emailDestino, null);
		if (respuesta == null) {
			createLogmessageErrorWsRecovery(
					NotificationManagerEventCodes.WS_ERROR_CONNECTION, mensaje,
					emailDestino, false);
			log.error(" Error de conexion WS notificaciones - No se envio email --> "
					+ " email= "
					+ emailDestino
					+ ","
					+ " mensaje= "
					+ mensaje
					+ "," + " evento = " + SpmConstants.NOTIFY_PASS_RECOVERY);

		} else if (!respuesta.getResponseBody().isSuccess()) {
			createLogmessageErrorWsRecovery(
					NotificationManagerEventCodes.FAIL_NOTIFICATION, mensaje,
					emailDestino, false);
		} else {
			createLogmessageRecovery(respuesta, mensaje, false,
					NotificationEventEnum.NOTIFY_PASS_RECOVERY);
		}

	}

	private void createLogmessageErrorWsRecovery(
			NotificationManagerEventCodes failNotification, String mensaje,
			String destino, Boolean notificarViaSms) {
		try {
			Logmessage logMessage = new Logmessage();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			logMessage.setCreationdateTim(now);
			logMessage.setDestemailChr(destino);
			long idprocessPk = SpmProcesses.SPM_NOTIFICATION_MANAGER;
			logMessage.setIdprocessPk(idprocessPk);
			if (mensaje != null) {
				logMessage.setMessageChr(mensaje);
			} else {
				logMessage.setMessageChr("No hay mensaje");
			}
			logMessage.setNotificationeventChr(SpmConstants.NO_NOTIFIED);
			logMessage.setStateChr(SpmConstants.ERROR);
			logMessage.setIdeventcodeNum(failNotification.getIdEventCode());

			if (!logMessageManager.insertLogmessage(logMessage)) {
				log.error("Insert Logmessage --> " + logMessage.toString());
			} else {
				log.info("Insert Logmessage done -->" + logMessage.toString());
			}

		} catch (Exception ex) {
			log.error("ERROR. Insert logmessage --> " + ex.getMessage(), ex);
		}

	}

	private void createLogmessageRecovery(SendNotificationResponse respuesta,
			String mensaje, Boolean notificarViaSms,
			NotificationEventEnum evento) {
		try {
			Logmessage logMessage = new Logmessage();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			logMessage.setCreationdateTim(now);
			long idprocessPk = SpmProcesses.SPM_NOTIFICATION_MANAGER;
			logMessage.setIdprocessPk(idprocessPk);
			logMessage
					.setDestemailChr(respuesta.getResponseBody().getDestiny());
			if (mensaje != null) {
				logMessage.setMessageChr(mensaje);
			} else {
				logMessage.setMessageChr("No hay mensaje");
			}

			String e = SpmConstants.NOTIFY_PASS_RECOVERY;
			logMessage.setNotificationeventChr(e);

			if (respuesta.getResponseBody().isSuccess()) {
				logMessage.setStateChr(SpmConstants.SUCCESS);
				logMessage
						.setIdeventcodeNum(NotificationManagerEventCodes.SUCCESS
								.getIdEventCode());
				String transactioncodeChr = respuesta.getResponseBody()
						.getTransactionCode();
				logMessage.setTransactioncodeChr(transactioncodeChr);
				log.info("La notificacion se realizo correctamente -->" + e);
			} else {
				logMessage.setStateChr(SpmConstants.ERROR);
				logMessage
						.setIdeventcodeNum(NotificationManagerEventCodes.OTHER_ERROR
								.getIdEventCode());
				log.error("Error al realizar la notificacion -->" + e);
			}

			if (!logMessageManager.insertLogmessage(logMessage)) {
				log.error("Insert Logmessage --> " + logMessage.toString());
			} else {
				log.info("Insert Logmessage done -->" + logMessage.toString());
			}

		} catch (Exception ex) {
			log.error("Insert logmessage --> " + ex.getMessage(), ex);
		}

	}

	private void createLogmessageErrorWs(
			NotificationRequestMessage requestMessage,
			NotificationManagerEventCodes failNotification, String mensaje,
			String destino, Boolean notificarViaSms) {
		try {
			Logmessage logMessage = new Logmessage();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			logMessage.setCreationdateTim(now);

			if (notificarViaSms == true) {
				logMessage.setDestnumberChr(destino);

				// Set orignumber
				String orignumberChr = systemParametersManager
						.getParameterValue(
								SpmProcesses.SPM_NOTIFICATION_MANAGER,
								NotificationCenterWsParameters.SHORT_NUMBER_VALUE
										.getValue());
				logMessage.setOrignumberChr(orignumberChr);

			} else {
				logMessage.setDestemailChr(destino);
			}

			if (requestMessage.getOp() != null) {
				// set idpaymentorderPk
				Long idpaymentorderPk = requestMessage.getOp()
						.getIdpaymentorderPk();
				logMessage.setIdpaymentorderPk(idpaymentorderPk);

				// set idCompanyPk
				Long idcompanyPk = requestMessage.getOp().getIdcompanyPk();
				logMessage.setIdcompanyPk(idcompanyPk);
			}

			long idprocessPk = SpmProcesses.SPM_NOTIFICATION_MANAGER;
			logMessage.setIdprocessPk(idprocessPk);

			if (mensaje != null) {
				logMessage.setMessageChr(mensaje);
			} else {
				logMessage.setMessageChr("No hay mensaje");
			}

			logMessage.setNotificationeventChr(SpmConstants.NO_NOTIFIED);
			logMessage.setStateChr(SpmConstants.ERROR);
			logMessage.setIdeventcodeNum(failNotification.getIdEventCode());

			if (!logMessageManager.insertLogmessage(logMessage)) {
				log.error("Insert Logmessage --> " + logMessage.toString());
			} else {
				log.info("Insert Logmessage done -->" + logMessage.toString());
			}

		} catch (Exception ex) {
			log.error("ERROR. Insert logmessage --> " + ex.getMessage(), ex);
		}

	}

	// Se registran los eventos donde no se realizan notificaciones
	private void createLogmessageError(
			NotificationRequestMessage requestMessage,
			NotificationManagerEventCodes evento) {
		try {
			Logmessage logMessageError = new Logmessage();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			logMessageError.setCreationdateTim(now);

			// No se envia mensaje de notificacion.
			if (requestMessage.getOp() != null) {
				// set idpaymentorderPk
				Long idpaymentorderPk = requestMessage.getOp()
						.getIdpaymentorderPk();
				logMessageError.setIdpaymentorderPk(idpaymentorderPk);

				// set idCompanyPk
				Long idcompanyPk = requestMessage.getOp().getIdcompanyPk();
				logMessageError.setIdcompanyPk(idcompanyPk);

			}
			// Set orignumber
			String orignumberChr = systemParametersManager.getParameterValue(
					SpmProcesses.SPM_NOTIFICATION_MANAGER,
					NotificationCenterWsParameters.SHORT_NUMBER_VALUE
							.getValue());
			logMessageError.setOrignumberChr(orignumberChr);

			long idprocessPk = SpmProcesses.SPM_NOTIFICATION_MANAGER;
			logMessageError.setIdprocessPk(idprocessPk);

			if (evento != null) {
				logMessageError
						.setNotificationeventChr(evento.getDescription());
				logMessageError.setIdeventcodeNum(evento.getIdEventCode());
			}

			logMessageError.setStateChr(SpmConstants.ERROR);

			logMessageError.setMessageChr(SpmConstants.NO_MESSAGE);

			if (!logMessageManager.insertLogmessage(logMessageError)) {
				log.error("Insertando Logmessage --> "
						+ logMessageError.toString());
			} else {
				log.debug("Insert Logmessage done -->"
						+ logMessageError.toString());
			}

		} catch (Exception e) {
			log.error("Error creando el registro de error del proceso de "
					+ "notificaciones");
		}

	}

	// Eventos con error del SMSMANAGER que se deben notificar
	private NotificationEventEnum specialsCases(NotificationEventEnum event) {
		NotificationEventEnum res = null;
		switch (event) {
		case NOTIFY_UNKNOW_USER_PREFIX:
			res = NotificationEventEnum.NOTIFY_UNKNOW_USER_PREFIX;
			break;
		case NOTIFY_UNKNOW_DESTINATION:
			res = NotificationEventEnum.NOTIFY_UNKNOW_DESTINATION;
			break;
		case NOTIFY_UNKNOW_USER:
			res = NotificationEventEnum.NOTIFY_UNKNOW_USER;
			break;
		case NOTIFY_PAYMENT_ORDER_NOT_FOUND:
			res = NotificationEventEnum.NOTIFY_PAYMENT_ORDER_NOT_FOUND;
			break;
		case NOTIFY_INVALID_ID_PAYMENT_ORDER:
			res = NotificationEventEnum.NOTIFY_INVALID_ID_PAYMENT_ORDER;
			break;
		case NOTIFY_INVALID_MESSAGE:
			res = NotificationEventEnum.NOTIFY_INVALID_MESSAGE;
			break;
		case NOTIFY_OTHER_ERROR:
			res = NotificationEventEnum.NOTIFY_OTHER_ERROR;
		default:
			break;
		}
		return res;
	}

	// Persiste en la base de datos el log del proceso de notificaciones.
	private void createLogmessage(SendNotificationResponse respuesta,
			NotificationRequestMessage requestMessage, String mensaje,
			Boolean notificarViaSms, NotificationEventEnum evento) {
		try {
			Logmessage logMessage = new Logmessage();
			Timestamp now = new Timestamp(System.currentTimeMillis());
			logMessage.setCreationdateTim(now);

			if (notificarViaSms == true) {
				String destnumberChr = respuesta.getResponseBody().getDestiny();
				logMessage.setDestnumberChr(destnumberChr);

				// Set orignumber
				String orignumberChr = systemParametersManager
						.getParameterValue(
								SpmProcesses.SPM_NOTIFICATION_MANAGER,
								NotificationCenterWsParameters.SHORT_NUMBER_VALUE
										.getValue());
				logMessage.setOrignumberChr(orignumberChr);

			} else {
				String destemailChr = respuesta.getResponseBody().getDestiny();
				logMessage.setDestemailChr(destemailChr);
			}

			if (requestMessage.getOp() != null) {
				// set idpaymentorderPk
				Long idpaymentorderPk = requestMessage.getOp()
						.getIdpaymentorderPk();
				logMessage.setIdpaymentorderPk(idpaymentorderPk);

				// set idCompanyPk
				Long idcompanyPk = requestMessage.getOp().getIdcompanyPk();
				logMessage.setIdcompanyPk(idcompanyPk);
			}

			long idprocessPk = SpmProcesses.SPM_NOTIFICATION_MANAGER;
			logMessage.setIdprocessPk(idprocessPk);

			if (mensaje != null) {
				logMessage.setMessageChr(mensaje);
			} else {
				logMessage.setMessageChr("No hay mensaje");
			}

			String e;
			switch (evento) {
			case NOTIFY_SIGNER_EMAIL:
				e = SpmConstants.NOTIFY_SIGNER_EMAIL;
				break;
			case NOTIFY_SIGNER_SMS:
				e = SpmConstants.NOTIFY_SIGNER_SMS;
				break;

			case NOTIFY_PO_CREATED:
				e = SpmConstants.NOTIFY_PO_CREATED;
				break;

			case NOTIFY_PO_CANCELED:
				e = SpmConstants.NOTIFY_PO_CANCELLED;
				break;

			case NOTIFY_PAYMENT_BENEFICIARY:
				e = SpmConstants.NOTIFY_PAYMENT_BENEFICIARY;
				break;

			case NOTIFY_INSUFFICIENT_MONEY:
				e = SpmConstants.NOTIFY_INSUFFICIENT_MONEY;
				break;

			case NOTIFY_PO_REVERSION:
				e = SpmConstants.NOTIFY_PO_REVERSION;
				break;
			case NOTIFY_PO_PARTIAL_REVERSION:
				e = SpmConstants.NOTIFY_PO_PARTIAL_REVERSION;
				break;
			case NOTIFY_PO_UNREVERSION:
				e = SpmConstants.NOTIFY_PO_UNREVERSION;
				break;

			case NOTIFY_PO_UNPAID:
				e = SpmConstants.NOTIFY_PO_UNPAID;
				break;

			case NOTIFY_PO_SIGNATURE_SUCCESS:
				e = SpmConstants.NOTIFY_PO_SIGNATURE_SUCCESS;
				break;

			case NOTIFY_SIGNER_BLOCK:
				e = SpmConstants.NOTIFY_SIGNER_BLOCK;
				break;

			case NOTIFY_VALID_TIME:
				e = SpmConstants.NOTIFY_VALID_TIME;
				break;

			case NOTIFY_OP_GENERATION_FAILED:
				e = SpmConstants.NOTIFY_OP_GENERATION_FAILED;
				break;

			case NOTIFY_INCORRECT_PIN:
				e = SpmConstants.NOTIFY_INCORRECT_PIN;
				break;

			case NOTIFY_OUTTIME_APPROVAL:
				e = SpmConstants.NOTIFY_OUTTIME_APPROVAL;
				break;

			case NOTIFY_PAYMENT_ORDER_NOT_FOUND:
				e = SpmConstants.NOTIFY_PAYMENT_ORDER_NOT_FOUND;
				break;

			case NOTIFY_INVALID_PAYMENT_ORDER_STATE:
				e = SpmConstants.NOTIFY_INVALID_PAYMENT_ORDER_STATE;

			case NOTIFY_NOT_SIGNER_TURN:
				e = SpmConstants.NOTIFY_NOT_SIGNER_TURN;

			case NOTIFY_INCORRECT_PIN_LAST_CHANCE:
				e = SpmConstants.NOTIFY_INCORRECT_PIN_LAST_CHANCE;

			case NOTIFY_INCORRECT_PIN_USER_BLOCKED:
				e = SpmConstants.NOTIFY_INCORRECT_PIN_USER_BLOCKED;

			case NOTIFY_INVALID_MESSAGE:
				e = SpmConstants.NOTIFY_INVALID_MESSAGE;
				break;

			case NOTIFY_PAYMENT_SIGNERS:
				e = SpmConstants.NOTIFY_PAYMENT_SIGNERS;
				break;

			case NOTIFY_UNKNOW_USER:
				e = SpmConstants.NOTIFY_UNKNOW_USER;
				break;
			case NOTIFY_UNKNOW_DESTINATION:
				e = SpmConstants.NOTIFY_UNKNOW_DESTINATION;
				break;
			case NOTIFY_UNKNOW_USER_PREFIX:
				e = SpmConstants.NOTIFY_UNKNOW_USER_PREFIX;
				break;
			case NOTIFY_USER_CREATED:
				e = SpmConstants.NOTIFY_USER_CREATED;
				break;
			case SEND_TOKEN:
				e = SpmConstants.SEND_TOKEN;
				break;
			case NOTIFY_SIGNEVENT:
				e = SpmConstants.NOTIFY_SIGNEVENT;
				break;
			default:
				e = SpmConstants.NOTIFY_OTHER_ERROR;
				break;
			}

			if (e != null) {
				logMessage.setNotificationeventChr(e);
			}

			if (respuesta.getResponseBody().isSuccess()) {
				logMessage.setStateChr(SpmConstants.SUCCESS);
				logMessage
						.setIdeventcodeNum(NotificationManagerEventCodes.SUCCESS
								.getIdEventCode());
				String transactioncodeChr = respuesta.getResponseBody()
						.getTransactionCode();
				logMessage.setTransactioncodeChr(transactioncodeChr);
				log.info("La notificacion se realizo correctamente -->" + e);
			} else {
				logMessage.setStateChr(SpmConstants.ERROR);
				logMessage
						.setIdeventcodeNum(NotificationManagerEventCodes.OTHER_ERROR
								.getIdEventCode());
				log.error("Error al realizar la notificacion -->" + e);
			}

			if (!logMessageManager.insertLogmessage(logMessage)) {
				log.error("Insert Logmessage --> " + logMessage.toString());
			} else {
				log.info("Insert Logmessage done -->" + logMessage.toString());
			}
		} catch (Exception ex) {
			log.error("Insert logmessage --> " + ex.getMessage(), ex);
		}

	}

	private ArrayList<String> obtenerNroTelefonoBeneficiarios(
			Long[] listaIdDestinatarios) {
		ArrayList<String> numerosBeneficiarios = new ArrayList<String>();
		Beneficiary beneficiario = new Beneficiary();

		for (Long idBeneficiario : listaIdDestinatarios) {
			try {
				beneficiario = beneficiaryManager
						.getBeneficiaryById(idBeneficiario);
				if (beneficiario != null) {
					if (beneficiario.getNotifysmsNum() == true) {
						String nroTel = beneficiario.getPhonenumberChr();
						numerosBeneficiarios.add(nroTel);
					}
				} else {
					log.warn("Beneficiario con Id: " + idBeneficiario
							+ "No encontrado");
				}

			} catch (Exception ex) {
				log.error(
						"Obtener beneficiario by id --> "
								+ beneficiario.getIdbeneficiaryPk()
								+ ex.getMessage(), ex);
			}
		}
		return numerosBeneficiarios;
	}

	private ArrayList<String> obtenerEmailBeneficiarios(
			Long[] listaIdDestinatarios) {
		ArrayList<String> emailsBeneficiarios = new ArrayList<String>();
		Beneficiary beneficiario = new Beneficiary();

		for (Long idBeneficiario : listaIdDestinatarios) {
			try {
				beneficiario = beneficiaryManager
						.getBeneficiaryById(idBeneficiario);
				if (beneficiario != null) {
					if (beneficiario.getNotifyemailNum() == true) {
						String email = beneficiario.getEmailChr();
						emailsBeneficiarios.add(email);
					}
				} else {
					log.warn("Beneficiario con Id: " + idBeneficiario
							+ "No encontrado");
				}

			} catch (Exception ex) {
				log.error("Obtener beneficiario by id --> " + ex.getMessage(),
						ex);
			}
		}
		return emailsBeneficiarios;
	}

	private ArrayList<String> obtenerNroTelefonoUsuarios(
			Long[] listaIdDestinatarios) {
		ArrayList<String> numerosUsuarios = new ArrayList<String>();
		User usuario = new User();

		for (Long idUsuario : listaIdDestinatarios) {
			try {
				usuario = userManager.getUserById(idUsuario);
				if (usuario != null) {
					if (usuario.getNotifysmsNum() == true) {
						String nroTel = usuario.getPhonenumberChr();
						numerosUsuarios.add(nroTel);
					}
				} else {
					log.warn("Usuario con Id: " + idUsuario + "No encontrado");
				}

			} catch (Exception ex) {
				log.error("Obtener usuario by id" + ex.getMessage(), ex);
			}
		}
		return numerosUsuarios;
	}

	private ArrayList<String> obtenerEmailUsuarios(Long[] listaIdDestinatarios) {
		ArrayList<String> emailsUsuarios = new ArrayList<String>();
		User usuario = new User();

		for (Long idUsuario : listaIdDestinatarios) {
			try {
				usuario = userManager.getUserById(idUsuario);
				if (usuario != null) {
					if (usuario.getNotifyemailNum() == true) {
						String email = usuario.getEmailChr();
						emailsUsuarios.add(email);
					}
				} else {
					log.warn("Usuario con Id: " + idUsuario + "No encontrado");
				}

			} catch (Exception ex) {
				log.error("Obtener usuario by id" + ex.getMessage(), ex);
			}
		}
		return emailsUsuarios;
	}

	/**
	 * Crea el mensaje para las notificaciones.
	 * 
	 * @param plantilla
	 * @param reqMessage
	 * @return el mensaje a ser notificado.
	 */
	private String createMessage(String plantilla,
			NotificationRequestMessage reqMessage) {
		String strParse = plantilla;
		log.debug("Parsing plantilla --> " + plantilla + ", " + reqMessage);
		for (SpecialWordsDescripcion specialWord : SpecialWordsDescripcion
				.values()) {
			switch (specialWord) {
			case AMOUNT:

				if (reqMessage.getOp() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse
							.replace(specialWord.getValue(), String.valueOf(f
									.format(reqMessage.getOp().getAmountNum())));
				}
				break;

			case AMOUNT_PAID:
				if (reqMessage.getPago() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(f.format(reqMessage.getPago()
									.getAmountpaidNum())));
				}

				break;

			case ATTEMPTS_NUMBER:
				if (reqMessage.getCantReintentos() != null) {
					strParse = strParse.replace(specialWord.getValue(),
							String.valueOf(reqMessage.getCantReintentos()));
				}
				break;

			case CANCELL_REASON:
				if (reqMessage.getOp() != null) {
					if (reqMessage.getOp().getMotiveChr() != null) {
						strParse = strParse.replace(specialWord.getValue(),
								reqMessage.getOp().getMotiveChr());
					} else {
						strParse = strParse
								.replace(specialWord.getValue(), " ");
					}
				}
				break;

			case COMPANY_NAME:
				if (reqMessage.getOp() != null) {
					Company company = new Company();
					Long idCompanyPk = reqMessage.getOp().getIdcompanyPk();
					company = companyManager.getCompanyById(idCompanyPk);
					String name = company.getCompanynameChr();
					strParse = strParse.replace(specialWord.getValue(), name);
				}

				break;
			case OP_TYPE:
				if (reqMessage.getOp() != null) {
					Paymentordertype pot = new Paymentordertype();
					long idPaymentordertype = reqMessage.getOp()
							.getIdpaymentordertypePk();
					pot = paymentordertypeManager
							.getPaymentordertypeById(idPaymentordertype);
					String nombreTipoOp = pot.getNameChr();
					strParse = strParse.replace(specialWord.getValue(),
							nombreTipoOp);
				}

				break;
			case PAYMENT_NUMBER:
				if (reqMessage.getPago() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(f.format(reqMessage.getPago()
									.getIdpaymentNum())));
				}
				break;
			case PO_NUMBER:
				if (reqMessage.getOp() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(f.format(reqMessage.getOp()
									.getIdpaymentorderPk())));
				}
				break;

			case VALID_TIME:
				if (reqMessage.getTiempoValidezSms() != null) {
					strParse = strParse.replace(specialWord.getValue(),
							String.valueOf(reqMessage.getTiempoValidezSms()));
				}
				break;
			case PO_STATE:
				if (reqMessage.getOp() != null) {
					String estado = null;
					if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.CANCELADA)) {
						estado = "Cancelada";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.EN_PROCESO)) {
						estado = "En Proceso";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.NO_PAGADA)) {
						estado = "No Pagada";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.NO_REVERTIDA)) {
						estado = "No Revertida";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.PAGADA)) {
						estado = "Pagada";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.PAGANDO)) {
						estado = "Pagando";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.PARCIALMENTE_PAGADA)) {
						estado = "Parcialmente Pagada";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.PARCIALMENTE_REVERTIDA)) {
						estado = "Parcialmente Revertida";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.PENDIENTE_DE_PAGO)) {
						estado = "Pendiente de Pago";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.REVERTIDA)) {
						estado = "Revertida";
					} else if (reqMessage.getOp().getStateChr()
							.equals(PaymentOrderStates.REVIRTIENDO)) {
						estado = "Revirtiendo";
					} else {
						estado = "Estado No valido";
					}

					strParse = strParse.replace(specialWord.getValue(), estado);
				}
				break;

			case TOTAL_SUCCESSFUL:
				if (reqMessage.getOp() != null
						&& reqMessage.getOp().getPaymentsuccessNum() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(f.format(reqMessage.getOp()
									.getPaymentsuccessNum())));
				}
				break;
			case PARTIAL_PAYMENT_AMOUNT:
				if (reqMessage.getOp() != null
						&& reqMessage.getOp().getPaymentpartsucNum() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(f.format(reqMessage.getOp()
									.getPaymentpartsucNum())));
				}
				break;
			case PAYMENT_ERROR:
				if (reqMessage.getOp() != null
						&& reqMessage.getOp().getPaymenterrorNum() != null) {
					NumberFormat f = NumberFormat.getInstance(new Locale("es"));
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(f.format(reqMessage.getOp()
									.getPaymenterrorNum())));
				}
				break;

			case PAYMENT_TOTAL:
				if (reqMessage.getOp() != null
						&& reqMessage.getOp().getTotalpaymentNum() != null) {
					strParse = strParse.replace(specialWord.getValue(), String
							.valueOf(reqMessage.getOp().getTotalpaymentNum()));
				}
				break;

			case USERNAME:
				if (reqMessage.getIdDestinatarios() != null
						&& reqMessage.getIdDestinatarios().length > 0) {

					Long i[] = reqMessage.getIdDestinatarios();
					Long idUser = i[0];// Se obtiene el unico elemento del
										// vector.
					User user = null;
					String username = "null";
					long wire = 5;
					while (wire-- > 0) {
						user = userManager.getUserById(idUser);
						if (user != null) {
							break;
						}
						SpmUtil.sleep(1000);
					}
					if (user != null) {
						username = user.getUsernameChr();
					} else {
						log.warn("No se encontro usuario especificado! --> userid="
								+ idUser);
					}
					strParse = strParse.replace(specialWord.getValue(),
							username);

				}
				break;
			case PIN:
				if (reqMessage.getIdDestinatarios() != null
						&& reqMessage.getIdDestinatarios().length > 0) {
					Long i[] = reqMessage.getIdDestinatarios();
					Long idUser = i[0];// Se obtiene el unico elemento del
										// vector.
					User user = null;
					String userPin = "-";
					long wire = 5;
					while (wire-- > 0) {
						user = userManager.getUserById(idUser);
						if (user != null) {
							break;
						}
						SpmUtil.sleep(1000);
					}
					if (user != null && user.getSmspinChr() != null) {
						userPin = user.getSmspinChr();
					} else {
						log.warn("No se encontro usuario especificado! --> userid="
								+ idUser);
					}
					strParse = strParse
							.replace(specialWord.getValue(), userPin);
				}
				break;
			case PASSWORD:
				if (reqMessage.getIdDestinatarios() != null) {
					String password = reqMessage.getPass() != null ? reqMessage
							.getPass() : "-";
					strParse = strParse.replace(specialWord.getValue(),
							password);
				}
				break;
			case TOKEN:
				if (reqMessage.getIdDestinatarios() != null) {
					String token = reqMessage.getPass() != null ? reqMessage
							.getPass() : "-";
					strParse = strParse.replace(specialWord.getValue(),
							token);
				}
				break;
			default:
				log.error("SpecialWord with out description --> "
						+ specialWord.getValue());
				break;
			}
		}
		return strParse;
	}

}
