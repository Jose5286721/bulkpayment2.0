package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Logpayment;

@Local
public interface MTSReversionInterfaceManagerLocal {

	/**
	 * A partir del registro de pago, genera las
	 * transacciones para enviar al mts.
	 * Se registran todas las transacciones antes de enviar al MTS.
	 * Se actualizan los estados antes de responder. 
	 * @param paymentorder
	 * @return
	 */
	public void processPayment(Logpayment paymentorder);
	
}
