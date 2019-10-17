package py.com.global.spm.model.systemparameters;

import py.com.global.spm.model.util.SpmProcesses;

public enum GeneralParameters {
	
	UPDATE_TIME(SpmProcesses.SPM_GENERAL, "UpdateTime"), //Tiempo para actualizar el cache.
	CONNECT_TIMEOUT_MS(SpmProcesses.SPM_GENERAL, "connect.timeout.ms"),
	CONNECT_READ_TIMEOUT_MS(SpmProcesses.SPM_GENERAL, "read.timeout.ms"),
	MAX_CONNECTIONS(SpmProcesses.SPM_GENERAL,"max.connections"),
    JMS_LISTENER_CONCURRENCY(SpmProcesses.SPM_GENERAL, "jms.listener.concurrency"),
	QUEUE_ENDPOINT_URL(SpmProcesses.SPM_GENERAL, "queueEndpointUrl"),
	QUEUE_BROKER_NAME(SpmProcesses.SPM_GENERAL, "queueBrokerName"),
	MEMORY_DATABASE_POOL_MIN_IDLE_CONNECTIONS(SpmProcesses.SPM_GENERAL, "redis.pool.minIdle"),
	MEMORY_DATABASE_POOL_MAX_IDLE_CONNECTIONS(SpmProcesses.SPM_GENERAL, "redis.pool.maxIdle"),
	MEMORY_DATABASE_POOL_MAX_TOTAL_CONNECTONS(SpmProcesses.SPM_GENERAL, "redis.pool.maxConnections");
	private Long idProcess;
	private String parameterName;

	private GeneralParameters(Long IdProcess, String parameterName) {
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
