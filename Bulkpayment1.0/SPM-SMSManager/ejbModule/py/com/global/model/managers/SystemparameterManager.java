package py.com.global.model.managers;

import org.apache.log4j.Logger;
import py.com.global.model.cache.ProcessCache;
import py.com.global.model.cache.SystemParameterCache;
import py.com.global.model.interfaces.SystemparameterManagerLocal;
import py.com.global.model.systemparameters.*;
import py.com.global.model.util.SpmConstants;
import py.com.global.spm.entities.Systemparameter;
import py.com.global.spm.entities.SystemparameterPK;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class SystemparameterManager
 * 
 * @author R2
 */
@Stateless
public class SystemparameterManager implements SystemparameterManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private Long updateTime = null;
	private SystemParameterCache parametersCache = SystemParameterCache
			.getInstance();
	private ProcessCache processesCache = ProcessCache.getInstance();

	private final Logger log = Logger.getLogger(SystemparameterManager.class);

	@PostConstruct
	public void initialize() {
		String updateTimeStr = null;
		try {
			updateTimeStr = System.getProperty("system_parameter.updateTime");
			log.info("TimeStr: "+updateTimeStr);
			if (updateTimeStr != null) {
				updateTime = Long.parseLong(updateTimeStr);
			} else {
				updateTimeStr = "NULL";
				updateTime=2000L;
			}
		} catch (NumberFormatException nfe) {
			updateTime = SpmConstants.UPDATE_TIME;
			log.error("Update time no valido, utilizando por defecto --> updateTimeStr="
					+ updateTimeStr + ", defaultUpdateTime=" + updateTime);
		} catch (Exception e) {
			updateTime = SpmConstants.UPDATE_TIME;
			log.error("Obteniendo update time, utilizando por defecto --> defaultUpdateTime="
					+ updateTime);
		}
	}

	public String getParameterValue(Long idProcess, String parameter) {
		log.trace("Searching --> idProcess=" + idProcess + ", parameter="
				+ parameter);
		Systemparameter s;
		this.populateCache();
		SystemparameterPK key = new SystemparameterPK(idProcess, parameter);
		s = parametersCache.getSystemParameterById(key);
		if (s == null) {
			return null;
		}
		return s.getValueChr();
	}

	public Integer getParameterValue(Long idProcess, String parameter,
			Integer defaultValue) {
		String value = this.getParameterValue(idProcess, parameter);

		if (value == null) {
			return defaultValue;
		} else {
			Integer valueInt = defaultValue;
			try {
				valueInt = Integer.valueOf(value);
			} catch (NumberFormatException nfe) {
				log.error("Error en formato: " + nfe.getMessage());
			}

			return valueInt;
		}

	}

	public String getParameterValue(Long idProcess, String parameter,
			String defaultValue) {
		String value = this.getParameterValue(idProcess, parameter);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public Long getParameterValue(Long idProcess, String parameter,
			Long defaultValue) {
		String value = this.getParameterValue(idProcess, parameter);

		if (value == null) {
			return defaultValue;
		} else {
			Long valueInt = defaultValue;
			try {
				valueInt = Long.valueOf(value);
			} catch (NumberFormatException nfe) {
				log.error("Error en formato: " + nfe.getMessage());
			}

			return valueInt;
		}
	}

	public String getParameterValue(NotificationParametersEnum param,
			String defaultValue) {
		return this.getParameterValue(param.getIdProcess(),
				param.getParameterName(), defaultValue);
	}

	public String getParameterValue(MtsInterfaceParameters param) {
		return this.getParameterValue(param.getIdProcess(),
				param.getParameterName());
	}

	public String getParameterValue(GeneralParameters param, String defaultValue) {
		return this.getParameterValue(param.getIdProcess(),
				param.getParameterName(), defaultValue);
	}

	public Long getParameterValue(GeneralParameters param, Long defaultValue) {
		return this.getParameterValue(param.getIdProcess(),
				param.getParameterName(), defaultValue);
	}

	/************** Metodos Privados ******************/

	@SuppressWarnings("unchecked")
	private void populateCache() {
		if ((System.currentTimeMillis() - parametersCache.getLastUpdate()) > updateTime) {
			log.trace("Loading system parameters");
			Query q = em.createNamedQuery("getAllParameters");
			parametersCache.populate(q.getResultList());
			this.populateProcessCache();
		}
	}

	@SuppressWarnings("unchecked")
	private void populateProcessCache() {
		try {
			if ((System.currentTimeMillis() - parametersCache.getLastUpdate()) > updateTime) {
				log.trace("Loading system process");
				Query q = em.createNamedQuery("getAllProcess");
				processesCache.populate(q.getResultList());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/****************************************************/

	public boolean getBooleanParameterValue(Long idProcess, String parameter,
			boolean defaultValue) {
		String value = this.getParameterValue(idProcess, parameter);
		if (value != null) {
			if (value.compareToIgnoreCase(SpmConstants.YES) == 0) {
				return true;
			} else if (value.compareToIgnoreCase(SpmConstants.NO) == 0) {
				return false;
			} else {
				log.error("Invalid parameter value --> idProcess=" + idProcess
						+ ", parameter=" + parameter + ", value=" + value);
			}
		}
		log.warn("Parameter not found, returning default value --> idProcess="
				+ idProcess + ", parameter=" + parameter + ", defaultValue="
				+ defaultValue);
		return defaultValue;
	}

	public boolean getBooleanParameterValue(Long idProcess, String parameter) {
		boolean paramValue = false;
		try {
			String value = this.getParameterValue(idProcess, parameter);
			if (value.compareToIgnoreCase(SpmConstants.YES) == 0) {
				paramValue = true;
			}
		} catch (Exception e) {
			log.error("Param not found", e);
		}
		return paramValue;
	}

	public boolean getBooleanParameterValue(NotificationParametersEnum parameter) {
		boolean paramValue = false;
		try {
			String value = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (value.compareToIgnoreCase(SpmConstants.YES) == 0) {
				paramValue = true;
			}
		} catch (Exception e) {
			log.error("Param not found", e);
		}
		return paramValue;
	}

	@Override
	public Integer getParameter(OPManagerParameters parameter,
			Integer defaultValue) {
		Integer value;
		try {
			String valueStr = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (valueStr == null) {
				value = defaultValue;
			} else {
				value = Integer.parseInt(valueStr);
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

	@Override
	public Long getParameter(OPManagerParameters parameter, Long defaultValue) {
		Long value;
		try {
			String valueStr = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (valueStr == null) {
				value = defaultValue;
			} else {
				value = Long.parseLong(valueStr);
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

	@Override
	public String getParameter(OPManagerParameters parameter,
			String defaultValue) {
		String value;
		try {
			value = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (value == null) {
				value = defaultValue;
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

	@Override
	public String getParameter(MtsInterfaceParameters parameter,
			String defaultValue) {
		String value;
		try {
			value = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (value == null) {
				value = defaultValue;
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

	@Override
	public long getParameterValue(FlowManagerParameters parameter) {
		long value = -1;
		try {
			value = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName(), parameter.getDefaultValue());
		} catch (Exception e) {
			log.error("Param not found", e);
		}
		return value;
	}

	@Override
	public String getParameter(SMSManagerParameters parameter) {
		String value = null;
		try {
			value = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
		} catch (Exception e) {
			log.error("Param not found --> " + parameter.toString());
		}
		return value;
	}

	@Override
	public String getParameter(TransferProcessParameters parameter,
			String defaultValue) {
		String value;
		try {
			value = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (value == null) {
				value = defaultValue;
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

	@Override
	public Long getParameter(CleanTimeoutPaymentParameters parameter,
			Long defaultValue) {
		Long value;
		try {
			String valueStr = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (valueStr == null) {
				value = defaultValue;
			} else {
				value = Long.parseLong(valueStr);
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}
	
	@Override
	public Long getParameter(TransferProcessParameters parameter,
			Long defaultValue) {
		Long value;
		try {
			String valueStr = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (valueStr == null) {
				value = defaultValue;
			} else {
				value = Long.parseLong(valueStr);
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

	@Override
	public Long getParameter(ReversionProcessParameters parameter,
			Long defaultValue) {
		Long value;
		try {
			String valueStr = this.getParameterValue(parameter.getIdProcess(),
					parameter.getParameterName());
			if (valueStr == null) {
				value = defaultValue;
			} else {
				value = Long.parseLong(valueStr);
			}
		} catch (Exception e) {
			log.error("Param not found", e);
			value = defaultValue;
		}
		return value;
	}

}
