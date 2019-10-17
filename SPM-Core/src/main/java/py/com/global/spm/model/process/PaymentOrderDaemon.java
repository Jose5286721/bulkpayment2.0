package py.com.global.spm.model.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import py.com.global.spm.model.util.SpmProcesses;

@Component
@EnableScheduling
public class PaymentOrderDaemon {
    private static Logger log = LogManager.getLogger(PaymentOrderDaemon.class);
    @Autowired
    private PaymentOrderGeneratorProcess paymentOrderGeneratorProcess;

    @Autowired
    private LogPaymentPayProcess logPaymentPayProcess;


    @Scheduled(fixedDelayString = "#{@getTime}", initialDelay = 2000)
    public void run() {
        try {
            log.info("Iniciando proceso en background --> proceso =" + SpmProcesses.PO_MANAGER);
           paymentOrderGeneratorProcess.checkAndProcessDrafts();
        }catch (Exception e){
            log.error("Error proceso en background --> proceso =" + SpmProcesses.PO_MANAGER);
        }
    }

    @Scheduled(fixedDelay = 3000L,initialDelay = 2000)
    public void payPayments() {
        try {
            logPaymentPayProcess.checkPayments();
        }catch (Exception e){
            log.error("Error proceso en background --> proceso =" + SpmProcesses.PO_MANAGER);
        }
    }
}
