package py.com.global.spm.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the PROCESSCONTROL database table.
 * 
 */
@Entity
@Table(name = "PROCESSCONTROL")
public class Processcontrol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9088677811486511371L;

	@EmbeddedId
	private ProcesscontrolPK id;

	@Column(name = "CHANGEDATE_TIM")
	private Timestamp changedateTim;

	@Column(name = "CREATIONDATE_TIM")
	private Timestamp creationdateTim;

	@Column(length = 30)
	private String descripcion;

	@Column(precision = 22)
	private Long running;

	@Column(precision = 22)
	private Long runnit;

	public Processcontrol() {
	}

	public ProcesscontrolPK getId() {
		return this.id;
	}

	public void setId(ProcesscontrolPK id) {
		this.id = id;
	}

	public Timestamp getChangedateTim() {
		return this.changedateTim;
	}

	public void setChangedateTim(Timestamp changedateTim) {
		this.changedateTim = changedateTim;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getRunning() {
		return this.running;
	}

	public void setRunning(Long running) {
		this.running = running;
	}

	public long getRunnit() {
		return this.runnit;
	}

	public void setRunnit(Long runnit) {
		this.runnit = runnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Processcontrol other = (Processcontrol) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Processcontrol [id=" + id + ", changedateTim=" + changedateTim
				+ ", creationdateTim=" + creationdateTim + ", descripcion="
				+ descripcion + "]";
	}

}