package py.com.global.spm.GUISERVICE.audit.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import py.com.global.spm.domain.entity.Logsession;
import py.com.global.spm.GUISERVICE.services.LogSessionService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

@Component
public class LoggerListener implements ApplicationListener<AbstractAuthenticationEvent>{
        private Logger logger= LogManager.getLogger(LoggerListener.class);

        @Autowired
        private LogSessionService logSessionService;

        @Override
        public void onApplicationEvent(AbstractAuthenticationEvent event) {
                try {
                        Logsession logsession = new Logsession();
                        WebAuthenticationDetails details = (WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
                        logsession.setIpChr(details.getRemoteAddress());
                        logsession.setUserChr(event.getAuthentication().getName());
                        logsession.setSuccessloginNum(event.getAuthentication().isAuthenticated());
                        if (event instanceof AuthenticationSuccessEvent) {
                                logsession.setMessageChr(SPM_GUI_Constants.MESSAGE_SUCCES_LOGIN);
                        } else if (event instanceof AuthenticationFailureBadCredentialsEvent) {
                                logsession.setMessageChr(SPM_GUI_Constants.MESSAGE_FAILED_LOGIN);
                        }
                        logSessionService.saveOrUpdate(logsession);
                }catch (Exception e){
                        logger.error("Error al guardar Session ",e);
                }
        }
}
