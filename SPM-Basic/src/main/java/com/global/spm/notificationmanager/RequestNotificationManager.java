package com.global.spm.notificationmanager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class RequestNotificationManager implements Serializable {

	private static final long serialVersionUID = -4798522925899705280L;
	private Long sessionId = null;
	private Long logNotificationId = null;
	private List<String> accounts = null;
	private List<String> emails = null;
	private String subject = null;
	private String message = null;
	private Boolean sendSms = false;
	private Boolean sendEmail = false;

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

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean isSendSms() {
		return sendSms;
	}

	public void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}

	public Boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public void addAccount(String account) {
		if (account != null) {
			if (this.accounts == null)
				this.accounts = new LinkedList<>();
			this.accounts.add(account);
		}
	}

	public void addEmail(String email) {
		if (email != null) {
			if (this.emails == null)
				this.emails = new LinkedList<>();
			this.emails.add(email);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestNotification [");
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
		builder.append("sendSms=");
		builder.append(sendSms);
		builder.append(", ");
		if (accounts != null) {
			builder.append("accounts=");
			builder.append(accounts);
			builder.append(", ");
		}
		builder.append("sendEmail=");
		builder.append(sendEmail);
		builder.append(", ");
		if (emails != null) {
			builder.append("emails=");
			builder.append(emails);
			builder.append(", ");
		}
		if (subject != null) {
			builder.append("subject=");
			builder.append(subject);
			builder.append(", ");
		}
		if (message != null) {
			builder.append("message=");
			builder.append(message);
		}
		builder.append("]");
		return builder.toString();
	}

}
