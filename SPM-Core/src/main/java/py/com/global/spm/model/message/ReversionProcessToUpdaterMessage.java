package py.com.global.spm.model.message;


import py.com.global.spm.domain.entity.Paymentorder;

import java.io.Serializable;

/**
 * 
 * @author Lino Chamorro
 *
 */
public class ReversionProcessToUpdaterMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5819049390983024611L;
	
	private Paymentorder paymentorder;

	public Paymentorder getPaymentorder() {
		return paymentorder;
	}

	public void setPaymentorder(Paymentorder paymentorder) {
		this.paymentorder = paymentorder;
	}


	@Override
	public String toString() {
		return "ReversionProcessToUpdaterMessage{" +
				"paymentorder=" + paymentorder +
				'}';
	}
}
