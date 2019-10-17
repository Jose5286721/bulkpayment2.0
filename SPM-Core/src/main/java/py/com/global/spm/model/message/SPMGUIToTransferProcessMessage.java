package py.com.global.spm.model.message;

import java.io.Serializable;

public class SPMGUIToTransferProcessMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9215908251860401671L;

	private long idpaymentorderPk;

	public long getIdpaymentorderPk() {
		return idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	@Override
	public String toString() {
		return "SPMGUIToTransferProcessMessage [idpaymentorderPk="
				+ idpaymentorderPk + "]";
	}

}
