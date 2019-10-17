package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PROCESS database table.
 * 
 */
@Entity
@Table(name="PROCESS")
@NamedQueries( { @NamedQuery(name = "getAllProcess", query = "SELECT p FROM Process p ORDER BY p.idprocessPk ASC") })
public class Process implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDPROCESS_PK", unique=true, nullable=false, precision=4)
	private long idprocessPk;

	@Column(name="DESCRIPTION_CHR", length=100)
	private String descriptionChr;

	@Column(name="PROCESSNAME_CHR", nullable=false, length=20)
	private String processnameChr;

	public Process() {
	}

	public long getIdprocessPk() {
		return this.idprocessPk;
	}

	public void setIdprocessPk(long idprocessPk) {
		this.idprocessPk = idprocessPk;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	public String getProcessnameChr() {
		return this.processnameChr;
	}

	public void setProcessnameChr(String processnameChr) {
		this.processnameChr = processnameChr;
	}

}