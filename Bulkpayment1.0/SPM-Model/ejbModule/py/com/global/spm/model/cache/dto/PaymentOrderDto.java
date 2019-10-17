package py.com.global.spm.model.cache.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;

import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.model.util.PaymentOrderStates;
import py.com.global.spm.model.util.PaymentStates;

public class PaymentOrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6746980864963862473L;
	// private static final Logger log = Logger
	// .getLogger(PaymentOrderDto.class);

	private Paymentorder paymentOrder;
	private Hashtable<Long, Logpayment> logPaymentHash;
	private int cantSuccess = 0; // Cantidad de pagos exitosos.
	private int cantTotal = -1; // Cantidad total de pagos.
	private int cantPending = -1; // Cantidad pagos pendientes.
	private long creationTimeStamp = 0; // TimeStamp de creacion de objeto.
	private boolean isTimeout = false;
	private Double amountPayOp = 0.0;
	private boolean isReintento;

	public PaymentOrderDto(Paymentorder paymentOrder,
			Hashtable<Long, Logpayment> logPaymentHash) {
		this.paymentOrder = paymentOrder;
		this.logPaymentHash = logPaymentHash;
		this.cantSuccess = 0;
		this.cantPending = logPaymentHash.size();
		this.cantTotal = logPaymentHash.size();
		this.amountPayOp = paymentOrder.getAmountpaidNum();
		this.creationTimeStamp = System.currentTimeMillis();
	}

	public Paymentorder getPaymentOrder() {
		this.updatePaymentOrderState();
		return paymentOrder;
	}

	public void setIdPaymentOrder(Paymentorder paymentOrder) {
		this.paymentOrder = paymentOrder;
	}

	public Collection<Logpayment> getLogPayment() {
		return this.logPaymentHash.values();
	}

	/**
	 * Metodo para actualizar estado de los pagos. Tambien actualiza el estado
	 * de la orden de pago en caso que sea necesario.
	 * 
	 * @param logpayment
	 *            Pago retornado por el MTSInterface.
	 * @return False en caso que logpayment ya no se encuentre en el hash.
	 */
	public boolean setLogPayment(Logpayment logpayment) {
		Logpayment logpayCache = this.logPaymentHash.get(logpayment
				.getIdpaymentNum());
		if (logpayCache == null) {
			return false;
		} else {
			this.cantPending--;
			if (logpayment.getStateChr().equalsIgnoreCase(
					PaymentStates.SATISFACTORIO)) {
				this.cantSuccess++;
				amountPayOp = amountPayOp + logpayment.getAmountpaidNum();// Monto
																			// total
																			// pagado.
			}
			logpayCache.setStateChr(logpayment.getStateChr());
			logpayCache.setAmountpaidNum(logpayment.getAmountpaidNum());
			updatePaymentOrderState();
			return true;
		}

	}

	public boolean existLogpayment(Logpayment logpayment) {
		Logpayment logpay = this.logPaymentHash.get(logpayment
				.getIdpaymentNum());
		if (logpay == null) {
			return false;
		} else {
			return true;
		}

	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	/**
	 * Indica si la orden de pago ya paso el timeOut de procesamiento
	 * correspondiente.
	 * 
	 * @param isTimeout
	 *            True si ya paso, y false en caso contrario.
	 */
	public void setTimeout(boolean isTimeout) {
		this.isTimeout = isTimeout;
	}

	private void updatePaymentOrderState() {
		// Si ya es timeout, la cantidad de pendientes se pone a cero.
		if (isTimeout) {
			this.cantPending = 0;
		}
		// Actualizo estado de la orden de pago.
		if (this.cantSuccess == this.cantTotal) {
			this.paymentOrder.setStateChr(PaymentOrderStates.PAGADA);
		} else if (this.cantSuccess > 0 && this.cantPending == 0) {
			this.paymentOrder
					.setStateChr(PaymentOrderStates.PARCIALMENTE_PAGADA);
		} else if (this.cantSuccess == 0 && this.cantPending == 0) {
			this.paymentOrder.setStateChr(PaymentOrderStates.NO_PAGADA);
		}
		this.paymentOrder.setAmountpaidNum(amountPayOp);
	}

	public boolean isReintento() {
		return isReintento;
	}

	public void setReintento(boolean isReintento) {
		this.isReintento = isReintento;
	}

}
