package py.com.global.spm.model.message;


import py.com.global.spm.domain.entity.Paymentorder;

import java.io.Serializable;

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
