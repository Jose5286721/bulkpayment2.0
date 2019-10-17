package com.global.spm.mfs.doc.collect;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsResponse;

@JacksonXmlRootElement(localName = "Envelope")
public class MfsCollectResponseEnvelop  {
	@JacksonXmlProperty(localName = "Body")
	private MfsCollectResponseBody body = null;

	public MfsCollectResponseBody getBody() {
		return body;
	}

	public void setBody(MfsCollectResponseBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsCollectResponseEnvelop [");
		if (body != null) {
			builder.append("body=");
			builder.append(body);
		}
		builder.append("]");
		return builder.toString();
	}
	
	@JacksonXmlRootElement(localName = "Body")
	public class MfsCollectResponseBody {
		@JacksonXmlProperty(localName = "mfsCollectResponse")
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
			builder.append("MfsCollectResponseBody [");
			if (response != null) {
				builder.append("response=");
				builder.append(response);
			}
			builder.append("]");
			return builder.toString();
		}

	}
}
