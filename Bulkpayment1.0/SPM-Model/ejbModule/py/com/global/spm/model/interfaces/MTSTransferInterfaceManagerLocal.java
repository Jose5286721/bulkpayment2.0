package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Logpayment;

@Local
public interface MTSTransferInterfaceManagerLocal {

	/**
	 * A partir del registro de pago, genera las
	 * transacciones para enviar al mts.
	 * Se registran todas las transacciones antes de enviar al MTS.
	 * Se actualizan los estados antes de responder. 
	 * @param paymentorder
	 * @return
	 */
	public void processPayment(Logpayment paymentorder);
	
	
	/**
	 * Retorna el monto que tiene disponible la empresa.
	 * @param paymentOrder Orden de pago a validar.
	 * @return True si todo esta OK, sino false.
	 */
	public Double getBalanceCompany(long idCompany);
	
	

}
