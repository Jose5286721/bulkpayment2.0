package com.global.spm.mfs.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "responseBody")
public class MfsResponseBody {

	@JacksonXmlProperty(localName = "transactionId")
	private String transactionId;

	@JacksonXmlProperty(localName = "statusCode")
	private Integer statusCode;

	@JacksonXmlProperty(localName = "statusMessage")
	private String statusMessage;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsResponseBody [");
		if (transactionId != null) {
			builder.append("transactionId=");
			builder.append(transactionId);
			builder.append(", ");
		}
		if (statusCode != null) {
			builder.append("statusCode=");
			builder.append(statusCode);
			builder.append(", ");
		}
		if (statusMessage != null) {
			builder.append("statusMessage=");
			builder.append(statusMessage);
		}
		builder.append("]");
		return builder.toString();
	}

}
