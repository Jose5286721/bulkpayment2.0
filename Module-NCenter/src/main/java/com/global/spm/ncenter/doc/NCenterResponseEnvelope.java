package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <ns0:SendNotificationResponse xmlns:ns0="http://www.tigo.com/SendNotificationResponse/V1">
         <ns2:ResponseHeader xmlns:ns2="http://www.tigo.com/Core/Common/Header/Response/V1">
            <ns2:Consumer code="" name=""/>
            <ns2:Service code="" name=""/>
            <ns2:Message messageId="12" messageIdCorrelation="123" conversationId="1">
               <ns2:state>OK</ns2:state>
            </ns2:Message>
            <ns2:Country name="" isoCode=""/>
         </ns2:ResponseHeader>
         <ns0:responseBody>
            <ns0:success>true</ns0:success>
            <ns0:destiny>0981407441</ns0:destiny>
            <ns0:transactionCode>0</ns0:transactionCode>
         </ns0:responseBody>
      </ns0:SendNotificationResponse>
   </soap:Body>
</soapenv:Envelope>
 * 
 * 
 * @author hugo
 *
 */

@JacksonXmlRootElement(localName = "Envelope")
public class NCenterResponseEnvelope {
	@JacksonXmlProperty(localName = "soapenv", isAttribute = true)
	private String soapenv = "http://schemas.xmlsoap.org/soap/envelope/";

	@JacksonXmlProperty(localName = "Body")
	private NCenterBody body;

	public String getSoapenv() {
		return soapenv;
	}

	public void setSoapenv(String soapenv) {
		this.soapenv = soapenv;
	}

	public NCenterBody getBody() {
		return body;
	}

	public void setBody(NCenterBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCenterResponseEnvelope [");
		if (soapenv != null) {
			builder.append("soapenv=");
			builder.append(soapenv);
			builder.append(", ");
		}
		if (body != null) {
			builder.append("body=");
			builder.append(body);
		}
		builder.append("]");
		return builder.toString();
	}

	@JacksonXmlRootElement(localName = "Body")
	public class NCenterBody {

		@JacksonXmlProperty(localName = "soap", isAttribute = true)
		private String soap = "http://schemas.xmlsoap.org/soap/envelope/";

		@JacksonXmlProperty(localName = "SendNotificationResponse")
		private NCenterSendNotificationResponse sendNotificationResponse;

		public String getSoap() {
			return soap;
		}

		public void setSoap(String soap) {
			this.soap = soap;
		}

		public NCenterSendNotificationResponse getSendNotificationResponse() {
			return sendNotificationResponse;
		}

		public void setSendNotificationResponse(NCenterSendNotificationResponse sendNotificationResponse) {
			this.sendNotificationResponse = sendNotificationResponse;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("NCenterBody [");
			if (soap != null) {
				builder.append("soap=");
				builder.append(soap);
				builder.append(", ");
			}
			if (sendNotificationResponse != null) {
				builder.append("sendNotificationResponse=");
				builder.append(sendNotificationResponse);
			}
			builder.append("]");
			return builder.toString();
		}

		@JacksonXmlRootElement(localName = "SendNotificationResponse")
		public class NCenterSendNotificationResponse {

			@JacksonXmlProperty(localName = "ResponseHeader")
			private NCenterResponseHeader header;

			@JacksonXmlProperty(localName = "responseBody")
			private NCenterResponseBody body;

			public NCenterResponseHeader getHeader() {
				return header;
			}

			public void setHeader(NCenterResponseHeader header) {
				this.header = header;
			}

			public NCenterResponseBody getBody() {
				return body;
			}

			public void setBody(NCenterResponseBody body) {
				this.body = body;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("NCenterSendNotificationResponse [");
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
	}

}
