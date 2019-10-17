package py.com.global.spm.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EVENTCODE database table.
 * 
 */
@Entity
@Table(name="EVENTCODE")
public class Eventcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EventcodePK id;

	@Column(name="DESCRIPTION_CHR", length=100)
	private String descriptionChr;

	public Eventcode() {
	}

	public EventcodePK getId() {
		return this.id;
	}

	public void setId(EventcodePK id) {
		this.id = id;
	}

	public String getDescriptionChr() {
		return this.descriptionChr;
	}

	public void setDescriptionChr(String descriptionChr) {
		this.descriptionChr = descriptionChr;
	}

}