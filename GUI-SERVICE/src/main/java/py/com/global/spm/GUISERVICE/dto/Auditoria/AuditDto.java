package py.com.global.spm.GUISERVICE.dto.Auditoria;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class AuditDto implements Serializable{
	/**
	 * Dto para mostrar Auditoria
	 */

	private static final long serialVersionUID = 2769618209365576378L;
//	private Date requestTime;
//	private String requestUrl;
	private String ip;
	private String usernameChr;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fechaCreacion;
	private String params;
	private String accessType;
	private String description;


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAccessType() { return accessType; }

	public void setAccessType(String accessType) { this.accessType = accessType; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public String getUsernameChr() { return usernameChr; }

	public void setUsernameChr(String usernameChr) { this.usernameChr = usernameChr; }

	public Date getFechaCreacion() { return fechaCreacion; }

	public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

	public String getParams() { return params; }

	public void setParams(String params) { this.params = params; }

	@Override
	public String toString() {
		return "AuditDto{" +
				"ip='" + ip + '\'' +
				", usernameChr='" + usernameChr + '\'' +
				", fechaCreacion=" + fechaCreacion +
				", params='" + params + '\'' +
				", accessType='" + accessType + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
