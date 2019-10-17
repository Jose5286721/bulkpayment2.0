package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * <ns0:responseBody> 
 * 		<ns0:success>true</ns0:success>
 * 		<ns0:destiny>0981407441</ns0:destiny>
 * 		<ns0:transactionCode>0</ns0:transactionCode> 
 *      <ns0:Error xmlns:ns0="http://www.tigo.com/Core/Common/Error/V1">
             <ns0:errorType>NEG</ns0:errorType>
             <ns0:code>ERR</ns0:code>
             <ns0:reason>Proceso no encontrado</ns0:reason>
             <ns0:description>Proceso no encontrado</ns0:description>
        </ns0:Error>
 * </ns0:responseBody>
 *
 * @author hugo
 *
 */
@JacksonXmlRootElement(localName = "responseBody")
public class NCenterResponseBody {

	@JacksonXmlProperty(localName = "success")
	private Boolean success;

	@JacksonXmlProperty(localName = "destiny")
	private String destiny;

	@JacksonXmlProperty(localName = "transactionCode")
	private Integer transactionCode;

	@JacksonXmlProperty(localName = "Error")
	private NCenterError error;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	public Integer getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(Integer transactionCode) {
		this.transactionCode = transactionCode;
	}

	public NCenterError getError() {
		return error;
	}

	public void setError(NCenterError error) {
		this.error = error;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NCenterResponseBody [");
		if (success != null) {
			builder.append("success=");
			builder.append(success);
			builder.append(", ");
		}
		if (destiny != null) {
			builder.append("destiny=");
			builder.append(destiny);
			builder.append(", ");
		}
		if (transactionCode != null) {
			builder.append("transactionCode=");
			builder.append(transactionCode);
			builder.append(", ");
		}
		if (error != null) {
			builder.append("error=");
			builder.append(error);
		}
		builder.append("]");
		return builder.toString();
	}

	@JacksonXmlRootElement(localName = "Error")
	public class NCenterError {
		@JacksonXmlProperty(localName = "errorType")
		private String errorType;

		@JacksonXmlProperty(localName = "code")
		private String code;

		@JacksonXmlProperty(localName = "reason")
		private String reason;

		@JacksonXmlProperty(localName = "description")
		private String description;

		public String getErrorType() {
			return errorType;
		}

		public void setErrorType(String errorType) {
			this.errorType = errorType;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("NCenterError [");
			if (errorType != null) {
				builder.append("errorType=");
				builder.append(errorType);
				builder.append(", ");
			}
			if (code != null) {
				builder.append("code=");
				builder.append(code);
				builder.append(", ");
			}
			if (reason != null) {
				builder.append("reason=");
				builder.append(reason);
				builder.append(", ");
			}
			if (description != null) {
				builder.append("description=");
				builder.append(description);
			}
			builder.append("]");
			return builder.toString();
		}

	}
}
