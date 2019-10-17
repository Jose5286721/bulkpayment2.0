package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.*;
import py.com.global.spm.model.eventcodes.TransferProcessEventCodes;
import py.com.global.spm.model.services.redis.PaymentOrderCacheService;
import py.com.global.spm.model.util.PaymentStates;
import py.com.global.spm.model.util.SpmProcesses;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class TransferService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActiveMQClientService activeMQClient;
    private final PaymentOrderDetailService paymentOrderDetailService;
    private final PaymentOrderCacheService paymentOrderCacheService;
    private final LogPaymentService logPaymentService;
    private final Logger log = LoggerFactory.getLogger(TransferService.class);

    @Autowired
    public TransferService( PaymentOrderDetailService paymentOrderDetailService,PaymentOrderCacheService paymentOrderCacheService,
                            LogPaymentService logPaymentService) {
        this.paymentOrderDetailService = paymentOrderDetailService;
        this.paymentOrderCacheService=paymentOrderCacheService;
        this.logPaymentService=logPaymentService;
    }


    public List<Logpayment> generateLogPayment(Paymentorder paymentorder) {
        // 1- Generar los logPayment a partir de la orden de pago.
        // 1.1 -Insertar en la BD el logPayment
        try {
            List<Logpayment> pagosList = Collections.synchronizedList( new ArrayList<>());
            long date = System.currentTimeMillis();
            log.info("Inicio de creacion de LogPayment");
            List<Paymentorderdetail> paymentorderdetails;
            int i=0;
            while (!(paymentorderdetails=paymentOrderDetailService.getPaymentOrderDetailsPag(paymentorder.getIdpaymentorderPk(),i)).isEmpty()){
                long t1 = System.currentTimeMillis();
                paymentorderdetails.parallelStream().forEach(draft ->{
                    Logpayment logpay = new Logpayment();
                    logpay.setAmountNum(draft.getAmountNum());
                    logpay.setCompany(paymentorder.getCompany());
                    logpay.setPaymentorderdetail(draft);
                    logpay.setIdprocessPk(SpmProcesses.TRANSFER_PROCESS);
                    logpay.setEventcode(new Eventcode(new EventcodeId(SpmProcesses.TRANSFER_PROCESS,TransferProcessEventCodes.SUCCESS
                            .getIdEventCode())));

                    logpay.setIdeventcodeNum(TransferProcessEventCodes.SUCCESS
                            .getIdEventCode());

                    Timestamp today = new Timestamp(System.currentTimeMillis());
                    logpay.setCreationdateTim(today);
                    logpay.setAmountpaidChr("0.0");
                    logpay.setRetry(-1);
                    logpay.setUpdatedateTim(today);
                    logpay.setStateChr(PaymentStates.PAGO_PENDIENTE);
                    // Obtengo el numero de telefono.
                    Beneficiary beneficiario = draft.getBeneficiary();
                    logpay.setPhonenumberChr(beneficiario.getPhonenumberChr());
                    logpay.setSubscriberDocNumChr(beneficiario.getSubscriberciChr());
                    pagosList.add(logpay);
                });
                log.info("{} grupo de {} en {} ms", i+1,paymentorderdetails.size(),System.currentTimeMillis()-t1);
                i++;
            }
            logPaymentService.saveOrUpdate(pagosList);
            log.info("Finalizacion de creacion de LogPayment --> {} ms",(System.currentTimeMillis()-date));
            paymentOrderCacheService.savePaymentOrderCache(paymentorder,pagosList,false);
            return pagosList;

        }catch (Exception e){
            log.error("Error generating logpayments for idPaymentOrder: {}",paymentorder.getIdpaymentorderPk());
            throw e;
        }
    }
    public List<Logpayment> getRetryLogPayment(Paymentorder paymentorder) {

        // 1- Obtener los logPayment a partir de la orden de pago.
        List<Logpayment> listaPago = logPaymentService
                .getRetryLogPayment(paymentorder.getIdpaymentorderPk());

        if (listaPago != null && !listaPago.isEmpty()) {
            // 2-Se agregan los registros al cache.
            paymentOrderCacheService.savePaymentOrderCache(paymentorder,listaPago,true);
            return listaPago;
        } else {
            return Collections.emptyList();
        }

    }

}
