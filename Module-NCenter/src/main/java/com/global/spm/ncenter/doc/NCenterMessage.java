package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "v11:Message")
public class NCenterMessage {
	@JacksonXmlProperty(localName = "messageId", isAttribute = true)
	private Long messageId;

	@JacksonXmlProperty(localName = "messageIdCorrelation", isAttribute = true)
	private Long messageIdCorrelation;

	@JacksonXmlProperty(localName = "conversationId", isAttribute = true)
	private Integer conversationId;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getMessageIdCorrelation() {
		return messageIdCorrelation;
	}

	public void setMessageIdCorrelation(Long messageIdCorrelation) {
		this.messageIdCorrelation = messageIdCorrelation;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [");
		if (messageId != null) {
			builder.append("messageId=");
			builder.append(messageId);
			builder.append(", ");
		}
		if (messageIdCorrelation != null) {
			builder.append("messageIdCorrelation=");
			builder.append(messageIdCorrelation);
			builder.append(", ");
		}
		if (conversationId != null) {
			builder.append("conversationId=");
			builder.append(conversationId);
		}
		builder.append("]");
		return builder.toString();
	}

}
