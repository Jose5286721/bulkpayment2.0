package py.com.global.spm.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

;

/**
 * 
 * @author Lino Chamorro
 * 
 */
@Entity
@Table(name = "ERRORLOG")
public class Errorlog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8445143827701456864L;
	private Long errorId;
	private Date creationdateTim;
	private String processChr;
	private String descriptionChr;

	@Id
	@Column(name = "ERROR_ID", unique = true, nullable = false, precision = 22, scale = 0)
	@NotNull
	@SequenceGenerator(name = "ERRORLOG_SEQ", sequenceName = "ERRORLOG_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "ERRORLOG_SEQ")
	public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	public Date getCreationdateTim() {
		return creationdateTim;
	}

	public void setCreationdateTim(Date creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	@Column(name = "PROCESSNAME_CHR", nullable = false)
	public String getProcessChr() {
		return processChr;
	}

	public void setProcessChr(String processChr) {
		this.processChr = processChr;
	}

	@Column(name = "DESCRIPTION_CHR", nullable = true)
	public String getDescriptionChr() {
		return descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}


	@Override
	public String toString() {
		return "Errorlog [errorId=" + errorId + ", creationdateTim="
				+ creationdateTim + ", processChr=" + processChr
				+ ", descriptionChr=" + descriptionChr + "]";
	}

}
