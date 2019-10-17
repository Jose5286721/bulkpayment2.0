package py.com.global.spm.GUISERVICE.utils;


public enum RedisParameters {
	
	MEMORY_DATABASE_POOL_MIN_IDLE_CONNECTIONS(SPM_GUI_Constants.PROCESS_SPM_GENERAL, "redis.pool.minIdle"),
	MEMORY_DATABASE_POOL_MAX_IDLE_CONNECTIONS(SPM_GUI_Constants.PROCESS_SPM_GENERAL, "redis.pool.maxIdle"),
	MEMORY_DATABASE_POOL_MAX_TOTAL_CONNECTONS(SPM_GUI_Constants.PROCESS_SPM_GENERAL, "redis.pool.maxConnections");
	private Long idProcess;
	private String parameterName;

	private RedisParameters(Long IdProcess, String parameterName) {
		this.idProcess = IdProcess;
		this.parameterName = parameterName;
	}

	public Long getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(Long idProcess) {
		this.idProcess = idProcess;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parametersName) {
		this.parameterName = parametersName;
	}
}
