package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the COMPANY database table.
 * 
 */
@Entity
@Table(name = "COMPANY")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDCOMPANY_PK", unique = true, nullable = false, precision = 22)
	private long idcompanyPk;

	@Column(name = "COMPANYNAME_CHR", nullable = false, length = 30)
	private String companynameChr;

	@Column(name = "CONTACTNAME_CHR", nullable = false, length = 50)
	private String contactnameChr;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "DESCRIPTION_CHR", length = 200)
	private String descriptionChr;

	@Column(name = "EMAIL_CHR", length = 50)
	private String emailChr;

	@Column(name = "LASTOPDATE_TIM")
	private Timestamp lastopdateTim;

	@Column(name = "PHONENUMBER_CHR", length = 20)
	private String phonenumberChr;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	@Column(name = "TRXDAYCOUNT_NUM", nullable = false, precision = 10)
	private Long trxdaycountNum;

	@Column(name = "TRXDAYLIMIT_NUM", nullable = false, precision = 10)
	private Long trxdaylimitNum;

	@Column(name = "TRXMONTHCOUNT_NUM", nullable = false, precision = 10)
	private Long trxmonthcountNum;

	@Column(name = "TRXMONTHLIMIT_NUM", nullable = false, precision = 10)
	private Long trxmonthlimitNum;

	@Column(name = "TRXTOTALCOUNT_NUM", nullable = false, precision = 10)
	private Long trxtotalcountNum;

	@Column(name = "TRXTOTALLIMIT_NUM", nullable = false, precision = 10)
	private Long trxtotallimitNum;

	
	/**
	 * Comision para calcular la consulta de saldo.
	 *
	 */
	@Column(name = "PERCENTCOMMISSION_NUM", nullable = false)
	private Double percentcommissionNum;
	
	/*
	 * Parametros para conectarse con el MTS..
	 */
	@Column(name = "MTSFIELD3_CHR", nullable = false, length = 30)
	private String mtsfield3Chr;

	@Column(name = "MTSFIELD1_CHR", nullable = false, length = 30)
	private String mtsfield1Chr;

	@Column(name = "MTSFIELD2_CHR", nullable = true, length = 30)
	private String mtsfield2Chr;

	@Column(name = "MTSFIELD4_CHR", nullable = false, length = 30)
	private String mtsfield4Chr;

	@Column(name="MTSFIELD5_CHR", nullable= false, length = 30)
	private String mtsfield5Chr ;
	
	public Company() {
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public String getCompanynameChr() {
		return this.companynameChr;
	}

	public void setCompanynameChr(String companynameChr) {
		this.companynameChr = companynameChr;
	}

	public String getContactnameChr() {
		return this.contactnameChr;
	}

	public void setContactnameChr(String contactnameChr) {
		this.contactnameChr = contactnameChr;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	public String getEmailChr() {
		return this.emailChr;
	}

	public void setEmailChr(String emailChr) {
		this.emailChr = emailChr;
	}

	public Timestamp getLastopdateTim() {
		return this.lastopdateTim;
	}

	public void setLastopdateTim(Timestamp lastopdateTim) {
		this.lastopdateTim = lastopdateTim;
	}

	public String getPhonenumberChr() {
		return this.phonenumberChr;
	}

	public void setPhonenumberChr(String phonenumberChr) {
		this.phonenumberChr = phonenumberChr;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public Long getTrxdaycountNum() {
		return this.trxdaycountNum;
	}

	public void setTrxdaycountNum(Long trxdaycountNum) {
		this.trxdaycountNum = trxdaycountNum;
	}

	public Long getTrxdaylimitNum() {
		return this.trxdaylimitNum;
	}

	public void setTrxdaylimitNum(Long trxdaylimitNum) {
		this.trxdaylimitNum = trxdaylimitNum;
	}

	public Long getTrxmonthcountNum() {
		return this.trxmonthcountNum;
	}

	public void setTrxmonthcountNum(Long trxmonthcountNum) {
		this.trxmonthcountNum = trxmonthcountNum;
	}

	public Long getTrxmonthlimitNum() {
		return this.trxmonthlimitNum;
	}

	public void setTrxmonthlimitNum(Long trxmonthlimitNum) {
		this.trxmonthlimitNum = trxmonthlimitNum;
	}

	public Long getTrxtotalcountNum() {
		return this.trxtotalcountNum;
	}

	public void setTrxtotalcountNum(Long trxtotalcountNum) {
		this.trxtotalcountNum = trxtotalcountNum;
	}

	public Long getTrxtotallimitNum() {
		return this.trxtotallimitNum;
	}

	public void setTrxtotallimitNum(Long trxtotallimitNum) {
		this.trxtotallimitNum = trxtotallimitNum;
	}
	/**
	 * MTSPassword
	 * @return
	 */
	public String getMtsfield3Chr() {
		return mtsfield3Chr;
	}

	public void setMtsfield3Chr(String mtsfield3Chr) {
		this.mtsfield3Chr = mtsfield3Chr;
	}

	/**
	 * MTSBand
	 * @return
	 */
	public String getMtsfield1Chr() {
		return mtsfield1Chr;
	}

	public void setMtsfield1Chr(String mtsfield1Chr) {
		this.mtsfield1Chr = mtsfield1Chr;
	}

	/**
	 * MTSWalletType
	 * @return
	 */
	public String getMtsfield2Chr() {
		return mtsfield2Chr;
	}

	public void setMtsfield2Chr(String mtsfield2Chr) {
		this.mtsfield2Chr = mtsfield2Chr;
	}

	/**
	 * MTSRolbind
	 * @return
	 */
	public String getMtsfield4Chr() {
		return mtsfield4Chr;
	}

	public void setMtsfield4Chr(String mtsfield4Chr) {
		this.mtsfield4Chr = mtsfield4Chr;
	}

	/**
	 * MTSUsr
	 * @return
	 */
	public String getMtsfield5Chr() {
		return mtsfield5Chr;
	}

	public void setMtsfield5Chr(String mtsfield5Chr) {
		this.mtsfield5Chr = mtsfield5Chr;
	}

	
	
	public Double getPercentcommissionNum() {
		return percentcommissionNum;
	}

	public void setPercentcommissionNum(Double percentcommissionNum) {
		this.percentcommissionNum = percentcommissionNum;
	}
	
	

	@Override
	public String toString() {
		return "Company [idcompanyPk=" + idcompanyPk + ", companynameChr="
				+ companynameChr + ", contactnameChr=" + contactnameChr
				+ ", creationdateTim=" + creationdateTim + ", descriptionChr="
				+ descriptionChr + ", emailChr=" + emailChr
				+ ", lastopdateTim=" + lastopdateTim + ", phonenumberChr="
				+ phonenumberChr + ", stateChr=" + stateChr
				+ ", trxdaycountNum=" + trxdaycountNum + ", trxdaylimitNum="
				+ trxdaylimitNum + ", trxmonthcountNum=" + trxmonthcountNum
				+ ", trxmonthlimitNum=" + trxmonthlimitNum
				+ ", trxtotalcountNum=" + trxtotalcountNum
				+ ", trxtotallimitNum=" + trxtotallimitNum
				+ ", percentcommissionNum=" + percentcommissionNum
				+ ", mtsfield3Chr=" + mtsfield3Chr + ", mtsfield1Chr="
				+ mtsfield1Chr + ", mtsfield2Chr=" + mtsfield2Chr
				+ ", mtsfield4Chr=" + mtsfield4Chr + ", mtsfield5Chr="
				+ mtsfield5Chr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idcompanyPk ^ (idcompanyPk >>> 32));
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
		Company other = (Company) obj;
		if (idcompanyPk != other.idcompanyPk)
			return false;
		return true;
	}


}