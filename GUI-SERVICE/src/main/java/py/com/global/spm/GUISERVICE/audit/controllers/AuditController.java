//package py.com.global.spm.GUISERVICE.audit.controllers;
//
//import org.apache.log4j.Logger;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.WebApplicationContext;
//import py.com.global.spm.GUISERVICE.utils.EntityInterface;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by global on 3/13/18.
// */
//
//@Component("auditController")
//@Scope(value = WebApplicationContext.SCOPE_REQUEST)
//public class AuditController implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    private static final Logger logger= Logger.getLogger(AuditController.class);
//
//    public Map<String, EntityInterface> old;
//
//    public AuditController() {
//        old = new HashMap<String, EntityInterface>();
//    }
//
//    public void guardarOld(EntityInterface entity) {
//        try {
//            old.put(entity.getClass() + ""+entity.getId(), (EntityInterface) entity.clone());
//        } catch (CloneNotSupportedException e) {
//            logger.error("No se pudo clonar el entity "+entity);
//        }
//    }
//
//    public EntityInterface obtenerOld(String key) {
//        return old.get(key);
//    }
//
//}
