package py.com.global.spm.model.interfaces;


import py.com.global.spm.entities.Draft;
import py.com.global.spm.entities.Paymentorder;
import py.com.global.spm.entities.Paymentorderdetail;
import py.com.global.spm.model.exceptions.PaymentOrderRequiredValuesException;

import javax.ejb.Local;
import java.util.List;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Local
public interface PaymentOrderManagerLocal {

	public void createPaymentorder(Paymentorder paymentorder,
                                   List<Paymentorderdetail> paymentorderdetails)
			throws PaymentOrderRequiredValuesException, Exception;

	public void createPaymentorderAndInactivateDraft(Draft draft,
                                                     Paymentorder paymentorder,
                                                     List<Paymentorderdetail> paymentorderdetails)
			throws PaymentOrderRequiredValuesException, Exception;

	public boolean updatePaymentorder(Paymentorder paymentorder);

	public Paymentorder getPaymentorder(long idpaymentorder);

	public List<Paymentorderdetail> getPaymentorderdetails(long idpaymentorder);

}
