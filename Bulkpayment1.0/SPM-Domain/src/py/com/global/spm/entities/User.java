package py.com.global.spm.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * The persistent class for the "USER" database table.
 * 
 */
@Entity
@Table(name = "USERS")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "getUserByPhoneNumber", query = "SELECT u FROM User u WHERE u.phonenumberChr = :phonenumber") })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDUSER_PK", unique = true, nullable = false, precision = 22)
	private long iduserPk;

	@Column(name = "ATTEMPT_NUM", nullable = false, precision = 1)
	private Long attemptNum;

	@Column(name = "CODIGOEQUIPOHUMANO_NUM", precision = 5)
	private Integer codigoequipohumanoNum;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "EMAIL_CHR", length = 100, unique = true)
	private String emailChr;

	//@Column(name = "IDCOMPANY_PK", precision = 22)
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
	@JoinTable(
			name = "USER_COMPANY",
			joinColumns = { @JoinColumn(name = "IDUSER_PK") },
			inverseJoinColumns = { @JoinColumn(name = "IDCOMPANY_PK") }
	)
	private Set<Company> idcompanyPk;

	@Column(name = "PASSWORD_CHR", nullable = false, length = 255)
	private String passwordChr;

	@Column(name = "PHONENUMBER_CHR", length = 20, unique = false)
	private String phonenumberChr;

	@Lob
	private byte[] publickey;

	@Column(name = "SMSPIN_CHR", length = 10)
	private String smspinChr;

	@Column(name = "SMSSIGN_NUM", nullable = false, precision = 1)
	private Boolean smssignNum;

	@Column(name = "STATE_CHR", nullable = false, length = 1)
	private String stateChr;

	@Column(name = "USERNAME_CHR", nullable = false, length = 30)
	private String usernameChr;

	@Column(name = "NOTIFYSMS_NUM", nullable = false, precision = 1)
	private Boolean notifysmsNum;

	@Column(name = "NOTIFYEMAIL_NUM", nullable = true, precision = 1)
	private Boolean notifyemailNum;

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

	public User() {
	}

	public long getIduserPk() {
		return this.iduserPk;
	}

	public void setIduserPk(long iduserPk) {
		this.iduserPk = iduserPk;
	}

	public Long getAttemptNum() {
		return this.attemptNum;
	}

	public void setAttemptNum(Long attemptNum) {
		this.attemptNum = attemptNum;
	}

	public Integer getCodigoequipohumanoNum() {
		return this.codigoequipohumanoNum;
	}

	public void setCodigoequipohumanoNum(Integer codigoequipohumanoNum) {
		this.codigoequipohumanoNum = codigoequipohumanoNum;
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


	public Set<Company> getIdcompanyPk() {
		return idcompanyPk;
	}

	public void setIdcompanyPk(Set<Company> idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public String getPasswordChr() {
		return this.passwordChr;
	}

	public void setPasswordChr(String passwordChr) {
		this.passwordChr = passwordChr;
	}

	public String getPhonenumberChr() {
		return this.phonenumberChr;
	}

	public void setPhonenumberChr(String phonenumberChr) {
		this.phonenumberChr = phonenumberChr;
	}

	public byte[] getPublickey() {
		return this.publickey;
	}

	public void setPublickey(byte[] publickey) {
		this.publickey = publickey;
	}

	public String getSmspinChr() {
		return this.smspinChr;
	}

	public void setSmspinChr(String smspinChr) {
		this.smspinChr = smspinChr;
	}

	public Boolean getSmssignNum() {
		return this.smssignNum;
	}

	public void setSmssignNum(Boolean smssignNum) {
		this.smssignNum = smssignNum;
	}

	public String getStateChr() {
		return this.stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	public String getUsernameChr() {
		return this.usernameChr;
	}

	public void setUsernameChr(String usernameChr) {
		this.usernameChr = usernameChr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (iduserPk ^ (iduserPk >>> 32));
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
		User other = (User) obj;
		if (iduserPk != other.iduserPk)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [iduserPk=" + iduserPk + ", attemptNum=" + attemptNum
				+ ", codigoequipohumanoNum=" + codigoequipohumanoNum
				+ ", creationdateTim=" + creationdateTim + ", emailChr="
				+ emailChr + ", idcompanyPk=" + idcompanyPk + ", passwordChr="
				+ passwordChr + ", phonenumberChr=" + phonenumberChr
				+ ", smspinChr=" + smspinChr + ", smssignNum=" + smssignNum
				+ ", stateChr=" + stateChr + ", usernameChr=" + usernameChr
				+ ", notifysmsNum=" + notifysmsNum + ", notifyemailNum="
				+ notifyemailNum + "]";
	}

}