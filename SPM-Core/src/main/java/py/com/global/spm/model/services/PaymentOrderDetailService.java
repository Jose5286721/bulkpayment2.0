package py.com.global.spm.model.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.domain.entity.Paymentorderdetail;
import py.com.global.spm.model.repository.IPaymentOrderDetailDao;

import java.util.List;

@Service
@Transactional
public class PaymentOrderDetailService {

    private final Logger logger = LoggerFactory.getLogger(PaymentOrderDetailService.class);


    private final IPaymentOrderDetailDao paymentOrderDetailDao;

    @Autowired
    public PaymentOrderDetailService(IPaymentOrderDetailDao paymentOrderDetailDao) {
        this.paymentOrderDetailDao = paymentOrderDetailDao;
    }

    public Paymentorderdetail saveOrUpdate(Paymentorderdetail paymentorderdetail){
        try {
            return paymentOrderDetailDao.saveAndFlush(paymentorderdetail);
        } catch (Exception e) {
            logger.error("Error al Guardar/Actualizar Paymentorderdetail", e);
            throw e;
        }
    }

    public void saveAll(List<Paymentorderdetail> paymentorderdetails){
        try {
             paymentOrderDetailDao.saveAll(paymentorderdetails);
        } catch (Exception e) {
            logger.error("Error al Guardar/Actualizar Paymentorderdetail", e);
            throw e;
        }
    }


    public List<Paymentorderdetail> getPaymentOrderDetails(long id){
        try {
            return paymentOrderDetailDao.findByPaymentorderIdpaymentorderPk(id, Pageable.unpaged());
        } catch (Exception e) {
            logger.error("Error al obtener los Paymentorderdetail", e);
            throw e;
        }
    }

    public List<Paymentorderdetail> getPaymentOrderDetailsPag(long id, int page){
        PageRequest pageRequest = PageRequest.of(page, 10000);
        try {
            return paymentOrderDetailDao.findByPaymentorderIdpaymentorderPk(id, pageRequest);
        } catch (Exception e) {
            logger.error("Error al obtener los Paymentorderdetail", e);
            throw e;
        }
    }

}
