package py.com.global.spm.model.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.Beneficiary;
import py.com.global.spm.model.interfaces.BeneficiaryManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class BeneficiaryManager
 * 
 * @author Rodolfo Cardozo
 */
@Stateless
public class BeneficiaryManager implements BeneficiaryManagerLocal {
	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	
	private final Logger log = Logger.getLogger(BeneficiaryManager.class);

    /**
     * Default constructor. 
     */
    public BeneficiaryManager() {
    }
    
    public Beneficiary getBeneficiaryById(Long idBeneficiary) {
    	Beneficiary b = null;
		try {
			b = em.find(Beneficiary.class, idBeneficiary);
		} catch (Exception e) {
			log.error("Finding Beneficiary --> idBeneficiary=" + idBeneficiary, e);
			return null;
		}
		return b;
	}

}
