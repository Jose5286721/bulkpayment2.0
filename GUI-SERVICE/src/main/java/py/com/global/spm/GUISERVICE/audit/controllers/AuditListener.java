//package py.com.global.spm.GUISERVICE.audit.controllers;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import py.com.global.spm.GUISERVICE.utils.EntityInterface;
//
//import javax.persistence.PostLoad;
//import javax.persistence.PrePersist;
//import javax.persistence.PreRemove;
//import javax.persistence.PreUpdate;
//
///**
// * Created by global on 3/13/18.
// */
//public class AuditListener {
//
//    AuditManager auditManager;
//    AuditController auditController;
//
//    @Autowired
//    BeanFactory beanFactory;
//
//    Logger logger= Logger.getLogger(AuditListener.class);
//
//    @PostLoad
//    public void guardarOld(EntityInterface item) {
//        try {
//
//            //	Lifecycle.beginCall();
//            auditController = beanFactory.getBean(AuditController.class);
//            if (auditController != null) {
//                auditController.guardarOld(item);
//            }
//            //Lifecycle.endCall();
//        } catch (Exception e) {
////            logger.error(e);
//        }
//    }
//
//    @PrePersist
//    public void persist(EntityInterface item) {
//        try {
//            //Lifecycle.beginCall();
//            auditManager = beanFactory.getBean(AuditManager.class);
//            auditManager.guardar(null, item, "audit_creacion", "audit_persist");
//            //Lifecycle.endCall();
//        } catch (Exception e) {
////            logger.error(e);
//        }
//    }
//
//    @PreUpdate
//    public void update(EntityInterface item) {
//        try {
//            //Lifecycle.beginCall();
//            auditManager = beanFactory.getBean(AuditManager.class);
//            auditController = beanFactory.getBean(AuditController.class);
//            if (auditController != null) {
//                auditManager.guardar(
//                        auditController.obtenerOld(item.getClass() + ""+item.getId()), item,
//                        "audit_actualizacion", "audit_update");
//            }
//            //Lifecycle.endCall();
//        } catch (Exception e) {
////            logger.error(e);
//        }
//    }
//
//    @PreRemove
//    public void remove(EntityInterface item) {
//        try {
//            //Lifecycle.beginCall();
//            auditManager = beanFactory.getBean(AuditManager.class);
//            auditManager.guardar(item, null, "audit_eliminacion", "audit_remove");
//            //Lifecycle.endCall();
//        } catch (Exception e) {
////            logger.error(e);
//        }
//    }
//}
