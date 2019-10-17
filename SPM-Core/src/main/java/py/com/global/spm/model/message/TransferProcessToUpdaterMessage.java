package py.com.global.spm.model.message;




import py.com.global.spm.model.dto.redis.LogPaymentCache;
import py.com.global.spm.model.dto.redis.PaymentOrderCache;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class TransferProcessToUpdaterMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4185122623869498549L;

	// OP procesada por el TransferProcess
	// estado: pendiente de pago, la empresa no tiene credito en billetera
	// estado: pagado, todos los pagos fueron realizados
	// estado: parcialmente pagado, al menos un pago fue realizado
	// estado: no pagado, ningun pago fue realizado
	private PaymentOrderCache paymentOrderCache;
	private List<LogPaymentCache> logPaymentCaches;
	private boolean isReintento;

	public PaymentOrderCache getPaymentOrderCache() {
		return paymentOrderCache;
	}

	public void setPaymentOrderCache(PaymentOrderCache paymentorder) {
		this.paymentOrderCache = paymentorder;
	}

	public List<LogPaymentCache> getLogPaymentCaches() {
		return logPaymentCaches;
	}

	public void setLogPaymentCaches(List<LogPaymentCache> logPaymentCaches) {
		this.logPaymentCaches = logPaymentCaches;
	}

	public boolean isReintento() {
		return isReintento;
	}

	public void setReintento(boolean isReintento) {
		this.isReintento = isReintento;
	}



}
