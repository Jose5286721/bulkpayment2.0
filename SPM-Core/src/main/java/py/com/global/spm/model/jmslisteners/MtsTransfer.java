package py.com.global.spm.model.jmslisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.model.message.MTSInterfaceToTransferProcessMessage;
import py.com.global.spm.model.message.TransferProcessToMTSInterfaceMessage;
import py.com.global.spm.model.services.MtsTransferService;
import py.com.global.spm.model.util.ConstantsQueue;


@Component
public class MtsTransfer {

    private final Logger logger = LoggerFactory.getLogger(MtsTransfer.class);

    private final MtsTransferService mtsTransferService;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public MtsTransfer(MtsTransferService mtsTransferService, JmsTemplate jmsTemplate) {
        this.mtsTransferService = mtsTransferService;
        this.jmsTemplate = jmsTemplate;
    }

   @JmsListener(destination = ConstantsQueue.MTS_INTERFACE_TRANSFER_QUEUE, containerFactory = "myFactory")
   public void receive(TransferProcessToMTSInterfaceMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            int priority = (jmsMessageHeaderAccessor.getPriority()!=null)?jmsMessageHeaderAccessor.getPriority():4;
            logger.debug("Received --> prioridad: {}, {}", priority, request);
            Logpayment logpayment = request.getLogpayment();
            //Le seteamos el antiguo estado que tenia antes de pasar a la cola
            logpayment.setStateChr(request.getStateChr());
            mtsTransferService.processPayment(logpayment, request.getWalletChr(), request.getIdpaymentOrderPk());
            logger.info("LogPaymentMTS {}", logpayment);
            MTSInterfaceToTransferProcessMessage mtp = new MTSInterfaceToTransferProcessMessage();
            mtp.setPayment(logpayment);
            jmsTemplate.setPriority(priority);
            jmsTemplate.convertAndSend(ConstantsQueue.MTS_INTERFACE_TRANSFER_PROCESS_QUEUE, mtp);
        }catch (Exception e){
            logger.error("MTS_INTERFACE_TRANSFER_QUEUE", e);
        }
    }

}
