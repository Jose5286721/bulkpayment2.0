package com.global.spm.mfs.doc.collect;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsRequest;

@JacksonXmlRootElement(localName = "Body")
public class MfsCollectRequestBody {
	@JacksonXmlProperty(localName = "sch:mfsCollectRequest")
	private MfsRequest mfsCollectRequest;

	public MfsRequest getMfsCollectRequest() {
		return mfsCollectRequest;
	}

	public void setMfsCollectRequest(MfsRequest mfsCollectRequest) {
		this.mfsCollectRequest = mfsCollectRequest;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsCollectRequestBody [");
		if (mfsCollectRequest != null) {
			builder.append("mfsCollectRequest=");
			builder.append(mfsCollectRequest);
		}
		builder.append("]");
		return builder.toString();
	}
}
