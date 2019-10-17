package py.com.global.spm.GUISERVICE.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBUser;
import py.com.global.spm.GUISERVICE.dto.User.UserAddDto;
import py.com.global.spm.GUISERVICE.dto.User.UserChangePasswordDto;
import py.com.global.spm.GUISERVICE.dto.User.UserEditDto;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.security.service.UserDetailsServiceImpl;
import py.com.global.spm.GUISERVICE.services.*;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by cbenitez on 3/19/18.
 */

@Api(tags = "Usuarios")
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController<User, Long>{

    private static final Logger logger = LogManager
            .getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    TokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    SuperCompanyService superCompanyService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ResponseDtoService responseDtoService;



    @PreAuthorize("hasRole('ROLE_CONSULTAR_USUARIO')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getUserListByFilter(@Valid @RequestBody CBUser cbUser,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size, HttpServletRequest request) {
        try {
            return userService.getUserByFilter(cbUser, direction,
                    properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener la lista de usuarios.", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_USUARIO')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getUserById(@PathVariable Long id) {
        try {
            return userService.getUserByIdForResponse(id);

        }catch (Exception e) {
            logger.error("Error al intentar obtener el usuario", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_USUARIO')")
    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object addUser(@Valid @RequestBody UserAddDto request) {
        try {
            return userService.addUser(request);
        }catch (Exception e) {
            logger.error("Error al intentar agregar el usuario", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_MODIFICAR_USUARIO')")
    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editUser(@Valid @RequestBody UserEditDto request) {
        try {
            return userService.editUser(request);
        }catch (Exception e) {
            logger.error("Error al intentar editar el usuario", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_CONSULTAR_USUARIO')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllUserByCompany(@RequestParam(value = "idCompany", required = false) Long companyId) {
        try {
            return userService.getAllUsersByCompany(companyId);

        }catch (Exception e) {
            logger.error("Error al intentar obtener lista de usuarios", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_WORKFLOW')")
    @GetMapping(value = "/usuariosFirmantes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllSignerUserByCompany(@RequestParam(value = "companyId", required = false) Long companyId){
        try {
            return userService.getUsersByFirmante(companyId);
        }catch (Exception e){
            logger.error("Error al intentar obtener la lista de usuarios", e);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CHANGE_PASSWORD')")
    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object changePassword(@RequestBody UserChangePasswordDto userChangePasswordDto, HttpServletRequest request){
        try {
            return userService.changePassword(request.getHeader("Authorization"),userChangePasswordDto.getNewPassword());
        }catch (Exception e){
            logger.error("Error al intentar cambiar la contraseña", e);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/superUser")
    public Object isSuperUser() {
        try {
            return userService.isLoggedUserSuperUserResponse();
        }catch (Exception e) {
            logger.error("Error al intentar obtener superCompany", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
