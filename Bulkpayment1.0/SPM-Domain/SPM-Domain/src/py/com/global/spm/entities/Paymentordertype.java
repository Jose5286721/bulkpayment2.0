package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PAYMENTORDERTYPE database table.
 * 
 */
@Entity
@Table(name="PAYMENTORDERTYPE")
public class Paymentordertype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDPAYMENTORDERTYPE_PK", unique=true, nullable=false, precision=22)
	private long idpaymentordertypePk;

	@Column(name="DESCRIPTION_CHR", length=100)
	private String descriptionChr;

	@Column(name="NAME_CHR", nullable=false, length=30)
	private String nameChr;

	public Paymentordertype() {
	}

	public long getIdpaymentordertypePk() {
		return this.idpaymentordertypePk;
	}

	public void setIdpaymentordertypePk(long idorderpaymenttypePk) {
		this.idpaymentordertypePk = idorderpaymenttypePk;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	public String getNameChr() {
		return this.nameChr;
	}

	public void setNameChr(String nameChr) {
		this.nameChr = nameChr;
	}

}