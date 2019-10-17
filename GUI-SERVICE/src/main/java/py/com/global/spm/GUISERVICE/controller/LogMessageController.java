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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogMessage;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.domain.entity.Logmessage;
import py.com.global.spm.GUISERVICE.services.LogMessageService;
import javax.validation.Valid;

@Api(tags = "Log Notificaciones")
@RestController
@RequestMapping(value = "/api/logMessage")
public class LogMessageController extends BaseController<Logmessage, Long>{
    public static final Logger logger = LoggerFactory.getLogger(LogMessageController.class);

    @Autowired
    private LogMessageService service;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_NOTIFICACIONES')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object logMtsListByFilter(@Valid @RequestBody CBLogMessage request,
                                         @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                         @RequestParam(value = "properties", defaultValue = "creationdateTim") String properties,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        try {
            return service.filterLogMessage(page, size, properties, direction, request);

        }catch (AuthorizationException e){
            throw e;
        }catch (Exception e) {
            logger.warn("Error al obtener la lista de notificaciones", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
