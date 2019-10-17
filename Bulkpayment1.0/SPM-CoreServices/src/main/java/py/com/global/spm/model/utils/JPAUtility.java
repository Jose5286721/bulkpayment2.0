package py.com.global.spm.model.utils;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class JPAUtility {
    private static final Logger logger = Logger.getLogger(JPAUtility.class);
    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(SpmConstants.SPM_PU);

    static EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }
    static void close(){
        try {
            if (emFactory != null)
                emFactory.close();
        }catch (Exception e){
            logger.error("Al intentar cerrar: "+ emFactory.getClass());
            logger.error(e);
        }
    }
}
