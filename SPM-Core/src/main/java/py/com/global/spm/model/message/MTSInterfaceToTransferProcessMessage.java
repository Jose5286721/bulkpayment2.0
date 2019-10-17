package py.com.global.spm.model.message;


import py.com.global.spm.domain.entity.Logpayment;

import java.io.Serializable;

/**
 *
 * @author Lino Chamorro
 *
 */
public class MTSInterfaceToTransferProcessMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3689523281669805510L;

    // OP aprobada por los firmantes
    // estado: pendiente de pago
    private Logpayment payment;

    public Logpayment getPayment() {
        return payment;
    }

    public void setPayment(Logpayment paymentorder) {
        this.payment = paymentorder;
    }

    @Override
    public String toString() {
        return "MTSInterfaceToTransferProcessMessage [payment=" + payment + "]";
    }


}
