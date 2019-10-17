package py.com.global.spm.model.managers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import py.com.global.spm.model.cache.PrefixDataTable;
import py.com.global.spm.model.interfaces.PrefixManagerLocal;
import py.com.global.spm.model.interfaces.SystemparameterManagerLocal;
import py.com.global.spm.model.systemparameters.GeneralParameters;
import py.com.global.spm.model.util.SpmConstants;

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
