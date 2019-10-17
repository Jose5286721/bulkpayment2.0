package py.com.global.model.managers;

import org.jboss.logging.Logger;
import py.com.global.model.cache.PrefixDataTable;
import py.com.global.model.interfaces.PrefixManagerLocal;
import py.com.global.model.interfaces.SystemparameterManagerLocal;
import py.com.global.model.systemparameters.GeneralParameters;
import py.com.global.model.util.SpmConstants;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class PrefixManager
 * 
 * @author Lino Chamorro
 */
@Stateless
public class PrefixManager implements PrefixManagerLocal {

	@EJB
	private SystemparameterManagerLocal systemParameterManager;

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;

	private PrefixDataTable prefixesCache = PrefixDataTable.getInstance();

	private final Logger log = Logger.getLogger(PrefixManager.class);

	/**
	 * Default constructor.
	 */
	public PrefixManager() {
		log.debug("Instanciando...");
	}

	@SuppressWarnings("unchecked")
	private void populateCache() {
		try {
			if ((System.currentTimeMillis() - prefixesCache.getLastUpdate()) > systemParameterManager
					.getParameterValue(GeneralParameters.UPDATE_TIME,
							SpmConstants.UPDATE_TIME)) {
				log.trace("Loading prefixes");
				Query q = em.createNamedQuery("getAllPrefixes");
				prefixesCache.populate(q.getResultList());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public String prefixSubscriber(String idSubscriber) {
		this.populateCache();
		return prefixesCache.prefixSubscriber(idSubscriber);
	}

}
