package com.global.spm.mfs.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
 * <S:Body xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
 * <sch:mfsCollectResponse xmlns:sch=
 * "http://xmlns.tigo.com/MFS/MFSServiceResponse/V2/schema">
 * <v3:ResponseHeader xmlns:v3="http://xmlns.tigo.com/ResponseHeader/V3">
 * <v3:GeneralResponse> <v3:correlationID>TEST-0000015</v3:correlationID>
 * <v3:status>OK</v3:status> <v3:code>collect-0-S</v3:code>
 * <v3:codeType>NEG</v3:codeType> <v3:description>PM_SUCCESS_IN</v3:description>
 * </v3:GeneralResponse> </v3:ResponseHeader> <sch:responseBody>
 * <sch:transactionId>108769965</sch:transactionId> </sch:responseBody>
 * </sch:mfsCollectResponse> </S:Body> </soapenv:Envelope>
 * 
 * @author hugo
 *
 */
@JacksonXmlRootElement(localName = "mfsResponse")
public class MfsResponse {
	@JacksonXmlProperty(localName = "ResponseHeader")
	private MfsResponseHeader header;

	@JacksonXmlProperty(localName = "responseBody")
	private MfsResponseBody body;

	public MfsResponseHeader getHeader() {
		return header;
	}

	public void setHeader(MfsResponseHeader header) {
		this.header = header;
	}

	public MfsResponseBody getBody() {
		return body;
	}

	public void setBody(MfsResponseBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsCollectResponse [");
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
