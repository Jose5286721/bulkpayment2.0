package py.com.global.spm.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Entity
@Table(name="ERRORLOG")
public class Errorlog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3527764745014364121L;

	@Id
	@SequenceGenerator(name = "ERRORLOG_SEQ", sequenceName = "ERRORLOG_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERRORLOG_SEQ")
	@Column(name = "ERROR_ID", unique = true, nullable = false, precision = 22)
	private Long errorId;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "PROCESSNAME_CHR", nullable = false)
	private String processChr;

	@Column(name = "DESCRIPTION_CHR", nullable = false)
	private String descriptionChr;
	
	public Errorlog() {
		super();	
	}

	public Errorlog(String processChr, String descriptionChr) {
		super();
		this.processChr = processChr;
		this.descriptionChr = descriptionChr;
	}

	public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}

	public Timestamp getCreationdateTim() {
		return creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getProcessChr() {
		return processChr;
	}

	public void setProcessChr(String processChr) {
		this.processChr = processChr;
	}

	public String getDescriptionChr() {
		return descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	@Override
	public String toString() {
		return "Errorlog [erroridChr=" + errorId + ", creationdateTim="
				+ creationdateTim + ", processChr=" + processChr
				+ ", descriptionChr=" + descriptionChr + "]";
	}

}
