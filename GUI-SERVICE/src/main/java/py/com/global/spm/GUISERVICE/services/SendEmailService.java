package py.com.global.spm.GUISERVICE.services;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.SendEmailDto;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
import py.com.global.spm.GUISERVICE.utils.ServletRequestUtils;

//import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SendEmailService {

    private static final Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    private static final String  datePattern = "dd-MM-yyyy HH:mm:ss";
    @Autowired
    private EmailService emailService;

    @Autowired
    private  UserService userService;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private ServletRequestUtils servletRequestUtils;

    public ResponseDto sendEmail(String email) {
        Systemparameter systemparameter;
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        List<String> replaceText = new ArrayList<>();
        SendEmailDto sendEmailDto = new SendEmailDto();
        try {
            if (!userService.existUserByEmail(email)) {
                replaceText.add("usuario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            String url = servletRequestUtils.getUrlToRecovery();
            User user = userService.getUserByEmail(email);
            systemparameter = systemParameterService.findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_EXPIRED_TIME);
            Integer expiration = systemparameter!=null ? Integer.valueOf(systemparameter.getValueChr()) : null;
            final py.com.global.spm.GUISERVICE.security.User userDetails = (py.com.global.spm.GUISERVICE.security.User) userDetailsService.loadUserByUsername(email);
            String token = tokenUtil.generateTokenToRecovery(userDetails,expiration);
            user.setTokenChr(token);
            user.setTokenvalidateTim(tokenUtil.getExpirationDateFromToken(token));
            sendEmailDto.setToEmail(email);
            String redirectUrl = url.replace(SPM_GUI_Constants.USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TOKEN, token);
            systemparameter = systemParameterService.findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_MESSAGE);
            sendEmailDto.setMessage(systemparameter != null ? getMessageToSend(systemparameter.getValueChr(),redirectUrl,user.getTokenvalidateTim()): null);

            systemparameter = systemParameterService.findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_SUBJECT);
            sendEmailDto.setSubject(systemparameter != null ? systemparameter.getValueChr() : null);

            systemparameter = systemParameterService.findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_FROM_EMAIL);
            sendEmailDto.setFromEmail(systemparameter != null ? systemparameter.getValueChr() : null);

            emailService.sendChangePasswordEmail(sendEmailDto);
            userService.saveOrUpdate(user);
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OKSENDEMAIL.getValue(), null));
            dataDTO.setDataCode(DataDTO.CODE.OKSENDEMAIL.getValue());
            responseDto.setData(dataDTO);
            return responseDto;
        }catch (Exception e) {
            logger.warn("Error al enviar email de cambio de contraseña", e);
            replaceText.add("Error al enviar email");
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    private String getMessageToSend(String message, String redirectUrl, Date expirationDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        String message1 = message.replace(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_LINK, redirectUrl);
        return message1.replace(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_SECURITY_PASS_RECOVERY_NOTIFICATION_MAIL_TEMPLATE_EXPIRED_TIME,simpleDateFormat.format(expirationDate));
    }
}*/
