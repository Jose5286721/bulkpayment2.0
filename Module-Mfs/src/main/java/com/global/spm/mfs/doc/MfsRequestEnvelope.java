package com.global.spm.mfs.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "soapenv:Envelope")
public class MfsRequestEnvelope<C> {

	@JacksonXmlProperty(localName = "xmlns:soapenv", isAttribute = true)
	private String soapenv = "http://schemas.xmlsoap.org/soap/envelope/";

	@JacksonXmlProperty(localName = "xmlns:sch", isAttribute = true)
	private String sch = "http://xmlns.tigo.com/MFS/MFSServiceRequest/V2/schema";

	@JacksonXmlProperty(localName = "xmlns:v3", isAttribute = true)
	private String v3 = "http://xmlns.tigo.com/RequestHeader/V3";

	@JacksonXmlProperty(localName = "xmlns:v2", isAttribute = true)
	private String v2 = "http://xmlns.tigo.com/ParameterType/V2";

	@JacksonXmlProperty(localName = "xmlns:v1", isAttribute = true)
	private String v1 = null;

	@JacksonXmlProperty(localName = "soapenv:Header")
	private String header = null;

	@JacksonXmlProperty(localName = "soapenv:Body")
	private C body = null;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public C getBody() {
		return body;
	}

	public void setBody(C body) {
		this.body = body;
	}

	public void setSch(String sch) {
		this.sch = sch;
	}

	public void setSoapenv(String soapenv) {
		this.soapenv = soapenv;
	}

	public void setV3(String v3) {
		this.v3 = v3;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsRequestEnvelope [");
//		if (soapenv != null) {
//			builder.append("soapenv=");
//			builder.append(soapenv);
//			builder.append(", ");
//		}
//		if (sch != null) {
//			builder.append("sch=");
//			builder.append(sch);
//			builder.append(", ");
//		}
//		if (v3 != null) {
//			builder.append("v3=");
//			builder.append(v3);
//			builder.append(", ");
//		}
//		if (v2 != null) {
//			builder.append("v2=");
//			builder.append(v2);
//			builder.append(", ");
//		}
//		if (v1 != null) {
//			builder.append("v1=");
//			builder.append(v1);
//			builder.append(", ");
//		}
		if (header != null) {
			builder.append("header=");
			builder.append(header);
			builder.append(", ");
		}
		if (body != null) {
			builder.append("body=");
			builder.append(body);
		}
		builder.append("]");
		return builder.toString();
	}

}
