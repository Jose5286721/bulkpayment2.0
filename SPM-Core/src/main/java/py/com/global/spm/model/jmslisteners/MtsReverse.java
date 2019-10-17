package py.com.global.spm.model.jmslisteners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.model.message.MTSReversionToReversionProcessMessage;
import py.com.global.spm.model.services.MTSReversionService;
import py.com.global.spm.model.util.ConstantsQueue;

@Component
public class MtsReverse {
    protected static Logger logger = LogManager.getLogger(MtsReverse.class);

    private final MTSReversionService mtsReversionService;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MtsReverse(MTSReversionService mtsReversionService, JmsTemplate jmsTemplate) {
        this.mtsReversionService = mtsReversionService;
        this.jmsTemplate = jmsTemplate;
    }

   @JmsListener(destination = ConstantsQueue.MTS_REVERSION_INTERFACE_QUEUE, containerFactory = "myFactory")
   public void receive(MTSReversionToReversionProcessMessage request, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            int jmsPriority = (jmsMessageHeaderAccessor.getPriority()!=null)?jmsMessageHeaderAccessor.getPriority():4;
            logger.info("Recibido --> {}, prioridad: {}", request,jmsPriority );
            mtsReversionService.processPayment(request.getPayment());
            MTSReversionToReversionProcessMessage mtp=new MTSReversionToReversionProcessMessage();
            mtp.setPayment(request.getPayment());
            jmsTemplate.setPriority(jmsPriority);
            jmsTemplate.convertAndSend(ConstantsQueue.REVERSION_GUI_QUEUE,mtp);
        }catch (Exception e){
            logger.error("MTS_REVERSION_INTERFACE_QUEUE : ", e);
        }
    }



}
