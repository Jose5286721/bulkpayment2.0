package com.global.spm.mfs.util;

import com.global.common.helper.IParameter;

public enum Parameter implements IParameter {

	// URLs
	COLLECT_SERVICEURL("mfs.collectServiceUrl", null),
	REVERSE_SERVICEURL("mfs.reverseServiceUrl", null),
	VALIDATEPIN_SERVICEURL("mfs.validatePinServiceUrl", null),
	AGENTINFO_SERVICEURL("mfs.agentInfoServiceUrl", null),
	
	// Connections
	CONNECTION_MAXTOTAL("mfs.connectionMaxTotal","100"),
	CONNECTION_MAXPERROUTE("mfs.connectionMaxPerRoute","100"),
	
	// Parametros 
	SUCCESS_REGULAREXPRESSION("mfs.successRegularExpression","OK|ok"),
	CONSUMERID("mfs.consumerId","RPM2.0"),
	COUNTRY("mfs.country","PRY"),
	
	// Parametros par Agent Info
	AGENTINFO_USERNAME("mfs.agentInfoUserName", null),
	AGENTINFO_PASSWORD("mfs.agentInfoPassword", null),
	AGENTINFO_STATUSACTIVE("mfs.agentInfoStatusActive","AC|ac"),
	
	// Codigos de error
	STATUS_CODE_GENERALERROR("999","MTS General error"),
	STATUS_CODE_CONNECTIONERROR("503","MTS Service Unavailable");
	
	private String key;
	private String defaultValue;
	
	Parameter(String key, String defaultValue)	{
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String key() {
		return key;
	}

	public String defaultValue() {
		return defaultValue;
	}
	
}
