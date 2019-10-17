package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;

import py.com.global.spm.domain.utils.CryptoUtils;

/**
 * The persistent class for the DRAFTDETAIL database table.
 * 
 */
@Entity
@Table(name = "DRAFTDETAIL")
public class Draftdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DraftdetailPK id;

	@Transient
	private Double amountNum;
	
	@Column(name = "AMOUNT_CHR", nullable = false, length=48)
	private String amountChr;
	
	public String getAmountChr() {
		return this.amountChr;
	}

	public void setAmountChr(String amountChr) {
		this.amountChr = amountChr;
	}

	public Draftdetail() {
	}

	public DraftdetailPK getId() {
		return this.id;
	}

	public void setId(DraftdetailPK id) {
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
	public String toString() {
		return "Draftdetail [id=" + id + ", amountNum=" + getAmountNum() + "]";
	}

}
