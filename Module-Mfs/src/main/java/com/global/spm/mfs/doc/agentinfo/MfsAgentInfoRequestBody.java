package com.global.spm.mfs.doc.agentinfo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.global.spm.mfs.doc.MfsRequest;

@JacksonXmlRootElement(localName = "Body")
public class MfsAgentInfoRequestBody {

	@JacksonXmlProperty(localName = "sch:GetAgentInfoRequest")
	private MfsRequest mfsAgentInfoRequest;

	public MfsRequest getMfsAgentInfoRequest() {
		return mfsAgentInfoRequest;
	}

	public void setMfsAgentInfoRequest(MfsRequest mfsAgentInfoRequest) {
		this.mfsAgentInfoRequest = mfsAgentInfoRequest;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MfsAgentInfoRequestBody [");
		if (mfsAgentInfoRequest != null) {
			builder.append("mfsAgentInfoRequest=");
			builder.append(mfsAgentInfoRequest);
		}
		builder.append("]");
		return builder.toString();
	}

}
