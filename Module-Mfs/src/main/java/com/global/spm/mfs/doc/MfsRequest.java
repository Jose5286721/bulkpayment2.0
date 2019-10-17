package com.global.spm.mfs.doc;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "MfsRequest")
public class MfsRequest {
	@JacksonXmlProperty(localName = "v3:RequestHeader")
	private MfsRequestHeader requestHeader;

	@JacksonXmlProperty(localName = "sch:requestBody")
	private MfsRequestBody requestBody;

	public MfsRequestHeader getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(MfsRequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public MfsRequestBody getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(MfsRequestBody requestBody) {
		this.requestBody = requestBody;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsCollectRequest [");
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

	@JacksonXmlRootElement(localName = "RequestHeader")
	public class MfsRequestHeader {
		@JacksonXmlProperty(localName = "v3:GeneralConsumerInformation")
		private GeneralConsumerInformation generalConsumerInformation;

		public GeneralConsumerInformation getGeneralConsumerInformation() {
			return generalConsumerInformation;
		}

		public void setGeneralConsumerInformation(GeneralConsumerInformation generalConsumerInformation) {
			this.generalConsumerInformation = generalConsumerInformation;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("RequestHeader [");
			if (generalConsumerInformation != null) {
				builder.append("generalConsumerInformation=");
				builder.append(generalConsumerInformation);
			}
			builder.append("]");
			return builder.toString();
		}

		@JacksonXmlRootElement(localName = "MfsCollectRequest")
		public class GeneralConsumerInformation {
			@JacksonXmlProperty(localName = "v3:consumerID")
			private String consumerID;

			@JacksonXmlProperty(localName = "v3:transactionID")
			private Long transactionID;

			@JacksonXmlProperty(localName = "v3:country")
			private String country;

			@JacksonXmlProperty(localName = "v3:correlationID")
			private Long correlationID;

			public String getConsumerID() {
				return consumerID;
			}

			public void setConsumerID(String consumerID) {
				this.consumerID = consumerID;
			}

			public Long getTransactionID() {
				return transactionID;
			}

			public void setTransactionID(Long transactionID) {
				this.transactionID = transactionID;
			}

			public Long getCorrelationID() {
				return correlationID;
			}

			public void setCorrelationID(Long correlationID) {
				this.correlationID = correlationID;
			}

			public String getCountry() {
				return country;
			}

			public void setCountry(String country) {
				this.country = country;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("GeneralConsumerInformation [");
				if (consumerID != null) {
					builder.append("consumerID=");
					builder.append(consumerID);
					builder.append(", ");
				}
				if (transactionID != null) {
					builder.append("transactionID=");
					builder.append(transactionID);
					builder.append(", ");
				}
				if (correlationID != null) {
					builder.append("correlationID=");
					builder.append(correlationID);
					builder.append(", ");
				}
				if (country != null) {
					builder.append("country=");
					builder.append(country);
				}
				builder.append("]");
				return builder.toString();
			}

		}
	}

	@JacksonXmlRootElement(localName = "requestBody")
	public class MfsRequestBody {
		@JacksonXmlProperty(localName = "sch:msisdn")
		private String msisdn;

		@JacksonXmlProperty(localName = "sch:pin")
		private String pin;

		@JacksonXmlProperty(localName = "sch:isAgent")
		private Boolean isAgent;

		@JacksonXmlProperty(localName = "sch:agentId")
		private String agendId;

		@JacksonXmlProperty(localName = "sch:agentPin")
		private String agendPin;

		@JacksonXmlProperty(localName = "sch:priceModel")
		private String priceModel;

		@JacksonXmlProperty(localName = "sch:amount")
		private String amount;

		@JacksonXmlProperty(localName = "sch:retryNumber")
		private String retryNumber;

		@JacksonXmlProperty(localName = "sch:EntityType")
		private EntityType entityType;
		
		@JacksonXmlProperty(localName = "v1:Credentials")
		private Credentials credentials;
		
		@JacksonXmlElementWrapper(localName = "sch:additionalParameters")
		@JacksonXmlProperty(localName = "v2:ParameterType")
		private List<ParameterType> additionalParameters;

		public String getMsisdn() {
			return msisdn;
		}

		public void setMsisdn(String msisdn) {
			this.msisdn = msisdn;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public String getAgendId() {
			return agendId;
		}

		public void setAgendId(String agendId) {
			this.agendId = agendId;
		}

		public String getAgendPin() {
			return agendPin;
		}

		public void setAgendPin(String agendPin) {
			this.agendPin = agendPin;
		}

		public String getPriceModel() {
			return priceModel;
		}

		public void setPriceModel(String priceModel) {
			this.priceModel = priceModel;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getRetryNumber() {
			return retryNumber;
		}

		public void setRetryNumber(String retryNumber) {
			this.retryNumber = retryNumber;
		}

		public Boolean getIsAgent() {
			return isAgent;
		}

		public void setIsAgent(Boolean isAgent) {
			this.isAgent = isAgent;
		}

		
		
		public EntityType getEntityType() {
			return entityType;
		}

		public void setEntityType(EntityType entityType) {
			this.entityType = entityType;
		}

		public Credentials getCredentials() {
			return credentials;
		}

		public void setCredentials(Credentials credentials) {
			this.credentials = credentials;
		}

		public List<ParameterType> getAdditionalParameters() {
			return additionalParameters;
		}

		public void setAdditionalParameters(List<ParameterType> additionalParameters) {
			this.additionalParameters = additionalParameters;
		}

		public void setParameterType(String name, String value) {
			if (name != null && value != null) {
				if (this.additionalParameters == null)
					this.additionalParameters = new LinkedList<ParameterType>();
				ParameterType parameter = new ParameterType();
				parameter.setParameterName(name);
				parameter.setParameterValue(value);
				this.additionalParameters.add(parameter);
			}
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MfsRequestBody [");
			if (msisdn != null) {
				builder.append("msisdn=");
				builder.append(msisdn);
				builder.append(", ");
			}
			if (pin != null) {
				builder.append("pin=");
				builder.append(pin);
				builder.append(", ");
			}
			if (isAgent != null) {
				builder.append("isAgent=");
				builder.append(isAgent);
				builder.append(", ");
			}
			if (agendId != null) {
				builder.append("agendId=");
				builder.append(agendId);
				builder.append(", ");
			}
			if (agendPin != null) {
				builder.append("agendPin=");
				builder.append(agendPin);
				builder.append(", ");
			}
			if (priceModel != null) {
				builder.append("priceModel=");
				builder.append(priceModel);
				builder.append(", ");
			}
			if (amount != null) {
				builder.append("amount=");
				builder.append(amount);
				builder.append(", ");
			}
			if (retryNumber != null) {
				builder.append("retryNumber=");
				builder.append(retryNumber);
				builder.append(", ");
			}
			if (entityType != null) {
				builder.append("entityType=");
				builder.append(entityType);
				builder.append(", ");
			}
			if (credentials != null) {
				builder.append("credentials=");
				builder.append(credentials);
				builder.append(", ");
			}
			if (additionalParameters != null) {
				builder.append("additionalParameters=");
				builder.append(additionalParameters);
			}
			builder.append("]");
			return builder.toString();
		}

		@JacksonXmlRootElement(localName = "v2:ParameterType")
		public class ParameterType {
			@JacksonXmlProperty(localName = "v2:parameterName")
			private String parameterName = null;

			@JacksonXmlProperty(localName = "v2:parameterValue")
			private String parameterValue = null;

			public String getParameterName() {
				return parameterName;
			}

			public void setParameterName(String parameterName) {
				this.parameterName = parameterName;
			}

			public String getParameterValue() {
				return parameterValue;
			}

			public void setParameterValue(String parameterValue) {
				this.parameterValue = parameterValue;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("ParameterType [");
				if (parameterName != null) {
					builder.append("parameterName=");
					builder.append(parameterName);
					builder.append(", ");
				}
				if (parameterValue != null) {
					builder.append("parameterValue=");
					builder.append(parameterValue);
				}
				builder.append("]");
				return builder.toString();
			}

		}

		@JacksonXmlRootElement(localName = "sch:EntityType")
		public class EntityType {
			@JacksonXmlProperty(localName = "sch:entityType")
			private String entityType = null;

			@JacksonXmlProperty(localName = "sch:entityId")
			private String entityId = null;

			public String getEntityType() {
				return entityType;
			}

			public void setEntityType(String entityType) {
				this.entityType = entityType;
			}

			public String getEntityId() {
				return entityId;
			}

			public void setEntityId(String entityId) {
				this.entityId = entityId;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("EntityType [");
				if (entityType != null) {
					builder.append("entityType=");
					builder.append(entityType);
					builder.append(", ");
				}
				if (entityId != null) {
					builder.append("entityId=");
					builder.append(entityId);
				}
				builder.append("]");
				return builder.toString();
			}
		}

		@JacksonXmlRootElement(localName = "v1:Credentials")
		public class Credentials {
			@JacksonXmlProperty(localName = "v1:user")
			private String user = null;

			@JacksonXmlProperty(localName = "v1:password")
			private String password = null;

			public String getUser() {
				return user;
			}

			public void setUser(String user) {
				this.user = user;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("Credentials [");
				if (user != null) {
					builder.append("user=");
					builder.append(user);
					builder.append(", ");
				}
				if (password != null) {
					builder.append("password=");
					builder.append(password);
				}
				builder.append("]");
				return builder.toString();
			}
		}
	}
}
