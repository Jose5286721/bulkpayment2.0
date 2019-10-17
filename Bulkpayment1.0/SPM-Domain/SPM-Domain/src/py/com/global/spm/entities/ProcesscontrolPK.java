package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROCESSCONTROL database table.
 * 
 */
@Embeddable
public class ProcesscontrolPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SERVER_PK", unique = true, nullable = false, length = 30)
	private String serverPk;

	@Column(name = "IDPROCESS_PK", unique = true, nullable = false, precision = 4)
	private long idprocessPk;

	public ProcesscontrolPK() {
	}

	public String getServerPk() {
		return this.serverPk;
	}

	public void setServerPk(String serverPk) {
		this.serverPk = serverPk;
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
		if (!(other instanceof ProcesscontrolPK)) {
			return false;
		}
		ProcesscontrolPK castOther = (ProcesscontrolPK) other;
		return this.serverPk.equals(castOther.serverPk)
				&& (this.idprocessPk == castOther.idprocessPk);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.serverPk.hashCode();
		hash = hash * prime
				+ ((int) (this.idprocessPk ^ (this.idprocessPk >>> 32)));

		return hash;
	}

	@Override
	public String toString() {
		return "ProcesscontrolPK [serverPk=" + serverPk + ", idprocessPk="
				+ idprocessPk + "]";
	}

}