package py.com.global.spm.model.services;

import com.global.spm.drivertransaction.RequestForReverseTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Logmts;
import py.com.global.spm.domain.entity.Logmtsrev;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.model.exceptions.MTSInterfaceException;
import py.com.global.spm.model.mts.MTSReversionProcessEventCodes;
import py.com.global.spm.model.mts.MtsService;
import py.com.global.spm.model.mts.ReverseResponse;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmConstants;
import py.com.global.spm.model.util.SpmProcesses;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MTSReversionService {
    private final Logger log = LoggerFactory.getLogger(MTSReversionService.class);

    private final LogMtsService logMtsService;

    private final JmsTemplate jmsTemplate;

    private final MtsService mtsService;

    @Autowired
    public MTSReversionService(LogMtsService logMtsService, JmsTemplate jmsTemplate, MtsService mtsService) {
        this.logMtsService = logMtsService;
        this.jmsTemplate = jmsTemplate;
        this.mtsService = mtsService;
    }

    public void processPayment(Logpayment payment) {
        // 1-Obtener las transacciones MTS a revertir del Logmts.
        List<Logmts> logmtsList = this.logMtsService.findLogmts(payment
                .getIdpaymentNum(),SpmConstants.SUCCESS);

        if (logmtsList != null && !logmtsList.isEmpty()) {
            int cantPending = logmtsList.size();// Para saber el estado de la
            // orden
            // de pago.
            int cantTotal = logmtsList.size();// Para saber el estado de la
            // orden de
            // pago.
            // 2- Enviar al mts la orden de reversion.
            //TODO: Determinar de donde conseguir el id de Sesion
            String idSession = null;

            //idSession = this.mtsMng.bind(payment.getCompany().getIdcompanyPk());
                for (Logmts logmts2 : logmtsList) {
                    Logmtsrev logrev = new Logmtsrev();
                    logrev.setAmountNum(logmts2.getAmountNum());
                    logrev.setPaymentorderdetail(logmts2.getPaymentorderdetail());
                    logrev.setEventcode(logmts2.getEventcode());//  bien.
                    logrev.setCompany(logmts2.getCompany());
                    logrev.setLogmts(logmts2);
                    logrev.setLogpayment(logmts2.getLogpayment());
                    //logrev.setIdpaymentorderPk(logmts2.getIdpaymentorderPk());
                    //logrev.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
                    logrev.setIdtrxmtsChr(logmts2.getIdtrxmtsChr());
                    logrev.setMethodChr(logmts2.getMethodChr());
                    logrev.setTrxtypeChr(logmts2.getTrxtypeChr().toString());
                    logrev.setRequestdateTim(new Timestamp(System
                            .currentTimeMillis()));
                    logrev.setIdsessionmtsChr(idSession);
                    logrev.setRequestChr("idSession=" + idSession
                            + ",idTransacion=" + logrev.getIdtrxmtsChr());

                    // 5- Ejecutar cada operacion.
                    try {
                        RequestForReverseTransaction request = new RequestForReverseTransaction();
                        request.setAccount(logmts2.getPaymentorderdetail().getBeneficiary().getPhonenumberChr());
                        request.setCollectAccount(logmts2.getPaymentorderdetail().getPaymentorder().getWorkflow().getWalletChr());
                        request.setSessionId(Long.parseLong(idSession));
                        request.setMonetaryTransactionId( logrev.getIdtrxmtsChr());
                        request.setAmount(logmts2.getAmountNum());
                        request.setCollectAccountPin(logmts2.getPaymentorderdetail().getPaymentorder().getCompany().getMtspasswordChr());
                        ReverseResponse response = mtsService.reverse(request);

                        // 6- Enviar actualizacion de registro al AsyncUpdater.
                        logrev.setResponsedateTim(new Timestamp(System
                                .currentTimeMillis()));
                        logrev.setIdtrxmtsChr(response.getNroTransaction()
                                .toString());
                        logrev.setResponseChr(response.getResponseStr());
                        logrev.setStateChr(SpmConstants.SUCCESS);
                        cantPending--;
                    } catch (MTSInterfaceException e) {
                        logrev.setResponsedateTim(new Timestamp(System
                                .currentTimeMillis()));
                        logrev.setStateChr(SpmConstants.ERROR);
                        logrev.setIdeventcodeNum(e.getIdEvent());
                        log.error("Error mts:IdEvent->" + e.getIdEvent()
                                + ",msg=" + e.getMessage());
                    }
                    // 7-Actualizamos el estado del logmts.
                    if (logrev.getStateChr().equals(
                            SpmConstants.SUCCESS)) {
                        logmts2.setStateChr(SpmConstants.REVERT);
                        logmts2.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
                    }
                    // 8-Enviar a AsyncUpdater
                    int jmsPriority = (logmts2.getPaymentorderdetail().getPaymentorder().getCompany().getPriority()!=null)?
                            logmts2.getPaymentorderdetail().getPaymentorder().getCompany().getPriority():4;

                    jmsTemplate.setPriority(jmsPriority);
                    jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTSREV ,logrev);
                    jmsTemplate.convertAndSend(ConstantsQueue.ASYNC_UPDATER_QUEUE_LOGMTS,logmts2);
                }

                // 9- Actualizar logpayment con datos actualizados.
                // 9.1- Verificar si el pago se queda con estado parcial.
                String estadoOp = null;
                if (cantPending == 0) {
                    estadoOp = PaymentStates.REVERTIDO;
                } else if (cantPending < cantTotal && cantPending > 0) {
                    estadoOp = PaymentStates.PARCIALMENTE_REVERTIDO;
                } else {
                    // El estado es invalido si no se realizo
                    log.warn("Estado de reversion indeterminado -> CantPending="
                            + cantPending + ",cantTotal=" + cantTotal);
                }
                // 9.2- Actualizar el monto pagado y el estado.
                if (estadoOp != null) {
                    payment.setStateChr(estadoOp);
                    payment.setIdprocessPk(SpmProcesses.MTS_REVERSION_INTERFACE);
                    payment.setIdeventcodeNum(MTSReversionProcessEventCodes.SUCCESS
                            .getIdEventCode());
                }
        } else {
            log.warn("No se puede revertir. El pago no tiene ninguna transferencia MTS asociada->"
                    + payment);
        }
    }

}
