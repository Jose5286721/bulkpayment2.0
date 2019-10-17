package py.com.global.spm.model.message;

import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.entity.Paymentorderdetail;

import java.io.Serializable;
import java.util.List;

public class OPManagerToFlowManagerMessage implements Serializable {

    private static final long serialVersionUID = -7968182744394238897L;

    // OP generada por el OPManager
    // estado: en proceso
    private Paymentorder paymentorder;
    private List<Paymentorderdetail> podetailList;

    public Paymentorder getPaymentorder() {
        return paymentorder;
    }

    public void setPaymentorder(Paymentorder paymentorder) {
        this.paymentorder = paymentorder;
    }

    public List<Paymentorderdetail> getPodetailList() {
        return podetailList;
    }

    public void setPodetailList(List<Paymentorderdetail> podetailList) {
        this.podetailList = podetailList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OPManagerToFlowManagerMessage{");
        sb.append("idPaymentorder=").append(paymentorder.getIdpaymentorderPk());
        sb.append(", podetailList=").append(podetailList);
        sb.append('}');
        return sb.toString();
    }
}

