package py.com.global.spm.model.jmslisteners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.message.FlowManagerToTransferProcessMessage;
import py.com.global.spm.model.message.SMSMangerToFlowManagerMessage;
import py.com.global.spm.model.message.SPMGUIToFlowManagerMessage;
import py.com.global.spm.model.services.FlowService;
import py.com.global.spm.model.services.PaymentOrderService;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentOrderStates;

@Component
public class FlowManager {
    protected static Logger logger = LogManager.getLogger(FlowManager.class);

    private final FlowService flowService;
    private final PaymentOrderService paymentOrderService;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public FlowManager(FlowService flowService, PaymentOrderService paymentOrderService, JmsTemplate jmsTemplate) {
        this.flowService = flowService;
        this.paymentOrderService = paymentOrderService;
        this.jmsTemplate = jmsTemplate;
    }


    /**
     * Firma o cancelacion realizada por medio de la interfaz web. La interfaz
     * realiza las validaciones correspondientes a la firma.
     *
     * @param request
     */

   @JmsListener(destination = ConstantsQueue.FLOW_MANAGER_QUEUE_SPMGUI, containerFactory = "myFactory")
   @Transactional
   public void receiveSpmGui(SPMGUIToFlowManagerMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridad: {} ", request, jmsMessageHeaderAccessor.getPriority());

            Paymentorder paymentorder = paymentOrderService.getPaymentorder(request.getIdpaymentorder());

            if(paymentorder == null){
                logger.error("No existe paymentOrder con id: {}",request.getIdpaymentorder());
                return;
            }
            int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
            if (request.getEvent() == SPMGUIToFlowManagerMessage.CANCELACION_ORDENDEPAGO) {
                logger.debug("Procesando cancelacion de orden de pago --> {}", request);

                // si corresponde, notificar generacion de orden de pago a los firmantes
                if (Boolean.TRUE.equals(paymentorder.getNotifygenNum())) {
                    flowService.notifyAllSigners(paymentorder, FlowManagerEventCodes.PAYMENT_ORDER_CANCELATION);
                    logger.debug("Payment order generation notificated to signers --> {}", paymentorder);
                }else {
                    // notificar generacion de orden de pago al creador del draft
                    flowService.notifyDraftCreator(paymentorder, FlowManagerEventCodes.PAYMENT_ORDER_CANCELATION);
                }

            } else if (request.getEvent() == SPMGUIToFlowManagerMessage.FIRMA_ORDENDEPAGO) {
                logger.debug("Procesando firma de orden de pago --> {}", request);
                // enviar confirmacion a firmante y al creador

                flowService.notifySigner(paymentorder, request.getIduser(), FlowManagerEventCodes.SUCCESS);
                flowService.notifySignEvent(paymentorder, request.getIduser());

                // si es el ultimo firmante, enviar al Transfer process
                if (request.isLastSigner()) {
                   paymentOrderService.updatePaymentorderState(paymentorder, PaymentOrderStates.PAGO_PENDIENTE);

                    FlowManagerToTransferProcessMessage message = new FlowManagerToTransferProcessMessage();
                    message.setPaymentorder(paymentorder);

                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.FLOW_MANAGER_TRANSFER_PROCESS_QUEUE, message);
                } else {
                    // si corresponde, notificar al siguiente firmante y
                    if (Boolean.TRUE.equals(paymentorder.getNotifysignerNum())) {
                        flowService.notifyNextSigner(paymentorder, request.getIdApproval());
                    }
                }

            } else {
                logger.error("Evento no valido --> {} " ,request);
            }

        }catch (Exception e){
            logger.error("Error en queue: " + ConstantsQueue.FLOW_MANAGER_QUEUE_SPMGUI, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.FLOW_MANAGER_QUEUE_SMSMANAGER, containerFactory = "myFactory")
   @Transactional
   public void receiveSmsManager(SMSMangerToFlowManagerMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridad: {}", request,jmsMessageHeaderAccessor.getPriority());

            // validar firma y notificar resultado de aprobacion
            Paymentorder paymentorder = paymentOrderService.getPaymentorder(request.getIdpaymentorder());
            if(paymentorder == null){
                logger.error("No existe paymentOrder con id: {}",request.getIdpaymentorder());
                return;
            }
            int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;

            FlowManagerEventCodes validate = flowService.approveBySMS(paymentorder, request);

            if (validate.sonIguales(FlowManagerEventCodes.SUCCESS)) {
                // enviar confirmacion a firmante y al creador
                flowService.notifySigner(paymentorder, request.getIduser(), validate);
                flowService.notifySignEvent(paymentorder, request.getIduser());

                // si es el ultimo firmante, enviar al Transfer process
                if (flowService.isLastSigner(paymentorder)) {

                    FlowManagerToTransferProcessMessage message = new FlowManagerToTransferProcessMessage();
                    message.setPaymentorder(paymentorder);
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.FLOW_MANAGER_TRANSFER_PROCESS_QUEUE, message);
                } else {
                    // si corresponde, notificar al siguiente firmante y
                    if (Boolean.TRUE.equals(paymentorder.getNotifysignerNum())) {
                        flowService.notifyNextSigner(paymentorder);
                    }
                }
            } else {
                // enviar notificacion de error a firmante
                if (!validate.sonIguales(FlowManagerEventCodes.INCORRECT_PIN)) {
                    flowService.notifySigner(paymentorder, request.getIduser(), validate);

                } else {
                    // notify with attempts lefts
                    flowService.notifySignIncorrectPinError(paymentorder, request.getIduser());
                }
            }

        }catch (Exception e){
            logger.error("Error en queue: " + ConstantsQueue.FLOW_MANAGER_QUEUE_SMSMANAGER, e);
        }
    }


}
