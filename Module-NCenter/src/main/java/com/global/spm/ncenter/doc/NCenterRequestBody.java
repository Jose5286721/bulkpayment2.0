package com.global.spm.ncenter.doc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "v1:requestBody")
public class NCenterRequestBody {

	@JacksonXmlProperty(localName = "v1:destiny")
	private String destiny;

	@JacksonXmlProperty(localName = "v1:idPlatform")
	private Integer idPlatform;

	@JacksonXmlProperty(localName = "v1:idProcess")
	private Integer idProcess;

	@JacksonXmlProperty(localName = "v1:parameters")
	private NCenterParameters parameters;

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	public Integer getIdPlatform() {
		return idPlatform;
	}

	public void setIdPlatform(Integer idPlatform) {
		this.idPlatform = idPlatform;
	}

	public Integer getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(Integer idProcess) {
		this.idProcess = idProcess;
	}

	public NCenterParameters getParameters() {
		return parameters;
	}

	public void setParameters(NCenterParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestBody [");
		if (destiny != null) {
			builder.append("destiny=");
			builder.append(destiny);
			builder.append(", ");
		}
		if (idPlatform != null) {
			builder.append("idPlatform=");
			builder.append(idPlatform);
			builder.append(", ");
		}
		if (idProcess != null) {
			builder.append("idProcess=");
			builder.append(idProcess);
			builder.append(", ");
		}
		if (parameters != null) {
			builder.append("parameters=");
			builder.append(parameters);
		}
		builder.append("]");
		return builder.toString();
	}
	
	@JacksonXmlRootElement(localName = "v1:parameters")
	public class NCenterParameters {

		@JacksonXmlProperty(localName = "v12:ParamType")
		private NCenterParamType paramType;

		public NCenterParamType getParamType() {
			return paramType;
		}

		public void setParamType(NCenterParamType paramType) {
			this.paramType = paramType;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Parameters [");
			if (paramType != null) {
				builder.append("paramType=");
				builder.append(paramType);
			}
			builder.append("]");
			return builder.toString();
		}
		
		@JacksonXmlRootElement(localName = "v12:ParamType")
		public class NCenterParamType {
			
			@JacksonXmlProperty(localName = "v12:name")
			private String name;

			@JacksonXmlProperty(localName = "v12:value")
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

	}

}
