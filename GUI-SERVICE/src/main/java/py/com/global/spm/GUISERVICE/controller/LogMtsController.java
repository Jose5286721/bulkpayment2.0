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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBReport;
import py.com.global.spm.domain.entity.Logmts;
import py.com.global.spm.GUISERVICE.services.LogMtsService;

import javax.validation.constraints.NotNull;
@Api(tags = "LogMts")
@RestController
@RequestMapping(value = "/api/logMts")

public class LogMtsController extends BaseController<Logmts, Long>{
    private static final Logger logger = LoggerFactory.getLogger(LogMtsController.class);
    @Autowired
    private LogMtsService logMtsService;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_REGISTRO_MTS')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getLogMtsListByFilter(@NotNull(message = "0030") @RequestBody CBReport cbLogMts,
                                                  @RequestParam(value="direction",required = false) String direction,
                                                  @RequestParam(value="properties",required = false) String properties,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        try {
            return logMtsService.filterLogMtsPag(cbLogMts, direction,
                    properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener la lista de log del Mts", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
