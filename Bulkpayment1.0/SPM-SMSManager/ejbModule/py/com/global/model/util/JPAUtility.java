package py.com.global.model.util;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtility {
    private static final Logger logger = Logger.getLogger(JPAUtility.class);
    private JPAUtility(){ }

    public static  JPAUtility newInstance(){
        return new JPAUtility();
    }

    private final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(SpmConstants.SPM_PU);

    public EntityManager getEntityManager(){
        return emFactory.createEntityManager();
    }
    public void close(){
        try {
            if (emFactory != null)
                emFactory.close();
        }catch (Exception e){
            logger.error("Al intentar cerrar: "+ emFactory.getClass());
            logger.error(e);
        }
    }
}
