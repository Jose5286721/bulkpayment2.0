package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SYSTEMPARAMETER database table.
 * 
 */
@Embeddable
public class SystemparameterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PARAMETER_PK", unique=true, nullable=false, length=50)
	private String parameterPk;

	@Column(name="IDPROCESS_PK", unique=true, nullable=false, precision=4)
	private long idprocessPk;

	public SystemparameterPK() {
	}
	
	
	
	public SystemparameterPK(long idprocessPk, String parameterPk) {
		super();
		this.parameterPk = parameterPk;
		this.idprocessPk = idprocessPk;
	}



	public String getParameterPk() {
		return this.parameterPk;
	}
	public void setParameterPk(String parameterPk) {
		this.parameterPk = parameterPk;
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
		if (!(other instanceof SystemparameterPK)) {
			return false;
		}
		SystemparameterPK castOther = (SystemparameterPK)other;
		return 
			this.parameterPk.equals(castOther.parameterPk)
			&& (this.idprocessPk == castOther.idprocessPk);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.parameterPk.hashCode();
		hash = hash * prime + ((int) (this.idprocessPk ^ (this.idprocessPk >>> 32)));
		
		return hash;
	}
}