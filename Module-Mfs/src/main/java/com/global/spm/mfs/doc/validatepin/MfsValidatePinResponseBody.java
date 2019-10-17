package com.global.spm.mfs.doc.validatepin;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsResponse;

@JacksonXmlRootElement(localName = "Body")
public class MfsValidatePinResponseBody {
	@JacksonXmlProperty(localName = "ValidatePinResponse")
	MfsResponse response;

	public MfsResponse getResponse() {
		return response;
	}

	public void setResponse(MfsResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsValidatePinResponseBody [");
		if (response != null) {
			builder.append("response=");
			builder.append(response);
		}
		builder.append("]");
		return builder.toString();
	}

}
