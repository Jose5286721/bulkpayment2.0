package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SYSTEMPARAMETER database table.
 * 
 */
@Entity
@Table(name="SYSTEMPARAMETER")
@NamedQueries( { @NamedQuery(name = "getAllParameters", query = "SELECT s FROM Systemparameter s ORDER BY s.id.idprocessPk, s.id.parameterPk ASC") })
public class Systemparameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SystemparameterPK id;

	@Column(name="CREATIONDATE_TIM", nullable=false)
	private Timestamp creationdateTim;

	@Column(name="DESCRIPCION_CHR", length=100)
	private String descripcionChr;

	@Column(name="VALUE_CHR", nullable=false, length=30)
	private String valueChr;

	public Systemparameter() {
	}

	public SystemparameterPK getId() {
		return this.id;
	}

	public void setId(SystemparameterPK id) {
		this.id = id;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDescripcionChr() {
		return this.descripcionChr;
	}

	public void setDescripcionChr(String descripcionChr) {
		this.descripcionChr = descripcionChr;
	}

	public String getValueChr() {
		return this.valueChr;
	}

	public void setValueChr(String valueChr) {
		this.valueChr = valueChr;
	}

}