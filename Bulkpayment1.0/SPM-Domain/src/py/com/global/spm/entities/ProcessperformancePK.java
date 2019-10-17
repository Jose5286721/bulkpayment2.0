package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROCESSPERFORMANCE database table.
 * 
 */
@Embeddable
public class ProcessperformancePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SERVER_PK", unique=true, nullable=false, length=30)
	private String serverPk;

	@Column(name="REMOTE_PK", unique=true, nullable=false, length=30)
	private String remotePk;

	@Column(name="IDPROCESS_PK", unique=true, nullable=false, precision=4)
	private long idprocessPk;

	public ProcessperformancePK() {
	}
	public String getServerPk() {
		return this.serverPk;
	}
	public void setServerPk(String serverPk) {
		this.serverPk = serverPk;
	}
	public String getRemotePk() {
		return this.remotePk;
	}
	public void setRemotePk(String remotePk) {
		this.remotePk = remotePk;
	}
	public long getIdprocessPk() {
		return this.idprocessPk;
	}
	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProcessperformancePK)) {
			return false;
		}
		ProcessperformancePK castOther = (ProcessperformancePK)other;
		return 
			this.serverPk.equals(castOther.serverPk)
			&& this.remotePk.equals(castOther.remotePk)
			&& (this.idprocessPk == castOther.idprocessPk);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.serverPk.hashCode();
		hash = hash * prime + this.remotePk.hashCode();
		hash = hash * prime + ((int) (this.idprocessPk ^ (this.idprocessPk >>> 32)));
		
		return hash;
	}
}