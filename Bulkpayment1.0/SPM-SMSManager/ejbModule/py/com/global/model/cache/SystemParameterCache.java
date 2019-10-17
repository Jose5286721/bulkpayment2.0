package py.com.global.model.cache;


import org.jboss.logging.Logger;
import py.com.global.spm.entities.Systemparameter;
import py.com.global.spm.entities.SystemparameterPK;

import java.util.Hashtable;
import java.util.List;


/**
 * @author R2
 */
public class SystemParameterCache {

	private Hashtable<SystemparameterPK, Systemparameter> parameters;
	private long lastUpdate = 0; // Ultima vez que la tabla fue poblada
	private static SystemParameterCache instance;
	private final Logger log = Logger.getLogger(SystemParameterCache.class);

	// Instanciar el Singleton
	static {
		instance = new SystemParameterCache();
	}

	// Constructor (llamado una unica vez)
	private SystemParameterCache() {
		log.debug("Instanciando...");
		parameters = new Hashtable<SystemparameterPK, Systemparameter>(128);
	}

	// Referencia del singleton
	public static SystemParameterCache getInstance() {
		return instance;
	}

	// Obtener el parametro del proceso
	public Systemparameter getSystemParameterById(SystemparameterPK key) {
		Systemparameter value = null;

		if (key != null && parameters != null) {
			value = parameters.get(key);
		}

		return value;
	}

	/**
	 * Obtener la ultima vez que la tabla fue poblada
	 * 
	 * @return
	 */
	public long getLastUpdate() {
		return lastUpdate;
	}

	// Cargar lista de parametros
	public void populate(List<Systemparameter> list) {
		if (parameters != null) {
			synchronized (parameters) {
				if (list != null) {
					long currentTime = System.currentTimeMillis();

					log.debug("Cargando parametros --> size=" + list.size());
					parameters.clear();
					for (Systemparameter s : list) {
						// Agregar la nueva entrada
						parameters.put(s.getId(), s);
					}
					lastUpdate = currentTime;
				}
			}
		}
	}
}
