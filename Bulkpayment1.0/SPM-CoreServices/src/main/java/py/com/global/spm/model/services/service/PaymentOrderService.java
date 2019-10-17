package py.com.global.spm.model.services.service;

import org.apache.log4j.Logger;
import py.com.global.spm.entities.Approval;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.User;
import py.com.global.spm.model.messages.SPMGUIToFlowManagerMessage;
import py.com.global.spm.model.messages.SPMGUIToTransferProcessMessage;
import py.com.global.spm.model.services.dto.CancelPaymentOrderDto;
import py.com.global.spm.model.services.dto.RetryPaymentOrder;
import py.com.global.spm.model.services.dto.SignRequestDto;
import py.com.global.spm.model.services.enums.EstadosPaymentorder;
import py.com.global.spm.model.utils.QueueManager;
import py.com.global.spm.model.utils.SpmConstants;
import py.com.global.spm.model.utils.SpmQueues;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Stateless
public class PaymentOrderService {
    private Logger logger = Logger.getLogger(PaymentOrderService.class);

    @Inject
    ResponseDtoService responseDtoService;

    @PersistenceContext(unitName = SpmConstants.SPM_PU)
    private EntityManager entityManager;

    public Response sign(SignRequestDto requestDto){
        Response response=null;
        boolean sent=false;
        try {
            if(requestDto==null){
                logger.info("PaymentOrder Request Invalid");
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            if(requestDto.getIdUser()==null || requestDto.getIdPaymentOrder()==null){
                logger.info("PaymentOrder Request Invalid");
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            User user=entityManager.find(User.class,requestDto.getIdUser());
            if(user==null) {
                logger.info("Usuario no Encontrado: "+requestDto.getIdUser());
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            Paymentorder paymentorder=entityManager.find(Paymentorder.class,requestDto.getIdPaymentOrder());
            if (paymentorder==null ||paymentorder.getStateChr()==null ||paymentorder.getStateChr().isEmpty()) {
               logger.info("Orden de Pago no Encontrada: "+requestDto.getIdPaymentOrder());
                return  response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            if(!(EstadosPaymentorder.EN_PROCESO.getCodigo0().equals(paymentorder.getStateChr().trim()))){
                logger.info("Estado Invalido de la Orden de Pago: "+paymentorder.getIdpaymentorderPk());
                return  response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoPaymentOrderStateSignError()).build();
            }

            Approval approval = getUserApproval(user, paymentorder);
            if(approval==null) {
                logger.info("Error Obteniendo Approval, idUser: "+requestDto.getIdUser()+ "idPaymentOrder: "+requestDto.getIdPaymentOrder());
                return response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoErrorFirmaNoAutorizada()).build();
            }

            if( ! isCurrentUserTurn(user,paymentorder)){
                logger.info("Turno de Firma Incorrecto IPaymentOrder: "+paymentorder.getIdpaymentorderPk());
                return  response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoPaymentOrderUserTurnError()).build();

            }

            approval.setSignedNum(true);
            approval.setSigndateTim(new Timestamp(new Date().getTime()));
            entityManager.merge(approval);
            if (isFullySigned(paymentorder)) {
                paymentorder.setStateChr(EstadosPaymentorder.PENDIENTE_PAGO.getCodigo0().trim());
                entityManager.merge(paymentorder);

            }
            entityManager.flush();
            //Envio del Mensaje al Core
            Serializable msg = new SPMGUIToFlowManagerMessage();
            ((SPMGUIToFlowManagerMessage) msg)
                    .setEvent(SPMGUIToFlowManagerMessage.FIRMA_ORDENDEPAGO);
            ((SPMGUIToFlowManagerMessage) msg).setLastSigner(paymentorder
                    .getStateChr().equals(
                            EstadosPaymentorder.PENDIENTE_PAGO.getCodigo0()));

            ((SPMGUIToFlowManagerMessage) msg).setIdApproval(approval
                    .getIdapprovalPk());
            ((SPMGUIToFlowManagerMessage) msg).setIduser(requestDto.getIdUser());
            ((SPMGUIToFlowManagerMessage) msg).setIdpaymentorder(paymentorder.getIdpaymentorderPk());
            sent = QueueManager.sendObject(msg, SpmQueues.FLOW_MANAGER_QUEUE);
            if(sent) {
                logger.info("Firma Enviada Correctamente a FLOW_MANAGER_QUEUE, ID PaymentOrder:" +requestDto.getIdPaymentOrder());
                response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoOK()).build();
            }else{
                logger.error("Error al enviar notificación de Firma a FLOW_MANAGER_QUEUE, ID PaymentOrder: "+requestDto.getIdPaymentOrder());
                response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoErrorSendToFlowManagerQueue()).build();
            }
        } catch (Exception e) {
            logger.error("Error al firmar la orden de Pago, ID: "+requestDto.getIdPaymentOrder(), e);
            response= Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
        }finally {
            QueueManager.closeQueueConn(SpmQueues.FLOW_MANAGER_QUEUE);
            if(response==null) {
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
            }
            return  response;
        }
    }


    public Response cancel(CancelPaymentOrderDto cancelPaymentOrderDto){
        Response response=null;
        boolean sent=false;

        try {
            if(cancelPaymentOrderDto==null){
                logger.info("PaymentOrder Request Invalid: "+cancelPaymentOrderDto);
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            if(cancelPaymentOrderDto.getIdUser()==null || cancelPaymentOrderDto.getIdPaymentOrder()==null){
                logger.info("PaymentOrder Request Invalid "+cancelPaymentOrderDto);
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            Paymentorder paymentorder=entityManager.find(Paymentorder.class,cancelPaymentOrderDto.getIdPaymentOrder());
            if(paymentorder==null||paymentorder.getStateChr()==null||paymentorder.getStateChr().isEmpty()){
                logger.info("Invalid PaymentOrder "+paymentorder);
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            if(!EstadosPaymentorder.stateIsChildOf(EstadosPaymentorder.getStateForCode(paymentorder.getStateChr().trim()),
                    EstadosPaymentorder.CANCELADA)){
                logger.info("PaymentOrder State Invalid, IdPaymentOrder: "+cancelPaymentOrderDto.getIdPaymentOrder());
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoPaymentOrderStateCancelError()).build();
            }
            User user=entityManager.find(User.class,cancelPaymentOrderDto.getIdUser());
            if(user==null) {
                logger.info("Usuario no Encontrado: "+cancelPaymentOrderDto.getIdUser());
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            Approval approval = getUserApproval(user, paymentorder);
            if(approval==null) {
                logger.info("Error Obteniendo Approval, idUser: "+cancelPaymentOrderDto.getIdUser()+ "idPaymentOrder: "+cancelPaymentOrderDto.getIdPaymentOrder());
                return response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoErrorFirmaNoAutorizada()).build();
            }
            paymentorder.setStateChr(EstadosPaymentorder.CANCELADA.getCodigo0().trim());
            entityManager.merge(paymentorder);
            entityManager.flush();
            Serializable msg = new SPMGUIToFlowManagerMessage();
            ((SPMGUIToFlowManagerMessage) msg)
                    .setEvent(SPMGUIToFlowManagerMessage.CANCELACION_ORDENDEPAGO);
            ((SPMGUIToFlowManagerMessage) msg).setIdpaymentorder(paymentorder
                    .getIdpaymentorderPk());
            ((SPMGUIToFlowManagerMessage) msg).setIduser(cancelPaymentOrderDto.getIdUser());
            sent = QueueManager.sendObject(msg, SpmQueues.FLOW_MANAGER_QUEUE);
            if(sent) {
                logger.info("Cancelación de Orden de pago Enviada a FLOW_MANAGER_QUEUE, ID: "+ cancelPaymentOrderDto.getIdPaymentOrder());
                response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoOK()).build();
            }else {
                logger.error("Error al enviar Notificación de Cancelación, a FLOW_MANAGER_QUEUE, ID:"+cancelPaymentOrderDto.getIdPaymentOrder());
                response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoErrorSendToFlowManagerQueue()).build();
            }
        }catch (Exception e){
            logger.error("Error al firmar la orden de Pago, ID: "+cancelPaymentOrderDto.getIdPaymentOrder(), e);
            response= Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
        }finally {
            QueueManager.closeQueueConn(SpmQueues.FLOW_MANAGER_QUEUE);
            if(response==null)
                response= Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();

            return  response;
        }
    }

    public Response reintentar(RetryPaymentOrder retryPaymentOrder){
        Response response=null;
        boolean sent=false;
        try {
            if(retryPaymentOrder==null || retryPaymentOrder.getIdPaymentOrder()==null || retryPaymentOrder.getIdUser()==null){
                logger.info("PaymentOrder Request Invalid, IdPaymentOrder:  "+retryPaymentOrder);
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            Paymentorder paymentorder=entityManager.find(Paymentorder.class,retryPaymentOrder.getIdPaymentOrder());
            if(paymentorder==null||paymentorder.getStateChr()==null||paymentorder.getStateChr().isEmpty()){
                logger.info("Invalid PaymentOrder, IdPaymentOrder: "+retryPaymentOrder.getIdPaymentOrder());
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            if(!EstadosPaymentorder.stateIsChildOf(EstadosPaymentorder.getStateForCode(paymentorder.getStateChr().trim()),
                    EstadosPaymentorder.PAGANDO)){
                logger.info("PaymentOrder State Invalid, IdPaymentOrder: "+retryPaymentOrder.getIdPaymentOrder());
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoPaymentOrderStateRetryError()).build();
            }
            User user=entityManager.find(User.class,retryPaymentOrder.getIdUser());
            if(user==null) {
                logger.info("Usuario no Encontrado: "+retryPaymentOrder.getIdUser());
                return response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoEmpty()).build();
            }
            Approval approval = getUserApproval(user, paymentorder);
            if(approval==null) {
                logger.info("Error Obteniendo Approval, idUser: "+retryPaymentOrder.getIdUser()+ "idPaymentOrder: "+retryPaymentOrder.getIdPaymentOrder());
                return response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoErrorFirmaNoAutorizada()).build();
            }
            Serializable msg = new SPMGUIToTransferProcessMessage();
            ((SPMGUIToTransferProcessMessage) msg)
                    .setIdpaymentorderPk(retryPaymentOrder.getIdPaymentOrder());
            sent = QueueManager.sendObject(msg, SpmQueues.TRANSFER_PROCESS_QUEUE);
            if(sent) {
                logger.info("Reintento de Orden de pago OK, ID: "+ retryPaymentOrder.getIdPaymentOrder());
                response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoOK()).build();
            }else {
                logger.error("Error al enviar el Reintento de Firma a TRANSFER_QUEUE, ID: "+retryPaymentOrder.getIdPaymentOrder());
                response = Response.status(Response.Status.OK).entity(responseDtoService.getDtoErrorSendToTrasferQueue()).build();
            }
        }catch (Exception e){
            logger.error("Error al Reintentar orden de Pago Nro:"+retryPaymentOrder.getIdPaymentOrder(),e);

        }finally {
            QueueManager.closeQueueConn(SpmQueues.TRANSFER_PROCESS_QUEUE);
            if(response==null)
                response= Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();

            return  response;
        }
    }
    private Approval getUserApproval(User u, Paymentorder po) {
        Approval ap=null;
        try {
           String hql = "SELECT ap FROM Approval ap WHERE ap.idpaymentorderPk= :po AND ap.iduserPk =:user";
           Query q = entityManager.createQuery(hql);
           q.setParameter("po", po.getIdpaymentorderPk());
           q.setParameter("user", u.getIduserPk());
           ap  = (Approval) q.getSingleResult();
       }catch (Exception e){
           logger.error(e.getMessage());
       }finally {
            return ap;
       }

    }

    private boolean isFullySigned(Paymentorder po) {
         /*Codigo Heredado*/
        String hql = "SELECT count(ap) FROM Approval ap WHERE ap.idpaymentorderPk= :po AND ap.signedNum =:signedNum";
        Query q = entityManager.createQuery(hql);
        q.setParameter("po", po.getIdpaymentorderPk());
        q.setParameter("signedNum", Boolean.FALSE);

        Number r = (Number) q.getSingleResult();
        logger.info("IsFullySigned: "+r.intValue());
        return r.intValue() == 0;
    }
    private boolean isCurrentUserTurn(User user, Paymentorder paymentorder) {
        try {
            /*Codigo Heredado*/
            boolean isAlreadySigned = isAlreadySignedByUser(user,
                    paymentorder);
            if (isAlreadySigned) {
                return false;
            }
            Integer currenyUserStep = getStepFor(user, paymentorder);
            Integer nextSignerStep = getMinNotSigned(paymentorder);
            if (currenyUserStep == null || nextSignerStep == null) {
                return false;
            }
            return currenyUserStep.intValue() == nextSignerStep.intValue();
        } catch (Exception e) {
            logger.error("isCurrentUserTurn", e);
        }
        return false;
    }

    private Integer getStepFor(User u, Paymentorder po) {
        try {
             /*Codigo Heredado*/
            String hql = "SELECT ap.stepPk FROM Approval ap WHERE ap.idpaymentorderPk= :po AND ap.iduserPk =:user";
            Query q = entityManager.createQuery(hql);
            q.setParameter("po", po.getIdpaymentorderPk());
            q.setParameter("user", u.getIduserPk());
            Number n = (Number) q.getSingleResult();
            logger.debug("Get Step For: "+n.intValue() );
            return n.intValue();
        } catch (Exception e) {
            logger.error("Firma Orden de Pago, getStepFor",e);
        }
        return -1;
    }

    private Integer getMinNotSigned(Paymentorder po) {
        /*Codigo Heredado*/
        try {
            String hql = "SELECT min(ap.stepPk) FROM Approval ap WHERE ap.idpaymentorderPk= :po AND ap.signedNum= :signed ";
            Query q = entityManager.createQuery(hql);
            q.setParameter("po", po.getIdpaymentorderPk());
            q.setParameter("signed", false);
            Long r = (Long) q.getSingleResult();
            logger.debug("GetMinNotSigned: "+r.intValue());
            return r.intValue();
        }catch(Exception e){
            logger.error("Error GetMinNotSigned: ", e);
        }
        return null;
    }

    private boolean isAlreadySignedByUser(User u, Paymentorder po) {
        /*Codigo Heredado*/
        String hql = "SELECT ap.signedNum FROM Approval ap WHERE ap.idpaymentorderPk= :po AND ap.iduserPk =:user";
        Query q = entityManager.createQuery(hql);
        q.setParameter("po", po.getIdpaymentorderPk());
        q.setParameter("user", u.getIduserPk());
        List<Boolean> l = q.getResultList();
        Boolean r = l.isEmpty() ? null : l.get(0);
        logger.debug("IsAlreadySigned: "+r);
        return r == null ? true : r;
    }
}
