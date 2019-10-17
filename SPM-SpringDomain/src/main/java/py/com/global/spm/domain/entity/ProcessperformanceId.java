package py.com.global.spm.domain.entity;

// Generated 15/07/2013 09:59:46 AM by Hibernate Tools 3.4.0.CR1

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ProcessperformanceId generated by hbm2java
 */
@Embeddable
public class ProcessperformanceId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serverPk;
	private String remotePk;
	private short idprocessPk;

	public ProcessperformanceId() {
	}

	public ProcessperformanceId(String serverPk, String remotePk,
			short idprocessPk) {
		this.serverPk = serverPk;
		this.remotePk = remotePk;
		this.idprocessPk = idprocessPk;
	}

	@Column(name = "SERVER_PK", nullable = false, length = 30)
	@NotNull
	@Length(max = 30)
	public String getServerPk() {
		return this.serverPk;
	}

	public void setServerPk(String serverPk) {
		this.serverPk = serverPk;
	}

	@Column(name = "REMOTE_PK", nullable = false, length = 30)
	@NotNull
	@Length(max = 30)
	public String getRemotePk() {
		return this.remotePk;
	}

	public void setRemotePk(String remotePk) {
		this.remotePk = remotePk;
	}

	@Column(name = "IDPROCESS_PK", nullable = false, precision = 4, scale = 0)
	public short getIdprocessPk() {
		return this.idprocessPk;
	}

	public void setIdprocessPk(short idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProcessperformanceId))
			return false;
		ProcessperformanceId castOther = (ProcessperformanceId) other;

		return ((this.getServerPk() == castOther.getServerPk()) || (this
				.getServerPk() != null && castOther.getServerPk() != null && this
				.getServerPk().equals(castOther.getServerPk())))
				&& ((this.getRemotePk() == castOther.getRemotePk()) || (this
						.getRemotePk() != null
						&& castOther.getRemotePk() != null && this
						.getRemotePk().equals(castOther.getRemotePk())))
				&& (this.getIdprocessPk() == castOther.getIdprocessPk());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getServerPk() == null ? 0 : this.getServerPk().hashCode());
		result = 37 * result
				+ (getRemotePk() == null ? 0 : this.getRemotePk().hashCode());
		result = 37 * result + this.getIdprocessPk();
		return result;
	}

}