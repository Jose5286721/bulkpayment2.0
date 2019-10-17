package com.global.spm.drivertransaction;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RequestForValidatePin {
	@JsonIgnore
	private Long sessionId = null;
	@JsonIgnore
	private String serviceName = null;
	private String account = null;
	private String pin = null;
	private Boolean agent = false;

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

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Boolean getAgent() {
		return agent;
	}

	public void setAgent(Boolean agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestValidatePin [");
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
			builder.append(", ");
		}
		if (pin != null) {
			builder.append("pin=");
			builder.append(pin);
			builder.append(", ");
		}
		if (agent != null) {
			builder.append("agent=");
			builder.append(agent);
		}
		builder.append("]");
		return builder.toString();
	}

}
