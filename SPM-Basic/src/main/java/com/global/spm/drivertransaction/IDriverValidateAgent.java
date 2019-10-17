package com.global.spm.drivertransaction;

import java.util.Map;

public interface IDriverValidateAgent {

	public void initialize(Map<String, String> parameters);

	public Boolean executeValidateAgent(RequestForValidateAgent request);
	
	public void destroy();
}
