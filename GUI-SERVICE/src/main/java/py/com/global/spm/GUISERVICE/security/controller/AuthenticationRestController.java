package py.com.global.spm.GUISERVICE.security.controller;

import io.swagger.annotations.Api;
import org.hibernate.validator.constraints.Email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.Company.CompanyShortDto;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.redis.UserPinLogin;
import py.com.global.spm.GUISERVICE.message.PinSmsNotificationMessage;
import py.com.global.spm.GUISERVICE.security.service.CustomUsernamePasswordAuthenticationToken;
import py.com.global.spm.GUISERVICE.security.service.UserDetailsServiceImpl;
import py.com.global.spm.GUISERVICE.services.redis.UserPinLoginService;
import py.com.global.spm.GUISERVICE.utils.CaptchaGenerator;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.GUISERVICE.security.AuthenticationRequest;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.security.User;
import py.com.global.spm.GUISERVICE.security.service.AuthenticationResponse;
import py.com.global.spm.GUISERVICE.services.*;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */

@Api(tags = "Autenticación")
@RestController
public class AuthenticationRestController{
    private static final Logger logger = LogManager
            .getLogger(AuthenticationRestController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UserService userService;

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private UserPinLoginService userPinLoginService;

    @Autowired
    private PasswordEncoder encoder;



    @PostMapping(value = "${jwt.route.authentication.path}")
    public Object createAuthenticationToken(@Valid
            @RequestBody AuthenticationRequest authenticationRequest, Device device,HttpServletRequest request) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new CustomUsernamePasswordAuthenticationToken(
                        authenticationRequest.getClientId(),
                        authenticationRequest.getKey(),
                       authenticationRequest.getCaptchaEncode(),
                       authenticationRequest.getUserInputCaptchaValue()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try{
            Object permissions ;
            List<CompanyShortDto> companyShortDtos;
            py.com.global.spm.domain.entity.User user = userService.getUserByEmail(authenticationRequest.getClientId());
            final User userDetails = (User) userDetailsService.loadUserByUsername(user.getEmailChr());
            Boolean isSuperUser = userService.isSuperUser();
            String token = jwtTokenUtil.generateTokenWithEmail(userDetails, device, null);
            if(user.isNewUser())
                return new AuthenticationResponse(token, null,null, true,null,null);
            ResponseDto dto = rolService.getAllPermisosByUserLoggedIn();
            if(dto.getData()!=null) {
                permissions = rolService.getAllPermisosByUserLoggedIn()
                        .getData()
                        .getBody()
                        .get("permisos");
            }else
                return ResponseEntity.ok(dto);
            if(superCompanyService.isSuperCompany()){
                Company company =superCompanyService.getSuperCompany();
                companyShortDtos = new ArrayList<>(Arrays.asList(new CompanyShortDto(company.getIdcompanyPk(),company.getCompanynameChr())));
            }else {
                companyShortDtos= userService.companyToShortDto(user);
                if(companyShortDtos.isEmpty())
                    return ResponseEntity.ok(responseDtoService.errorResponse(ErrorsDTO.CODE.USERNOCOMPANYASOC.getValue(),null));
            }

            String authToFactor = systemParameterService.getSystemParameterValue(SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                    SPM_GUI_Constants.SYSTEM_PARAMETERS_AUTH2FACTOR,"N");

            if(SPM_GUI_Constants.isTrue(authToFactor)){
                UserPinLogin upl = userPinLoginService.findById(user.getIduserPk());
                if(upl != null && userPinLoginService.isBlocked(upl))
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.USERBLOCKED.getValue(),Stream.of(utilService.getTimeRemaining(LocalDateTime.now(),upl.getPinLoginValidateTim())).collect(Collectors.toList()));

                PinSmsNotificationMessage message = new PinSmsNotificationMessage();
                message.setIdUser(user.getIduserPk());
                message.setPhonenumberChr(user.getPhonenumberChr());

                jmsTemplate.setPriority(SPM_GUI_Constants.MAX_PRIORITY);
                jmsTemplate.convertAndSend(SPM_GUI_Constants.PIN_LOGIN_NOTIFICATION_QUEUE,message);

                return new AuthenticationResponse(token, permissions,companyShortDtos,null,isSuperUser,true);
            }
            return new AuthenticationResponse(token, permissions,companyShortDtos,null,isSuperUser,false);

        }catch (Exception e){
            logger.error("Error al iniciar sesion",e);
            return responseDtoService.errorResponse(ErrorsDTO.CODE.CONNECTION.getValue(),null);
        }
    }

    @GetMapping(value = "/captcha/getImage",produces = MediaType.IMAGE_JPEG_VALUE)
    public  @ResponseBody byte[] getCaptcha(HttpServletResponse response) {
        byte[] captchaImage = new byte[0];
        try {
            final ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            // Creación de la imagen de captcha
            String textCode = CaptchaGenerator.generateCaptchaText(4);
            final BufferedImage challenge = CaptchaGenerator.generateImage(textCode,
                    170,50,2, Color.WHITE);

            // Codificamos la imagen en JPEG
            ImageIO.write( challenge, "jpg", jpegOutputStream );

            // Guardamos la imagen como un flujo de bytes
            captchaImage = jpegOutputStream.toByteArray();
            response.addHeader("CaptchaEncode",encoder.encode(textCode));

        }catch (Exception e){
            logger.error("Al generar captcha",e);
        }
        return captchaImage;
    }

//    @GetMapping(value = "forgot",produces = MediaType.APPLICATION_JSON_VALUE)
//    public Object forgotPassword(@RequestParam("email") @Email(message = "0028") String email){
//        try {
//            return null;//sendEmailService.sendEmail(email);
//        }catch (Exception e) {
//                logger.warn("Error al enviar email de cambio de contraseña ", e);
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
