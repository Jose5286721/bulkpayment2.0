package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Paymentordertype;

@Local
public interface PaymentordertypeManagerLocal {

	public Paymentordertype getPaymentordertypeById(long idPaymentordertype);

	public String getPaymentOrderType(long idpaymentorder);

}
