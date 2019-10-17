package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBAudit;
import py.com.global.spm.GUISERVICE.services.LogAuditService;

@Api(tags = "LogAudits")
@RestController
@RequestMapping(value = "api/logaudit")
public class LogAuditController {
    private static final Logger logger = LoggerFactory.getLogger(LogAuditController.class);

    @Autowired
    LogAuditService logauditService;

    /**
     * Servicio para obtener lista de auditoria
     * @return
     */
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllLogAudit() {
        try {
            return logauditService.getAllLogAudit();
        }  catch (Exception e) {
            logger.warn("Error al obtener Auditoria", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * Servicio para obtener lista de auditoria segun filtro
     * @param cbAudit
     * @param direction
     * @param properties
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getLogAuditListbyFilter(@RequestBody CBAudit cbAudit,
                                          @RequestParam(value= "direction",required = false) String direction,
                                          @RequestParam(value = "properties", required = false) String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size){
        try{
            return logauditService.getLogAuditByFilter(cbAudit, direction, properties, page, size);

        }catch (Exception e){
            logger.error("Error al obtener Auditoria");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }




}
