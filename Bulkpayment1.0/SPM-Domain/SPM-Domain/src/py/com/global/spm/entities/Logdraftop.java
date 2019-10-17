package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the LOGDRAFTOP database table.
 * 
 */
@Entity
@Table(name = "LOGDRAFTOP")
public class Logdraftop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOGDRAFTOP_IDDRAFOPPK_GENERATOR", sequenceName = "LOGDRAFTOP_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGDRAFTOP_IDDRAFOPPK_GENERATOR")
	@Column(name = "IDDRAFOP_PK", unique = true, nullable = false, precision = 22)
	private long iddrafopPk;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "IDCOMPANY_PK", nullable = false, precision = 22)
	private long idcompanyPk;

	@Column(name = "IDDRAFT_PK", nullable = false, precision = 22)
	private long iddraftPk;

	@Column(name = "IDEVENTCODE_NUM", nullable = false, precision = 5)
	private long ideventcodeNum;

	@Column(name = "IDPAYMENTORDER_PK", precision = 22)
	private Long idpaymentorderPk;

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4)
	private long idprocessPk;

	public Logdraftop() {
	}

	public long getIddrafopPk() {
		return this.iddrafopPk;
	}

	public void setIddrafopPk(long iddrafopPk) {
		this.iddrafopPk = iddrafopPk;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public long getIdcompanyPk() {
		return this.idcompanyPk;
	}

	public void setIdcompanyPk(long idcompanyPk) {
		this.idcompanyPk = idcompanyPk;
	}

	public long getIddraftPk() {
		return this.iddraftPk;
	}

	public void setIddraftPk(long iddraftPk) {
		this.iddraftPk = iddraftPk;
	}

	public long getIdeventcodeNum() {
		return this.ideventcodeNum;
	}

	public void setIdeventcodeNum(long ideventcodeNum) {
		this.ideventcodeNum = ideventcodeNum;
	}

	public long getIdpaymentorderPk() {
		return this.idpaymentorderPk;
	}

	public void setIdpaymentorderPk(long idpaymentorderPk) {
		this.idpaymentorderPk = idpaymentorderPk;
	}

	public long getIdprocessPk() {
		return this.idprocessPk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (iddrafopPk ^ (iddrafopPk >>> 32));
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
		Logdraftop other = (Logdraftop) obj;
		if (iddrafopPk != other.iddrafopPk)
			return false;
		return true;
	}

	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	@Override
	public String toString() {
		return "Logdraftop [iddrafopPk=" + iddrafopPk + ", creationdateTim="
				+ creationdateTim + ", idcompanyPk=" + idcompanyPk
				+ ", iddraftPk=" + iddraftPk + ", ideventcodeNum="
				+ ideventcodeNum + ", idpaymentorderPk=" + idpaymentorderPk
				+ ", idprocessPk=" + idprocessPk + "]";
	}

}