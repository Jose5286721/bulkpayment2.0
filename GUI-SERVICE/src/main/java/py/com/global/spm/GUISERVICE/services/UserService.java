package py.com.global.spm.GUISERVICE.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import py.com.global.spm.GUISERVICE.dao.*;
import py.com.global.spm.GUISERVICE.dto.*;
import py.com.global.spm.GUISERVICE.dto.Company.CompanyShortDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBUser;
import py.com.global.spm.GUISERVICE.dto.User.*;
import py.com.global.spm.GUISERVICE.dto.utils.PasswordNotificationDto;
import py.com.global.spm.GUISERVICE.exceptions.ObjectNotFoundException;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.security.service.CustomWebAuthenticationDetails;
import py.com.global.spm.GUISERVICE.specifications.UserSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Rol;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.domain.utils.CryptoUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by cbenitez on 3/19/18.
 */
@Transactional
@Service
public class UserService {

    private static Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private IUserDao dao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private PrintResponses printResponses;

    @Autowired
    private RolService rolService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GeneralHelper generalHelper;

    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private IWorkflowDao workflowDao;

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private IApprovalDao approvalDao;

    /**
     * Persistir usuario
     *
     * @param usuario
     * @return {@link User}
     * @throws Exception
     */

    public User saveOrUpdate(User usuario) throws Exception {
        logger.info("Guardando usuario.. ");
        try {
            return dao.save(usuario);
        } catch (Exception e) {
            logger.error("Error al intentar guardar usuario.");
            throw e;
        }
    }


    public User getUserById(Long id) throws Exception {
        logger.info("Obteniendo usuario con id {}", id);
        try {
            return dao.findByiduserPk(id);
        } catch (Exception e) {
            logger.error("Error al intentar obtener el usuario. ", e);
            throw new Exception();
        }
    }

    public User getUserByEmail(String email) throws Exception {
        logger.info("Obteniendo usuario con email {}", email);
        try {
            return dao.findByEmailChr(email);
        } catch (Exception e) {
            logger.error("Error al intentar obtener el usuario. ", e);
            throw new Exception();
        }
    }


    public ResponseDto getUserByIdForResponse(Long id) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {

            if (id == null) {
                replaceText.add("usuario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            User theUser = getUserById(id);
            if(!(superCompanyService.isSuperCompany() || companyService.isCompanyInUser(theUser,companyService.getLoggedUserIdCompany()))){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }

            replaceText.add("usuario");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("usuario", addToUserResponseDto(theUser));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el usuario. ", e);
            throw e;
        }
    }

    /**
     * Obtener datos basicos de usuario logueado.
     *
     * @return {@link User}
     * @throws Exception
     */
    public User getLoggedUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return dao.findByEmailChr(email);
    }


    /**
     * Agrega un usuario al sistema
     *
     * @param
     * @return
     */
    public ResponseDto addUser(UserAddDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();
        User newUser = new User();

        try {
            List<Company> companyList = new ArrayList<>();
            if (superCompanyService.isLoggedUserFromSuperCompany()) {
                if (request.getIdcompany() == null) {
                    replaceText.add("empresa");
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
                }

                for(Long idCompany: request.getIdcompany()){
                    Company company=companyService.getCompanyById(idCompany);
                    if(company!=null)
                        companyList.add(company);
                }
                newUser.setCompany(companyList);
            } else {
                //Si no es un super usuario el que crea los usuarios, crea usuarios para su empresa.
                if (getLoggedUser().getCompany() == null) {
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.USERNOCOMPANYASOC.getValue(), null);
                }
                companyList.add(companyService.getLoggedUserCompany());
                newUser.setCompany(companyList);
            }

            if (Boolean.TRUE.equals(request.getSmssignNum()) && request.getSmspinChr() == null) {
                replaceText.add("PIN SMS");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }
            if (existUserByEmail(request.getEmailChr())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(), null);
            }

            if(existUserByUserName(request.getUsernameChr())){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(), null);
            }

            if(!generalHelper.isValidPhoneNumber(request.getPhonenumberChr())){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPHONE.getValue(),null);

            }
            newUser = saveOrUpdate(cargarNuevoUsuario(request, newUser));
            trySendNotification(newUser.getIduserPk(), newUser.getPasswordChr());
            replaceText.add("usuario");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("usuario", newUser);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar crear Usuario: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    /**
     * Edita un usuario
     *
     * @param
     * @return
     */
    public ResponseDto editUser(UserEditDto request) {
        boolean isSuperCompany = superCompanyService.isSuperCompany();
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();
        User usuario = dao.findByiduserPk(request.getIduserPk());
        Long assoWorkflows;
        List<Company> availableCompanies = (isSuperCompany)? companyDao.findAllByStateChr(SPM_GUI_Constants.ACTIVO): getLoggedUser().getCompany();
        List<Company> modifiedCompanies;

        try {
            if (usuario == null) {
                replaceText.add("usuario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }
            if (request.getEmailChr() == null || request.getUsernameChr() == null
                    || request.getUserlastnameChr() == null || request.getUserjobtitleChr() == null
                    || request.getPhonenumberChr() == null) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), null);
            }
            if (!usuario.getEmailChr().equals(request.getEmailChr().trim()) && existUserByEmail(request.getEmailChr())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(), null);
            } else if (!usuario.getEmailChr().equals(request.getEmailChr().toLowerCase().trim())) {
                usuario.setEmailChr(request.getEmailChr().toLowerCase().trim());
            }
            if (!usuario.getUsernameChr().equals(request.getUsernameChr().trim()) && existUserByUserName(request.getUsernameChr())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(), null);
            }else{
                usuario.setUsernameChr(request.getUsernameChr().trim());
            }
            /*
                Se anhade las empresas inactivas que vinculadas al usuario
             */
            modifiedCompanies = usuario.getCompany().stream().filter(company -> company.getStateChr().equals(SPM_GUI_Constants.INACTIVO))
                    .collect(Collectors.toList());

            for(Company company: availableCompanies){
                assoWorkflows = workflowDao.countByCompanyIdcompanyPkAndCompanyUsersIduserPkAndCompanyUsersEsFirmante(company.getIdcompanyPk(), usuario.getIduserPk(), true);
                if(!request.getIdcompany().contains(company.getIdcompanyPk())) {
                    if(assoWorkflows != 0) {
                        replaceText.add(usuario.getUsernameChr());
                        replaceText.add(company.getCompanynameChr());
                        return responseDtoService.errorResponse(ErrorsDTO.CODE.ASOCOMPANYWORKFLOW.getValue(), replaceText);
                    }
                }else
                    modifiedCompanies.add(company);
            }

            usuario.setCompany(modifiedCompanies);
            usuario.setUserlastnameChr(request.getUserlastnameChr());
            usuario.setUserjobtitleChr(request.getUserjobtitleChr());
            usuario.setStateChr(request.getStateChr());

            if (!generalHelper.isValidPhoneNumber(request.getPhonenumberChr())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPHONE.getValue(), null);
            }
            usuario.setPhonenumberChr(request.getPhonenumberChr());
            usuario.setSmssignNum(request.getSmssignNum());
            if (Boolean.TRUE.equals(request.getSmssignNum()) && request.getSmspinChr()!=null && !request.getSmspinChr().trim().isEmpty() ) {
                usuario.setSmspinChr(CryptoUtils.encryptToBCrypt(request.getSmspinChr().toLowerCase().trim()));
            }
            if (isSuperCompany ) {
                usuario.setCodigoequipohumanoNum(request.getCodigoequipohumanoNum());
            }

            if(usuario.getEsFirmante() != null && !usuario.getEsFirmante().equals(request.getEsFirmante())){
                if(approvalDao.countByWorkflowdetUser(usuario) > 0L){
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.ASOUSERAPPROVAL.getValue(), null);

                }
                workflowService.removeDetailsByUser(usuario);
                usuario.setEsFirmante(request.getEsFirmante());
            }
            if(isSuperUser() && request.getTemporaryPasswordChr()!=null) {
                usuario.setPasswordChr(CryptoUtils.encryptToBCrypt(request.getTemporaryPasswordChr().trim()));
                usuario.setNewUser(true);
            }
            usuario.setNotifysmsNum(request.getNotifysmsNum());
            usuario.setNotifyemailNum(request.getNotifyemailNum());
            Rol rol = rolService.getRolById(request.getRolId());
            if (rol == null) {
                replaceText.add("rol");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }

            if(!generalHelper.isValidPhoneNumber(request.getPhonenumberChr())){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPHONE.getValue(),null);

            }
            usuario.setRols(rol);
            usuario = saveOrUpdate(usuario);

            replaceText.add("usuario");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("usuario", usuario);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar crear Usuario: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }


    /**
     * Carga de nuevo usuario
     *
     * @param request
     * @param newUser
     * @return
     */
    private User cargarNuevoUsuario(UserAddDto request, User newUser) {
        Date now = new Date();
        newUser.setNewUser(true);
        newUser.setEmailChr(request.getEmailChr().toLowerCase().trim());
        newUser.setNotifyemailNum(request.getNotifyemailNum());
        newUser.setUsernameChr(request.getUsernameChr());
        newUser.setUserlastnameChr(request.getUserlastnameChr());
        newUser.setUserjobtitleChr(request.getUserjobtitleChr());
        newUser.setPhonenumberChr(request.getPhonenumberChr());
        newUser.setAttemptNum(0);
        newUser.setCodigoequipohumanoNum(request.getCodigoequipohumanoNum());
        newUser.setNotifysmsNum(request.getNotifysmsNum());
        //Password temporal
        newUser.setPasswordChr(CryptoUtils.encryptToBCrypt(request.getTemporaryPasswordChr()));
        newUser.setStateChr(SPM_GUI_Constants.ACTIVO);
        newUser.setCreationdateTim(now);
        newUser.setEsFirmante(request.getEsFirmante());
        /*
         * crear usuario con password expirado de tal manera que oblique a
         * cambiar el password
         */
        String passExpirationTimeStr = systemParameterService
                .getSystemParameterValue(
                        SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                        SPM_GUI_Constants.PASS_EXPIRATION_TIME,
                        SPM_GUI_Constants.DEFAULT_PASS_EXPIRATION_TIME
                        );
        long passExpirationTime = 0;
        try {
            // expiracion de password definido en horas
            passExpirationTime = Long.parseLong(passExpirationTimeStr);
        } catch (NumberFormatException e) {
            logger.error("Invalid passExpirationTime --> "
                    + passExpirationTimeStr);
        }

        passExpirationTime *= (3600 * 1000);
        newUser.setPasschangedateTim(new Date(now.getTime()
                - passExpirationTime));

        newUser.setSmssignNum(request.getSmssignNum());
        if (Boolean.TRUE.equals(request.getSmssignNum())) {
            //Pin encriptado a sha1 - re viejo
            newUser.setSmspinChr(CryptoUtils.encryptToBCrypt(request.getSmspinChr()));
            newUser.setSmspasswordChr(CryptoUtils.encryptToBCrypt(newUser.getSmspinChr()));
        }
        Rol rol = rolService.getRolById(request.getRolId());
        if (rol != null) {
            newUser.setRols(rol);
        }

        return newUser;
    }

    //TODO: validar respuesta al consumir servicio
    /**
     * Envio de Mensajes a traves de web service
     * @param id
     * @param password
     */
    private void trySendNotification(Long id, String password) {
        try {
                if (password == null)
                    throw new ObjectNotFoundException("Parametro de password invalido");
                if(id == null)
                    throw new ObjectNotFoundException("Parametro del usuario invalido");

                ResponseCoreDto response = restTemplate.postForObject(utilService.getCOREURL() + "/notification/passwordsms",
                        new PasswordNotificationDto(id, password), ResponseCoreDto.class);

        }catch (Exception e){
            logger.error("Al intentar enviar notificacion");
            logger.debug("Al intentar enviar notificacion: "+ e);
        }
    }


    public boolean existUserByEmail(String email) {
        if (email != null && !email.isEmpty()) {
            return dao.findByEmailChr(email.trim().toLowerCase()) != null;
        }
        return false;
    }


    public boolean existUserByUserName(String name){
        if (name != null && !name.isEmpty())
            return (dao.findByusernameChr(name.trim())) != null;
        return false;
    }
    public List<CompanyShortDto> companyToShortDto(User user){
        List<CompanyShortDto> companyShortDtos = new ArrayList<>();
        try {
            for(Company c: user.getCompany()){
                if(c.getStateChr().equals(SPM_GUI_Constants.ACTIVO))
                    companyShortDtos.add(new CompanyShortDto(c.getIdcompanyPk(), c.getCompanynameChr()));
            }
        }catch (Exception e){
            logger.error("companyToShortDto {}", e.getMessage());
        }
        return companyShortDtos;
    }

    /**
     * Lista de usuarios por criterios de busqueda
     *
     * @param cb
     * @param direction
     * @param properties
     * @param pagina
     * @param rows
     * @return
     */
    public ResponseDto getUserByFilter(@NotNull(message = "0030") CBUser cb, String direction,
                                       String properties,
                                       Integer pagina, Integer rows) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);

        try {

            logger.info("Criterios de busqueda. => {}", cb.toString());
            if (cb.getStateChr() == null) {
                replaceText.add("estado");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }

            Specification<User> where = Specification.where(UserSpecs.getByEstado(cb.getStateChr()));

            if (cb.getUsernameChr() != null) {
                where = where.and(UserSpecs.getByNombre(cb.getUsernameChr()));
            }

            if (cb.getEmailChr() != null) {
                where = where.and(UserSpecs.getByEmail(cb.getEmailChr()));
            }

            if (superCompanyService.isLoggedUserFromSuperCompany() && cb.getCompanyId() != null) {
                where = where.and(UserSpecs.getByCompanyId(cb.getCompanyId()));
            } else if (!superCompanyService.isLoggedUserFromSuperCompany() && getLoggedUser().getCompany() != null) {
                where = where.and(UserSpecs.getByCompanyId(companyService.getLoggedUserIdCompany()));
                where = where.and(UserSpecs.getByIsNotSuperUser());
            } else if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.USERNOCOMPANYASOC.getValue(), null);
            }
            Page<User> users = dao.findAll(where, pageRequest);

            String code;
            if (users.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("usuarios", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (users.getContent().isEmpty()) {
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("usuarios");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }

                body.put("message", respuesta);
                body.put("usuarios", addToUserListResponseDto(users.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(users.getSize(), users.getTotalPages(), users.getTotalElements()));
            logger.info("Se obtiene lista de {}. Total de elementos {}.", User.class.getSimpleName(),
                    users.getTotalElements());
            return responseDto;
        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de usuarios", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }
    public ResponseDto getUsersByFirmante(Long companyId){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        List<User> firmantes;
        try{
            logger.debug("Obteniendo Firmantes por empresa...");
            String code;
            if (!superCompanyService.isLoggedUserFromSuperCompany()){
                firmantes=dao.findByEsFirmanteAndCompanyIdcompanyPkAndStateChr(true,companyService.getLoggedUserIdCompany(),SPM_GUI_Constants.ACTIVO);
            }else{
                firmantes=dao.findByEsFirmanteAndCompanyIdcompanyPkAndStateChr(true, companyId,SPM_GUI_Constants.ACTIVO);
            }
            if(ListHelper.hasElements(firmantes)){
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                replaceText.add("usuarios");
                respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                body.put("message", respuesta);
                body.put("usuarios", addToUserListResponseDto(firmantes));
                dataDTO.setBody(body);
            }else{
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("usuarios", null);
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            return responseDto;
        } catch (Exception e) {
         logger.warn("Error en la generacion de lista de firmantes", e);
         replaceText.add("Error Obteniendo Firmantes");
        return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
       }
    }
    private Sort ordenamiento(String direction, String properties) {
        String dire = direction;
        String prope = properties;

        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "iduserPk";
        }

        logger.info("Ordenar lista por {} en orden {}", prope, dire);
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    /**
     * Carga el userDTO que tendra la respuesta
     *
     * @param usuario
     * @return dto
     */
    public UserDto addToUserResponseDto(User usuario) {

        UserDto dto = new UserDto();
        dto.setIduserPk(usuario.getIduserPk());
        dto.setEmailChr(usuario.getEmailChr());
        dto.setUsernameChr(usuario.getUsernameChr());
        dto.setStateChr(usuario.getStateChr());
        dto.setUserlastnameChr(usuario.getUserlastnameChr());
        dto.setUserjobtitleChr(usuario.getUserjobtitleChr());
        dto.setPhonenumberChr(usuario.getPhonenumberChr());
        dto.setSmssignNum(usuario.isSmssignNum());
        dto.setCodigoequipohumanoNum(usuario.getCodigoequipohumanoNum());
        dto.setNotifysmsNum(usuario.isNotifysmsNum());
        dto.setNotifyemailNum(usuario.isNotifyemailNum());
        dto.setCompany(companyService.getLoggedUserCompany());
        dto.setCompanies(companyToShortDto(usuario));
        dto.setCreationdateTim(usuario.getCreationdateTim());
        dto.setEsFirmante(usuario.getEsFirmante());
        dto.setRol(usuario.getRols());
        return dto;
    }
    public ResponseDto changePassword(String token, String newPassword){
        List<String> replaceText = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        try {
            String email = jwtTokenUtil.getEmailFromToken(token);
            User user = dao.findByEmailChr(email);
            if(!token.equals(user.getTokenChr()))
                return responseDtoService.errorResponse(ErrorsDTO.CODE.DENIEDACCES.getValue(),null);
            if(newPassword.isEmpty()){
                replaceText.add("contraseña");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(),replaceText);
            }
            //TODO: Parametrizar la expresion regular
            Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$");
            Matcher mat = pattern.matcher( newPassword );
            if(!mat.matches()) {
                replaceText.add("contraseña");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPASSWORD.getValue(), replaceText);
            }
            user.setPasswordChr(CryptoUtils.encryptToBCrypt(newPassword.trim()));
            user.setTokenChr(null);
            user.setTokenvalidateTim(null);
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            responseDto.setData(dataDTO);
            return responseDto;
        }catch (Exception e) {
            logger.warn("Error en el cambio de contraseña", e);
            replaceText.add("Error cambiando contraseña");
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    /**
     * Carga userDTO para la respuesta
     *
     * @param userList
     * @return
     */
    public List<UserResponseListDto> addToUserListResponseDto(List<User> userList) {

        List<UserResponseListDto> dtoList = new ArrayList<>();

        if (ListHelper.hasElements(userList)) {
            for (User usuario : userList) {
                UserResponseListDto dto = new UserResponseListDto();
                dto.setIduserPk(usuario.getIduserPk());
                dto.setEmailChr(usuario.getEmailChr());
                dto.setUsernameChr(usuario.getUsernameChr());
                dto.setStateChr(usuario.getStateChr());
                if (usuario.getCompany() != null) {
                    for(Company c: usuario.getCompany()) {
                        if(dto.getCompanyNameChr() ==null){
                            dto.setCompanyNameChr(c.getCompanynameChr());
                        }else {
                            dto.setCompanyNameChr(dto.getCompanyNameChr()+", "+c.getCompanynameChr());
                        }

                    }
                }
                dto.setUserlastnameChr(usuario.getUserlastnameChr());
                dto.setPhonenumberChr(usuario.getPhonenumberChr());
                dto.setCodigoequipohumanoNum(usuario.getCodigoequipohumanoNum());
                dto.setCreationdateTim(usuario.getCreationdateTim());
                dto.setEsFirmante(usuario.getEsFirmante());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * Lista de usuarios de la empresa enviada como parametro, o en caso de ser nulo
     * y no pertenecer a una super empresa, se retorna lista de usuarios de la empresa del usuario logueado
     * @return ResponseDto
     */
    public ResponseDto getAllUsersByCompany(Long companyId) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<User> userList;
        String mensaje;
        try {
            if(superCompanyService.isLoggedUserFromSuperCompany()){
                userList = dao.findByCompanyIdcompanyPk(companyId);
            }else{
                userList = dao.findByCompanyIdcompanyPk(companyService.getLoggedUserIdCompany());
            }

            if (ListHelper.hasElements(userList)) {
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            } else {
                replaceText.add("usuarios");
                mensaje = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("usuarios", addToUserListResponseDto(userList));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de usuarios. ", e);
            return null;
        }
    }

    public UserResponseListDto addUserResponseDto(User usr) {
        UserResponseListDto responseListDto = new UserResponseListDto();
        if (usr != null) {
            responseListDto.setIduserPk(usr.getIduserPk());
            if (usr.getCompany() != null) {
                for(Company c: usr.getCompany()) {
                    if(responseListDto.getCompanyNameChr() ==null){
                        responseListDto.setCompanyNameChr(c.getCompanynameChr());
                    }else {
                        responseListDto.setCompanyNameChr(responseListDto.getCompanyNameChr()+", "+c.getCompanynameChr());
                    }

                }
            }
            responseListDto.setUserlastnameChr(usr.getUserlastnameChr());
            responseListDto.setEmailChr(usr.getEmailChr());
            responseListDto.setStateChr(String.valueOf(usr.getStateChr()));
            responseListDto.setUsernameChr(usr.getUsernameChr());
            responseListDto.setPhonenumberChr(usr.getPhonenumberChr());
            responseListDto.setCodigoequipohumanoNum(usr.getCodigoequipohumanoNum());
            responseListDto.setCreationdateTim(usr.getCreationdateTim());
        }
        return responseListDto;
    }

    public boolean isSuperUser(){
        Long idSuperUser = systemParameterService.getSuperUserId();
        Long idLoggedUser = getLoggedUser().getIduserPk();
        return idLoggedUser.equals(idSuperUser);
    }

    public ResponseDto isLoggedUserSuperUserResponse() {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        replaceText.add("respuesta");
        mensaje = messageUtil.getMensaje(DataDTO.CODE.GETFEM.getValue(), replaceText);
        body.put("mensaje", mensaje);
        body.put("superUser", isSuperUser());
        dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
        dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
        dataDTO.setBody(body);
        responseDto.setData(dataDTO);
        logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
        return responseDto;


    }

}
