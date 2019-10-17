package py.com.global.spm.model.message;


import py.com.global.spm.domain.entity.Logpayment;

import java.io.Serializable;

/**
 * @author Lino Chamorro
 */
public class MTSReversionToReversionProcessMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3097246606153472824L;

    private Logpayment payment;


    public Logpayment getPayment() {
        return payment;
    }

    public void setPayment(Logpayment paymentorder) {
        this.payment = paymentorder;
    }

    @Override
    public String toString() {
        return "ReversionProcessToMTSReversion [payment=" + payment + "]";
    }


}

