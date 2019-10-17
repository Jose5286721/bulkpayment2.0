package com.global.spm.mfs.doc.reversion;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsResponse;

@JacksonXmlRootElement(localName = "Envelope")
public class MfsReverseResponseEnvelop {
	@JacksonXmlProperty(localName = "Body")
	private MfsReverseResponseBody body = null;

	public MfsReverseResponseBody getBody() {
		return body;
	}

	public void setBody(MfsReverseResponseBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsReverseResponseEnvelop [");
		if (body != null) {
			builder.append("body=");
			builder.append(body);
		}
		builder.append("]");
		return builder.toString();
	}
	
	@JacksonXmlRootElement(localName = "Body")
	public class MfsReverseResponseBody {
		@JacksonXmlProperty(localName = "mfsReversionByExternalIdResponse")
        MfsResponse response;

		public MfsResponse getResponse() {
			return response;
		}

		public void setResponse(MfsResponse response) {
			this.response = response;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MfsReverseResponseBody [");
			if (response != null) {
				builder.append("response=");
				builder.append(response);
			}
			builder.append("]");
			return builder.toString();
		}

	}
}
