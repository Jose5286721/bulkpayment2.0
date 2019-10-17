package py.com.global.spm.model.messages;

import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.Paymentorderdetail;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class OPManagerToFlowManagerMessage implements Serializable {

	/**
	 * 
	 */
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

}
