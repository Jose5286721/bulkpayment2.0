package py.com.global.spm.GUISERVICE.security.service;
import org.hibernate.validator.constraints.NotEmpty;
import py.com.global.spm.GUISERVICE.dto.Company.CompanyShortDto;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    private Object permissions;

    private List<CompanyShortDto> companies;

    private Boolean isNewUser;

    private Boolean isSuperUser;

    private Boolean sendSms;


    public AuthenticationResponse(String token, Object permissions, List<CompanyShortDto> companies, Boolean isNewUser, Boolean isSuperUser, Boolean sendSms) {
        this.token = token;
        this.permissions = permissions;
        this.companies = companies;
        this.isNewUser = isNewUser;
        this.isSuperUser = isSuperUser;
        this.sendSms = sendSms;
    }
    public AuthenticationResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public Object getPermissions() {
        return permissions;
    }

    public List<CompanyShortDto> getCompanies() {
        return companies;
    }

    public Boolean getNewUser() {
        return isNewUser;
    }

    public Boolean getSuperUser() {
        return isSuperUser;
    }

    public Boolean getSendSms() {
        return sendSms;
    }
}
