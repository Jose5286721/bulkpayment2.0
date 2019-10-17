package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "soapenv:Envelope")
public class NCenterRequestEnvelope {

	@JacksonXmlProperty(localName = "xmlns:soapenv", isAttribute = true)
	private String soapenv = "http://schemas.xmlsoap.org/soap/envelope/";

	@JacksonXmlProperty(localName = "xmlns:v1", isAttribute = true)
	private String v1 = "http://www.tigo.com/SendNotificationRequest/V1";

	@JacksonXmlProperty(localName = "xmlns:v11", isAttribute = true)
	private String v11 = "http://www.tigo.com/Core/Common/Header/Request/V1";

	@JacksonXmlProperty(localName = "xmlns:v12", isAttribute = true)
	private String v12 = "http://www.tigo.com/SendNotification/V1";

	@JacksonXmlProperty(localName = "soapenv:Header")
	private String header;

	@JacksonXmlProperty(localName = "soapenv:Body")
	private NCenterBody body;

	public String getSoapenv() {
		return soapenv;
	}

	public void setSoapenv(String soapenv) {
		this.soapenv = soapenv;
	}

	public String getV1() {
		return v1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCenterRequestEnvelope [");
//		if (soapenv != null) {
//			builder.append("soapenv=");
//			builder.append(soapenv);
//			builder.append(", ");
//		}
//		if (v1 != null) {
//			builder.append("v1=");
//			builder.append(v1);
//			builder.append(", ");
//		}
//		if (v11 != null) {
//			builder.append("v11=");
//			builder.append(v11);
//			builder.append(", ");
//		}
//		if (v12 != null) {
//			builder.append("v12=");
//			builder.append(v12);
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

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV11() {
		return v11;
	}

	public void setV11(String v11) {
		this.v11 = v11;
	}

	public String getV12() {
		return v12;
	}

	public void setV12(String v12) {
		this.v12 = v12;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public NCenterBody getBody() {
		return body;
	}

	public void setBody(NCenterBody body) {
		this.body = body;
	}

	@JacksonXmlRootElement(localName = "soapenv:Body")
	public class NCenterBody {

		@JacksonXmlProperty(localName = "v1:SendNotificationRequest")
		private NCenterSendNotificationRequest sendNotificationRequest;

		public NCenterSendNotificationRequest getSendNotificationRequest() {
			return sendNotificationRequest;
		}

		public void setSendNotificationRequest(NCenterSendNotificationRequest sendNotificationRequest) {
			this.sendNotificationRequest = sendNotificationRequest;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Body [");
			if (sendNotificationRequest != null) {
				builder.append("sendNotificationRequest=");
				builder.append(sendNotificationRequest);
			}
			builder.append("]");
			return builder.toString();
		}

		@JacksonXmlRootElement(localName = "v1:SendNotificationRequest")
		public class NCenterSendNotificationRequest {

			@JacksonXmlProperty(localName = "v11:RequestHeader")
			private NCenterRequestHeader requestHeader;

			@JacksonXmlProperty(localName = "v1:requestBody")
			private NCenterRequestBody requestBody;

			public NCenterRequestHeader getRequestHeader() {
				return requestHeader;
			}

			public void setRequestHeader(NCenterRequestHeader requestHeader) {
				this.requestHeader = requestHeader;
			}

			public NCenterRequestBody getRequestBody() {
				return requestBody;
			}

			public void setRequestBody(NCenterRequestBody requestBody) {
				this.requestBody = requestBody;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("SendNotificationRequest [");
				if (requestHeader != null) {
					builder.append("requestHeader=");
					builder.append(requestHeader);
					builder.append(", ");
				}
				if (requestBody != null) {
					builder.append("requestBody=");
					builder.append(requestBody);
				}
				builder.append("]");
				return builder.toString();
			}

		}

	}

}
