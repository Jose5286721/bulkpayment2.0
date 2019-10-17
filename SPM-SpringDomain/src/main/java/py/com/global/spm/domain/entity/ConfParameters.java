package py.com.global.spm.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import py.com.global.spm.domain.audit.EntityAuditEventListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "CONFPARAMETERS")
@EntityListeners(EntityAuditEventListener.class)
public class ConfParameters implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String value;
	@JsonIgnore
	private byte[] template;
	private String fileType;

	public ConfParameters() {

	}

	public ConfParameters(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Id
	@Column(name = "KEY_PK", nullable = false, length = 200)
	@NotNull
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VALUE_CHR", nullable = false, length = 500)
	@NotNull
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Lob
	@Column(name = "TEMPLATE")
	public byte[] getTemplate() {
		return this.template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}

	@Column(name = "FILETYPE_CHR")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "ConfParameters{" +
				"key='" + key + '\'' +
				", value='" + value + '\'' +
				", fileType='" + fileType + '\'' +
				'}';
	}
}
