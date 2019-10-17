package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
	<ns2:ResponseHeader xmlns:ns2="http://www.tigo.com/Core/Common/Header/Response/V1">
		<ns2:Consumer code="" name=""/>
        <ns2:Service code="" name=""/>
        <ns2:Message messageId="12" messageIdCorrelation="123" conversationId="1">
        	<ns2:state>OK</ns2:state>
        </ns2:Message>
    	<ns2:Country name="" isoCode=""/>
	</ns2:ResponseHeader>
          
 * @author hugo
 *
 */

@JacksonXmlRootElement(localName = "ResponseHeader")
public class NCenterResponseHeader {

	@JacksonXmlProperty(localName = "Consumer")
	private NCenterParamType consumer;

	@JacksonXmlProperty(localName = "Service")
	private NCenterParamType service;

	@JacksonXmlProperty(localName = "Message")
	private NCenterMessage message;

	@JacksonXmlProperty(localName = "Country")
	private NCenterCountry country;

	public NCenterParamType getConsumer() {
		return consumer;
	}

	public void setConsumer(NCenterParamType consumer) {
		this.consumer = consumer;
	}

	public NCenterParamType getService() {
		return service;
	}

	public void setService(NCenterParamType service) {
		this.service = service;
	}

	public NCenterMessage getMessage() {
		return message;
	}

	public void setMessage(NCenterMessage message) {
		this.message = message;
	}

	public NCenterCountry getCountry() {
		return country;
	}

	public void setCountry(NCenterCountry country) {
		this.country = country;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCenterResponseHeader [");
		if (consumer != null) {
			builder.append("consumer=");
			builder.append(consumer);
			builder.append(", ");
		}
		if (service != null) {
			builder.append("service=");
			builder.append(service);
			builder.append(", ");
		}
		if (message != null) {
			builder.append("message=");
			builder.append(message);
			builder.append(", ");
		}
		if (country != null) {
			builder.append("country=");
			builder.append(country);
		}
		builder.append("]");
		return builder.toString();
	}

	@JacksonXmlRootElement(localName = "ParamType")
	public class NCenterParamType {

		@JacksonXmlProperty(localName = "name", isAttribute = true)
		private String name;

		@JacksonXmlProperty(localName = "value", isAttribute = true)
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ParamType [");
			if (name != null) {
				builder.append("name=");
				builder.append(name);
				builder.append(", ");
			}
			if (value != null) {
				builder.append("value=");
				builder.append(value);
			}
			builder.append("]");
			return builder.toString();
		}

	}

	@JacksonXmlRootElement(localName = "Country")
	public class NCenterCountry {
		@JacksonXmlProperty(localName = "name", isAttribute = true)
		private String name;

		@JacksonXmlProperty(localName = "isoCode", isAttribute = true)
		private String isoCode;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIsoCode() {
			return isoCode;
		}

		public void setIsoCode(String isoCode) {
			this.isoCode = isoCode;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Country [");
			if (name != null) {
				builder.append("name=");
				builder.append(name);
				builder.append(", ");
			}
			if (isoCode != null) {
				builder.append("isoCode=");
				builder.append(isoCode);
			}
			builder.append("]");
			return builder.toString();
		}

	}

}
