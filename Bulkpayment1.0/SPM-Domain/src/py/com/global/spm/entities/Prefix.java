package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the PREFIX database table.
 * 
 * @author Lino Chamorro
 */
@Entity
@Table(name = "PREFIX")
@NamedQueries( { @NamedQuery(name = "getAllPrefixes", query = "SELECT p FROM Prefix p") })
public class Prefix implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PREFIX_PK", unique = true, nullable = false, length = 10)
	private String prefixPk;

	@Column(name = "CREATIONDATE_TIM", nullable = false)
	private Timestamp creationdateTim;

	@Column(name = "DESCRIPTION_CHR", length = 100)
	private String descriptionChr;

	@Column(name = "LENGTH_NUM", nullable = false, precision = 2)
	private Integer lengthNum;

	@Column(name = "NEWPREFIX_CHR", nullable = false, length = 10)
	private String newprefixChr;

	public Prefix() {
	}

	public String getPrefixPk() {
		return this.prefixPk;
	}

	public void setPrefixPk(String prefixPk) {
		this.prefixPk = prefixPk;
	}

	public Timestamp getCreationdateTim() {
		return this.creationdateTim;
	}

	public void setCreationdateTim(Timestamp creationdateTim) {
		this.creationdateTim = creationdateTim;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

	public Integer getLengthNum() {
		return this.lengthNum;
	}

	public void setLengthNum(Integer lengthNum) {
		this.lengthNum = lengthNum;
	}

	public String getNewprefixChr() {
		return this.newprefixChr;
	}

	public void setNewprefixChr(String newprefixChr) {
		this.newprefixChr = newprefixChr;
	}

	@Override
	public String toString() {
		return "Prefix [creationdateTim=" + creationdateTim
				+ ", descriptionChr=" + descriptionChr + ", lengthNum="
				+ lengthNum + ", newprefixChr=" + newprefixChr + ", prefixPk="
				+ prefixPk + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((prefixPk == null) ? 0 : prefixPk.hashCode());
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
		Prefix other = (Prefix) obj;
		if (prefixPk == null) {
			if (other.prefixPk != null)
				return false;
		} else if (!prefixPk.equals(other.prefixPk))
			return false;
		return true;
	}

}