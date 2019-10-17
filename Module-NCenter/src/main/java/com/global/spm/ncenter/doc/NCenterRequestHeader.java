package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "v11:RequestHeader")
public class NCenterRequestHeader {
	@JacksonXmlProperty(localName = "v11:Message")
	private NCenterMessage message;

	public NCenterMessage getMessage() {
		return message;
	}

	public void setMessage(NCenterMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestHeader [");
		if (message != null) {
			builder.append("message=");
			builder.append(message);
		}
		builder.append("]");
		return builder.toString();
	}

}
