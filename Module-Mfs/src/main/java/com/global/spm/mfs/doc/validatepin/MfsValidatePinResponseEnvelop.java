package com.global.spm.mfs.doc.validatepin;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Envelope")
public class MfsValidatePinResponseEnvelop  {
	@JacksonXmlProperty(localName = "Body")
	private MfsValidatePinResponseBody body = null;

	public MfsValidatePinResponseBody getBody() {
		return body;
	}

	public void setBody(MfsValidatePinResponseBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsValidatePinResponseEnvelop [");
		if (body != null) {
			builder.append("body=");
			builder.append(body);
		}
		builder.append("]");
		return builder.toString();
	}
}
