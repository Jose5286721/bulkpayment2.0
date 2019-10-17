package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;

import py.com.global.spm.domain.utils.CryptoUtils;

/**
 * The persistent class for the PAYMENTORDERDETAIL database table.
 * 
 */
@Entity
@Table(name = "PAYMENTORDERDETAIL")
@NamedQueries({ @NamedQuery(name = "getPaymentorderdetailByIdPaymentorder", query = "SELECT pd FROM Paymentorderdetail pd WHERE pd.id.idpaymentorderPk = :idpaymentorder") })
public class Paymentorderdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PaymentorderdetailPK id;

	@Transient
	private Double amountNum;
	
	@Column(name = "AMOUNT_CHR", nullable = false, length=48)
	private String amountChr;
	
	public String getAmountChr() {
		return this.amountChr;
	}

	public Paymentorderdetail() {
	}

	public Paymentorderdetail(long idpaymentorderPk, long idbeneficiaryPk) {
		id = new PaymentorderdetailPK(idpaymentorderPk, idbeneficiaryPk);
	}

	public PaymentorderdetailPK getId() {
		return this.id;
	}

	public void setId(PaymentorderdetailPK id) {
		this.id = id;
	}

	public Double getAmountNum() {
		CryptoUtils cu = new CryptoUtils();
		return cu.desencriptarMonto(this.getAmountChr());
	}

	public void setAmountNum(Double amountNum) {
		CryptoUtils cu = new CryptoUtils();
		this.amountChr = cu.encriptarMonto(amountNum);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paymentorderdetail other = (Paymentorderdetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Paymentorderdetail [id=" + id + ", amountNum=" + getAmountNum()
				+ "]";
	}

	public String toStringAtGlance() {
		return "idpaymentorderPk=" + id.getIdpaymentorderPk()
				+ ", idbeneficiaryPk=" + id.getIdbeneficiaryPk()
				+ ", amountNum=" + getAmountNum();
	}

}
