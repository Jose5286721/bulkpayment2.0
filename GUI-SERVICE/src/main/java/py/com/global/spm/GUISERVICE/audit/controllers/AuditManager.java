//package py.com.global.spm.GUISERVICE.audit.controllers;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.WebApplicationContext;
//import py.com.global.spm.GUISERVICE.model.Logaudit;
//import py.com.global.spm.GUISERVICE.model.User;
//import py.com.global.spm.GUISERVICE.services.LogAuditService;
//import py.com.global.spm.GUISERVICE.utils.EntityInterface;
//import javax.servlet.http.HttpServletRequest;
//import javax.faces.context.FacesContext;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Date;
//
///**
// * Created by global on 3/13/18.
// */
//@Component("auditManager")
////@AutoCreate
//@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
//public class AuditManager {
//
//    private User userLoggedIn;
//
//    @Autowired
//    private LogAuditService logAuditService;
//
//    private static final Logger logger= Logger.getLogger(AuditManager.class);
//
//
//    public <E extends EntityInterface> void guardar(E old, E e, String tipoAcceso,
//                                                    String accion) {
//        String msg = accion;
//        Logaudit auditoria = new Logaudit();
//        auditoria.setIpChr(obtainClientAddress());
//        auditoria.setAccesstypeChr(msg);
//        auditoria.setFechacreacionTim(new Date());
//        auditoria.setUsernameChr(userLoggedIn == null ? "System" : userLoggedIn.getEmailChr());
//        auditoria.setUser(userLoggedIn);
//        try {
//            E modif = obtenerModificados(old, e);
//            auditoria.setParamsChr("[ID="+e.getId()+"]"+" - "+ modif.toString());
//            auditoria.setDescriptionChr(msg + " "
//                    + modif.getClass().getSimpleName());
//            logAuditService.saveOrUpdate(auditoria);
//
//        } catch (Exception e1) {
//            logger.debug("guardar: ", e1);
//        }
//    }
//
//    /**
//     *
//     * @param old
//     * @param e
//     * @return
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     * @throws NoSuchMethodException
//     * @throws SecurityException
//     * @throws InvocationTargetException
//     * @throws IllegalArgumentException
//     */
//    @SuppressWarnings("unchecked")
//    private <E extends EntityInterface> E obtenerModificados(E old, E e)
//            throws InstantiationException, IllegalAccessException,
//            SecurityException, NoSuchMethodException, IllegalArgumentException,
//            InvocationTargetException {
//        if (old == null) {
//            return e;
//        }
//        if (e == null) {
//            return old;
//        }
//        E retorno = (E) e.getClass().newInstance();
//        Field[] atributos = e.getClass().getDeclaredFields();
//        String atributo_nombre = "";
//
//        for (int i = 0; i < atributos.length; i++) {
//            atributo_nombre = atributos[i].getName();
//            if (atributo_nombre.contains("serialVersionUID")
//                    || atributo_nombre.contains("default_interceptor")
//                    || atributo_nombre.contains("handler")
//                    || atributo_nombre.contains("method_filter")
//                    || atributo_nombre.contains("_methods_")) {
//                continue;
//            }
//            //System.out.println(atributos[i].getType());
//            String getterName = (atributos[i].getType().equals(boolean.class)?"is":"get")
//                    + atributo_nombre.substring(0, 1).toUpperCase()
//                    + atributo_nombre.substring(1);
//            String setterName = "set"
//                    + atributo_nombre.substring(0, 1).toUpperCase()
//                    + atributo_nombre.substring(1);
//
//            Method mGet = e.getClass().getMethod(getterName);
//            Class<?> tipo_retorno = mGet.getReturnType();
//            Method mSet = retorno.getClass()
//                    .getMethod(setterName, tipo_retorno);
//            if (!tipo_retorno.getName().contains("java.util.Set")
//                    && !tipo_retorno.getName().contains("java.util.List")) {
//                Object valorOld = mGet.invoke(old);
//                Object valorNew = mGet.invoke(e);
//
////				Id id = mGet.getAnnotation(Id.class);
////				EmbeddedId emId = mGet.getAnnotation(EmbeddedId.class);
//
////				if (id == null && emId==null) {
//                if (valorNew == null || valorNew.equals(valorOld)) {
//                    continue;
//                } else {
//                    mSet.invoke(retorno, valorNew);
//                }
////				} else {
////					mSet.invoke(retorno, valorNew);
////				}
//
//            }
//        }
//
//        return retorno;
//    }
//
//
//
//    public String obtainClientAddress() {
//        if (FacesContext.getCurrentInstance() == null) {
//            return "No IP";
//        }
//        final String behindProxy = FacesContext.getCurrentInstance()
//                .getExternalContext().getRequestHeaderMap()
//                .get("x-forwarded-for");
//        final String remoteAddr = ((HttpServletRequest) FacesContext
//                .getCurrentInstance().getExternalContext().getRequest())
//                .getRemoteAddr();
//        logger.debug("behindProxy: " + behindProxy);
//        logger.debug("remoteAddr: " + remoteAddr);
//        if (behindProxy != null) {
//            return behindProxy;
//        } else {
//            return remoteAddr;
//        }
//    }
//
//
//
//}
//
