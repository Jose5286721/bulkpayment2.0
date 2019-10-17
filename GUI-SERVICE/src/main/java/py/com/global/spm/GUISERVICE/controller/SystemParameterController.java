package py.com.global.spm.GUISERVICE.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBSystemParameter;
import py.com.global.spm.GUISERVICE.dto.SystemParameters.SystemParameterRequestDto;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.GUISERVICE.services.SystemParameterService;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.validation.Valid;


@Api(tags = "System Parameter")
@RestController
@RequestMapping(value = "/api/systemparameter")
public class SystemParameterController extends BaseController<Systemparameter, Long>{
    private static final Logger logger = LoggerFactory
            .getLogger(SystemParameterController.class);

    @Autowired
    SystemParameterService systemParameterService;

    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addSystemParameter(@Valid @RequestBody SystemParameterRequestDto request) {
        try {
            return systemParameterService.addSystemParameter(request);
        } catch (Exception e) {
            logger.warn("Error al crear el SystemParameter", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editSystemParameter(@Valid @RequestBody SystemParameterRequestDto request) {
        try {
            return systemParameterService.editSystemParamter(request);

        } catch (Exception e) {
            logger.error("Error al intentar editar el System Parameter", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteSystemParameter(@Valid @RequestBody SystemParameterRequestDto request) {
        try {
            return systemParameterService.deleteSystemParameter(request);

        } catch (Exception e) {
            logger.error("Error al intentar eliminar el System Parameter", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getWorkflowListByFilter(@Valid @RequestBody CBSystemParameter cbSystemParameter,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        try {
            return systemParameterService.getSystemParameterByFilter(cbSystemParameter, direction, properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener los SystemParameters ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/getById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getSystemParameter(@Valid @RequestBody SystemParameterRequestDto request) {
        try {
            return systemParameterService.getSystemparameterService(request);

        } catch (Exception e) {
            logger.error("Error al intentar retornar el System Parameter", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
