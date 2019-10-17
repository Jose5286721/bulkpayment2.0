package py.com.global.spm.GUISERVICE.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.redis.UserPinLogin;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.security.service.AuthenticationResponse;
import py.com.global.spm.GUISERVICE.security.service.UserDetailsServiceImpl;
import py.com.global.spm.GUISERVICE.services.*;
import py.com.global.spm.GUISERVICE.services.redis.UserPinLoginService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController

public class TokenController {

    private static final Logger logger = LogManager
            .getLogger(TokenController.class);

    private final UserPinLoginService userPinLoginService;

    private final UserService userService;

    private final TokenUtil jwtTokenUtil;

    private final CompanyService companyService;

    private final ResponseDtoService responseDtoService;

    private final SystemParameterService systemParameterService;

    private final UtilService utilService;

    private final UserDetailsServiceImpl userDetailsService;

    public TokenController(UserPinLoginService userPinLoginService, UserService userService,
                           TokenUtil tokenUtil, CompanyService companyService, ResponseDtoService responseDtoService,
                           SystemParameterService systemParameterService, UtilService utilService,
                           UserDetailsServiceImpl userDetailsService){
        this.userPinLoginService = userPinLoginService;
        this.userService = userService;
        this.jwtTokenUtil = tokenUtil;
        this.companyService = companyService;
        this.responseDtoService = responseDtoService;
        this.systemParameterService = systemParameterService;
        this.utilService = utilService;
        this.userDetailsService = userDetailsService;

    }

    @GetMapping(value = "validateToken",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object validateToken(@RequestParam("token") @NotNull(message = "0030") String token){
        try {
            if(jwtTokenUtil.isValidToken(token)){
                py.com.global.spm.domain.entity.User user = userService.getUserByEmail(jwtTokenUtil.getEmailFromToken(token));
                return token.equalsIgnoreCase(user.getTokenChr());
            }
            return false;
        }catch (Exception e) {
            logger.warn("Error al validar token", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/api/getTokenByCompany")
    public  Object getTokenBySelectedCompany(@RequestParam("id") Long idCompany, @RequestParam(value = "pin", required = false)String pin) {
        if (companyService.getLoggedUserIdCompany() == null) {
            String authToFactor = systemParameterService.getSystemParameterValue(SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                    SPM_GUI_Constants.SYSTEM_PARAMETERS_AUTH2FACTOR, "N");
            if (SPM_GUI_Constants.isTrue(authToFactor)) {
                py.com.global.spm.domain.entity.User user = userService.getLoggedUser();
                UserPinLogin upl = userPinLoginService.findById(user.getIduserPk());
                if (upl == null) {
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
                }
                if (LocalDateTime.now().isAfter(upl.getPinLoginValidateTim()))
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.EXPIREDPINDATE.getValue(), null);

                if (userPinLoginService.isBlocked(upl))
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.USERBLOCKED.getValue(), Stream.of(utilService.getTimeRemaining(LocalDateTime.now(), upl.getPinLoginValidateTim())).collect(Collectors.toList()));

                if (!pin.trim().equals(upl.getPinLoginChr())) {
                    upl.setPinLoginAttemptNum(upl.getPinLoginAttemptNum() + 1);
                    userPinLoginService.save(upl);
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPIN.getValue(), null);
                }
                userPinLoginService.delete(upl.getIduserPk());
            }
        }
        py.com.global.spm.domain.entity.User user2 = userService.getLoggedUser();
        if (!companyService.isCompanyInUser(user2, idCompany))
            return ResponseEntity.ok(responseDtoService.errorResponse(ErrorsDTO.CODE.NOCOMPANY.getValue(), null));

        final py.com.global.spm.GUISERVICE.security.User userDetails = (py.com.global.spm.GUISERVICE.security.User) userDetailsService.loadUserByUsername(user2.getEmailChr());
        final String token = jwtTokenUtil.generateTokenWithEmail(userDetails, idCompany);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
