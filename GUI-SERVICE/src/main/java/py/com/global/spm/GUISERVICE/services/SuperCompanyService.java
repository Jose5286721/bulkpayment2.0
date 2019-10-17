package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.security.service.CustomWebAuthenticationDetails;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.PrintResponses;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cbenitez on 3/20/18.
 */
@Transactional
@Service
public class SuperCompanyService {

    private static final Logger logger = LoggerFactory
            .getLogger(SuperCompanyService.class);

    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private PrintResponses printResponses;
    @Autowired
    private TokenUtil jwtTokenUtil;


    public Company getLoggedUserCompany(){
        try {
            CustomWebAuthenticationDetails usernamePasswordAuthenticationToken =(CustomWebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
            Long id=jwtTokenUtil.getIdCompanyFromToken(usernamePasswordAuthenticationToken.getToken());
            logger.debug("CompanyFromLoggedUser : {}",id);
            return companyService.getCompanyById(id);
        }catch (Exception e){
            logger.error("Error GetLoggedCompany", e);
        }
        return null;
    }
    public Long getLoggedUserIdCompany(){
        try {
            CustomWebAuthenticationDetails usernamePasswordAuthenticationToken =(CustomWebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
            Long id=jwtTokenUtil.getIdCompanyFromToken(usernamePasswordAuthenticationToken.getToken());
            logger.debug("CompanyFromLoggedUser : {}",id);
            return id;
        }catch (Exception e){
            logger.error("Error GetLoggedCompany", e);
        }
        return null;
    }

    public boolean isUserFromSuperCompany(User u) {
        Long idSuperCompany = systemParameterService.getSuperCompanyId();
        if (idSuperCompany == null || u == null) {
            return false;
        }
        List<Company> companies = u.getCompany();
        if (companies == null) {
            return false;
        }
        for(Company c: companies) {
            if (c != null && c.getIdcompanyPk().longValue() == idSuperCompany.longValue())
                    return true;
        }
        return false;
    }

    public boolean isLoggedUserFromSuperCompany() {
        User userLoggedIn = userService.getLoggedUser();
        return isUserFromSuperCompany(userLoggedIn);
    }

    public ResponseDto isLoggedUserFromSuperCompanyResponse() {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        replaceText.add("respuesta");
        mensaje = messageUtil.getMensaje(DataDTO.CODE.GETFEM.getValue(), replaceText);
        body.put("mensaje", mensaje);
        body.put("superCompany", isLoggedUserFromSuperCompany());
        dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
        dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
        dataDTO.setBody(body);
        responseDto.setData(dataDTO);
        logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
        return responseDto;


    }

    public Company getSuperCompany() {
        Long idSuperCompany = systemParameterService.getSuperCompanyId();
        if (idSuperCompany == null) {
            return null;
        }
        return companyService.getCompanyById(idSuperCompany);
    }

    public boolean isSuperCompany() {
        Long idSuperCompany = systemParameterService.getSuperCompanyId();
        User userLoggedIn = userService.getLoggedUser();
        List<Company> companies = userLoggedIn.getCompany();
        if (idSuperCompany == null || companies == null) {
            return false;
        }
        for(Company c: companies) {
            if (c != null && c.getIdcompanyPk().longValue() == idSuperCompany.longValue())
                return true;
        }
        return  false;
    }

}
