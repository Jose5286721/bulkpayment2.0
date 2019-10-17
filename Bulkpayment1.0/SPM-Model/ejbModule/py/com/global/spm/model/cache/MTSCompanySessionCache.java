package py.com.global.spm.model.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Company;
import py.com.global.spm.model.util.MTSCompanySession;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public class MTSCompanySessionCache {

	private Logger log = Logger.getLogger(MTSCompanySessionCache.class);
	private Hashtable<Company, MTSCompanySession> cache;
	private static MTSCompanySessionCache instance;

	static {
		instance = new MTSCompanySessionCache();
	}

	private MTSCompanySessionCache() {
		super();
		this.cache = new Hashtable<Company, MTSCompanySession>();
	}

	public static MTSCompanySessionCache getInstance() {
		return instance;
	}

	public void addSessionToCache(Company company, String idSession) {
		MTSCompanySession session = cache.get(company);
		if (session != null) {
			log.warn("MTSCompanySession already added --> " + session);
		} else {
			session = new MTSCompanySession(company, idSession, 0);
			cache.put(company, session);
			log.info("Registering session --> " + session);
		}
	}

	synchronized public String getIdSession(Company company) {
		String idSession = null;
		MTSCompanySession session = cache.get(company);
		if (session != null) {
			idSession = session.getIdSession();
		} else {
			log.warn("GetIdSession Error. MTSCompanySession for company not found --> "
					+ company);
		}
		return idSession;
	}

	synchronized public boolean incrementUsedSession(Company company) {
		boolean success = false;
		MTSCompanySession session = cache.get(company);
		if (session != null) {
			session.incrementUsedSession();
			success = true;
		} else {
			log.error("IncrementUsedSession Error. MTSCompanySession for company not found --> "
					+ company);
		}
		return success;
	}

	synchronized public boolean decrementUsedSession(Company company) {
		boolean success = false;
		MTSCompanySession session = cache.get(company);
		if (session != null) {
			session.decrementUsedSession();
			if (session.getUsedSession() < 1) {
				log.warn("MTSCompanySession must be removed --> " + session);
			}
			success = true;
		} else {
			log.error("DecrementUsedSession Error. MTSCompanySession for company not found --> "
					+ company);
		}
		return success;
	}

	synchronized public boolean hasToRemoveUsedSession(Company company) {
		boolean hastToRemove = false;
		MTSCompanySession session = cache.get(company);
		if (session != null) {
			if (session.getUsedSession() < 1) {
				hastToRemove = true;
			}
		} else {
			log.error("HasToRemoveUsedSession Error. MTSCompanySession for company not found --> "
					+ company);

		}
		return hastToRemove;
	}

	public boolean removeSessionFromCache(Company company) {
		boolean success = false;
		MTSCompanySession session = cache.get(company);
		if (session != null) {
			cache.remove(company);
			log.info("MTSCompanySession removed! --> " + session);
		} else {
			log.error("RemoveSessionFromCache Error. MTSCompanySession already removed --> "
					+ company);
		}
		return success;
	}

	public List<MTSCompanySession> getExpiredMTSCompanySession(
			long timeoutInMillis) {
		List<MTSCompanySession> expiredList = new ArrayList<MTSCompanySession>();
		Collection<MTSCompanySession> allsession = cache.values();
		long now = System.currentTimeMillis();
		for (MTSCompanySession session : allsession) {
			if ((now - session.getLastUpdate().getTime()) > timeoutInMillis) {
				expiredList.add(session);
			}
		}
		return expiredList;
	}

}
