package py.com.global.spm.GUISERVICE.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.IPaymentorderdetailDao;
import py.com.global.spm.GUISERVICE.enums.EstadosPaymentorder;
import py.com.global.spm.domain.entity.Paymentorderdetail;

@Service
public class PaymentOrderDetailService {
    private IPaymentorderdetailDao dao;

    public PaymentOrderDetailService(IPaymentorderdetailDao detailDao){
        this.dao = detailDao;
    }

    /**
     * Obtener detalles de ordenes de pago por cedula de identidad y id de orden de pago
     * @param ci: cedula de identidad
     * @param paymentOrderId: id de orden de pago
     * @return List<Paymentorderdetail> detalles de ordenes de pago
     * @see Paymentorderdetail
     */
     Page<Paymentorderdetail> byBeneficiaryAndPaymentOrderId(String ci, Long paymentOrderId, PageRequest pageRequest){
        Page<Paymentorderdetail> detail =
               dao.findBybeneficiarySubscriberciChrAndPaymentorderIdpaymentorderPkAndPaymentorderStateChr(ci, paymentOrderId, EstadosPaymentorder.SATISFACTORIO.getCodigo0(), pageRequest);
        return detail;

    }

    /**
     * Obtener detalles de orden de pago por cedula de identidad
     * @param ci: cedula de identidad
     * @return List<Paymentorderdetail> detalles de ordenes de pago
     * @see Paymentorderdetail
     */
     Page<Paymentorderdetail> byBeneficiaryCi(String ci, PageRequest pageRequest){
        Page<Paymentorderdetail> details = dao.findBybeneficiarySubscriberciChrAndPaymentorderStateChr
                        (ci, EstadosPaymentorder.SATISFACTORIO.getCodigo0(), pageRequest);
        return details;

    }

    /**
     * Obtener detalles de orden de pago por id de orden de pago
     * @param idPaymentOrder: id de ordend de pago
     * @return List<Paymentorderdetail>: detalles de ordenes de pago
     */
     Page<Paymentorderdetail> byPaymentOrderId(Long idPaymentOrder, PageRequest pageRequest){
        Page<Paymentorderdetail> details = dao.findByPaymentorderIdpaymentorderPkAndPaymentorderStateChr(idPaymentOrder, EstadosPaymentorder.SATISFACTORIO.getCodigo0(), pageRequest);
        return details;

    }
}
