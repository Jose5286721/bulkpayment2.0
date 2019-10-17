package py.com.global.spm.GUISERVICE.jmslistener;

import com.global.spm.notificationmanager.RequestNotificationManager;
import com.global.spm.notificationmanager.ResponseNotificationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.message.PinSmsNotificationMessage;
import py.com.global.spm.GUISERVICE.services.NotificationService;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.GUISERVICE.services.redis.UserPinLoginService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class PinSmsNotificationQueue {
    private static final Logger logger = LogManager
            .getLogger(PinSmsNotificationQueue.class);
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private UserPinLoginService userPinLoginService;

    @JmsListener(destination = SPM_GUI_Constants.PIN_LOGIN_NOTIFICATION_QUEUE, containerFactory = "myFactory")
    public void receive (PinSmsNotificationMessage request ){
        try {
            Random r = new SecureRandom();
            String pin = String.valueOf(100000+r.nextInt(899999));
            String message = systemParameterService.getSystemParameterValue(SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                    SPM_GUI_Constants.SYSTEM_PARAMETERS_AUTH2FACTOR_MESSAGE,SPM_GUI_Constants.DEFAULT_AUTH2FACTOR_MESSAGE);
            message = message.replace(SPM_GUI_Constants.AUHT2FACTOR_MESSAGE_PARAM_PIN,pin);

            RequestNotificationManager requestN = new RequestNotificationManager();
            requestN.setSessionId(request.getIdUser());
            requestN.setLogNotificationId(request.getIdUser());
            requestN.setSendEmail(false);
            requestN.setSendSms(true);
            requestN.setMessage(message);
            requestN.addAccount(request.getPhonenumberChr());
            ResponseNotificationManager response = notificationService.executeNotification(requestN);
            if(Boolean.TRUE.equals(response.getStatus()))
                userPinLoginService.createPinLogin(request.getIdUser(), pin);

        }catch (Exception e){
            logger.error("Error en la cola {}",SPM_GUI_Constants.PIN_LOGIN_NOTIFICATION_QUEUE,e);
        }
    }
}
