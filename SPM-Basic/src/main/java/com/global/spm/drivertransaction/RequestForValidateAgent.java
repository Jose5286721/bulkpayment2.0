package com.global.spm.drivertransaction;

public class RequestForValidateAgent {
	private Long sessionId = null;
	private String serviceName = null;
	private String account = null;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestValidateAgent [");
		if (sessionId != null) {
			builder.append("sessionId=");
			builder.append(sessionId);
			builder.append(", ");
		}
		if (serviceName != null) {
			builder.append("serviceName=");
			builder.append(serviceName);
			builder.append(", ");
		}
		if (account != null) {
			builder.append("account=");
			builder.append(account);
		}
		builder.append("]");
		return builder.toString();
	}

}
