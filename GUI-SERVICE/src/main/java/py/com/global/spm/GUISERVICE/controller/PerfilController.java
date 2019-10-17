package py.com.global.spm.GUISERVICE.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.Perfil.Password.ModifyPasswordDTO;
import py.com.global.spm.GUISERVICE.dto.Perfil.UserProfile;
import py.com.global.spm.GUISERVICE.services.ProfileService;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.validation.Valid;

/**
 * Controlador para el perfil de usuarios
 * @version 1.0
 * @author Global SI
 * @see BaseController
 */
@Api(tags = "Perfil")
@RestController
@RequestMapping(value = "/api/profile")
public class PerfilController extends BaseController<User, Long>{
    private static final Logger logger = LoggerFactory
            .getLogger(PerfilController.class);

    @Autowired
    private ProfileService service;

    @PostMapping(value = "/password/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = " hasRole('ROLE_MODIFICAR_USUARIO') ")
    @JsonView({ScopeViews.Basics.class})
    public Object changePassword(@Valid @RequestBody ModifyPasswordDTO request){
        try {
            return service.changePassword(request);

        }catch (Exception e) {
            logger.warn("Error al crear la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @GetMapping(value = "/view", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object viewUserProfile(){
        try {
            return service.getUserProfile();

        }catch (Exception e) {
            logger.warn("Error al crear la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView({ScopeViews.Basics.class})
    public Object editUserProfile(@Valid @RequestBody UserProfile request){
        try {
            return service.editBasicProfile(request);

        }catch (Exception e) {
            logger.warn("Error al crear la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
