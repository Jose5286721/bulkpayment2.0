package py.com.global.spm.model.messages;


import py.com.global.spm.entities.Logpayment;

import java.io.Serializable;

/**
 * 
 * @author Lino Chamorro
 *
 */
public class ReversionProcessToMTSReversionMessage implements Serializable {

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
