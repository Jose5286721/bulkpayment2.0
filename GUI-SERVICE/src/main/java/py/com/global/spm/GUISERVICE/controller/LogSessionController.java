package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogSession;
import py.com.global.spm.GUISERVICE.services.LogSessionService;

@Api(tags = "LogSession")
@RestController
@RequestMapping(value = "api/logsession")
public class LogSessionController {
    private static final Logger logger = LoggerFactory.getLogger(RolController.class);

    @Autowired
    LogSessionService logSessionService;


    /**
     * Servicio para obtener lista del log de sesiones segun filtro
     * @param cbLogSession
     * @param direction
     * @param properties
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getLogAuditListbyFilter(@RequestBody CBLogSession cbLogSession,
                                          @RequestParam(value= "direction",required = false) String direction,
                                          @RequestParam(value = "properties", required = false) String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size){
        try{
            return logSessionService.getLogSessionByFilter(cbLogSession, direction, properties, page, size);

        }catch (Exception e){
            logger.error("Error al obtener Auditoria");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }




}
