package com.global.spm.ncenter.util;

import com.global.common.helper.IParameter;

public enum Parameter implements IParameter {

	// URLs
	NOTIFICATIONCENTER_SERVICEURL("ncenter.ncenterServicesUrl",null),

	// Connections
	CONNECTION_MAXTOTAL("ncenter.connectionMaxTotal","10"),
	CONNECTION_MAXPERROUTE("ncenter.connectionMaxPerRoute","10"),
	
	// Parametros 
	SUCCESS_REGULAREXPRESSION("ncenter.successRegularExpression","OK|ok"),
	
	// Parametros para Notification Center
	SMS_PLATFORMID("ncenter.smsPlatformId", "14"),
	SMS_PROCESSID("ncenter.smsProcessId", "6"),
	SMS_MAXMESSAGELENGTH("ncenter.smsMaxMessageLength","160"),
	SMS_MINMESSAGELENGTH("ncenter.smsMinMessageLength","100"),
	EMAIL_PLATFORMID("ncenter.emailPlatformId", null),
	EMAIL_PROCESSID("ncenter.emailProcessId", null),
	
	// Codigos de error
	STATUS_CODE_SUCCESS("200", "Service ok"),
	STATUS_CODE_GENERALERROR("999","General error"),
	STATUS_CODE_CONNECTIONERROR("503","Service Unavailable");
	
	private String key;
	private String defaultValue;

	Parameter(String key, String defaultValue) {
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
