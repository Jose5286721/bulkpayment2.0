package py.com.global.spm.model.jmslisteners;

import com.global.spm.notificationmanager.NotificationResponse;
import com.global.spm.notificationmanager.RequestNotificationManager;
import com.global.spm.notificationmanager.ResponseNotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.eventcodes.NotificationEventEnum;
import py.com.global.spm.model.eventcodes.NotificationManagerEventCodes;
import py.com.global.spm.model.message.NotificationRequestMessage;
import py.com.global.spm.model.services.*;
import py.com.global.spm.model.systemparameters.NotificationCenterWsParameters;
import py.com.global.spm.model.util.*;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class Notifications {
    private static final Logger log = LoggerFactory
            .getLogger(Notifications.class);


    private final NotificationService notificationService;


    private final SystemParameterService systemParameterService;


    private final CompanyService companyService;


    private final PaymentOrderTypeService paymentOrderTypeService;

    private final UserService userService;


    private final BeneficiaryService beneficiaryService;


    private final JmsTemplate jmsTemplate;

    @Autowired
    public Notifications(NotificationService notificationService, SystemParameterService systemParameterService
            ,CompanyService companyService, PaymentOrderTypeService paymentOrderTypeService, UserService userService,
                         BeneficiaryService beneficiaryService, JmsTemplate jmsTemplate) {
        this.notificationService = notificationService;
        this.systemParameterService = systemParameterService;
        this.companyService = companyService;
        this.paymentOrderTypeService = paymentOrderTypeService;
        this.userService = userService;
        this.beneficiaryService = beneficiaryService;
        this.jmsTemplate = jmsTemplate;
    }

   @JmsListener(destination = ConstantsQueue.NOTIFICATION_QUEUE, containerFactory = "myFactory")
    public void receive(NotificationRequestMessage requestMessage, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            log.info("reqMessage--> {}, jmsPriority: {} " , requestMessage, jmsMessageHeaderAccessor.getPriority());
            long init = System.currentTimeMillis();
            String eventValue = requestMessage.getEvent().getValue();
            // PatternPaymentBeneficiary
            NotificationEventEnum event = requestMessage.getEvent();

            // Validaciones previas a la notificacion.
            if (eventValue == null) {
                log.error(" Evento de notificacion null ");
                createLogmessageError(requestMessage,
                        NotificationManagerEventCodes.EVENT_CODE_NULL);
                return;
            }

            String plantillaMensaje = systemParameterService
                    .getParameterValue(SpmProcesses.SPM_NOTIFICATION_MANAGER,
                            eventValue, "Obteniendo -->" + eventValue);
            // Verifica plantilla para el mensaje.
            if (plantillaMensaje == null) {
                log.error("Plantilla de mensaje no existe para el evento--> {}", eventValue);
                createLogmessageError(requestMessage,
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
                    createLogmessageError(requestMessage,
                            NotificationManagerEventCodes.MESSAGE_NULL);
                    return;
                }
            }
            // Caso de notificacion a Beneficiarios
            if (eventValue
                    .equals(NotificationEventEnum.NOTIFY_PAYMENT_BENEFICIARY
                            .getValue())) {
                // Se notifica a los beneficiarios via sms.
                /* Se obtiene el id de los destinatarios */
                Long[] listaIdDestinatarios = requestMessage.getIdDestinatarios();
                log.trace("Obteniendo lista de destinatarios");
                RequestNotificationManager requestNotificationManager = new RequestNotificationManager();
                ArrayList<String> listaNroTelefonos = obtenerNroTelefonoBeneficiarios(listaIdDestinatarios);
                if (!listaNroTelefonos.isEmpty()) {
                    log.warn("telefonos de beneficiarios no existen");
                    requestNotificationManager.setAccounts(listaNroTelefonos);
                    requestNotificationManager.setSendSms(true);
                }
                ArrayList<String> listaEmails = obtenerEmailBeneficiarios(listaIdDestinatarios);
                if (!listaEmails.isEmpty()) {
                    requestNotificationManager.setEmails(listaEmails);
                    requestNotificationManager.setSendEmail(true);
                }
                requestNotificationManager.setSessionId(requestMessage.getOp().getIdpaymentorderPk());
                requestNotificationManager.setMessage(mensaje);
                requestNotificationManager.setLogNotificationId(requestMessage.getOp().getIdpaymentorderPk());
                requestNotificationManager.setSessionId(requestMessage.getOp().getIdpaymentorderPk());
                ResponseNotificationManager responseNotificationManager = notificationService.executeNotification(requestNotificationManager);

                if (responseNotificationManager == null) {
                    for (String destino : listaNroTelefonos) {
                        createLogmessageErrorWs(
                                requestMessage,
                                NotificationManagerEventCodes.WS_ERROR_CONNECTION,
                                mensaje, destino, requestMessage.getOp().getNotifybenficiaryNum());
                        log.error("Error de conexion WS notificaciones - No se envio sms -->  mensaje= {}, destino= {}, evento = {} "
                                , mensaje, destino ,  requestMessage.getEvent());
                    }
                } else {
                    if (Boolean.TRUE.equals(responseNotificationManager.getStatus())) {
                        if (responseNotificationManager.getResponseEmail() != null) {
                            for (NotificationResponse emailResponse : responseNotificationManager.getResponseEmail()) {
                                if (emailResponse != null)
                                    createLogmessage(requestMessage, emailResponse.getDestiny(), mensaje,
                                            false, event, emailResponse.getResponse().getStatus(), emailResponse.getResponse().getStatusCode());
                                else
                                    createLogmessage(requestMessage, SpmConstants.NOTIFY_UNKNOW_DESTINATION, mensaje,
                                            false, event, false, SpmConstants.NOTIFY_OTHER_ERROR);
                            }
                        }
                        if (responseNotificationManager.getResponseSms() != null) {
                            for (NotificationResponse smsResponse : responseNotificationManager.getResponseSms()) {
                                if (smsResponse != null)
                                    createLogmessage(requestMessage, smsResponse.getDestiny(), mensaje,
                                            true, event, smsResponse.getResponse().getStatus(), smsResponse.getResponse().getStatusCode());
                                else
                                    createLogmessage(requestMessage, SpmConstants.NOTIFY_UNKNOW_DESTINATION, mensaje,
                                            true, event, false, SpmConstants.NOTIFY_OTHER_ERROR);
                            }
                        }
                    } else {
                        for (String destino : listaNroTelefonos) {
                            createLogmessageErrorWs(
                                    requestMessage,
                                    NotificationManagerEventCodes.FAIL_NOTIFICATION,
                                    mensaje, destino, requestMessage.getOp().getNotifybenficiaryNum());
                        }
                    }
                }
            } else if (sc != null) {// Se notifican eventos speciales del SMS
                // manager
                String nroTelefono = requestMessage.getUserPhoneNumber();
                if (nroTelefono == null) {
                    log.warn("telefono de usuario no existe ");
                    return;
                }
                Boolean notificarViaSms = true;

                try {
                    String specialMessage = systemParameterService
                            .getParameterValue(
                                    SpmProcesses.SPM_NOTIFICATION_MANAGER,
                                    sc.getValue(),
                                    "Obteniendo -->" + sc.getValue());
                    RequestNotificationManager requestNotificationManager = new RequestNotificationManager();
                    requestNotificationManager.setMessage(specialMessage);
                    requestNotificationManager.setSendSms(true);
                    requestNotificationManager.setLogNotificationId(requestMessage.getOp().getIdpaymentorderPk());
                    requestNotificationManager.setSessionId(requestMessage.getOp().getIdpaymentorderPk());
                    requestNotificationManager.setAccounts(Collections.singletonList(nroTelefono));
                    ResponseNotificationManager respuesta = notificationService.executeNotification(requestNotificationManager);

                    if (respuesta == null) {
                        createLogmessageErrorWs(
                                requestMessage,
                                NotificationManagerEventCodes.WS_ERROR_CONNECTION,
                                mensaje, nroTelefono, notificarViaSms);
                        log.error(" Error de conexion WS notificaciones - No se envio sms -->  nro telefono = {}, mensaje= {},  evento = {}"
                                ,nroTelefono, mensaje,requestMessage.getEvent());

                    } else if (Boolean.FALSE.equals(respuesta.getStatus())) {
                        createLogmessageErrorWs(
                                requestMessage,
                                NotificationManagerEventCodes.FAIL_NOTIFICATION,
                                mensaje, nroTelefono, notificarViaSms);
                    } else {
                        createLogmessage(requestMessage, nroTelefono,
                                specialMessage, notificarViaSms, event, respuesta.getStatus(), respuesta.getStatusCode());
                    }
                } catch (Exception e) {
                    log.error("Error. Special case notification--> "
                            + e.getMessage());
                    log.debug("Error. Special case notification--> ", e);
                }

            } else {
                Long[] listaIdDestinatarios = requestMessage.getIdDestinatarios();
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
                            log.error(" Obteniendo ID destinatario - Creador del Draft Para los sms -->  Evento: {} "
                                    , NotificationEventEnum.NOTIFY_SIGNEVENT
                                            .getValue());
                        }

                    } else {
                        listaNroTelefonos = obtenerNroTelefonoUsuarios(listaIdDestinatarios);
                    }

                    if (listaNroTelefonos == null
                            || listaNroTelefonos.isEmpty()) {
                        log.warn("telefonos de usuarios no existen, lista nro Telefonos = []");
                    } else {
                        // Se notifica a los usuarios via sms.
                        RequestNotificationManager requestNotificationManager = new RequestNotificationManager();
                        requestNotificationManager.setLogNotificationId(requestMessage.getOp().getIdpaymentorderPk());
                        requestNotificationManager.setSessionId(requestMessage.getOp().getIdpaymentorderPk());
                        requestNotificationManager.setMessage(mensaje);
                        requestNotificationManager.setSendSms(true);
                        requestNotificationManager.setAccounts(listaNroTelefonos);
                        ResponseNotificationManager respuesta = notificationService.executeNotification(requestNotificationManager);


                        if (respuesta == null) {
                            for (String nroTelefono : listaNroTelefonos) {
                                createLogmessageErrorWs(
                                        requestMessage,
                                        NotificationManagerEventCodes.WS_ERROR_CONNECTION,
                                        mensaje, nroTelefono, true);
                                log.error(" Error de conexion WS notificaciones - No se envio sms -->  nro telefono = {}, mensaje= {},  evento = {}"
                                        , nroTelefono, mensaje, requestMessage.getEvent());
                            }

                        } else if (Boolean.FALSE.equals(respuesta.getStatus())) {
                            for (String nroTelefono : listaNroTelefonos) {
                                createLogmessageErrorWs(
                                        requestMessage,
                                        NotificationManagerEventCodes.FAIL_NOTIFICATION,
                                        mensaje, nroTelefono, true);
                            }
                        } else {
                            for (NotificationResponse response : respuesta.getResponseSms()) {
                                createLogmessage(requestMessage, response.getDestiny(),
                                        mensaje, true, event,
                                        response.getResponse().getStatus(), response.getResponse().getStatusCode());
                            }
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
                            log.error(" Obteniendo ID destinatario - Creador del Draft Para los emails --> Evento: {} "
                                    , NotificationEventEnum.NOTIFY_SIGNEVENT.getValue());
                        }

                    } else {
                        listaEmails = obtenerEmailUsuarios(listaIdDestinatarios);
                    }

                    if (listaEmails == null || listaEmails.isEmpty()) {
                        log.warn("Emails de usuarios no existen ");
                        return;
                    }

                    RequestNotificationManager requestNotificationManager = new RequestNotificationManager();
                    requestNotificationManager.setMessage(mensaje);
                    requestNotificationManager.setSendSms(false);
                    requestNotificationManager.setSendEmail(true);
                    requestNotificationManager.setEmails(listaEmails);
                    requestNotificationManager.setLogNotificationId(requestMessage.getOp().getIdpaymentorderPk());
                    requestNotificationManager.setSessionId(requestMessage.getOp().getIdpaymentorderPk());
                    ResponseNotificationManager respuesta = notificationService.executeNotification(requestNotificationManager);

                    if (respuesta == null) {
                        for (String email : listaEmails) {
                            createLogmessageErrorWs(
                                    requestMessage,
                                    NotificationManagerEventCodes.WS_ERROR_CONNECTION,
                                    mensaje, email, false);
                            log.error(" Error de conexion WS notificaciones - No se envio email -->  email= {}, mensaje= {}, evento = {} ",
                                    email, mensaje, requestMessage.getEvent());
                        }

                    } else if (Boolean.FALSE.equals(respuesta.getStatus())) {
                        if (respuesta.getResponseEmail() != null)
                            for (NotificationResponse response : respuesta.getResponseEmail()) {
                                if (response != null)
                                    createLogmessageErrorWs(
                                            requestMessage,
                                            NotificationManagerEventCodes.FAIL_NOTIFICATION,
                                            mensaje, response.getDestiny(), false);
                            }
                    } else {
                        if (respuesta.getResponseEmail() != null)
                            for (NotificationResponse response : respuesta.getResponseEmail())
                                if (response != null)
                                    createLogmessage(requestMessage, response.getDestiny(),
                                            mensaje, false, event,
                                            response.getResponse().getStatus(), response.getResponse().getStatusCode());
                    }
                }
            }
            long end = System.currentTimeMillis();
            log.trace("processTime= {} ms", (end - init));
        } catch (Exception e) {
            log.error("Error al Notificar: {} " , e.getMessage());
            log.debug("Error al Notificar: ", e);
        }

    }

    // Persiste en la base de datos el log del proceso de notificaciones.
    private void createLogmessage(NotificationRequestMessage requestMessage, String destino, String mensaje,
                                  boolean notificarViaSms, NotificationEventEnum evento, boolean success,
                                  String transactionCode) {
        try {
            Logmessage logMessage = new Logmessage();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            logMessage.setCreationdateTim(now);
            if (notificarViaSms) {
                logMessage.setDestnumberChr(destino);

                // Set orignumber
                String orignumberChr = systemParameterService
                        .getParameterValue(
                                SpmProcesses.SPM_NOTIFICATION_MANAGER,
                                NotificationCenterWsParameters.SHORT_NUMBER_VALUE
                                        .getValue());
                logMessage.setOrignumberChr(orignumberChr);

            } else {
                logMessage.setDestemail(destino);
            }
            int jmsPriority = 4;
            if (requestMessage.getOp() != null) {
                // set idpaymentorderPk
                Long idpaymentorderPk = requestMessage.getOp()
                        .getIdpaymentorderPk();
                logMessage.setIdpaymentorderPk(idpaymentorderPk);
                // set Company
                logMessage.setCompany(requestMessage.getOp().getCompany());
                jmsPriority = (requestMessage.getOp().getCompany() != null &&
                        requestMessage.getOp().getCompany().getPriority() != null) ? requestMessage.getOp().getCompany().getPriority() : 4;

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
                    break;
                case NOTIFY_NOT_SIGNER_TURN:
                    e = SpmConstants.NOTIFY_NOT_SIGNER_TURN;
                    break;
                case NOTIFY_INCORRECT_PIN_LAST_CHANCE:
                    e = SpmConstants.NOTIFY_INCORRECT_PIN_LAST_CHANCE;
                    break;
                case NOTIFY_INCORRECT_PIN_USER_BLOCKED:
                    e = SpmConstants.NOTIFY_INCORRECT_PIN_USER_BLOCKED;
                    break;
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


            logMessage.setNotificationeventChr(e);


            if (success) {
                logMessage.setStateChr(SpmConstants.SUCCESS);
                logMessage.setIdeventcodeNum(NotificationManagerEventCodes.SUCCESS.getIdEventCode());
                logMessage.setTransactioncodeChr(transactionCode);
                log.info("La notificacion se realizo correctamente --> {}" , e);
            } else {
                logMessage.setStateChr(SpmConstants.ERROR);
                logMessage.setIdeventcodeNum(NotificationManagerEventCodes.OTHER_ERROR.getIdEventCode());
                log.error("Error al realizar la notificacion --> {}", e);
            }
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, logMessage);
            log.debug("Send Logmessage to queue:{} , LogMessage: {} ",ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, logMessage);
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

            if (Boolean.TRUE.equals(notificarViaSms)) {
                logMessage.setDestnumberChr(destino);

                // Set orignumber
                String orignumberChr = systemParameterService
                        .getParameterValue(
                                SpmProcesses.SPM_NOTIFICATION_MANAGER,
                                NotificationCenterWsParameters.SHORT_NUMBER_VALUE
                                        .getValue());
                logMessage.setOrignumberChr(orignumberChr);

            } else {
                logMessage.setDestemail(destino);
            }
            int jmsPriority = 4;
            if (requestMessage.getOp() != null) {
                // set idpaymentorderPk
                Long idpaymentorderPk = requestMessage.getOp()
                        .getIdpaymentorderPk();
                logMessage.setIdpaymentorderPk(idpaymentorderPk);

                // set idCompanyPk
                Long idcompanyPk = requestMessage.getOp().getCompany().getIdcompanyPk();
                logMessage.setCompany(companyService.getCompanyById(idcompanyPk));
                jmsPriority = (requestMessage.getOp().getCompany().getPriority() != null) ?
                        requestMessage.getOp().getCompany().getPriority() : 4;
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
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, logMessage);
            log.debug("Send Logmessage to queue: {}, Message: {} ", ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, logMessage);

        } catch (Exception ex) {
            log.error("ERROR. Insert logmessage --> " + ex.getMessage(), ex);
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
                break;
            default:
                break;
        }
        return res;
    }

    /** Crea el mensaje para las notificaciones.
     *
     * @param plantilla
     * @param reqMessage
     * @return el mensaje a ser notificado.
     **/
    private String createMessage(String plantilla,
                                 NotificationRequestMessage reqMessage) {
        String strParse = plantilla;
        log.debug("Parsing plantilla --> {} , mensaje {}", plantilla, reqMessage);
        for (SpecialWordsDescription specialWord : SpecialWordsDescription
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
                        strParse = strParse.replace(specialWord.getValue(), (f.format(reqMessage.getPago()
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
                        Long idCompanyPk = reqMessage.getOp().getCompany().getIdcompanyPk();
                        Company company = companyService.getCompanyById(idCompanyPk);
                        String name = (company != null) ? company.getCompanynameChr() : "";
                        strParse = strParse.replace(specialWord.getValue(), name);
                    }

                    break;
                case OP_TYPE:
                    if (reqMessage.getOp() != null) {
                        long idPaymentordertype = reqMessage.getOp().getPaymentordertype().getIdorderpaymenttypePk();
                        Paymentordertype pot = paymentOrderTypeService
                                .getPaymentordertypeById(idPaymentordertype);
                        String nombreTipoOp = (pot != null) ? pot.getNameChr() : "";
                        strParse = strParse.replace(specialWord.getValue(),
                                nombreTipoOp);
                    }

                    break;
                case PAYMENT_NUMBER:
                    if (reqMessage.getPago() != null) {
                        NumberFormat f = NumberFormat.getInstance(new Locale("es"));
                        strParse = strParse.replace(specialWord.getValue(), (f.format(reqMessage.getPago()
                                .getIdpaymentNum())));
                    }
                    break;
                case PO_NUMBER:
                    if (reqMessage.getOp() != null) {
                        NumberFormat f = NumberFormat.getInstance(new Locale("es"));
                        strParse = strParse.replace(specialWord.getValue(), (f.format(reqMessage.getOp()
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
                        String estado;
                        switch (reqMessage.getOp().getStateChr()) {
                            case PaymentOrderStates.CANCELADA:
                                estado = "Cancelada";
                                break;
                            case PaymentOrderStates.FIRMA_EN_PROCESO:
                                estado = "En Proceso";
                                break;
                            case PaymentOrderStates.ERROR:
                                estado = "No Pagada";
                                break;
                            case PaymentOrderStates.NO_REVERTIDA:
                                estado = "No Revertida";
                                break;
                            case PaymentOrderStates.SATISFACTORIO:
                                estado = "Pagada";
                                break;
                            case PaymentOrderStates.PAGO_EN_PROCESO:
                                estado = "Pagando";
                                break;
                            case PaymentOrderStates.PARCIALMENTE_PAGADA:
                                estado = "Parcialmente Pagada";
                                break;
                            case PaymentOrderStates.REVERTIDA_PARCIALMENTE:
                                estado = "Parcialmente Revertida";
                                break;
                            case PaymentOrderStates.PAGO_PENDIENTE:
                                estado = "Pendiente de Pago";
                                break;
                            case PaymentOrderStates.REVERTIDA:
                                estado = "Revertida";
                                break;
                            case PaymentOrderStates.REVERSION_EN_PROCESO:
                                estado = "Revirtiendo";
                                break;
                            default:
                                estado = "Estado No valido";
                                break;
                        }

                        strParse = strParse.replace(specialWord.getValue(), estado);
                    }
                    break;

                case TOTAL_SUCCESSFUL:
                    if (reqMessage.getOp() != null
                            && reqMessage.getOp().getPaymentsuccessNum() != null) {
                        NumberFormat f = NumberFormat.getInstance(new Locale("es"));
                        strParse = strParse.replace(specialWord.getValue(), (f.format(reqMessage.getOp()
                                .getPaymentsuccessNum())));
                    }
                    break;
                case PARTIAL_PAYMENT_AMOUNT:
                    if (reqMessage.getOp() != null
                            && reqMessage.getOp().getPaymentpartsucNum() != null) {
                        NumberFormat f = NumberFormat.getInstance(new Locale("es"));
                        strParse = strParse.replace(specialWord.getValue(), (f.format(reqMessage.getOp()
                                .getPaymentpartsucNum())));
                    }
                    break;
                case PAYMENT_ERROR:
                    if (reqMessage.getOp() != null
                            && reqMessage.getOp().getPaymenterrorNum() != null) {
                        NumberFormat f = NumberFormat.getInstance(new Locale("es"));
                        strParse = strParse.replace(specialWord.getValue(), (f.format(reqMessage.getOp()
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

                        Long[] i = reqMessage.getIdDestinatarios();
                        Long idUser = i[0];// Se obtiene el unico elemento del
                        // vector.
                        User user = null;
                        String username = "null";

                        user = userService.getUserById(idUser);

                        if (user != null) {
                            username = user.getUsernameChr();
                        } else {
                            log.warn("No se encontro usuario especificado! --> userid= {}", idUser);
                        }
                        strParse = strParse.replace(specialWord.getValue(),
                                username);

                    }
                    break;
                case PIN:
                    if (reqMessage.getIdDestinatarios() != null
                            && reqMessage.getIdDestinatarios().length > 0) {
                        Long[] i = reqMessage.getIdDestinatarios();
                        Long idUser = i[0];// Se obtiene el unico elemento del
                        // vector.
                        User user = null;
                        String userPin = "-";
                        user = userService.getUserById(idUser);
                        if (user != null && user.getSmspinChr() != null) {
                            userPin = user.getSmspinChr();
                        } else {
                            log.warn("No se encontro usuario especificado! --> userid= {}", idUser);
                        }
                        strParse = strParse
                                .replace(specialWord.getValue(), userPin);
                    }
                    break;
                case PASSWORD:
                case TOKEN:
                    if (reqMessage.getIdDestinatarios() != null) {
                        String password = reqMessage.getPass() != null ? reqMessage
                                .getPass() : "-";
                        strParse = strParse.replace(specialWord.getValue(),
                                password);
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
                Long idcompanyPk = requestMessage.getOp().getCompany().getIdcompanyPk();
                logMessageError.getCompany().setIdcompanyPk(idcompanyPk);

            }
            int jmsPriority = (requestMessage.getOp()!=null && requestMessage.getOp().getCompany().getPriority() != null)
                    ?requestMessage.getOp().getCompany().getPriority() : 4;
            // Set orignumber
            String orignumberChr = systemParameterService.getParameterValue(
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
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, logMessageError);
            log.debug("Send Logmessage to queue: {}, message: {} ", ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, logMessageError);

        } catch (Exception e) {
            log.error("Error creando el registro de error del proceso de "
                    + "notificaciones");
        }

    }

    private ArrayList<String> obtenerNroTelefonoBeneficiarios(
            Long[] listaIdDestinatarios) {
        ArrayList<String> numerosBeneficiarios = new ArrayList<>();
        Beneficiary beneficiario = new Beneficiary();
        try {
            if (listaIdDestinatarios != null)
                for (Long idBeneficiario : listaIdDestinatarios) {
                    try {
                        beneficiario = beneficiaryService
                                .getBeneficiaryById(idBeneficiario);
                        if (beneficiario != null) {
                            if (beneficiario.isNotifysmsNum()) {
                                String nroTel = beneficiario.getPhonenumberChr();
                                numerosBeneficiarios.add(nroTel);
                            }
                        } else {
                            log.warn("Beneficiario con Id: {} no encontrado", idBeneficiario);
                        }

                    } catch (Exception ex) {
                        log.error(
                                "Obtener beneficiario by id -->"+ ((beneficiario!=null)?beneficiario.getIdbeneficiaryPk():"null"), ex);
                    }
                }
        } catch (Exception e) {
            log.error("obtenerNroTelefonoBeneficiarios "
                    + e.getMessage());
        }
        return numerosBeneficiarios;

    }

    private ArrayList<String> obtenerNroTelefonoUsuarios(
            Long[] listaIdDestinatarios) {
        ArrayList<String> numerosUsuarios = new ArrayList<>();
        try {
            for (Long idUsuario : listaIdDestinatarios) {
                try {
                    User usuario = userService.getUserById(idUsuario);
                    if (usuario != null) {
                        if (usuario.isNotifysmsNum()) {
                            String nroTel = usuario.getPhonenumberChr();
                            numerosUsuarios.add(nroTel);
                        }
                    } else {
                        log.warn("Usuario con Id: {}, no encontrado", idUsuario);
                    }

                } catch (Exception ex) {
                    log.error("Obtener usuario by id" + ex.getMessage(), ex);
                }
            }
        } catch (Exception e) {
            log.error("obtenerNroTelefonoUsuarios: " , e);
        }
        return numerosUsuarios;
    }

    private ArrayList<String> obtenerEmailBeneficiarios(
            Long[] listaIdDestinatarios) {
        ArrayList<String> emailsBeneficiarios = new ArrayList<>();
        try {
            for (Long idBeneficiario : listaIdDestinatarios) {
                try {
                    Beneficiary beneficiario = beneficiaryService
                            .getBeneficiaryById(idBeneficiario);
                    if (beneficiario != null) {
                        if (beneficiario.isNotifyemailNum()) {
                            String email = beneficiario.getEmailChr();
                            emailsBeneficiarios.add(email);
                        }
                    } else {
                        log.warn("Beneficiario con Id: {} no encontrado", idBeneficiario);
                    }

                } catch (Exception ex) {
                    log.error("Obtener beneficiario by id --> " + ex.getMessage(),
                            ex);
                }
            }
        } catch (Exception e) {
            log.error("obtenerEmailBeneficiarios: " + e.getMessage(), e);
        }
        return emailsBeneficiarios;
    }

    private ArrayList<String> obtenerEmailUsuarios(Long[] listaIdDestinatarios) {
        ArrayList<String> emailsUsuarios = new ArrayList<>();

        for (Long idUsuario : listaIdDestinatarios) {
            try {
                User usuario = userService.getUserById(idUsuario);
                if (usuario != null) {
                    if (usuario.isNotifyemailNum()) {
                        String email = usuario.getEmailChr();
                        emailsUsuarios.add(email);
                    }
                } else {
                    log.warn("Usuario con Id: {} no encontrado ", idUsuario);
                }

            } catch (Exception ex) {
                log.error("Obtener usuario by id" + ex.getMessage(), ex);
            }
        }
        return emailsUsuarios;
    }
}
