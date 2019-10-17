package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.message.TransferProcessToMTSInterfaceMessage;
import py.com.global.spm.model.repository.ILogPaymentDao;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentStates;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@EnableCaching
@CacheConfig(cacheNames = "logpaymentCache")
public class LogPaymentService {
    private final Logger log = LoggerFactory.getLogger(LogPaymentService.class);

    private final ILogPaymentDao dao;

    private final JmsTemplate jmsTemplate;


    @Autowired
    public LogPaymentService(ILogPaymentDao dao, JmsTemplate jmsTemplate) {
        this.dao = dao;
        this.jmsTemplate = jmsTemplate;
    }

    public void persistPaymentList(List<Logpayment> pagosList) {
        try {
            dao.saveAll(pagosList);
        } catch (Exception e) {
            log.error("Creating logPayment--> ", e);
            throw e;
        }
    }

    public Logpayment saveOrUpdate(Logpayment logpayment) throws Exception {
        try {
            logpayment.setUpdatedateTim(new Timestamp(System.currentTimeMillis()));
            return dao.save(logpayment);
        } catch (Exception e) {
            log.debug("Error al insertar logpayment --> " + logpayment);
            throw e;
        }


    }

    public List<Logpayment> saveOrUpdate(List<Logpayment> logpayments) {
        try {
            return dao.saveAll(logpayments);
        } catch (Exception e) {
            log.error("Error saving all logPayments");
            throw e;
        }
    }

    public Logpayment findById(Long idpaymentNum) {

        try {
            Optional<Logpayment> l = dao.findById(idpaymentNum);
            return l.orElse(null);
        } catch (Exception e) {
            log.error("Al buscar Logpayment --> ", e);
            return null;
        }
    }

    public List<Logpayment> getPaymentsByIdPaymentOrder(long idPaymentOrder) {
        try {
            return dao.findByPaymentorderdetailIdIdpaymentorderPk(idPaymentOrder);
        } catch (Exception e) {
            log.error("Getting logpayments --> ", e);
            throw e;
        }
    }

    public List<Logpayment> getPaymentsToReversionByIdPaymentOrder(
            long idPaymentOrder) {
        try {

            return dao.findByPaymentorderAnAndStateChr(idPaymentOrder, PaymentStates.SATISFACTORIO, PaymentStates.PARCIALMENTE_PAGADA);

        } catch (Exception e) {
            log.error("Getting logpayments -->", e);
            throw e;
        }
    }

    public List<Logpayment> getRetryLogPayment(long idPaymentOrder) {
        try {
            return dao.findByPaymentorderAnAndStateChr(idPaymentOrder, PaymentStates.ERROR, PaymentStates.PARCIALMENTE_PAGADA);
        } catch (Exception e) {
            log.error("getRetryLogPayment : {}", e.getMessage());
            throw e;
        }
    }

    public List<Logpayment> getPaymentsToPay(Long idpaymentOrder, int maxCant, int retry) {
        Pageable pageRequest = PageRequest.of(0, maxCant);
        List<String> states = Arrays.asList(PaymentStates.ERROR, PaymentStates.PARCIALMENTE_PAGADA,PaymentStates.PAGO_PENDIENTE);
        return dao.findByPaymentorderdetailIdIdpaymentorderPkAndStateChrIsInAndRetryIsLessThan(idpaymentOrder,states , retry, pageRequest);
    }


    public void sendLogPayment(Logpayment logpayment, Paymentorder paymentorder, int jmsPriority) throws Exception{
        TransferProcessToMTSInterfaceMessage tpmi = new TransferProcessToMTSInterfaceMessage();
        tpmi.setLogpayment(logpayment);
        tpmi.setWalletChr(paymentorder.getWorkflow().getWalletChr());
        tpmi.setIdpaymentOrderPk(paymentorder.getIdpaymentorderPk());
        //Guardamos el estado que tenia antes de ser procesada por la cola
        tpmi.setStateChr(logpayment.getStateChr());

        //Para evitar pagos repetidos
        logpayment.setStateChr(PaymentStates.PAGO_EN_PROCESO);
        logpayment.setRetry(paymentorder.getRetry());

        dao.save(logpayment);
        jmsTemplate.setPriority(jmsPriority);
        jmsTemplate.convertAndSend(ConstantsQueue.MTS_INTERFACE_TRANSFER_QUEUE, tpmi);
    }
}
