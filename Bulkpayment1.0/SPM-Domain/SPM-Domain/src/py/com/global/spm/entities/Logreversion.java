package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the LOGMTS database table.
 * 
 */
@Entity
@Table(name="LOGREVERSION")
public class Logreversion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOGREVERSIONPK_GENERATOR", sequenceName="LOGREVERSION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOGREVERSIONPK_GENERATOR")
	@Column(name="IDLOGREVERSION_PK", unique=true, nullable=false, precision=22)
	private long idlogreversionPk;

	@Column(name="IDEVENTCODE_NUM")
	private long ideventcodeNum;

	@Column(name="CREATIONDATE_TIM")
	private Timestamp creationdateTim;
	
	@Column(name="IDPROCESS_PK")
	private long idprocessPk;

	@Column(name="IDCOMPANY_PK")
	private long idcompanyPk;
	
	@Column(name="IDPAYMENTORDER_PK")
	private long idpaymentorderPk;
	
	@Column(name = "IDUSER_PK")
	private long iduserPk;
	
	@Column(name="STATE_CHR")
	private String stateChr;
	
	public Logreversion() {
	}

	public long getIdlogreversionPk() {
		return idlogreversionPk;
	}

	public void setIdlogreversionPk(long idlogreversionPk) {
		this.idlogreversionPk = idlogreversionPk;
	}

	public long getIdeventcodeNum() {
		return ideventcodeNum;
	}

	public void setIdeventcodeNum(long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public Timestamp getCreationdateTim() {
		return creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public long getIdprocessPk() {
		return idprocessPk;
	}

	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public long getIdcompanyPk() {
		return idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIdpaymentorderPk() {
		return idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public long getIduserPk() {
		return iduserPk;
	}

	public void setIduserPk(long iduserPk) {
		this.iduserPk = iduserPk;
	}

	public String getStateChr() {
		return stateChr;
	}

	public void setStateChr(String stateChr) {
		this.stateChr = stateChr;
	}

	@Override
	public String toString() {
		return "Logreversion [idlogreversionPk=" + idlogreversionPk
				+ ", ideventcodeNum=" + ideventcodeNum + ", creationdateTim="
				+ creationdateTim + ", idprocessPk=" + idprocessPk
				+ ", idcompanyPk=" + idcompanyPk + ", idpaymentorderPk="
				+ idpaymentorderPk + ", iduserPk=" + iduserPk + ", stateChr="
				+ stateChr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idlogreversionPk ^ (idlogreversionPk >>> 32));
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
		Logreversion other = (Logreversion) obj;
		if (idlogreversionPk != other.idlogreversionPk)
			return false;
		return true;
	}

	
	
	

}