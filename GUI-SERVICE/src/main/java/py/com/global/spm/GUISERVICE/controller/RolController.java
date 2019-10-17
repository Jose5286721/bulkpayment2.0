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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBRol;
import py.com.global.spm.GUISERVICE.dto.Rol.RolAddDto;
import py.com.global.spm.GUISERVICE.dto.Rol.RolRequestDto;
import py.com.global.spm.domain.entity.Rol;
import py.com.global.spm.GUISERVICE.services.RolService;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.validation.Valid;

/**
 * Created by cbenitez on 3/22/18.
 */

@Api(tags = "Roles")
@RestController
@RequestMapping(value = "/api/rol")
public class RolController extends BaseController<Rol, Long>{

    private static final Logger logger = LogManager
            .getLogger(RolController.class);

    @Autowired
    RolService rolService;

    @PreAuthorize("hasRole('ROLE_MODIFICAR_ROL')")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addRol(@Valid @RequestBody RolAddDto request) {
        try {
            return rolService.addRol(request);
        }catch (Exception e) {
            logger.warn("Error al crear el rol", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_CONSULTAR_ROL')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getRolListByFilter(@Valid @RequestBody CBRol CBRol,
                                              @RequestParam("direction") String direction,
                                              @RequestParam("properties") String properties,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size) {
        try {
            return rolService.getRolByFilter(CBRol, direction, properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener los roles", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_ROL')")
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editRol(@Valid @RequestBody RolRequestDto request) {
        try {
            return rolService.editRol(request);
        }catch (Exception e) {
            logger.error("Error al intentar editar el rol", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_ROL')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getRolById(@PathVariable Long id) {
        try {
            return rolService.getRolByIdResponse(id);

        }catch (Exception e) {
            logger.error("Error al intentar obtener el rol", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllPermissions() {
        try {
            return rolService.getAllPermissions();
        }catch (Exception e) {
            logger.error("Error al intentar obtener los permisos", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_ROL')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllRoles() {
        try {
            return rolService.getRoles();
        }  catch (Exception e) {
            logger.warn("Error al obtener lista de roles", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView({ScopeViews.Basics.class})
    @GetMapping(value = "/permissions/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getPermissionNameForUser() {
        try {
            return rolService.getAllPermisosByUserLoggedIn();

        }catch (Exception e) {
            logger.error("Error al intentar obtener los permisos del usuario logueado", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_ROL')")
    @GetMapping(value = "{id}/permissions/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getPermissionsForRolSelected(@PathVariable("id") String rolId) {
        try {
            return rolService.getPermissionsSelectedForRol(Long.parseLong(rolId));
        }catch (Exception e) {
            logger.error("Error al intentar obtener los permisos", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
