package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.domain.entity.SystemparameterId;
import py.com.global.spm.model.repository.ISystemParameterDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 **/
@Service
@CacheConfig(cacheNames = "systemParameter")
public class SystemParameterService {

    private final Logger log = LoggerFactory.getLogger(SystemParameterService.class);
    private final ISystemParameterDao dao;

    @Autowired
    public SystemParameterService(ISystemParameterDao dao) {
        this.dao = dao;
    }

    @Cacheable(key = "#idProcess+'-'+#parameter", unless = "#result.equals(#defaultValue)")
    public String getParameterValue(Long idProcess, String parameter, String defaultValue) {
        log.debug(String.format("Searching --> idProcess= %d , parameter= %s ", idProcess, parameter));
        try {
            SystemparameterId key = new SystemparameterId(parameter, idProcess);
            Optional<Systemparameter> s = dao.findById(key);
            if (s.isPresent()) return s.get().getValueChr();
        } catch (Exception e) {
            log.error("Obteniendo parametro: --> idProcess=" + idProcess + ", parameter="
                    + parameter);
        }
        return defaultValue;

    }

    @Cacheable(key = "#idProcess+'-'+#parameter", unless="#result == null")
    public String getParameterValue(Long idProcess, String parameter) {
        try {
            log.debug(String.format("Searching --> idProcess= %d , parameter= %s ", idProcess, parameter));
            SystemparameterId key = new SystemparameterId(parameter, idProcess);
            Optional<Systemparameter> s = dao.findById(key);
            if (s.isPresent()) return s.get().getValueChr();
        } catch (Exception e) {
            log.error("Obteniendo parametro: --> idProcess=" + idProcess + ", parameter="
                    + parameter);
        }
        return null;
    }

    /**
     * Lista de parametros por AdditionalData ID
     *
     * @param idProcess
     * @return
     */
    public ConcurrentHashMap<String, String> getParameters(Long idProcess) {
        ConcurrentHashMap<String, String> parameters = null;
        try {
            List<Systemparameter> list = dao.findByProcessIdprocessPk(idProcess);
            if (list != null && list.size() > 0) {
                parameters = new ConcurrentHashMap<>();
                for (Systemparameter parameter : list) {
                    parameters.put(parameter.getId().getParameterPk(), parameter.getValueChr());
                }
            }
            return parameters;
        } catch (Exception e) {
            log.error("Error al obtener los parametros por IdProcess " + idProcess);
            throw e;
        }
    }
    @Cacheable(key = "#idProcess+'-'+#parameter", unless = "#result.equals(#defaultValue)")
    public Integer getParameterValue(Long idProcess, String parameter, Integer defaultValue) {
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
    @Cacheable(key = "#idProcess+'-'+#parameter", unless = "#result.equals(#defaultValue)")
    public Long getParameterValue(Long idProcess, String parameter, Long defaultValue) {

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


    @Cacheable(key = "#idProcess+'-'+#parameter", unless = "#result.equals(#defaultValue)")
    public BigDecimal getParameterValue(Long idProcess, String parameter, BigDecimal defaultValue) {

        String value = this.getParameterValue(idProcess, parameter);

        if (value == null) {
            return defaultValue;
        } else {
            BigDecimal valueInt = defaultValue;
            try {
                valueInt = new BigDecimal(value);
            } catch (NumberFormatException nfe) {
                log.error("Error en formato: " + nfe.getMessage());
            }

            return valueInt;
        }
    }


}
