package py.com.global.spm.model.services.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.model.dto.redis.LogPaymentCache;
import py.com.global.spm.model.dto.redis.PaymentOrderCache;
import py.com.global.spm.model.repository.redis.IPaymenOrderCacheDao;
import py.com.global.spm.model.util.ConstantsQueue;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentStates;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentOrderCacheService {
    private final IPaymenOrderCacheDao dao;
    private final LogPaymentCacheService logPaymentCacheService;
    private final JmsTemplate jmsTemplate;


    private final Logger logger = LoggerFactory.getLogger(PaymentOrderCacheService.class);

    @Autowired
    public PaymentOrderCacheService(IPaymenOrderCacheDao dao, LogPaymentCacheService logPaymentCacheService, JmsTemplate jmsTemplate){
        this.dao = dao;
        this.logPaymentCacheService = logPaymentCacheService;
        this.jmsTemplate=jmsTemplate;
    }

    public PaymentOrderCache save(PaymentOrderCache paymentOrderCache){
       try {
           return dao.save(paymentOrderCache);
       }catch (Exception e){
           logger.error("Al guardar PaymentOrderCache",e);
           throw e;
       }

    }

    public void savePaymentOrderCache(Paymentorder paymentorder, List<Logpayment> pagosList, boolean isReintento){
        paymentorder.setStateChr(PaymentOrderStates.PAGO_EN_PROCESO);
        List<LogPaymentCache> logPaymentCaches = new ArrayList<>();
        pagosList.forEach(logpayment -> { logPaymentCaches.add(new LogPaymentCache(logpayment.getPaymentorderdetail().getPaymentorder().getIdpaymentorderPk(),
                    logpayment.getIdpaymentNum(),logpayment.getStateChr(),logpayment.getPaymentorderdetail().getBeneficiary().getIdbeneficiaryPk(),logpayment.getAmountpaidNum()));
        });
        logPaymentCacheService.saveAll(logPaymentCaches);
        PaymentOrderCache paymentOrderCache = new PaymentOrderCache(paymentorder,pagosList.size(),isReintento);
        this.save(paymentOrderCache);
    }

    public PaymentOrderCache findById(Long id){
        Optional<PaymentOrderCache> optional=dao.findById(id);
        return optional.orElse(null);

    }

    public boolean existPaymentOrderCache(Long id){
        try{
            return dao.existsById(id);
        }catch (Exception e){
            logger.info("Al obtener PaymentOrderCache",e);
            throw e;
        }
    }

    public synchronized PaymentOrderCache updatePaymentOrderCache(Logpayment payment){
        // 1-Se actualiza el cache de pagos.
            PaymentOrderCache po = findById(payment.getPaymentorderdetail().getId().getIdpaymentorderPk());
            LogPaymentCache logpayCache = logPaymentCacheService.findById(payment.getIdpaymentNum());
            if (logpayCache != null) {
                int cantPending = po.getCantPending();
                int cantSuccess = po.getCantSuccess();
                BigDecimal amountPayOp = po.getAmountPayOp();
                cantPending--;
                if (PaymentStates.SATISFACTORIO.equalsIgnoreCase(payment.getStateChr())) {
                    cantSuccess++;
                    amountPayOp = amountPayOp.add(payment.getAmountpaidNum());
                }
                logpayCache.setStateChr(payment.getStateChr());
                logpayCache.setAmountpaidNum(payment.getAmountpaidNum());

                int cantTotal = po.getCantTotal();
                if (po.isTimeout()) {
                    cantPending = 0;
                }
                // Actualizo estado de la orden de pago.
                if (cantSuccess == cantTotal) {
                    po.setStateChr(PaymentOrderStates.SATISFACTORIO);
                } else if (cantSuccess > 0 && cantPending == 0) {
                    po.setStateChr(PaymentOrderStates.PARCIALMENTE_PAGADA);
                } else if (cantSuccess == 0 && cantPending == 0) {
                    po.setStateChr(PaymentOrderStates.ERROR);
                }
                po.setAmountPayOp(amountPayOp);
                po.setCantPending(cantPending);
                po.setCantSuccess(cantSuccess);
                logPaymentCacheService.saveOrUpdate(logpayCache);
                po=this.save(po);
            }
            return po;
    }

    public void deletePaymentOrderCache(Long id){
        try {
            dao.deleteById(id);
        }catch (Exception e){
            logger.error("Al eliminar de la cache ",e);
        }
    }
}
