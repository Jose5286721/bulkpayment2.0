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
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.dto.redis.PaymentOrderCache;
import py.com.global.spm.model.message.*;
import py.com.global.spm.model.services.*;
import py.com.global.spm.model.services.redis.LogPaymentCacheService;
import py.com.global.spm.model.services.redis.PaymentOrderCacheService;
import py.com.global.spm.model.systemparameters.TransferProcessParameters;
import py.com.global.spm.model.util.*;

import java.sql.Timestamp;
import java.util.List;

import static py.com.global.spm.model.util.PaymentOrderStates.*;


@Component
public class Transfer {

    private final Logger log = LoggerFactory.getLogger(Transfer.class);

    private final JmsTemplate jmsTemplate;

    private final SystemParameterService systemParameterService;

    private final TransferService transferService;

    private final PaymentOrderService paymentOrderService;

    private final LogPaymentService logPaymentService;

    private final PaymentOrderCacheService paymentOrderCacheService;

    private final LogPaymentCacheService logPaymentCacheService;

    @Autowired
    public Transfer(JmsTemplate jmsTemplate, SystemParameterService systemParameterService, TransferService transferService, PaymentOrderService paymentOrderService, LogPaymentService logPaymentService, PaymentOrderCacheService paymentOrderCacheService
    ,LogPaymentCacheService logPaymentCacheService) {
        this.jmsTemplate = jmsTemplate;
        this.systemParameterService = systemParameterService;
        this.transferService = transferService;
        this.paymentOrderService = paymentOrderService;
        this.logPaymentService = logPaymentService;
        this.paymentOrderCacheService=paymentOrderCacheService;
        this.logPaymentCacheService = logPaymentCacheService;
    }

   @JmsListener(destination = ConstantsQueue.MTS_INTERFACE_TRANSFER_PROCESS_QUEUE, containerFactory = "myFactory")
   public void receiveMtsInterface(MTSInterfaceToTransferProcessMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        // 1- Buscar en el cache el pago.
        try {
            int jmsPriority = (jmsMessageHeaderAccessor.getPriority()!=null)?jmsMessageHeaderAccessor.getPriority():4;
            log.info("Received --> prioridad {}, {}", jmsPriority, request);
            if(logPaymentCacheService.existLogPaymentInPo(request.getPayment().getIdpaymentNum())){
                PaymentOrderCache paymentOrderCache = paymentOrderCacheService.updatePaymentOrderCache(request.getPayment());
                if (!PAGO_EN_PROCESO.equalsIgnoreCase(paymentOrderCache.getStateChr())) {
                    //3.1- Enviar resultado al proceso Updater.
                    TransferProcessToUpdaterMessage tpu = new TransferProcessToUpdaterMessage();
                    tpu.setPaymentOrderCache(paymentOrderCache);
                    tpu.setLogPaymentCaches(logPaymentCacheService.findByPaymentOrderId(paymentOrderCache.getIdpaymentorderPk()));
                    tpu.setReintento(paymentOrderCache.isReintento());
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.UPDATER_QUEUE_TRANSFER,tpu);
                    paymentOrderCacheService.deletePaymentOrderCache(paymentOrderCache.getIdpaymentorderPk());
                    logPaymentCacheService.deleteAll(tpu.getLogPaymentCaches());
                }
            } else {
                log.warn("Mensaje Descartado");
            }
        }catch (Exception e){
            log.error("ERROR receiveMtsInterface",e);
        }


    }


   @JmsListener(destination = ConstantsQueue.FLOW_MANAGER_TRANSFER_PROCESS_QUEUE, containerFactory = "myFactory")
   @Transactional
   public void receiveFlowManager(FlowManagerToTransferProcessMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
            Paymentorder paymentorder = paymentOrderService.getPaymentorder(request
                    .getPaymentorder().getIdpaymentorderPk());
            if (paymentorder != null && !validateDuplicate(paymentorder)) {
                // Default JMS priority = 4
                int jmsPriority = (
                        paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
                if (validateAmount(paymentorder)) {
                    if (PAGO_PENDIENTE.equalsIgnoreCase(
                            paymentorder.getStateChr())
                            || FIRMA_EN_PROCESO.equalsIgnoreCase(
                            paymentorder.getStateChr())) {
                        // 1- Si el estado es PENDIENTE DE PAGO o En Proceso.
                        // 1.1- Generar pagos en base a la orden de pago con estado
                        // pendiente y persiste en la base de datos.
                        List<Logpayment> logpaymentList = transferService
                                .generateLogPayment(paymentorder);
                        // 1.2- Cambiar a estado en Proceso la orden de pago.
                        paymentorder.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
                        paymentorder.setIdeventcodeNum(0L);
                        paymentorder.setTotalpaymentNum((long) logpaymentList.size());
                        paymentorder.setStateChr(PAGO_EN_PROCESO);
                        paymentorder.setReintento(false);
                        paymentorder.setRetry(0);
                        paymentorder.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
                        paymentOrderService.saveOrUpdate(paymentorder);
                    } else if (ERROR.equalsIgnoreCase(paymentorder.getStateChr())
                            || PARCIALMENTE_PAGADA.equalsIgnoreCase(
                            paymentorder.getStateChr())) {
                        // Si el estado es NO PAGADO o PARCIALMENTE PAGADA.
                        // Reintento manual de pagos.
                        // 2- Obtener pagos con estado Error o parcialmente
                        // satisfactorio.
                        List<Logpayment> logpaymentList = logPaymentService
                                .getRetryLogPayment(paymentorder.getIdpaymentorderPk());

                        if (logpaymentList != null) {
                            // 2.1- Cambiar a estado en Proceso la orden de pago.
                            paymentorder.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
                            paymentorder.setIdeventcodeNum(0L);
                            paymentorder.setStateChr(PAGO_EN_PROCESO);
                            paymentorder.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
                            paymentorder.setReintento(true);
                            paymentorder.setRetry(paymentorder.getRetry()+1);
                            paymentOrderService.saveOrUpdate(paymentorder);
                        } else {
                            log.warn("Reintento de pagos: La orden de pago no tiene pagos con estados parciales--> idPaymentOrder: "
                                    + paymentorder.getIdpaymentorderPk());
                        }

                    } else {
                        log.warn("Estado de orden de pago invalido --> IdPaymentOrder: {} " , paymentorder.getIdpaymentorderPk());
                    }
                } else {
                    // Enviar con estado recibido la op al Updater.
                    // 3.1- Enviar resultado al proceso Updater.
                    TransferProcessToUpdaterMessage tpu = new TransferProcessToUpdaterMessage();
                    tpu.setPaymentOrderCache(new PaymentOrderCache(paymentorder.getIdpaymentorderPk()));
                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.UPDATER_QUEUE_TRANSFER, tpu);
                }
            } else {
                log.warn("Orden de pago en proceso, solicitud descartada, envío Duplicado, IdPaymentOrder --> {}" ,
                        ((paymentorder != null) ? paymentorder.getIdpaymentorderPk() : "null"));
            }
        } catch (Exception e) {
            log.error("Transfer Queue error: {} ", e.getMessage());
            log.debug("Transfer Queue error: ", e);
        }
    }

   @JmsListener(destination = ConstantsQueue.SPMGUI_TRANSFER_PROCESS_QUEUE, containerFactory = "myFactory")
   @Transactional
   public void receiveSpmGui(SPMGUIToTransferProcessMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            log.info("Received --> prioridad {}, {}", jmsMessageHeaderAccessor.getPriority(), request);
            Paymentorder po = paymentOrderService.getPaymentorder(request
                    .getIdpaymentorderPk());
            if (po == null) {
                log.warn("Payment order not found  id: --> {}" , request.getIdpaymentorderPk());
            } else {
                if (!validateDuplicate(po)) {
                    // Default JMS priority = 4
                    int jmsPriority = (po.getCompany().getPriority() != null) ? po.getCompany().getPriority() : 4;
                    if (validateAmount(po)) {
                        if (PAGO_PENDIENTE.equalsIgnoreCase(po.getStateChr())
                                || FIRMA_EN_PROCESO.equalsIgnoreCase(po.getStateChr())) {
                            // 1- Si el estado es PENDIENTE DE PAGO o En Proceso.
                            // 1.1- Generar pagos en base a la orden de pago con estado
                            // pendiente y persiste en la base de datos.
                            List<Logpayment> logpaymentList = transferService
                                    .generateLogPayment(po);
                            if (logpaymentList != null) {
                                // 1.2- Cambiar a estado en Proceso la orden de pago.
                                po.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
                                po.setIdeventcodeNum(0L);
                                po.setStateChr(PAGO_EN_PROCESO);
                                po.setRetry(0);
                                po.setTotalpaymentNum((long) logpaymentList.size());
                                paymentOrderService.saveOrUpdate(po);
                            } else {
                                log.warn("receiveSpmGui -> Payment Order ID: " + po.getIdpaymentorderPk() + " " +
                                        "do not have LogPayments !!!");
                            }
                        } else if (ERROR.equalsIgnoreCase(po.getStateChr())
                                || PARCIALMENTE_PAGADA.equalsIgnoreCase(po.getStateChr())) {
                            // Si el estado es NO PAGADO o PARCIALMENTE PAGADA.
                            // Reintento manual de pagos.
                            // 2- Obtener pagos con estado Error o parcialmente
                            // satisfactorio.
                            List<Logpayment> logpaymentList = transferService.getRetryLogPayment(po);

                            if (!logpaymentList.isEmpty()) {
                                // 2.1- Cambiar a estado en Proceso la orden de pago.
                                po.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
                                po.setIdeventcodeNum(0L);
                                po.setStateChr(PAGO_EN_PROCESO);
                                po.setRetry(po.getRetry()+1);
                                paymentOrderService.saveOrUpdate(po);
                            } else {
                                log.warn("Reintento de pagos: La orden de pago no tiene pagos con estados parciales--> {}", po);
                            }

                        } else {
                            log.warn("Estado de orden de pago invalido --> {}" , po);
                        }
                    } else {
                        // Enviar con estado recibido la op al Updater.
                        // 3.1- Enviar resultado al proceso Updater.
                        TransferProcessToUpdaterMessage tpu = new TransferProcessToUpdaterMessage();
                        tpu.setPaymentOrderCache(new PaymentOrderCache(po.getIdpaymentorderPk()));
                        jmsTemplate.setPriority(jmsPriority);
                        jmsTemplate.convertAndSend(ConstantsQueue.UPDATER_QUEUE_TRANSFER, tpu);
                    }
                } else {
                    log.warn("Orden de pago en proceso, solicitud descartada --> {}" , po);
                }
            }
        } catch (Exception e) {
            log.error("receiveSpmGui -> {}", e.getMessage());
            log.debug("receiveSpmGui -> ", e);
        }
    }

    private boolean validateDuplicate(Paymentorder paymentorder) {

        return paymentOrderCacheService.existPaymentOrderCache(paymentorder.getIdpaymentorderPk());

    }

    private boolean validateAmount(Paymentorder paymentorder) {
        boolean tieneCredito = false;
        String validarMonto = systemParameterService.getParameterValue(
                TransferProcessParameters.VALIDATION_AMOUNT.getIdProcess(),
                TransferProcessParameters.VALIDATION_AMOUNT.getParameterName(), SpmConstants.NO);
        if (validarMonto.equalsIgnoreCase(SpmConstants.YES)) {
            //TODO Consulta de saldo al MTS.
            // tieneCredito = this.transferProcess.validateAmountPaymentOrder(op);
        } else {
            tieneCredito = true;
        }
        return tieneCredito;
    }
}
