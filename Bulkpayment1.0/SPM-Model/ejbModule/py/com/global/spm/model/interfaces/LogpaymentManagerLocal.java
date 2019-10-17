package py.com.global.spm.model.interfaces;

import java.util.List;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.model.exceptions.LogPaymentException;

public interface LogpaymentManagerLocal {

	/**
	 * Persiste las lista de pagos recibidos como parametro.
	 * Lanza una excepcion con un codigo de evento en caso
	 * que no se pueda persistir.
	 * @param pagosList
	 * @throws LogPaymentException
	 */
	public void persistPaymentList(List<Logpayment> pagosList) throws LogPaymentException;

	/**
	 * Actualiza un logpayment en particular.
	 * @param payment
	 * @throws LogPaymentException
	 */
	public boolean updateLogpayment(Logpayment payment);
	
	/**
	 * Obtiene todos los pagos con estados validos para realizar luego una operacion 
	 * de reversion.
	 * @param idPaymentOrder
	 * @return
	 */
	public List<Logpayment> getPaymentsToReversionByIdPaymentOrder(long idPaymentOrder);

	/**
	 * Para obtener los pagos que se reintentaran el pago.
	 * @param paymentorder
	 * @return 
	 */
	
	public List<Logpayment> getRetryLogPayment(long paymentorderId);
	
	public List<Logpayment> getPaymentsByIdPaymentOrder(
			long idPaymentOrder);

}
