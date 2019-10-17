package py.com.global.spm.model.util;

import java.io.Serializable;

/**
 * 
 * 
 * @author Lino Chamorro
 * 
 */
public class PaymentorderdetailWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7969504513413124572L;

	public PaymentorderdetailWrapper(String beneficiaryNumberStr,
			String amountStr) {
		super();
		this.beneficiaryNumberStr = beneficiaryNumberStr;
		this.amountStr = amountStr;
	}

	private String beneficiaryNumberStr;
	private String amountStr;

	public String getBeneficiaryNumberStr() {
		return beneficiaryNumberStr;
	}

	public void setBeneficiaryNumberStr(String beneficiaryNumberStr) {
		this.beneficiaryNumberStr = beneficiaryNumberStr;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

}
