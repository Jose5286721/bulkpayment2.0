package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.IUserDao;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Perfil.BasicProfile;
import py.com.global.spm.GUISERVICE.dto.Perfil.Password.ModifyPasswordDTO;
import py.com.global.spm.GUISERVICE.dto.Perfil.UserProfile;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.domain.utils.CryptoUtils;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.PrintResponses;
import py.com.global.spm.domain.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicios para manejo de perfil de usuarios
 * @see py.com.global.spm.GUISERVICE.controller.PerfilController
 * @version 1.0
 * @since 18-09-18
 * @author Global SI
 */
@Service
public class ProfileService {
    private static final Logger logger = LoggerFactory.
            getLogger(ProfileService.class);

    private IUserDao userDao;
    private UserService userService;
    private ResponseDtoService responseDtoService;
    private MessageUtil messageUtil;
    private PrintResponses printResponses;

    public ProfileService(IUserDao userDao, ResponseDtoService responseDtoService,
                          UserService userService, MessageUtil messageUtil, PrintResponses printResponses){
        this.userDao = userDao;
        this.responseDtoService= responseDtoService;
        this.userService = userService;
        this.messageUtil = messageUtil;
        this.printResponses = printResponses;
    }

    /**
     * Servicio para cambiar de contraseña desde el perfil de usuario
     * @param request: ModifypasswordDTO --> Cuerpo del request
     * @return ResponseDto
     */
    public ResponseDto changePassword(ModifyPasswordDTO request){
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> body = new HashMap<>();
        DataDTO dataDTO = new DataDTO();
        List<String> replaceText = new ArrayList<>();
        try {

            User user = userService.getLoggedUser();
            if (user == null) {
                replaceText.add("Usuario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            user.setPasswordChr(CryptoUtils.encryptToBCrypt(request.getPassword().trim()));
            if(user.isNewUser())user.setNewUser(false);

            userService.saveOrUpdate(user);
            //TODO enviar correo de que se cambio la contraseña
            //trySendNotification();
            replaceText.add("Contraseña");
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.EDITEXITOFEM.getValue(), replaceText));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;

        }catch (Exception e){
            logger.error("Error al intentar modificar contraseña: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    /**
     * Servicio para obtener perfil basico del usuario
     * @return ResponseDto
     */
    public ResponseDto getUserProfile(){
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> body = new HashMap<>();
        DataDTO dataDTO = new DataDTO();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        try{

            User user = userService.getLoggedUser();
            if(user == null){
                replaceText.add("Usuario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            replaceText.add("Perfil");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("perfil", setInitialBasicProfileValues(user));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;

        }catch(Exception e){
            logger.warn("Error en la obtencion de perfil", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }
    }

    private BasicProfile setInitialBasicProfileValues(User user){
        BasicProfile basicProfile = new BasicProfile();
        basicProfile.setUserName(user.getUsernameChr());
        basicProfile.setUserLastName(user.getUserlastnameChr());
        basicProfile.setUserJobTitle(user.getUserjobtitleChr());
        basicProfile.setFirmante(user.getEsFirmante());
        return basicProfile;

    }

    /**
     * Servicio para editar perfil basico del usuario
     * @param request: UserProfile
     * @return ResponseDto
     */
    public ResponseDto editBasicProfile(UserProfile request){
        ResponseDto responseDto = new ResponseDto();
        Map<String, Object> body = new HashMap<>();
        DataDTO dataDTO = new DataDTO();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        try{

            User user = userService.getLoggedUser();
            if (user == null){
                replaceText.add("Usuario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            user.setUsernameChr(request.getUserName());
            user.setUserlastnameChr(request.getUserLastName());
            if(request.getPassword()!=null && !request.getPassword().trim().isEmpty()) {
                user.setPasswordChr(CryptoUtils.encryptToBCrypt(request.getPassword().trim()));
                if (user.isNewUser()) user.setNewUser(false);
            }
            if(request.getSmspinChr()!=null && !request.getSmspinChr().trim().isEmpty() )
                user.setSmspinChr(CryptoUtils.encryptToBCrypt(request.getSmspinChr()));
            userService.saveOrUpdate(user);
            replaceText.add("Perfil");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;

        }catch (Exception e){
            logger.error("Error al intentar modificar perfil usuario: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }
    }
}
