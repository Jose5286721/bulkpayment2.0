package py.com.global.spm.GUISERVICE.services.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.redis.IUserPinLoginDao;
import py.com.global.spm.GUISERVICE.dto.redis.UserPinLogin;
import py.com.global.spm.GUISERVICE.security.User;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import java.time.LocalDateTime;

@Service
public class UserPinLoginService {
    private static final Logger logger = LoggerFactory
            .getLogger(UserPinLoginService.class);
    @Autowired
    private IUserPinLoginDao dao;

    @Autowired
    private SystemParameterService systemParameterService;

    public void createPinLogin(Long idUser, String pin){
        String pinLoginValidateTim = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_PINLOGIN_VALIDATEPERIOD,
                SPM_GUI_Constants.DEFAULT_PINLOGIN_VALIDATEPERIOD);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusMinutes(Long.parseLong(pinLoginValidateTim));
        UserPinLogin userPinLogin = new UserPinLogin(idUser,pin,localDateTime,0);
        dao.save(userPinLogin);
    }
    public void save(UserPinLogin userPinLogin){
        dao.save(userPinLogin);
    }
    public UserPinLogin findById(Long id){
        return dao.findById(id).orElse(null);
    }

    public boolean isBlocked(UserPinLogin upl){
        String maxAttempts = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.SYSTEM_PARAMETERS_PINLOGIN_MAXATTEMPT,
                SPM_GUI_Constants.DEFAULT_PINLOGIN_MAXATTEMPT);
        LocalDateTime now  = LocalDateTime.now();

        return upl.getPinLoginAttemptNum()>= Integer.parseInt(maxAttempts)
                && now.isBefore(upl.getPinLoginValidateTim());

    }

    public void delete(Long id){
        dao.deleteById(id);
    }



}
