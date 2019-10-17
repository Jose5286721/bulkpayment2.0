package py.com.global.spm.model.managers;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.*;

import org.apache.log4j.Logger;

import py.com.global.spm.entities.User;
import py.com.global.spm.model.interfaces.UserManagerLocal;
import py.com.global.spm.model.util.SpmConstants;

/**
 * Session Bean implementation class UserManager
 * 
 * @author Rodolfo Cardozo
 */
@Stateless
public class UserManager implements UserManagerLocal {

	@PersistenceContext(unitName = SpmConstants.SPM_PU)
	private EntityManager em;
	@Resource
	private SessionContext context;

	private final Logger log = Logger.getLogger(UserManager.class);

	/**
	 * Default constructor.
	 */
	public UserManager() {
	}

	public User getUserById(Long idUser) {
		User u = null;
		try {
			u = em.find(User.class, idUser);
		} catch (Exception e) {
			log.error("Finding user --> idUser=" + idUser, e);
			return null;
		}
		return u;
	}

	@Override
	public boolean updateUser(User user) {
		try {
			log.debug("Updating user --> " + user);
			em.merge(user);
			return true;
		} catch (Exception e) {
			context.setRollbackOnly();
			log.error("updating user --> " + user, e);
		}
		return false;
	}

	@Override
	public User getSingleUserByPhoneNumber(String phoneNumber) {
		log.debug("Getting single user by phonenumber --> phoneNumber="
				+ phoneNumber);
		User u = null;
		try {
			Query q = em.createNamedQuery("getUserByPhoneNumber");
			q.setParameter("phonenumber", phoneNumber);
			u = (User) q.getSingleResult();
		} catch (NoResultException nre) {
			log.warn("No user found --> phoneNumber=" + phoneNumber);
		} catch (NonUniqueResultException e) {
			log.error("Existe mas de un usuario con el mismo nro de telefono --> phoneNumber="
					+ phoneNumber);
		} catch (Exception e) {
			log.error("Getting user by phonenumber --> phoneNumber="
					+ phoneNumber, e);
		}
		return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByMisdn(String msisdn) {
		log.debug("Getting users by msisdn --> msisdn=" + msisdn);
		List<User> list = null;
		try {
			String hpql = "SELECT u FROM User u WHERE u.phonenumberChr = :msisdn";
			Query q = em.createQuery(hpql);
			q.setParameter("msisdn", msisdn);
			list = q.getResultList();
		} catch (Exception e) {
			log.error("Getting users by msisdn --> msisdn=" + msisdn, e);
		}
		return list;
	}

}
