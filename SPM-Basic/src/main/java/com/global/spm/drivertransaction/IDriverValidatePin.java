package com.global.spm.drivertransaction;

import java.util.Map;

public interface IDriverValidatePin {
	
	public void initialize(Map<String, String> parameters);

	public Boolean executeValidatePin(RequestForValidatePin request);
	
	public void destroy();

}
