package py.com.global.spm.model.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.message.TransferProcessToMTSInterfaceMessage;
import py.com.global.spm.model.services.ActiveMQClientService;
import py.com.global.spm.model.services.LogPaymentService;
import py.com.global.spm.model.services.PaymentOrderService;
import py.com.global.spm.model.services.SystemParameterService;
import py.com.global.spm.model.systemparameters.ProcessPaymentParameters;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LogPaymentPayProcess {
    private static Logger log = LogManager.getLogger(LogPaymentPayProcess.class);
    @Autowired
    private ActiveMQClientService activeMQClientService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private LogPaymentService logPaymentService;

    @Transactional
    public void checkPayments(){
        try {
            Long maxSize = systemParameterService.getParameterValue(ProcessPaymentParameters.MAX_LOGPAYMENT_SIZE.getIdProcess(),ProcessPaymentParameters.MAX_LOGPAYMENT_SIZE.getParameterName(),100L);

            Long actualQueueSize = activeMQClientService.getQueueSize(ConstantsQueue.MTS_INTERFACE_TRANSFER_QUEUE,SpmConstants.QUEUE_SIZE);
            if(actualQueueSize<maxSize){
                List<Paymentorder> paymentorders = paymentOrderService.findPaymentOrderByState(PaymentOrderStates.PAGO_EN_PROCESO);

                if(paymentorders!=null && !paymentorders.isEmpty()){
                    int maxPerOP = (int)(maxSize/paymentorders.size());

                    for (Paymentorder paymentorder: paymentorders){
                        int jmsPriority = (paymentorder.getCompany().getPriority() != null) ? paymentorder.getCompany().getPriority() : 4;
                        List<Logpayment> logpayments = logPaymentService.getPaymentsToPay(paymentorder.getIdpaymentorderPk(), maxPerOP, paymentorder.getRetry());

                        for(Logpayment logpayment: logpayments)
                            logPaymentService.sendLogPayment(logpayment, paymentorder, jmsPriority);
                    }
                }else
                    log.debug("Sin ordenes de pago a pagar");
            }
        }catch (Exception e){
            log.error("Error en LogPaymentPayProcess",e);
        }
    }

}
