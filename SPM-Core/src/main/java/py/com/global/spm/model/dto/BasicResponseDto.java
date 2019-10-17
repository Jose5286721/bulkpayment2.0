package py.com.global.spm.model.dto;

import java.io.Serializable;

public class BasicResponseDto implements Serializable {

	private static final long serialVersionUID = 7118751786696474433L;

	private String statusCode;
	private String description;
	private Boolean status;

	public BasicResponseDto(String statusCode, String description, Boolean status) {
		this.statusCode = statusCode;
		this.description = description;
		this.status = status;
	}

	public BasicResponseDto() {

	}

	public void setBasicResponseFalse(BasicResponseDto basicResponseDto){
		this.setStatusCode(basicResponseDto.getStatusCode());
		this.setDescription(basicResponseDto.getDescription());
		this.setStatus(false);
	}

	public void setBasicResponseTrue(BasicResponseDto basicResponseDto){
		this.setStatusCode(basicResponseDto.getStatusCode());
		this.setDescription(basicResponseDto.getDescription());
		this.setStatus(true);
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String toStringBasicResponse() {
		return "{description='" + description + '\'' + ", statusCode='" + statusCode + '\'' + ", status=" + status
				+ '}';
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BasicResponse [");
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (statusCode != null) {
			builder.append("statusCode=");
			builder.append(statusCode);
			builder.append(", ");
		}
		if (description != null) {
			builder.append("description=");
			builder.append(description);
		}
		builder.append("]");
		return builder.toString();
	}

}
