package com.global.spm.drivertransaction;

import java.io.Serializable;
import java.math.BigDecimal;

public class RequestForReverseTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long sessionId = null;
	private String commerceName = null;
	private String serviceName = null;
	private String account = null;
	private String accountPin = null;
	private String collectAccount = null; // Cuenta recaudadora
	private String collectAccountPin = null;
	private BigDecimal amount = null;
	private BigDecimal amountCommission = null;
	private Long rpmTransactionId = null;
	private String monetaryTransactionId = null;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public String getCommerceName() {
		return commerceName;
	}

	public void setCommerceName(String commerceName) {
		this.commerceName = commerceName;
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

	public String getAccountPin() {
		return accountPin;
	}

	public void setAccountPin(String accountPin) {
		this.accountPin = accountPin;
	}

	public String getCollectAccount() {
		return collectAccount;
	}

	public void setCollectAccount(String collectAccount) {
		this.collectAccount = collectAccount;
	}

	public String getCollectAccountPin() {
		return collectAccountPin;
	}

	public void setCollectAccountPin(String collectAccountPin) {
		this.collectAccountPin = collectAccountPin;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountCommission() {
		return amountCommission;
	}

	public void setAmountCommission(BigDecimal amountCommission) {
		this.amountCommission = amountCommission;
	}

	public Long getRpmTransactionId() {
		return rpmTransactionId;
	}

	public void setRpmTransactionId(Long rpmTransactionId) {
		this.rpmTransactionId = rpmTransactionId;
	}

	public String getMonetaryTransactionId() {
		return monetaryTransactionId;
	}

	public void setMonetaryTransactionId(String monetaryTransactionId) {
		this.monetaryTransactionId = monetaryTransactionId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestReverseMonetaryTransaction [");
		if (sessionId != null) {
			builder.append("sessionId=");
			builder.append(sessionId);
			builder.append(", ");
		}
		if (commerceName != null) {
			builder.append("commerceName=");
			builder.append(commerceName);
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
		if (accountPin != null) {
			builder.append("accountPin=");
			builder.append(accountPin);
			builder.append(", ");
		}
		if (collectAccount != null) {
			builder.append("collectAccount=");
			builder.append(collectAccount);
			builder.append(", ");
		}
		if (collectAccountPin != null) {
			builder.append("collectAccountPin=");
			builder.append(collectAccountPin);
			builder.append(", ");
		}
		if (amount != null) {
			builder.append("amount=");
			builder.append(amount);
			builder.append(", ");
		}
		if (amountCommission != null) {
			builder.append("amountCommission=");
			builder.append(amountCommission);
			builder.append(", ");
		}
		if (rpmTransactionId != null) {
			builder.append("rpmTransactionId=");
			builder.append(rpmTransactionId);
			builder.append(", ");
		}
		if (monetaryTransactionId != null) {
			builder.append("monetaryTransactionId=");
			builder.append(monetaryTransactionId);
		}
		builder.append("]");
		return builder.toString();
	}

}
