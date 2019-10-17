package py.com.global.spm.model.process;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.eventcodes.FlowManagerEventCodes;
import py.com.global.spm.model.services.*;
import py.com.global.spm.model.util.*;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@Service
@Transactional
public class PaymentOrderGeneratorProcess {

    private static Logger log = LogManager.getLogger(PaymentOrderGeneratorProcess.class);

    @Autowired
    private DraftService draftService;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private FlowService flowService;

    public void checkAndProcessDrafts() {
        // Procesar programados
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Draft> programedList = draftService.getProgramedPaymentorder(now);
        int totalProgDarft = programedList.size();
        int generated = 0;
        int noGenerated = 0;

        for (Draft draft : programedList) {
            if (this.generatePaymentorderAndNotify(draft, true)) {
                generated++;
            } else {
                log.error("Error en la generacion de Orden de pago --> " + draft);
                draft.setStateChr(DraftStates.ERROR);
                draftService.saveOrUpdate(draft);
                noGenerated++;
            }
        }
        log.info("Totales Programados --> draft=" + totalProgDarft
                + ", generados=" + generated + ", no generados=" + noGenerated);

        // Procesar recurrentes
        Calendar cal = new GregorianCalendar();
        cal.setTime(now);

        List<Draft> recurrentList = draftService.getRecurrentPaymentorder(now);
        int totalRecurrentDarft = 0;
        generated = 0;
        noGenerated = 0;
        if (recurrentList != null) {
            totalRecurrentDarft = recurrentList.size();
            for (Draft draft : recurrentList) {
                if (this.generatePaymentorderAndNotify(draft, false)) {
                    generated++;
                } else {
                    noGenerated++;
                }
            }
        }
        log.info("Totales Recurrentes --> draft=" + totalRecurrentDarft
                + ", generados=" + generated + ", no generados=" + noGenerated);

    }
    private boolean generatePaymentorderAndNotify(Draft draft, boolean programado) {
        try {
            Paymentorder paymentorder = paymentOrderService.generatePaymentorder(draft, programado);

            // si corresponde, notificar generacion de orden de pago a los firmantes
            if (paymentorder.getNotifygenNum()) {
                flowService.notifyAllSigners(paymentorder, FlowManagerEventCodes.PAYMENT_ORDER_GENERATED);
                log.debug("Payment order generation notificated to signers --> " + paymentorder);
            }else {
                // notificar generacion de orden de pago al creador del draft
                flowService.notifyDraftCreator(draft, paymentorder, FlowManagerEventCodes.PAYMENT_ORDER_GENERATED);
            }

            // si corresponde, notificar al primer firmante
            if (paymentorder.getNotifysignerNum()) {
                flowService.notifyNextSigner(paymentorder);
            }

            return true;
        } catch (Exception e) {
            log.error("Error desconocido generando orden de pago --> " + draft, e);
            return false;
        }
    }

}
