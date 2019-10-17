package com.global.spm.mfs.doc.validatepin;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsRequest;

@JacksonXmlRootElement(localName = "soapenv:Body")
public class MfsValidatePinRequestBody {
	@JacksonXmlProperty(localName = "sch:ValidatePinRequest")
	private MfsRequest validatePinRequest;

	public MfsRequest getValidatePinRequest() {
		return validatePinRequest;
	}

	public void setValidatePinRequest(MfsRequest validatePinRequest) {
		this.validatePinRequest = validatePinRequest;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidatePinRequestBody [");
		if (validatePinRequest != null) {
			builder.append("validatePinRequest=");
			builder.append(validatePinRequest);
		}
		builder.append("]");
		return builder.toString();
	}

}
