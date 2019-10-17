package py.com.global.spm.model.messages;


import py.com.global.spm.entities.Paymentorder;

import java.io.Serializable;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class FlowManagerToTransferProcessMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3689523281669805510L;

	// OP aprobada por los firmantes
	// estado: pendiente de pago
	private Paymentorder paymentorder;

	public Paymentorder getPaymentorder() {
		return paymentorder;
	}

	public void setPaymentorder(Paymentorder paymentorder) {
		this.paymentorder = paymentorder;
	}

	@Override
	public String toString() {
		return "FlowManagerToTransferProcessMessage [paymentorder="
				+ paymentorder + "]";
	}

}
