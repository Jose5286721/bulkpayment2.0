package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Paymentordertype;
import py.com.global.spm.model.repository.IPaymentOrderTypeDao;

@Service
@Transactional
public class PaymentOrderTypeService {
    private final Logger log = LoggerFactory.getLogger(PaymentOrderTypeService.class);

    private final IPaymentOrderTypeDao dao;

    @Autowired
    public PaymentOrderTypeService(IPaymentOrderTypeDao dao) {
        this.dao = dao;
    }

    public Paymentordertype getPaymentordertypeById(long idPaymentordertype) {
        try {
           return dao.findById(idPaymentordertype).orElse(null);
        } catch (Exception e) {
            log.error("Error al obtener Paymentordertype --> idPaymentordertype= " + idPaymentordertype, e);
            throw e;
        }
    }

    public String getPaymentOrderType(long idpaymentorder) {
        try {
           Paymentordertype p = dao.findByPaymentordersIdpaymentorderPk(idpaymentorder);

           if(p!=null){
               return p.getNameChr();
           }else
               return null;

       }catch (Exception e){
           log.error("Error al obtener Paymentordertype --> ",e);
           throw e;
       }
    }
}
