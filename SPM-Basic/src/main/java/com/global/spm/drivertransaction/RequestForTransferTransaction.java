package com.global.spm.drivertransaction;

import java.io.Serializable;
import java.math.BigDecimal;


public class RequestForTransferTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long sessionId = null;
	private Long rpmTransactionId = null;
	private String commerceName = null;
	private String serviceName = null;
	private String account = null; // Cuenta pagadora
	private String accountPin = null;
	private String collectAccount = null; // Cuenta recaudadora
	private String collectAccountPin = null;
	private String collectProfile = null;
	private BigDecimal amount = null;
	private BigDecimal amountCommission = null;
	private String transactionDescription = null;

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

	public String getCollectProfile() {
		return collectProfile;
	}

	public void setCollectProfile(String collectProfile) {
		this.collectProfile = collectProfile;
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

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestTransferMonetaryTransaction [");
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
		if (collectProfile != null) {
			builder.append("collectProfile=");
			builder.append(collectProfile);
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
		if (transactionDescription != null) {
			builder.append("transactionDescription=");
			builder.append(transactionDescription);
		}
		builder.append("]");
		return builder.toString();
	}

}
