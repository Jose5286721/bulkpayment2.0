package com.global.spm.notificationmanager;

import com.global.spm.basic.BasicResponseDto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class ResponseNotificationManager extends BasicResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long sessionId = null;
	private Long logNotificationId = null;
	private List<NotificationResponse> responseSms = null;
	private List<NotificationResponse> responseEmail = null;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Long getLogNotificationId() {
		return logNotificationId;
	}

	public void setLogNotificationId(Long logNotificationId) {
		this.logNotificationId = logNotificationId;
	}

	public List<NotificationResponse> getResponseSms() {
		return responseSms;
	}

	public void setResponseSms(List<NotificationResponse> responseSms) {
		this.responseSms = responseSms;
	}

	public List<NotificationResponse> getResponseEmail() {
		return responseEmail;
	}

	public void setResponseEmail(List<NotificationResponse> responseEmail) {
		this.responseEmail = responseEmail;
	}

	public void addResponseEmail(NotificationResponse response) {
		if (response != null) {
			if (this.responseEmail == null)
				this.responseEmail = new LinkedList<>();
			this.responseEmail.add(response);
		}
	}

	public void addResponseSms(NotificationResponse response) {
		if (response != null) {
			if (this.responseSms == null)
				this.responseSms = new LinkedList<>();
			this.responseSms.add(response);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseNotificationManager [");
		builder.append(super.toString());
		builder.append(", ");
		if (sessionId != null) {
			builder.append("sessionId=");
			builder.append(sessionId);
			builder.append(", ");
		}
		if (logNotificationId != null) {
			builder.append("logNotificationId=");
			builder.append(logNotificationId);
			builder.append(", ");
		}
		if (responseSms != null) {
			builder.append("responseSms=");
			builder.append(responseSms);
			builder.append(", ");
		}
		if (responseEmail != null) {
			builder.append("responseEmail=");
			builder.append(responseEmail);
		}
		builder.append("]");
		return builder.toString();
	}

}
