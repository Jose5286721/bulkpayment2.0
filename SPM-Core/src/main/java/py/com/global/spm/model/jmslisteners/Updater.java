package py.com.global.spm.model.jmslisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Draft;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.dto.redis.LogPaymentCache;
import py.com.global.spm.model.dto.redis.PaymentOrderCache;
import py.com.global.spm.model.eventcodes.NotificationEventEnum;
import py.com.global.spm.model.message.NotificationRequestMessage;
import py.com.global.spm.model.message.OPManagerToUpdaterMessage;
import py.com.global.spm.model.message.ReversionProcessToUpdaterMessage;
import py.com.global.spm.model.message.TransferProcessToUpdaterMessage;
import py.com.global.spm.model.services.*;
import py.com.global.spm.model.systemparameters.NotificationParametersEnum;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmUtil;

import java.util.Date;
import java.util.List;

import static py.com.global.spm.model.systemparameters.NotificationParametersEnum.*;

@Component
public class Updater {
    private static final Logger log = LoggerFactory
            .getLogger(Updater.class);

    private final  DraftService draftService;

    private final UpdaterService updaterService;

    private final JmsTemplate jmsTemplate;

    private final SystemParameterService systemParameterService;

    private final PaymentOrderService paymentOrderService;

    private final LogDraftOpService logDraftOpService;


    @Autowired
    public Updater(DraftService draftService, UpdaterService updaterService, JmsTemplate jmsTemplate, SystemParameterService systemParameterService, PaymentOrderService paymentOrderService, LogDraftOpService logDraftOpService) {
        this.draftService = draftService;
        this.updaterService = updaterService;
        this.jmsTemplate = jmsTemplate;
        this.systemParameterService = systemParameterService;
        this.paymentOrderService = paymentOrderService;
        this.logDraftOpService = logDraftOpService;
    }

   @JmsListener(destination = ConstantsQueue.UPDATER_QUEUE_TRANSFER, containerFactory = "myFactory")
   @Transactional
   public void receive(TransferProcessToUpdaterMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        long init = System.currentTimeMillis();
        log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
        try {
            PaymentOrderCache poc = request.getPaymentOrderCache();
            List<LogPaymentCache> logPaymentCache = request.getLogPaymentCaches();
            Paymentorder paymentorder = paymentOrderService.getPaymentorder(poc.getIdpaymentorderPk());
            paymentorder.setStateChr(poc.getStateChr());
            paymentorder.setAmountpaidNum(poc.getAmountPayOp());
            // notificaciones
            if (paymentorder != null) {
                if (paymentorder.getStateChr().equalsIgnoreCase(
                        PaymentOrderStates.PAGO_PENDIENTE)) {
                    log.warn("Orden de pago pendiente de pago --> {}" , paymentorder);
                    /*
                     * valida y notifica que no se realizo el pago por saldo
                     * insuficiente
                     */
                    validateAndNotifyPaidPending(paymentorder);
                } else if (paymentorder.getStateChr().equalsIgnoreCase(
                        PaymentOrderStates.ERROR)) {
                    log.warn("Orden de pago no pagada --> {}" , paymentorder);
                    // actualizar contador de transacciones de orden de pago
                    updatePaymentorderCounter(paymentorder,
                           logPaymentCache);
                    // valida y notifica no pagado
                    validateAndNotifyNoPagado(paymentorder);
                } else if (paymentorder.getStateChr().equalsIgnoreCase(
                        PaymentOrderStates.SATISFACTORIO)
                        || paymentorder.getStateChr().equalsIgnoreCase(
                        PaymentOrderStates.PARCIALMENTE_PAGADA)) {
                    // actualizar contador de transacciones de empresa
                    updateCompanyCounters(paymentorder, logPaymentCache);
                    // actualizar contador de transacciones de orden de pago
                    updatePaymentorderCounter(paymentorder, logPaymentCache);
                    // validar y notificar firmantes
                    validateAndNotifyPayment(paymentorder);
                    // validar y notificar a beneficiarios pago o parcialmente
                    // pagado
                    validateAndNotifyPaymentToBeneficiaries(paymentorder,logPaymentCache);
                } else {
                    log.warn("Notificacion no realizada. Estado no valido --> {}", paymentorder);
                }
                // actualizar orden de pago
                paymentorder.setReintento(poc.isReintento());
                paymentorder.setUpdatedateTim(new Date());
                paymentOrderService.saveOrUpdate(paymentorder);
            } else {
                log.error("UPDATER QUEUE -> received a NULL PaymentOrder ");
            }
        } catch (Exception e) {
            log.error("Updating --> " + e.getMessage() , e);
        }
        long end = System.currentTimeMillis();
        log.trace("processTime= {} seg, received request= {}", SpmUtil.millisToSecondStr(end - init), request);
    }

   @JmsListener(destination = ConstantsQueue.UPDATER_QUEUE_REVERSION, containerFactory = "myFactory")
    public void receive(ReversionProcessToUpdaterMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
        // ByR2:
        Long init = System.currentTimeMillis();
        Long end = null;
        log.debug("Processing request message --> {} ", request);
        Paymentorder paymentorder = request.getPaymentorder();
        // actualizar estado orden de pago
        //this.updatePaymentorder(paymentorder, false);
        // notificaciones
        if (paymentorder.getStateChr().equalsIgnoreCase(
                PaymentOrderStates.NO_REVERTIDA)) {
            log.warn("Orden de pago no revertida -->  {}" , paymentorder);
            // valida y notifica no revertido
            validateAndNotifyNoRevertida(paymentorder);
        } else if (paymentorder.getStateChr().equalsIgnoreCase(
                PaymentOrderStates.REVERTIDA)
                || paymentorder.getStateChr().equalsIgnoreCase(
                PaymentOrderStates.REVERTIDA_PARCIALMENTE)) {
            // validar y notificar a firmantes revertida o parcialmente
            // revertida
            validateAndNotifyRevertida(paymentorder);
        } else {
            log.warn("Notificacion no realizada. Estado no valido --> {} ", paymentorder);
        }
        end = System.currentTimeMillis();
        log.trace("processTime= {} segs, request={} ", SpmUtil.millisToSecondStr(end - init), request);

    }

   @JmsListener(destination = ConstantsQueue.UPDATER_QUEUE_OP_MANAGER, containerFactory = "myFactory")
    public void receive(OPManagerToUpdaterMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
            logDraftOpService.insertLogdraftop(request.getLogdraftop());
            log.debug("Insert of LogDraftOPService in database --> OK");
        } catch (Exception e) {
            log.error("UPDATER_QUEUE_OP_MANAGER: " + e.getMessage() , e);
            log.debug("UPDATER_QUEUE_OP_MANAGER", e);
        }


    }


    private void validateAndNotifyPaidPending(Paymentorder paymentorder) {
        try {
            if (paymentorder != null) {
                log.debug("Notificar pendiente de pago --> {} ", paymentorder);
                // notificar creador OP (draft)
                Draft draft = null;
                draft = draftService.getDraftById(paymentorder.getDraft().getIddraftPk());
                if (draft != null && draft.getUser() != null) {
                    // Default JMS priority = 4
                    int jmsPriority = (paymentorder.getCompany().getPriority() != null)
                            ? paymentorder.getCompany().getPriority() : 4;
                    NotificationRequestMessage notificationRequest;
                    notificationRequest = updaterService.getSMSRequest(paymentorder,
                            NotificationEventEnum.NOTIFY_INSUFFICIENT_MONEY,
                            draft.getUser().getIduserPk());
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);

                    // validar y notificar firmantes
                    boolean notifyPendingPaid = Boolean.parseBoolean(systemParameterService.getParameterValue(
                            NOTIFY_SIGN_PAID_PENDIND.getIdProcess()
                            , NOTIFY_SIGN_PAID_PENDIND.getParameterName()));
                    if (notifyPendingPaid) {
                        Long[] signers = updaterService.getSignersWithoutIdDraftCreator(
                                paymentorder, draft.getUser().getIduserPk());
                        if (signers != null) {
                            notificationRequest = updaterService.getSMSRequest(
                                    paymentorder,
                                    NotificationEventEnum.NOTIFY_INSUFFICIENT_MONEY,
                                    signers);
                            jmsTemplate.setPriority(jmsPriority);
                            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
                        } else {
                            log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> {} ",
                                    paymentorder.getIdpaymentorderPk());
                        }
                    } else {
                        log.warn("Notificacion pendiente de pago a firmantes --> false");
                    }

                } else {
                    log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> {} ",
                             paymentorder);
                }
            } else {
                log.error("UPDATER QUEUE -> receive a NULL PAYMENTORDER!!!");
            }
        } catch (Exception e) {
            log.error("validateAndNotifyPaidPending: {} ", e.getMessage());
            log.debug("validateAndNotifyPaidPending: ", e);
        }
    }

    /**
     * Actualiza los contadores de transacciones (paymentsuccessNum,
     * paymenterrorNum, paymentpartsucNum) de la orden de pago. El
     * totalpaymentNum ya setea el transfer process
     *
     * @param paymentorder
     */
    private void updatePaymentorderCounter(Paymentorder paymentorder,
                                           List<LogPaymentCache> logpaymentList) {
        if (paymentorder != null) {
            long success = paymentorder.getPaymentsuccessNum();
            long parcsuccess = 0;
            long error = 0;
            if (logpaymentList != null)
                for (LogPaymentCache logpayment : logpaymentList) {
                    if (logpayment != null) {
                        if (PaymentStates.SATISFACTORIO.
                                equals(logpayment.getStateChr())) {
                            success++;
                        } else if (PaymentStates.PARCIALMENTE_PAGADA.
                                equals(logpayment.getStateChr())) {
                            parcsuccess++;
                        } else if (PaymentStates.ERROR.equals(logpayment.getStateChr())) {
                            error++;
                        } else {
                            log.warn("Guarda que el estado de este logpayment no se tiene en cuenta para actualizar contadores -->  {}"
                                    , logpayment);
                        }
                    }
                }
            paymentorder.setPaymentsuccessNum(success);
            paymentorder.setPaymentpartsucNum(parcsuccess);
            paymentorder.setPaymenterrorNum(error);
        }
    }

    private void validateAndNotifyNoPagado(Paymentorder paymentorder) {
        log.debug("Notificar no pagado --> {}", paymentorder);
        int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
        // notificar creador OP
        Draft draft = paymentorder.getDraft();
        if (draft.getUser() != null) {
            NotificationRequestMessage notificationRequest;
            notificationRequest = updaterService
                    .getSMSRequest(paymentorder,
                            NotificationEventEnum.NOTIFY_PO_UNPAID,
                            draft.getUser().getIduserPk());
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);

            // validar y notificar firmantes
            boolean notifyUnpaid =
                    Boolean.parseBoolean(systemParameterService.getParameterValue(NOTIFY_SIGN_UNPAID.getIdProcess(), NotificationParametersEnum.NOTIFY_SIGN_UNPAID.getParameterName(), "true"));
            if (notifyUnpaid) {
                Long[] signers = (draft.getUser() != null) ? updaterService.getSignersWithoutIdDraftCreator(
                        paymentorder, draft.getUser().getIduserPk()) : null;
                if (signers != null) {
                    notificationRequest = updaterService.getSMSRequest(
                            paymentorder, NotificationEventEnum.NOTIFY_PO_UNPAID,
                            signers);
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
                } else {
                    log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> {}"
                            ,  paymentorder);
                }
            } else {
                log.warn("Notificacion a firmantes de ordenes de pago no pagadas --> false");
            }
        } else {
            log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> {}"
                    , paymentorder);
        }
    }

    private void updateCompanyCounters(Paymentorder paymentorder,
                                       List<LogPaymentCache> logpaymentList) {
        int payments = 0;
        int unpaid = 0;
        int total = 0;
        if (logpaymentList != null) {
            for (LogPaymentCache lp : logpaymentList) {
                if (lp != null) {
                    if (PaymentStates.SATISFACTORIO.equals(lp.getStateChr())) {
                        payments++;
                    } else {
                        unpaid++;
                    }
                    total++;
                }
            }
            log.debug("Updating company counters --> paid= {}  , unpaid= {} , total= {}, PaymentOrder = {} ", payments, unpaid, total, paymentorder);
            boolean updateCompany = updaterService.updateCompany(paymentorder.getCompany(), payments,
                    paymentorder.getUpdatedateTim());
            if (!updateCompany)
                log.error("Failed to Update the Company Counters, idCompany : {} ", paymentorder.getCompany().getIdcompanyPk());
        }
    }

    private void validateAndNotifyPaymentToBeneficiaries(
            Paymentorder paymentorder, List<LogPaymentCache> logpaymentList) {
        if (paymentorder != null) {
            if (paymentorder.getNotifybenficiaryNum()) {
                int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
                for (LogPaymentCache logpayment : logpaymentList) {
                    if (PaymentStates.SATISFACTORIO.equals(logpayment.getStateChr())
                            || PaymentStates.PARCIALMENTE_PAGADA.equals(logpayment.getStateChr())) {
                        // notificar beneficiario
                        NotificationRequestMessage notificationRequest = updaterService
                                .getSMSRequest(
                                        paymentorder,
                                        logpayment,
                                        NotificationEventEnum.NOTIFY_PAYMENT_BENEFICIARY,
                                        logpayment.getIdbeneficiaryPk());

                        jmsTemplate.setPriority(jmsPriority);
                        jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
                    }
                }
            } else {
                log.warn("Notificacion de pago a beneficiarios --> false");
            }
        }
    }

    private void validateAndNotifyPayment(Paymentorder paymentorder) {
        if (paymentorder != null) {
            NotificationRequestMessage notificationRequest;
            // notificar creador OP
            Draft draft = paymentorder.getDraft();
            if (draft.getUser() != null) {
                int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
                notificationRequest = updaterService.getSMSRequest(paymentorder,
                        NotificationEventEnum.NOTIFY_PAYMENT_SIGNERS,
                        draft.getUser().getIduserPk());
                jmsTemplate.setPriority(jmsPriority);
                jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);

                if (paymentorder.getNotifysignerNum()) {
                    Long[] signers = updaterService.getSignersWithoutIdDraftCreator(
                            paymentorder, draft.getUser().getIduserPk());
                    if (signers != null) {
                        notificationRequest = updaterService.getSMSRequest(
                                paymentorder,
                                NotificationEventEnum.NOTIFY_PAYMENT_SIGNERS, signers);
                        jmsTemplate.setPriority(jmsPriority);
                        jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
                    } else {
                        log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> {}",
                                paymentorder);
                    }
                } else {
                    log.warn("Notificacion de pago a firmantes --> false");
                }
            } else {
                log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> {} "
                        , paymentorder);
            }
        }
    }

    private void validateAndNotifyRevertida(Paymentorder paymentorder) {
        // ByR2
        log.debug("Notificar orden de pago revertida --> {}",
                 paymentorder.toString());
        int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
        NotificationRequestMessage notificationRequest;
        // notificar creador OP
        Draft draft = paymentorder.getDraft();
            notificationRequest = updaterService.getSMSRequest(paymentorder,
                    NotificationEventEnum.NOTIFY_PO_REVERSION,
                    draft.getUser().getIduserPk());
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
        // validar y notificar firmantes
        boolean notifyUnpaid = Boolean.parseBoolean(systemParameterService.getParameterValue(NotificationParametersEnum.NOTIFY_SIGN_REVERSION.getIdProcess(),
                NotificationParametersEnum.NOTIFY_SIGN_REVERSION.getParameterName(), "true"));
        if (notifyUnpaid) {
            Long[] signers = updaterService.getSignersWithoutIdDraftCreator(
                    paymentorder, draft.getUser().getIduserPk());
            if (signers != null) {
                notificationRequest = updaterService.getSMSRequest(
                        paymentorder,
                        NotificationEventEnum.NOTIFY_PO_REVERSION, signers);
                jmsTemplate.setPriority(jmsPriority);
                jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
            } else {
                log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> {} ",
                         paymentorder);
            }
        } else {
            log.warn("Notificacion no revertido a firmantes --> false");
        }

    }

    private void validateAndNotifyNoRevertida(Paymentorder paymentorder) {
        // ByR2
        log.debug("Notificar no revertido --> {}" , paymentorder);
        NotificationRequestMessage notificationRequest;
        // notificar creador OP
        Draft draft = paymentorder.getDraft();
        if (draft.getUser() != null) {
            int jmsPriority =(paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
            notificationRequest = updaterService.getSMSRequest(paymentorder,
                    NotificationEventEnum.NOTIFY_PO_UNREVERSION,
                    draft.getUser().getIduserPk());
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);

            // validar y notificar firmantes
            boolean notifyUnpaid = Boolean.parseBoolean(systemParameterService.getParameterValue(NotificationParametersEnum.NOTIFY_SIGN_REVERSION.getIdProcess(),
                    NotificationParametersEnum.NOTIFY_SIGN_REVERSION.getParameterName(), "true"));
            if (notifyUnpaid) {
                Long[] signers = updaterService.getSignersWithoutIdDraftCreator(
                        paymentorder, draft.getUser().getIduserPk());
                if (signers != null) {
                    notificationRequest = updaterService.getSMSRequest(
                            paymentorder,
                            NotificationEventEnum.NOTIFY_PO_UNREVERSION, signers);
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notificationRequest);
                } else {
                    log.error("Error de notificacion. No se encuentran los firmantes de la orden de pago! --> {}"
                            , paymentorder);
                }
            } else {
                log.warn("Notificacion no revertido a firmantes --> false");
            }
        } else {
            log.error("Error de notificacion. No se encuentra draft correspondiente a la orden de pago! --> {}", paymentorder);
        }
    }
}
