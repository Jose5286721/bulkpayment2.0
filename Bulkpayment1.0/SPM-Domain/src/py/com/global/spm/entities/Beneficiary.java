package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the BENEFICIARY database table.
 * 
 */
@Entity
@Table(name = "BENEFICIARY")
public class Beneficiary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BENEFICIARY_SEQ_GEN",sequenceName="BENEFICIARY_SEQ",allocationSize=0)
	@GeneratedValue(generator="BENEFICIARY_SEQ_GEN")
	@Column(name = "IDBENEFICIARY_PK", unique = true, nullable = false, precision = 22)
	private long idbeneficiaryPk;

	@Column(name = "BENEFICIARYLASTNAME_CHR", length = 50)
	private String beneficiarylastnameChr;

	@Column(name = "BENEFICIARYNAME_CHR", nullable = false, length = 50)
	private String beneficiarynameChr;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "EMAIL_CHR", length = 50)
	private String emailChr;

	@Column(name = "PHONENUMBER_CHR", nullable = false, length = 20)
	private String phonenumberChr;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	@Column(name = "GENERICO1_CHR", nullable = true, length = 500)
	private String generico1Chr;
	
	@Column(name = "GENERICO2_CHR", nullable = true, length = 500)
	private String generico2Chr;
	
	@Column(name = "GENERICO3_CHR", nullable = true, length = 500)
	private String generico3Chr;
	
	@Column(name = "NOTIFYSMS_NUM", nullable = false, precision = 1)
	private Boolean notifysmsNum;

	@Column(name = "IDCOMPANY_PK", nullable = false, precision = 22)
	private long idcompanyPk;
	
	@Column(name = "ROLSP_CHR")
	private String rolspChr;
	
	@Column(name = "WALLET_CHR")
	private String walletChr;
	
	@Column(name = "CURRENCY_CHR")
	private String currencyChr;
	
	@Column(name = "SUBSCRIBERCI_CHR", length = 20)
	private String subscriberciChr;

	public Boolean getNotifysmsNum() {
		return notifysmsNum;
	}

	public void setNotifysmsNum(Boolean notifysmsNum) {
		this.notifysmsNum = notifysmsNum;
	}

	public Boolean getNotifyemailNum() {
		return notifyemailNum;
	}

	public void setNotifyemailNum(Boolean notifyemailNum) {
		this.notifyemailNum = notifyemailNum;
	}

	@Column(name = "NOTIFYEMAIL_NUM", nullable = true, precision = 1)
	private Boolean notifyemailNum;

	public Beneficiary() {
	}

	public long getIdbeneficiaryPk() {
		return this.idbeneficiaryPk;
	}

	public void setIdbeneficiaryPk(long idbeneficiaryPk) {
		this.idbeneficiaryPk = idbeneficiaryPk;
	}

	public String getBeneficiarylastnameChr() {
		return this.beneficiarylastnameChr;
	}

	public void setBeneficiarylastnameChr(String beneficiarylastnameChr) {
		this.beneficiarylastnameChr = beneficiarylastnameChr;
	}

	public String getBeneficiarynameChr() {
		return this.beneficiarynameChr;
	}

	public void setBeneficiarynameChr(String beneficiarynameChr) {
		this.beneficiarynameChr = beneficiarynameChr;
	}


	public String getGenerico1Chr() {
		return this.generico1Chr;
	}

	public void setGenerico1(String generico1Chr) {
		this.generico1Chr = generico1Chr;
	}

	public String getGenerico2Chr() {
		return this.generico2Chr;
	}

	public void setGenerico2(String generico2Chr) {
		this.generico2Chr = generico2Chr;
	}
	public String getGenerico3Chr() {
		return this.generico3Chr;
	}

	public void setGenerico3(String generico3Chr) {
		this.generico3Chr = generico3Chr;
	}
	
	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getEmailChr() {
		return this.emailChr;
	}

	public void setEmailChr(String emailChr) {
		this.emailChr = emailChr;
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

	public long getIdcompanyPk() {
		return idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}
	
	public String getRolspChr() {
		return rolspChr;
	}

	public void setRolspChr(String rolspChr) {
		this.rolspChr = rolspChr;
	}

	public String getWalletChr() {
		return walletChr;
	}

	public void setWalletChr(String walletChr) {
		this.walletChr = walletChr;
	}

	public String getCurrencyChr() {
		return currencyChr;
	}

	public void setCurrencyChr(String currencyChr) {
		this.currencyChr = currencyChr;
	}
	
	public String getSubscriberciChr() {
		return subscriberciChr;
	}

	public void setSubscriberciChr(String subscriberciChr) {
		this.subscriberciChr = subscriberciChr;
	}

	@Override
	public String toString() {
		return "Beneficiary [idbeneficiaryPk=" + idbeneficiaryPk
				+ ", beneficiarylastnameChr=" + beneficiarylastnameChr
				+ ", beneficiarynameChr=" + beneficiarynameChr
				+ ", creationdateTim=" + creationdateTim + ", emailChr="
				+ emailChr + ", phonenumberChr=" + phonenumberChr
				+ ", stateChr=" + stateChr + ", notifysmsNum=" + notifysmsNum
				+ ", idcompanyPk=" + idcompanyPk + ", rolspChr=" + rolspChr
				+ ", walletChr=" + walletChr + ", currencyChr=" + currencyChr
				+ ", generico1Chr=" + generico1Chr + ", generico2Chr=" + generico2Chr + ", generico3Chr=" + generico3Chr
				+ ", notifyemailNum=" + notifyemailNum + "]";
	}

	

}