package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogdraftOp;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.domain.entity.Logdraftop;
import py.com.global.spm.GUISERVICE.services.LogDraftOpService;

import javax.validation.Valid;

@Api(tags = "Log Draftop")
@RestController
@RequestMapping(value = "/api/logdraftop")
public class LogDraftOpController extends BaseController<Logdraftop, Long>{
    private static final Logger logger = LoggerFactory.getLogger(LogDraftOpController.class);

    @Autowired
    private LogDraftOpService service;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_REGISTRO_DE_GEN_OP')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getLogDraftOpByFilter(@Valid @RequestBody CBLogdraftOp request,
                                         @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                         @RequestParam(value = "properties", defaultValue = "creationdateTim") String properties,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size) {
        try {
            return service.filterLogDraftOp(page, size, properties, direction, request);

        }catch (AuthorizationException e){
            throw e;
        }catch (Exception e) {
            logger.warn("Error al obtener la lista de log del draftop", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
