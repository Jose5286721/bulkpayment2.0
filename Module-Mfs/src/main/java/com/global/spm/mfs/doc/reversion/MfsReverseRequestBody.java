package com.global.spm.mfs.doc.reversion;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsRequest;

@JacksonXmlRootElement(localName = "Body")
public class MfsReverseRequestBody {
	@JacksonXmlProperty(localName = "sch:mfsReversionByExternalIdRequest")
	private MfsRequest mfsReverseRequest;

	public MfsRequest getMfsReverseRequest() {
		return mfsReverseRequest;
	}

	public void setMfsReverseRequest(MfsRequest mfsReverseRequest) {
		this.mfsReverseRequest = mfsReverseRequest;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsReverseRequestBody [");
		if (mfsReverseRequest != null) {
			builder.append("mfsReverseRequest=");
			builder.append(mfsReverseRequest);
		}
		builder.append("]");
		return builder.toString();
	}

}
