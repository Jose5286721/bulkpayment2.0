package py.com.global.spm.GUISERVICE.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class ServletRequestUtils {

    @Value("${recovery.link}")
    private String recoveryUrl;

    private ServletRequestUtils(){

    }
    public  String getUrlToRecovery(){
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String url = recoveryUrl.replace(SPM_GUI_Constants.RECOVERY_URL_PARAM_SCHEME,request.getScheme());
        url = url.replace(SPM_GUI_Constants.RECOVERY_URL_PARAM_IP,request.getServerName());
        url = url.replace(SPM_GUI_Constants.RECOVERY_URL_PARAM_CONTEXT_PATH,request.getContextPath());
        url = url.replace(SPM_GUI_Constants.RECOVERY_URL_PARAM_PORT,String.valueOf(request.getServerPort()));
        return url;
    }

}
