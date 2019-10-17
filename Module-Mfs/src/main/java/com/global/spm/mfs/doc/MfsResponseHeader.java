package com.global.spm.mfs.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ResponseHeader")
public class MfsResponseHeader {

	@JacksonXmlProperty(localName = "GeneralResponse")
	private MfsGeneralResponse mfsGeneralResponse;

	public MfsGeneralResponse getMfsGeneralResponse() {
		return mfsGeneralResponse;
	}

	public void setMfsGeneralResponse(MfsGeneralResponse mfsGeneralResponse) {
		this.mfsGeneralResponse = mfsGeneralResponse;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsResponseHeader [");
		if (mfsGeneralResponse != null) {
			builder.append("mfsGeneralResponse=");
			builder.append(mfsGeneralResponse);
		}
		builder.append("]");
		return builder.toString();
	}

	@JacksonXmlRootElement(localName = "GeneralResponse")
	public class MfsGeneralResponse {

		@JacksonXmlProperty(localName = "correlationID")
		private String correlationID = null;

		@JacksonXmlProperty(localName = "status")
		private String status = null;

		@JacksonXmlProperty(localName = "code")
		private String code = null;

		@JacksonXmlProperty(localName = "codeType")
		private String codeType = null;

		@JacksonXmlProperty(localName = "description")
		private String description = null;

		public String getCorrelationID() {
			return correlationID;
		}

		public void setCorrelationID(String correlationID) {
			this.correlationID = correlationID;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getCodeType() {
			return codeType;
		}

		public void setCodeType(String codeType) {
			this.codeType = codeType;
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
			builder.append("MfsGeneralResponse [");
			if (correlationID != null) {
				builder.append("correlationID=");
				builder.append(correlationID);
				builder.append(", ");
			}
			if (status != null) {
				builder.append("status=");
				builder.append(status);
				builder.append(", ");
			}
			if (code != null) {
				builder.append("code=");
				builder.append(code);
				builder.append(", ");
			}
			if (codeType != null) {
				builder.append("codeType=");
				builder.append(codeType);
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
