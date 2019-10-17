package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.eventcodes.NotificationEventEnum;
import py.com.global.spm.model.message.NotificationRequestMessage;
import py.com.global.spm.model.message.SMSMangerToFlowManagerMessage;
import py.com.global.spm.model.systemparameters.FlowManagerParameters;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.domain.utils.CryptoUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class FlowService {
    private final Logger logger = LoggerFactory.getLogger(FlowService.class);

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private DraftService draftService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentOrderService paymentOrderService;

    public List<Approval> generateApprovalFlow(Paymentorder paymentorder) {
        try {
           Workflow workflow = workflowService.getWorkflowById(paymentorder.getWorkflow().getIdworkflowPk());
            if (workflow != null) {
                List<Approval> approvalList = new ArrayList<Approval>();
                Approval approval;

                for (Workflowdet workflowDet : workflow.getWorkflowdets()) {
                    approval = new Approval();
                    approval.setPaymentorder(paymentorder);
                    approval.setCreationdateTim(new Timestamp(System.currentTimeMillis()));
                    approval.setCompany(paymentorder.getCompany());
                    approval.setWorkflowdet(workflowDet);
                    approval.setSignedNum(false);
                    approval.setStepPk(workflowDet.getStep());
                    approvalList.add(approval);
                }


                return  approvalService.saveApprovals(approvalList);
            } else {
                logger.error("Workflow no encontrado --> " + paymentorder.toString());
                return null;
            }
        } catch (Exception e) {
            logger.error("Generando approval flow --> " + paymentorder.toString());
            return null;
        }
    }


    public Long notifyDraftCreator(Draft draft, Paymentorder paymentorder, FlowManagerEventCodes event) {
        Long[] destinatario = {draft.getUser().getIduserPk()};

        NotificationRequestMessage notification = this
                .getNotificationRequestMessage(paymentorder, destinatario, event);
        int jmsPriority = (draft.getCompany().getPriority()!=null)? draft.getCompany().getPriority(): 4;
        jmsTemplate.setPriority(jmsPriority);
        jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
        return draft.getUser().getIduserPk();
    }

    public void notifySigner(Paymentorder paymentorder, long iduser, FlowManagerEventCodes event) {
        Long[] destinatario = { iduser };

        NotificationRequestMessage notification = this
                .getNotificationRequestMessage(paymentorder, destinatario, event);
        int jmsPriority = (paymentorder.getCompany().getPriority()!=null)? paymentorder.getCompany().getPriority(): 4;
        jmsTemplate.setPriority(jmsPriority);
        jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);

    }

    public void notifySignEvent(Paymentorder paymentorder, long iduser) {
        Draft draft = draftService.getDraftById(paymentorder.getDraft().getIddraftPk());

        if(draft!= null){
            Long[] destinatario = { iduser, draft.getUser().getIduserPk() };

            NotificationRequestMessage notification = this
                    .getNotificationRequestMessage(paymentorder, destinatario, NotificationEventEnum.NOTIFY_SIGNEVENT);
            int jmsPriority = (draft.getCompany().getPriority()!=null)? draft.getCompany().getPriority(): 4;
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
        }else
            logger.error("No existe el draft con id: {}", paymentorder.getDraft().getIddraftPk());

    }



    public void notifyDraftCreator(Paymentorder paymentorder, FlowManagerEventCodes event) {
        Draft draft = draftService.getDraftById(paymentorder.getIdpaymentorderPk());

        if(draft == null){
            logger.warn("No existe draft con idpaymentOrder: {}" ,paymentorder.getIdpaymentorderPk());
        }else {
            Long[] destinatario = {draft.getUser().getIduserPk()};

            NotificationRequestMessage notification = this
                    .getNotificationRequestMessage(paymentorder, destinatario, event);
            int jmsPriority = (draft.getCompany().getPriority()!=null)? draft.getCompany().getPriority(): 4;
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
        }
    }

    private NotificationRequestMessage getNotificationRequestMessage(
            Paymentorder paymentorder, Long[] destinatarios, FlowManagerEventCodes event) {

        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(FlowManagerEventCodes.parseMessage(event));
        notificationRequest.setOp(paymentorder);
        notificationRequest.setIdDestinatarios(destinatarios);
        return notificationRequest;

    }

    private NotificationRequestMessage getNotificationRequestMessage(
            Paymentorder paymentorder, Long[] destinatarios, NotificationEventEnum event) {
        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(event);
        notificationRequest.setOp(paymentorder);
        notificationRequest.setIdDestinatarios(destinatarios);
        return notificationRequest;
    }


    public void notifyAllSigners(Paymentorder paymentorder, FlowManagerEventCodes event) {
        List<Approval> approvalList = approvalService.getApprovalsByIdPaymentorder(paymentorder
                .getIdpaymentorderPk());
        NotificationRequestMessage notification = this.getNotificationRequestMessage(paymentorder,
                this.getSigners(approvalList), event);
        int jmsPriority = (paymentorder.getCompany().getPriority()!=null)? paymentorder.getCompany().getPriority(): 4;
        jmsTemplate.setPriority(jmsPriority);
        jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
    }

    private Long[] getSigners(List<Approval> approvalList) {
        List<Long> list = new ArrayList<>();
        Long[] signers = new Long[0];
        for (Approval a : approvalList) {
            list.add(a.getWorkflowdet().getUser().getIduserPk());
        }
        return list.toArray(signers);
    }

    public boolean isLastSigner(Paymentorder paymentorder) {
        return paymentorder.getStateChr().equalsIgnoreCase(
                PaymentOrderStates.PAGO_PENDIENTE);
    }

    public void notifySignIncorrectPinError(Paymentorder paymentorder, long iduser) {
        long maxRetries = systemParameterService
                .getParameterValue(FlowManagerParameters.MAX_APPROVAL_FAILED.getIdProcess(),
                        FlowManagerParameters.MAX_APPROVAL_FAILED.getParameterName(),
                        FlowManagerParameters.MAX_APPROVAL_FAILED.getDefaultValue());

        logger.debug("notifySignIncorrectPinError --> iduser=" + iduser + ", " + paymentorder);
        User user = userService.getUserById(iduser);

        if(user!= null) {
            int attemptsLefts = (int) (maxRetries - user.getAttemptNum());
            Long[] destinatario = {iduser};

            NotificationRequestMessage notification = this.getNotificationRequestMessage(paymentorder, destinatario,
                    FlowManagerEventCodes.INCORRECT_PIN);
            notification.setCantReintentos(attemptsLefts);
            int jmsPriority = (paymentorder.getCompany().getPriority()!=null)? paymentorder.getCompany().getPriority(): 4;
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
        }

    }


    public void notifyNextSigner(Paymentorder paymentorder) {
        List<Approval> signers = approvalService.getApprovalsByIdPaymentorder(paymentorder.getIdpaymentorderPk());
        NotificationRequestMessage notification;

        if(signers == null){
            logger.error("No se pudo obtener los firmantes para la notificacion, Orden de pago:" + paymentorder);
        }else {
            int jmsPriority = (paymentorder.getCompany().getPriority()!=null)? paymentorder.getCompany().getPriority(): 4;
            for (Approval approval : signers) {
                if (!approval.isSignedNum()) {
                    Long[] destinatario = {approval.getWorkflowdet().getUser().getIduserPk()};

                    // notificar firmante via SMS
                    notification = this.getNotificationRequestMessage(paymentorder, destinatario, FlowManagerEventCodes.SIGNER_NOTIFICATE);

                    long maxApprovalTime = systemParameterService
                            .getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME.getIdProcess(),
                                    FlowManagerParameters.MAX_APPROVAL_TIME.getParameterName(),
                                    FlowManagerParameters.MAX_APPROVAL_TIME.getDefaultValue());

                    if (maxApprovalTime > 0) {
                        notification.setTiempoValidezSms((int) maxApprovalTime);
                    }
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);

                    // notificar firmante via EMAIL
                    notification = this.getNotificationRequestMessage(paymentorder, destinatario,
                            FlowManagerEventCodes.SIGNER_NOTIFICATE_EMAIL);

                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
                    break;
                }
            }
        }
    }

    public void notifyNextSigner(Paymentorder paymentorder, long idlastapproval) {
        List<Approval> signers = approvalService
                .getApprovalsByIdPaymentorder(paymentorder.getIdpaymentorderPk());
        NotificationRequestMessage notification;

        if(signers == null){
            logger.error("No se pudo obtener los firmantes para la notificacion, Orden de pago:" + paymentorder);
        }else {
            boolean notificate = false;
            int jmsPriority = (paymentorder.getCompany().getPriority()!=null)? paymentorder.getCompany().getPriority(): 4;
            for (Approval approval : signers) {
                if (notificate) {
                    Long[] destinatario = {approval.getWorkflowdet().getUser().getIduserPk()};

                    notification = this.getNotificationRequestMessage(paymentorder,
                            destinatario, FlowManagerEventCodes.SIGNER_NOTIFICATE);

                    long maxApprovalTime = systemParameterService
                            .getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME.getIdProcess(),
                                    FlowManagerParameters.MAX_APPROVAL_TIME.getParameterName(),
                                    FlowManagerParameters.MAX_APPROVAL_TIME.getDefaultValue());

                    if (maxApprovalTime > 0) {
                        notification.setTiempoValidezSms((int) maxApprovalTime);
                    }
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.NOTIFICATION_QUEUE, notification);
                    break;
                } else {
                    if (approval.getIdapprovalPk() == idlastapproval) {
                        notificate = true;
                    }
                }
            }
        }
    }


    public FlowManagerEventCodes approveBySMS(Paymentorder paymentorder, SMSMangerToFlowManagerMessage request) {
        // validar estado de la orden de pago
        if (!paymentorder.getStateChr().equals(PaymentOrderStates.FIRMA_EN_PROCESO)) {
            logger.error("Invalid payment order state --> " + paymentorder.toString());
            return FlowManagerEventCodes.INVALID_PAYMENT_ORDER_STATE;
        }

        User user = userService.getUserById(request.getIduser());
        if (user==null) {
            logger.error("No existe el usuario con id: " + request.getIduser());
            return FlowManagerEventCodes.NO_EXIST_USER;
        }

        // Validar estado usuario
        if (!SpmConstants.ACTIVE.equalsIgnoreCase(user.getStateChr().toString())) {
            logger.error("Estado de usuario no valido --> "
                    + paymentorder.toString() + ", user=" + user.toString());
            return FlowManagerEventCodes.INACTIVE_USER;
        }

        List<Approval> signers = approvalService.getApprovalsByIdPaymentorder(paymentorder.getIdpaymentorderPk());
        if (signers==null) {
            logger.error("No existen signers con idPaymentorderPk: " + paymentorder.getIdpaymentorderPk());
            return FlowManagerEventCodes.NO_EXIST_SIGNERS;
        }

        // validar turno de firma
        long nextUserToSign = -1;
        Approval lastApproval = null;
        Approval currentApproval = null;
        for (Approval approval : signers) {
            if (!approval.isSignedNum()) {
                currentApproval = approval;
                nextUserToSign = approval.getWorkflowdet().getUser().getIduserPk();
                break;
            } else {
                lastApproval = approval;
            }
        }
        if (currentApproval == null) {
            logger.error("Current Approval not found --> " + paymentorder);
            return FlowManagerEventCodes.OTHER_ERROR;
        }
        if (nextUserToSign != request.getIduser()) {
            logger.error("No corresponde al turno de usuario --> "
                    + paymentorder.toString() + ", user=" + user.toString());
            return FlowManagerEventCodes.NOT_SIGNER_TURN;
        }
        // validar periodo de tiempo de aprobacion por sms
        long maxApprovalTime = systemParameterService
                .getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME.getIdProcess(),
                        FlowManagerParameters.MAX_APPROVAL_TIME.getParameterName(),
                        FlowManagerParameters.MAX_APPROVAL_TIME.getDefaultValue());

        long current = System.currentTimeMillis();
        long lastApproveTime;
        if (lastApproval == null) {
            lastApproveTime = currentApproval.getCreationdateTim().getTime();
        } else {
            lastApproveTime = lastApproval.getSigndateTim().getTime();
        }
        // si es menor a 0, tiempo ilimitado
        if ((maxApprovalTime > 0)
                && (current - lastApproveTime > (maxApprovalTime * SpmConstants.HOUR_IN_MILLIS))) {
            logger.error("Aprobacion por sms expirado --> {} , user= {}",paymentorder,user);
            return FlowManagerEventCodes.APPROVAL_TIME_EXPIRED;
        }
        // validar firma (si la firma es incorrecta, incrementar contador)
        // si supera la cantidad de intentos, bloquear el usuario
        String pinSms= CryptoUtils.encryptToBCrypt(request.getPin().toLowerCase().trim());

        long caseSesitive = systemParameterService
                .getParameterValue(FlowManagerParameters.SMS_PIN_CASE_SENSITIVE.getIdProcess(),
                        FlowManagerParameters.SMS_PIN_CASE_SENSITIVE.getParameterName(),
                        FlowManagerParameters.SMS_PIN_CASE_SENSITIVE.getDefaultValue());

        boolean pinCorrect = false;
        if (caseSesitive != 1
                && pinSms.compareToIgnoreCase(user.getSmspinChr()) == 0) {
            pinCorrect = true;
        } else if (pinSms.compareTo(user.getSmspinChr()) == 0) {
            pinCorrect = true;
        }
        if (!pinCorrect) {
            long maxRetries = systemParameterService
                    .getParameterValue(FlowManagerParameters.MAX_APPROVAL_FAILED.getIdProcess(),
                            FlowManagerParameters.MAX_APPROVAL_FAILED.getParameterName(),
                            FlowManagerParameters.MAX_APPROVAL_FAILED.getDefaultValue());

            user.setAttemptNum(user.getAttemptNum() + 1);
            if (maxRetries == user.getAttemptNum()) {
                user.setStateChr(SpmConstants.INACTIVO);
                userService.saveorUpdate(user);
                logger.error("SMS Pin de usuario no valido, usuario bloqueado!! --> "
                        + paymentorder.toString() + ", user=" + user.toString());
                return FlowManagerEventCodes.INCORRECT_PIN_USER_BLOCKED;
            } else if (maxRetries - 1 == user.getAttemptNum()) {
                userService.saveorUpdate(user);
                logger.error("SMS Pin de usuario no valido --> " + paymentorder.toString() + ", user=" + user.toString());
                return FlowManagerEventCodes.INCORRECT_PIN_LAST_CHANCE;
            } else {
                userService.saveorUpdate(user);
                logger.error("SMS Pin de usuario no valido --> " + paymentorder.toString() + ", user=" + user.toString());
                return FlowManagerEventCodes.INCORRECT_PIN;
            }
        } else if (user.getAttemptNum() > 0) {
            user.setAttemptNum(0);
            userService.saveorUpdate(user);
        }
        // Actualizar approval
        currentApproval.setSignedNum(true);
        currentApproval.setSigndateTim(new Timestamp(System.currentTimeMillis()));
        approvalService.saveorUpdate(currentApproval);
        logger.info("Orden de pago, firmada --> " + paymentorder.toString() + ", user=" + user.toString());

        // ultimo firmante aprobo, actualizar orden de pago
        if (currentApproval.getStepPk() == signers.size()) {
            paymentOrderService.updatePaymentorderState(paymentorder, PaymentOrderStates.PAGO_PENDIENTE);
        }
        return FlowManagerEventCodes.SUCCESS;
    }

}
