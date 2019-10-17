package com.global.spm.drivertransaction;

import com.global.spm.basic.BasicResponseDto;

import java.io.Serializable;


public class ResponseMonetaryTransaction extends BasicResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long sessionId = null;
	private String monetaryTransactionId = null;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public String getMonetaryTransactionId() {
		return monetaryTransactionId;
	}

	public void setMonetaryTransactionId(String monetaryTransactionId) {
		this.monetaryTransactionId = monetaryTransactionId;
	}

	@Override
	public String toString() {
		return "ResponseMonetaryTransaction{" +
				"sessionId=" + sessionId +
				", monetaryTransactionId='" + monetaryTransactionId + '\'' + toStringBasicResponse() +
				'}';
	}
}
