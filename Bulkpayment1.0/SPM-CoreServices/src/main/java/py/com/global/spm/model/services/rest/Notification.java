package py.com.global.spm.model.services.rest;


import org.apache.log4j.Logger;
import py.com.global.spm.entities.Approval;
import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.User;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.interfaces.*;
import py.com.global.spm.model.messages.NotificationRequestMessage;
import py.com.global.spm.model.messages.SPMGUIToFlowManagerMessage;
import py.com.global.spm.model.messages.SPMGUIToReversionProcessMessage;
import py.com.global.spm.model.services.dto.FirmaDto;
import py.com.global.spm.model.services.enums.EstadosPaymentorder;
import py.com.global.spm.model.services.service.ResponseDtoService;
import py.com.global.spm.model.services.dto.DraftPaymentOrderDto;
import py.com.global.spm.model.services.dto.UserPaymentOrderDto;
import py.com.global.spm.model.services.dto.PasswordNotificationDto;
import py.com.global.spm.model.systemparameters.FlowManagerParameters;
import py.com.global.spm.model.utils.QueueManager;
import py.com.global.spm.model.utils.SpmConstants;
import py.com.global.spm.model.utils.SpmQueues;
import py.com.global.spm.domain.utils.NotificationEventEnum;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Path("notification")
@RequestScoped
public class Notification{

    private static Logger logger= Logger.getLogger(Notification.class);

    @Inject
    ResponseDtoService responseDtoService;

    @EJB
    private UserManagerLocal userManager;

    @EJB
    private DraftManagerLocal draftManager;

    @EJB
    private PaymentOrderManagerLocal paymentOrderManager;

    @EJB
    private SystemparameterManagerLocal systemparameterManager;

    @EJB
    private ApprovalManagerLocal approvalManager;

    @EJB
    PaymentOrderManagerLocal paymentorder;

    @EJB
    UserManagerLocal userManagerLocal;

    @PersistenceContext(unitName = SpmConstants.SPM_PU)
    private EntityManager entityManager;



    @POST
    @Path("/passwordsms")
    @Produces("application/json")
    @Consumes("application/json")
    public Response sendPasswordUser(@Valid PasswordNotificationDto dto) {
        Boolean sent=false;
        try {
           NotificationRequestMessage msg = new NotificationRequestMessage();
           msg.setEvent(NotificationEventEnum.NOTIFY_USER_CREATED);
           msg.setPass(dto.getPassword());
           Long[] usersToNotify = {dto.getIdUserpk()};
           msg.setIdDestinatarios(usersToNotify);
           sent = QueueManager.sendObject(msg, SpmQueues.NOTIFICATION_QUEUE);
        }catch (Exception e){
           logger.error("Error al notificar password", e);
       }finally {
            QueueManager.closeQueueConn(SpmQueues.NOTIFICATION_QUEUE);
            return  (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }

    @POST
    @Path("/allsigner")
    @Produces("application/json")
    @Consumes("application/json")
    public Response notifyAllSigners(@Valid DraftPaymentOrderDto dto) {
        Boolean sent=false;
        try {

            Paymentorder paymentorder=paymentOrderManager.getPaymentorder(dto.getIdPaymentOrder());
            List<Approval> approvalList = approvalManager
                    .getApprovalsByIdPaymentorder(paymentorder
                            .getIdpaymentorderPk());
            NotificationRequestMessage notification = this
                    .getNotificationRequestMessage(paymentorder,
                            this.getSigners(approvalList, dto.getIdDraft()), FlowManagerEventCodes.SIGNER_NOTIFICATE);
            sent = QueueManager.sendObject(notification, SpmQueues.NOTIFICATION_QUEUE);
        }catch (Exception e){
            logger.error("Error al notificar password", e);
        }finally {
            QueueManager.closeQueueConn(SpmQueues.NOTIFICATION_QUEUE);
            return  (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }

    @POST
    @Path("/signevent")
    @Produces("application/json")
    @Consumes("application/json")
    public Response notifySignEvent(@Valid UserPaymentOrderDto dto) {
        Boolean sent=false;
        try {
            Paymentorder paymentorder=paymentOrderManager.getPaymentorder(dto.getIdPaymentOrder());
            System.out.println("Orden de pago-->  "+paymentorder.toString());
            Draft draft = draftManager.getDraftById(paymentorder.getIddraftPk());
            User draftCreator = userManager.getUserById(draft.getIduserPk());
            Long[] destinatario = { dto.getIdUser(), draftCreator.getIduserPk() };
            NotificationRequestMessage notification = this
                    .getNotificationRequestMessage(paymentorder, destinatario,
                            NotificationEventEnum.NOTIFY_SIGNEVENT);
            sent = QueueManager.sendObject(notification, SpmQueues.NOTIFICATION_QUEUE);
        }catch (Exception e){
            logger.error("Error al notificar Draft", e);
        }finally {
            QueueManager.closeQueueConn(SpmQueues.NOTIFICATION_QUEUE);
            return  (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }

    @POST
    @Path("/nextsigner")
    @Produces("application/json")
    @Consumes("application/json")
    public Response notifyNextSigner(@QueryParam("id") Long idPaymentOrder) {
        Boolean sent=false;
        Boolean sentSms=false;
        Boolean sentMail=false;
        try {
            Paymentorder paymentorder=paymentOrderManager.getPaymentorder(idPaymentOrder);
            List<Approval> signers = approvalManager
                    .getApprovalsByIdPaymentorder(paymentorder
                            .getIdpaymentorderPk());
            User user;
            NotificationRequestMessage notification;
            for (Approval approval : signers) {
                if (!approval.getSignedNum()) {
                    user = userManager.getUserById(approval.getIduserPk());
                    Long[] destinatario = { user.getIduserPk() };
                    // notificar firmante via SMS
                    notification = this.getNotificationRequestMessage(paymentorder,
                            destinatario, FlowManagerEventCodes.SIGNER_NOTIFICATE);
                    long maxApprovalTime = systemparameterManager
                            .getParameterValue(FlowManagerParameters.MAX_APPROVAL_TIME);
                    if (maxApprovalTime > 0) {
                        notification.setTiempoValidezSms((int) maxApprovalTime);
                    }
                    sentSms=QueueManager.sendObject(notification, SpmQueues.NOTIFICATION_QUEUE);
                    // notificar firmante via EMAIL
                    notification = this.getNotificationRequestMessage(paymentorder,
                            destinatario,
                            FlowManagerEventCodes.SIGNER_NOTIFICATE_EMAIL);
                    sentMail=QueueManager.sendObject(notification, SpmQueues.NOTIFICATION_QUEUE);
                    break;
                }
            }

        }catch (Exception e){
            logger.error("Error al notificar Draft", e);
        }finally {
            sent=(sentSms && sentMail);
            QueueManager.closeQueueConn(SpmQueues.NOTIFICATION_QUEUE);
            return  (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }

    @POST
    @Path("/incorrectpin")
    @Produces("application/json")
    @Consumes("application/json")
    public Response notifySignIncorrectPinError(UserPaymentOrderDto dto) {
        boolean sent=false;
        try {
            long iduser = dto.getIdUser();
            Paymentorder paymentorder = paymentOrderManager.getPaymentorder(iduser);
            long maxRetries = systemparameterManager
                    .getParameterValue(FlowManagerParameters.MAX_APPROVAL_FAILED);
            logger.debug("notifySignIncorrectPinError --> iduser=" + iduser + ", "
                    + paymentorder);
            User user = userManager.getUserById(iduser);
            int attemptsLefts = (int) (maxRetries - user.getAttemptNum());
            Long[] destinatario = {iduser};
            NotificationRequestMessage notification = this
                    .getNotificationRequestMessage(paymentorder, destinatario,
                            FlowManagerEventCodes.INCORRECT_PIN);
            notification.setCantReintentos(attemptsLefts);
            sent=QueueManager.sendObject(notification, SpmQueues.NOTIFICATION_QUEUE);
        }catch (Exception e ){
            logger.error("IncorrectPinNotification", e);
        }finally {
            QueueManager.closeQueueConn(SpmQueues.NOTIFICATION_QUEUE);
            return  (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }

    }
    @POST
    @Path("/sign")
    @Produces("application/json")
    @Consumes("application/json")
    public Response firmar(@Valid FirmaDto dto) {
        Boolean sent = false;
        try {

            Paymentorder paymentOrder = paymentorder.getPaymentorder(dto.getIdPaymentOrder());
            User user = userManagerLocal.getUserById(dto.getIdUser());
            Serializable msg = new SPMGUIToFlowManagerMessage();
            ((SPMGUIToFlowManagerMessage) msg)
                    .setEvent(SPMGUIToFlowManagerMessage.FIRMA_ORDENDEPAGO);
            ((SPMGUIToFlowManagerMessage) msg).setLastSigner(paymentOrder
                    .getStateChr().equals(
                            EstadosPaymentorder.PENDIENTE_PAGO.getCodigo0()));
            Approval ap = getUserApproval(user, paymentOrder);
            ((SPMGUIToFlowManagerMessage) msg).setIdApproval(ap
                    .getIdapprovalPk());
            ((SPMGUIToFlowManagerMessage) msg).setIduser(dto.getIdUser());
            ((SPMGUIToFlowManagerMessage) msg).setIdpaymentorder(paymentOrder.getIdpaymentorderPk());
            sent = QueueManager.sendObject(msg, SpmQueues.FLOW_MANAGER_QUEUE);
        } catch (Exception e) {
            logger.error("Error al enviar el pedido de firma", e);
        } finally {
            QueueManager.closeQueueConn(SpmQueues.FLOW_MANAGER_QUEUE);
            return (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }
    @POST
    @Path("/cancel")
    @Produces("application/json")
    @Consumes("application/json")
    public Response cancelar(@Valid FirmaDto dto) {
        Boolean sent = false;
        try {
            Paymentorder paymentOrder = paymentorder.getPaymentorder(dto.getIdPaymentOrder());
            Serializable msg = new SPMGUIToFlowManagerMessage();
            ((SPMGUIToFlowManagerMessage) msg)
                    .setEvent(SPMGUIToFlowManagerMessage.CANCELACION_ORDENDEPAGO);
            ((SPMGUIToFlowManagerMessage) msg).setIdpaymentorder(paymentOrder
                    .getIdpaymentorderPk());
            ((SPMGUIToFlowManagerMessage) msg).setIduser(dto.getIdUser());
            sent = QueueManager.sendObject(msg, SpmQueues.FLOW_MANAGER_QUEUE);
        } catch (Exception e) {
            logger.error("Error al enviar el pedido de cancelacion de firma", e);
        } finally {
            QueueManager.closeQueueConn(SpmQueues.FLOW_MANAGER_QUEUE);
            return (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }
    @POST
    @Path("/revert")
    @Produces("application/json")
    @Consumes("application/json")
    public Response revertir(@Valid FirmaDto dto) {
        Boolean sent = false;
        try {
            Paymentorder paymentOrder = paymentorder.getPaymentorder(dto.getIdPaymentOrder());
            Serializable msg = new SPMGUIToReversionProcessMessage();
            ((SPMGUIToReversionProcessMessage) msg)
                    .setIdpaymentorderPk(paymentOrder.getIdpaymentorderPk());
            ((SPMGUIToReversionProcessMessage) msg).setIdUserPk(dto.getIdUser());
            sent = QueueManager.sendObject(msg, SpmQueues.TRANSFER_PROCESS_QUEUE);
        } catch (Exception e) {
            logger.error("Error al enviar el pedido de cancelacion de firma", e);
        } finally {
            QueueManager.closeQueueConn(SpmQueues.TRANSFER_PROCESS_QUEUE);
            return (sent) ? Response.status(200).entity(responseDtoService.getDtoOK()).build() : Response.status(200).entity(responseDtoService.getDtoERROR()).build();
        }
    }
    private Approval getUserApproval(User u, Paymentorder po) {
        String hql = "SELECT ap FROM Approval ap WHERE ap.paymentorder= :po AND ap.workflowdet.user =:user";
        Query q = entityManager.createQuery(hql);
        q.setParameter("po", po);
        q.setParameter("user", u);

        Approval ap = (Approval) q.getSingleResult();
        return ap;
    }

    private NotificationRequestMessage getNotificationRequestMessage(
            Paymentorder paymentorder, Long[] destinatarios,
            FlowManagerEventCodes event) {
        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(FlowManagerEventCodes.parseMessage(event));
        notificationRequest.setOp(paymentorder);
        notificationRequest.setIdDestinatarios(destinatarios);
        return notificationRequest;
    }

    private Long[] getSigners(List<Approval> approvalList, Long idDraftcreator) {
        List<Long> alambre = new ArrayList<Long>();
        Long[] signers = new Long[0];
        for (Approval a : approvalList) {
            // no notificar al creador cuando es firmante para no recibir
            // duplicados
            if (a.getIduserPk() != idDraftcreator.longValue()) {
                alambre.add(a.getIduserPk());
            }
        }
        return alambre.toArray(signers);
    }

    private NotificationRequestMessage getNotificationRequestMessage(
            Paymentorder paymentorder, Long[] destinatarios,
            NotificationEventEnum event) {
        NotificationRequestMessage notificationRequest = new NotificationRequestMessage();
        notificationRequest.setEvent(event);
        notificationRequest.setOp(paymentorder);
        notificationRequest.setIdDestinatarios(destinatarios);
        return notificationRequest;
    }
}
