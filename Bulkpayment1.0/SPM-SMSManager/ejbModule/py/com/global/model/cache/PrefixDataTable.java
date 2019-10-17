package py.com.global.model.cache;

import org.apache.log4j.Logger;
import py.com.global.spm.entities.Prefix;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class PrefixDataTable {

	private long lastUpdate = 0; // Ultima vez que la tabla fue poblada
	private static PrefixDataTable instance = null;
	private List<Prefix> cache;
	private final Logger log = Logger.getLogger(PrefixDataTable.class);

	// Instanciar el Singleton
	static {
		instance = new PrefixDataTable();
	}

	/**
	 * Constructor
	 */
	public PrefixDataTable() {
		cache = new ArrayList<Prefix>();
		lastUpdate = 0L;
		log.debug("Instanciando tabla en memoria...");
	}

	/**
	 * Referencia del singleton
	 * 
	 * @return
	 */
	public static PrefixDataTable getInstance() {
		return instance;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Cargando tabla en memoria
	 * 
	 * @param list
	 */
	public void populate(List<Prefix> list) {
		synchronized (cache) {
			cache.clear();
			for (Prefix p : list) {
				cache.add(p);
			}
			lastUpdate = System.currentTimeMillis();
		}
		log.trace("Prefixes loaded --> " + cache.size());
	}

	public String prefixSubscriber(String idSubscriber) {
		log.trace("Prefixing subscriber --> idSubscriber=" + idSubscriber);
		boolean found = false;
		String aux = idSubscriber;
		for (Prefix px : cache) {
			if (idSubscriber.length() >= px.getPrefixPk().length()) {
				if (px.getPrefixPk().equals(
						idSubscriber.substring(0, px.getPrefixPk().length()))
						&& idSubscriber.length() == px.getLengthNum()) {
					idSubscriber = px.getNewprefixChr()
							+ idSubscriber.substring(px.getPrefixPk().length());
					found = true;
					break;
				}
			}
		}
		if (!found) {
			log.error("Invalid idSubscriber --> "
					+ "Prefix not found, idSubscriber=" + idSubscriber);
			return null;
		}
		log.debug("Prefixing subscriber --> idSubscriber=" + aux
				+ ", idSubscriberPrefixed=" + idSubscriber);
		return idSubscriber;
	}
}
