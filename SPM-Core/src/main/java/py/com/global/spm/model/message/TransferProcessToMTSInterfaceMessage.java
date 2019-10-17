package py.com.global.spm.model.message;


import py.com.global.spm.domain.entity.Logpayment;

import java.io.Serializable;

public class TransferProcessToMTSInterfaceMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3689523281669805510L;

	// OP aprobada por los firmantes
	// estado: pendiente de pago
	private Logpayment logpayment;

	private String walletChr;

	private Long idpaymentOrderPk;

	private String stateChr;


	public Logpayment getLogpayment() {
		return logpayment;
	}

	public void setLogpayment(Logpayment logpayment) {
		this.logpayment = logpayment;
	}

	public String getWalletChr() {
		return walletChr;
	}

	public void setWalletChr(String walletChr) {
		this.walletChr = walletChr;
	}

	public Long getIdpaymentOrderPk() {
		return idpaymentOrderPk;
	}

	public void setIdpaymentOrderPk(Long idpaymentOrderPk) {
		this.idpaymentOrderPk = idpaymentOrderPk;
	}

	public String getStateChr() {
		return stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	@Override
	public String toString() {
		return "TransferProcessToMTSInterfaceMessage{" +
				"logpayment=" + logpayment +
				", walletChr='" + walletChr + '\'' +
				", idpaymentOrderPk=" + idpaymentOrderPk +
				", stateChr='" + stateChr + '\'' +
				'}';
	}
}
