package py.com.global.spm.model.messages;


import py.com.global.spm.entities.Logpayment;
import java.io.Serializable;

/**
 * 
 * @author R2
 * 
 */
public class TransferProcessToMTSInterfaceMessage implements Serializable {

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
		return "TransferProcessToMTSInterfaceMessage [payment=" + payment + "]";
	}
	
	
}
