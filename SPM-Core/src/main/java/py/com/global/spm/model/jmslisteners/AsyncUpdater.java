package py.com.global.spm.model.jmslisteners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.services.*;
import py.com.global.spm.model.util.ConstantsQueue;

@Component
public class AsyncUpdater {

    protected static Logger logger = LogManager.getLogger(AsyncUpdater.class);

    private final LogMtsService logMtsService;
    private final LogMtsRevService logMtsRevService;
    private final LogPaymentService logPaymentService;
    private final LogReversionService logReversionService;
    private final SmsLogMessageService smsLogMessageService;
    private final ProcessControlService processControlService;
    private final LogDraftOpService logDraftOpService;
    private final LogMessageService logMessageService;

    @Autowired
    public AsyncUpdater(LogMtsService logMtsService, LogMtsRevService logMtsRevService, LogPaymentService logPaymentService, LogReversionService logReversionService, SmsLogMessageService smsLogMessageService, ProcessControlService processControlService, LogDraftOpService logDraftOpService, LogMessageService logMessageService) {
        this.logMtsService = logMtsService;
        this.logMtsRevService = logMtsRevService;
        this.logPaymentService = logPaymentService;
        this.logReversionService = logReversionService;
        this.smsLogMessageService = smsLogMessageService;
        this.processControlService = processControlService;
        this.logDraftOpService = logDraftOpService;
        this.logMessageService = logMessageService;
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS, containerFactory = "myFactory")
    public void receiveLogmts(Logmts logmts, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridad jms: {}", logmts ,jmsMessageHeaderAccessor.getPriority());

            logMtsService.updateLogmts(logmts);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS, e);
        }
    }


   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTSREV, containerFactory = "myFactory")
    public void receiveLogmtsrev(Logmtsrev logmtsrev, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridadJMS: {} ", logmtsrev, jmsMessageHeaderAccessor.getPriority());

            logMtsRevService.insertLogmtsrev(logmtsrev);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTSREV, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGPAYMENT, containerFactory = "myFactory")
    public void receiveLogpayment(Logpayment logpayment, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridad: {}", logpayment, jmsMessageHeaderAccessor.getPriority());

            logPaymentService.saveOrUpdate(logpayment);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGPAYMENT, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGREVERSION, containerFactory = "myFactory")
    public void receiveLogreversion(Logreversion logreversion, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridad : {}" , logreversion, jmsMessageHeaderAccessor.getPriority());

            logReversionService.updateLogreversion(logreversion);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGREVERSION, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_SMSLOGMESSAGE, containerFactory = "myFactory")
    public void receiveSmslogmessage(Smslogmessage smslogmessage, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioriadad: {}", smslogmessage, jmsMessageHeaderAccessor.getPriority());

            smsLogMessageService.insertSmslogmessage(smslogmessage);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_SMSLOGMESSAGE, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_PROCESSCONTROL, containerFactory = "myFactory")
    public void receiveProcesscontrol(Processcontrol processcontrol, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, proridad: {}", processcontrol, jmsMessageHeaderAccessor.getPriority());

            processControlService.createOrUpdate(processcontrol);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGREVERSION, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGDRAFTTOP, containerFactory = "myFactory")
    public void receiveLogDraftTop(Logdraftop logdraftop, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {},  prioriadad: {}" , logdraftop, jmsMessageHeaderAccessor.getPriority());

            logDraftOpService.insertLogdraftop(logdraftop);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGDRAFTTOP, e);
        }
    }

   @JmsListener(destination = ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, containerFactory = "myFactory")
    public void receiveSmslogmessage(Logmessage logmessage, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
        try {
            logger.info("Recibido --> {}, prioridad: {}", logmessage, jmsMessageHeaderAccessor.getPriority());

            logMessageService.insertLogmessage(logmessage);

        }catch (Exception e){
            logger.error("Error en queue: "
                    + ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMESSAGE, e);
        }
    }
}
