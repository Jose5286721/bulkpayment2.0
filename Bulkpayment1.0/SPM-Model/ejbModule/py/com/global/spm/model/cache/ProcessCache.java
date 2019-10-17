package py.com.global.spm.model.cache;

import java.util.Hashtable;
import java.util.List;
import py.com.global.spm.entities.Process;
import org.jboss.logging.Logger;

/**
 * @author Hugo
 */
public class ProcessCache {
	public final static long UPLOAD_TIME = 60000; // Tiempo minino entre recargas de datos

	private static ProcessCache instance;
	private long lastUpload = 0; // Ultima vez que la tabla fue poblada	
	private Hashtable<String, Process> processes;
	private final Logger log = Logger.getLogger(ProcessCache.class);

	// Instanciar el Singleton
	static {
		instance = new ProcessCache();
	}

	// Constructor (llamado una unica vez)
	private ProcessCache() {
		log.debug("Instanciando...");
		processes = new Hashtable<String, Process>(128);
	}

	// Referencia del singleton
	synchronized public static ProcessCache getInstance() {
		return instance;
	}

	/**
	 * Obtener el parametro del proceso
	 * 
	 * @param processName
	 * @return
	 */
	synchronized public Process getProcess(String processName) {
		Process proc = null;

		if (processName != null && processes != null) {
			proc = processes.get(processName);
		}

		return proc;
	}

	/**
	 * Obtener el parametro del proceso
	 * 
	 * @param processName
	 * @return
	 */
	synchronized public long getProcessID(String processName) {
		long id = 0;

		if (processName != null && processes != null) {
			Process proc = processes.get(processName);
			if (proc != null)
				id = proc.getIdprocessPk();
		}

		return id;
	}

	/**
	 * Obtener la ultima vez que la tabla fue poblada
	 * 
	 * @return
	 */
	synchronized public long getLastUpload() {
		return lastUpload;
	}

	// Cargar lista de parametros
	synchronized public void populate(List<Process> list) {

		if (processes != null && list != null) {
			long currentTime = System.currentTimeMillis();
			
			log.debug("Cargando procesos --> size="+list.size());
			processes.clear();
			for (Process p : list) {
				if (p != null)
					processes.put(p.getProcessnameChr(), p);
			}
			lastUpload = currentTime;
		}
	}
}
