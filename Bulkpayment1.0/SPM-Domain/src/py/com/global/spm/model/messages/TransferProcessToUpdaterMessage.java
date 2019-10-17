package py.com.global.spm.model.messages;



import py.com.global.spm.entities.Logpayment;
import py.com.global.spm.entities.Paymentorder;

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
	private Paymentorder paymentorder;
	private boolean isReintento;
	private List<Logpayment> logpaymentList;

	public Paymentorder getPaymentorder() {
		return paymentorder;
	}

	public void setPaymentorder(Paymentorder paymentorder) {
		this.paymentorder = paymentorder;
	}

	public List<Logpayment> getLogpaymentList() {
		return logpaymentList;
	}

	public void setLogpaymentList(List<Logpayment> logpaymentList) {
		this.logpaymentList = logpaymentList;
	}

	public boolean isReintento() {
		return isReintento;
	}

	public void setReintento(boolean isReintento) {
		this.isReintento = isReintento;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(
				"\nTransferProcessToUpdaterMessage:");
		s.append("\nPaymentorder=" + paymentorder);
		s.append("\nLogpayment:");
		if (logpaymentList != null) {
			for (Logpayment lp : logpaymentList) {
				s.append("\n\t" + lp.toString());
			}
		} else {
			s.append("\n\tnull");
		}
		return s.append("\n").toString();
	}

}
