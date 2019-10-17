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
import py.com.global.spm.GUISERVICE.dto.CodigoEventos.CodigoEventoRequestDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBCodigoEventos;
import py.com.global.spm.GUISERVICE.services.CodigoEventosService;
import py.com.global.spm.domain.entity.Eventcode;

import javax.validation.Valid;

@Api(tags = "EventCodes")
@RestController
@RequestMapping(value = "/api/eventcode")
public class CodigoEventosController extends BaseController<Eventcode, Long>{
    private static final Logger logger = LoggerFactory
            .getLogger(SystemParameterController.class);

    @Autowired
    CodigoEventosService codigoEventosService;

    @PreAuthorize("hasRole('CONSULTAR_COD_EVENTO')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getWorkflowListByFilter(@Valid @RequestBody CBCodigoEventos cbSystemParameter,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        try {
            return codigoEventosService.getEventCodeByFilter(cbSystemParameter, direction, properties, page, size);
        } catch (Exception e) {
            logger.warn("Error al obtener los SystemParameters ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('MODIFICAR_COD_EVENTO')")
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editSystemParameter(@Valid @RequestBody CodigoEventoRequestDto request) {
        try {
            return codigoEventosService.editCodigoEvento(request);
        } catch (Exception e) {
            logger.error("Error al intentar editar el Codigo Evento", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('MODIFICAR_COD_EVENTO')")
    @PostMapping(value = "/getById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getCodigoEvento(@Valid @RequestBody CodigoEventoRequestDto request) {
        try {
            return codigoEventosService.getCodigoEventoService(request);
        } catch (Exception e) {
            logger.error("Error al intentar retornar el Codigo Evento", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
