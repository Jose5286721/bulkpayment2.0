package py.com.global.spm.model.interfaces;

import java.util.List;

import javax.ejb.Local;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.dto.PaymentOrderDto;

@Local
public interface TransferProcessManagerLocal {

	/**
	 * Metodo que genera los logPayment a partir de la orden de pago. Los
	 * registros son generados con estado "En proceso" Se agregan los registros
	 * al cache.
	 * 
	 * @param paymentorder
	 * @return Lista de pagos.
	 */
	public List<Logpayment> generateLogPayment(Paymentorder paymentorder);

	/**
	 * Metodo para obtener los Logpayments para reintentar los pagos. Son
	 * Logpayments con estado Error o Parcialmente Satisfactorio. Se obtiene de
	 * la base de datos y se mete en el hash.
	 * 
	 * @param paymentorder
	 * @return Lista de pagos a reitentar.
	 */
	public List<Logpayment> getRetryLogPayment(Paymentorder paymentorder);

	/**
	 * Metodo que verifica si el pago todavia se encuentra en cache.
	 * 
	 * @param logpayment
	 * @return True si existe.
	 */
	public boolean existLogpayment(Logpayment logpayment);

	/**
	 * Se actualiza el cache de pagos. Se envia actualiza en la base de datos.
	 * 
	 * @param paymentorder
	 * @return Dto guardado en el cache.
	 */
	public PaymentOrderDto updatePaymentOrderCache(Logpayment paymentorder);

	/**
	 * Para sacar del cache una orden de pago.
	 * 
	 * @param paymentorder
	 * @return Dto guardado en el cache.
	 */
	public PaymentOrderDto removePaymentOrderCache(PaymentOrderDto logpayment);

	/**
	 * Metodo encargado de limpiar el cache de las ordenes de pago.
	 * 
	 */
	public void cleanCachePaymentOrder();

	/**
	 * Retorna una cadena con los id de las ordenes de pago que estan en el
	 * hash.
	 * 
	 * @return
	 */
	public String listCachePaymentOrder();

	/**
	 * Retorna true si la orden de pago se encuentra en el
	 * 
	 * @param paymentOrder
	 * @return
	 */
	public boolean existPaymentOrder(Paymentorder paymentOrder);

	/**
	 * Metodo para validar la orden de pago. Verifica si la orden de pago tiene
	 * un monto valido dependiendo del estado de la orden de pago.
	 * 
	 * @param paymentOrder
	 *            Orden de pago a validar.
	 * @return True si todo esta OK, sino false.
	 */
	public boolean validateAmountPaymentOrder(Paymentorder paymentOrder);
	
}
