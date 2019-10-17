package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PAYMENTORDERDETAIL database table.
 * 
 */
@Embeddable
public class PaymentorderdetailPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "IDPAYMENTORDER_PK", unique = true, nullable = false, precision = 22)
	private long idpaymentorderPk;

	@Column(name = "IDBENEFICIARY_PK", unique = true, nullable = false, precision = 22)
	private long idbeneficiaryPk;

	public PaymentorderdetailPK() {
	}

	public PaymentorderdetailPK(long idpaymentorderPk, long idbeneficiaryPk) {
		super();
		this.idpaymentorderPk = idpaymentorderPk;
		this.idbeneficiaryPk = idbeneficiaryPk;
	}

	public long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public long getIdbeneficiaryPk() {
		return this.idbeneficiaryPk;
	}

	public void setIdbeneficiaryPk(long idbeneficiaryPk) {
		this.idbeneficiaryPk = idbeneficiaryPk;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PaymentorderdetailPK)) {
			return false;
		}
		PaymentorderdetailPK castOther = (PaymentorderdetailPK) other;
		return (this.idpaymentorderPk == castOther.idpaymentorderPk)
				&& (this.idbeneficiaryPk == castOther.idbeneficiaryPk);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash
				* prime
				+ ((int) (this.idpaymentorderPk ^ (this.idpaymentorderPk >>> 32)));
		hash = hash
				* prime
				+ ((int) (this.idbeneficiaryPk ^ (this.idbeneficiaryPk >>> 32)));

		return hash;
	}

	@Override
	public String toString() {
		return "PaymentorderdetailPK [idpaymentorderPk=" + idpaymentorderPk
				+ ", idbeneficiaryPk=" + idbeneficiaryPk + "]";
	}

}