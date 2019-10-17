package py.com.global.spm.model.interfaces;

import java.util.List;

import javax.ejb.Local;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.cache.dto.ReversalDto;

@Local
public interface ReversionProcessManagerLocal {

	/**
	 * Metodo que verifica si el pago todavia se encuentra en cache.
	 * @param logpayment
	 * @return True si existe.
	 */
	public boolean existLogpayment(Logpayment logpayment);

	/**
	 * Se actualiza el cache de pagos.
	 * Se envia actualiza en la base de datos.
	 * @param paymentorder
	 * @return Dto guardado en el cache.
	 */
	public ReversalDto updateReversalCache(Logpayment paymentorder);
	
	/**
	 * Para sacar del cache una orden de pago.
	 * @param paymentorder
	 * @return Dto guardado en el cache.
	 */
	public ReversalDto removeReversalCache(ReversalDto logpayment);
	
	/**
	 * Metodo encargado de limpiar el cache de las ordenes de pago.
	 * 
	 */
	public void cleanCachePaymentOrder(); 
	
	/**
	 * Retorna una cadena con los id de las ordenes de pago que estan en el hash.
	 * @return
	 */
	public  String listCachePaymentOrder();
	
	/**
	 * Retorna true si la orden de pago se encuentra en el 
	 * @param paymentOrder
	 * @return
	 */
	public boolean existPaymentOrder(Paymentorder paymentOrder);

	/**
	 * Metodo para validar la orden de pago. Verifica que no haya pasado el tiempo maximo para poder reversar.
	 * Verifica si la orden de pago tiene un monto valido.
	 * @param paymentOrder Orden de pago a validar.
	 * @return True si todo esta OK, sino false.
	 */
	public boolean validateTimePaymentOrder(Paymentorder paymentOrder);

	/**
	 * Obtiene los pagos a revertir y agrega en el hash.
	 * @param paymentOrder
	 * @return
	 */
	public List<Logpayment> getPaymentsToReversion(Paymentorder paymentOrder);

}
