package py.com.global.spm.model.jmslisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.domain.entity.Logreversion;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.eventcodes.ReversionProcessEventCodes;
import py.com.global.spm.model.message.MTSReversionToReversionProcessMessage;
import py.com.global.spm.model.message.ReversionProcessToMTSReversionMessage;
import py.com.global.spm.model.message.ReversionProcessToUpdaterMessage;
import py.com.global.spm.model.message.SPMGUIToReversionProcessMessage;
import py.com.global.spm.model.services.LogPaymentService;
import py.com.global.spm.model.services.PaymentOrderService;
import py.com.global.spm.model.util.*;

import java.util.List;

@Component
public class Reversion {
    private static final Logger log = LoggerFactory
            .getLogger(Reversion.class);

    private final PaymentOrderService paymentOrderService;

    private final LogPaymentService logPaymentService;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public Reversion(PaymentOrderService paymentOrderService, LogPaymentService logPaymentService, JmsTemplate jmsTemplate) {
        this.paymentOrderService = paymentOrderService;
        this.logPaymentService = logPaymentService;
        this.jmsTemplate = jmsTemplate;
    }

   @JmsListener(destination = ConstantsQueue.REVERSION_GUI_QUEUE, containerFactory = "myFactory")
    public void receive(SPMGUIToReversionProcessMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {

        try {
            log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
            Paymentorder paymentOrder = paymentOrderService.getPaymentorder(request.getIdpaymentorderPk());
            int jmsPriority = (paymentOrder.getCompany().getPriority() != null) ?
                    paymentOrder.getCompany().getPriority() : 4;
            // 1- Obtener pagos a revertir
            List<Logpayment> pagosParaRevertir = logPaymentService
                    .getPaymentsToReversionByIdPaymentOrder(paymentOrder
                            .getIdpaymentorderPk());

            if (pagosParaRevertir != null && !pagosParaRevertir.isEmpty()) {
                // 2- Cambiar a estado en Proceso la orden de pago.
                paymentOrder.setIdprocessPk(SpmProcesses.REVERSION_PROCESS);
                paymentOrder.setStateChr(PaymentOrderStates.REVERSION_EN_PROCESO);
                paymentOrder.setIdeventcodeNum(0L);
                paymentOrderService.saveOrUpdate(paymentOrder);
                // 3- Enviar cada pago a reveritir al MTSInterface.
                for (Logpayment logpayment : pagosParaRevertir) {
                    ReversionProcessToMTSReversionMessage tpmi = new ReversionProcessToMTSReversionMessage();
                    tpmi.setPayment(logpayment);
                    logpayment.setStateChr(PaymentStates.PARCIALMENTE_REVERTIDO);
                    logPaymentService.saveOrUpdate(logpayment);
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.MTS_REVERSION_INTERFACE_QUEUE, tpmi);
                }
                Logreversion lr = new Logreversion();
                lr.setCompany(paymentOrder.getCompany());
                lr.setIdpaymentorderPk(paymentOrder.getIdpaymentorderPk());
                lr.setIdUser(request.getIdUserPk());
                lr.setIdProcess(SpmProcesses.REVERSION_PROCESS);
                lr.setStateChr(SpmConstants.SUCCESS);
                lr.setIdEventCodeNum(0L);//Exito.
                jmsTemplate.setPriority(jmsPriority);
                jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGREVERSION, lr);
            } else {
                Logreversion lr = new Logreversion();
                lr.setCompany(paymentOrder.getCompany());
                lr.setIdpaymentorderPk(paymentOrder.getIdpaymentorderPk());
                lr.setIdUser(request.getIdUserPk());
                lr.setIdProcess(SpmProcesses.REVERSION_PROCESS);
                lr.setStateChr(SpmConstants.ERROR);
                lr.setIdEventCodeNum(ReversionProcessEventCodes.WITHOUT_PAYMENTS.getIdEventCode());
                jmsTemplate.setPriority(jmsPriority);
                jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGREVERSION, lr);
                log.warn("No hay pagos para revertir de la op con id="
                        + paymentOrder.getIdpaymentorderPk());
            }
           /* {
                Logreversion lr = new Logreversion();
                lr.setCompany(paymentOrder.getCompany());
                lr.setIdpaymentorderPk(paymentOrder.getIdpaymentorderPk());
                lr.setIduserPk(idUserPk);
                lr.setIdprocessPk(SpmProcesses.REVERSION_PROCESS);
                lr.setStateChr(SpmConstants.ERROR);
                lr.setIdeventcodeNum(ReversionProcessEventCodes.DUPLICATE_REVERSION
                        .getIdEventCode());
                this.sendAsyncUpdater(lr);
                log.warn("Orden de pago en proceso, solicitud descartada -->" + op);
            }*/
        } catch (Exception e) {
            log.error("Reversion Queue---> {}" , e.getMessage());
            log.debug("Reversion Queue--->", e);
        }
    }


   @JmsListener(destination = ConstantsQueue.MTS_REVERSION_INTERFACE_QUEUE, containerFactory = "myFactory")
   @Transactional
   public void receive(MTSReversionToReversionProcessMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
        if (request.getPayment() != null) {
            Paymentorder paymentorder = request.getPayment().getPaymentorderdetail().getPaymentorder();
            // 3- Verificar si es el ultimo pago a procesar.
            if (!paymentorder.getStateChr()
                    .equalsIgnoreCase(PaymentOrderStates.REVERSION_EN_PROCESO)) {
                // 3.1- Enviar resultado al proceso Updater.
                ReversionProcessToUpdaterMessage tpu = new ReversionProcessToUpdaterMessage();
                tpu.setPaymentorder(paymentorder);
                int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;

                //TODO Falta envial al Updater

                   /*if (!this.sendRequestUpdater(tpu)) {
                       log.error("Fallo al envio de mensaje al UPDATER-->" + tpu);
                   }
                   // 3.2- Sacar del cache.
                   this.reversionProcess.removeReversalCache(pod);*/
            }
        } else {
            log.warn("Mensaje descartado --> {}", request);
        }
    }
}
