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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBProceso;
import py.com.global.spm.GUISERVICE.dto.SystemParameters.ProcesoDto;
import py.com.global.spm.GUISERVICE.services.ProcesosService;

import javax.validation.Valid;

@Api(tags = "Procesos")
@RestController
@RequestMapping(value = "/api/system/proceso")
public class ProcesosController extends BaseController<Process, Long>{

    private static final Logger logger = LoggerFactory
            .getLogger(ProcesosController.class);


    @Autowired
    ProcesosService procesosService;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_PROCESO')")
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllProcesses() {
        try {
            return procesosService.getAllProcesos();
        } catch (Exception e) {
            logger.error("Error al intentar obtener los Procesos", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_CONSULTAR_PROCESO')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getProcesosListByFilter(@Valid @RequestBody CBProceso cbProceso,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        try {
            return procesosService.getProcesoByFilter(cbProceso, direction, properties, page, size);
        } catch (Exception e) {
            logger.warn("Error al obtener los Procesos ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_MODIFICAR_PROCESO')")
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editWorkflow(@Valid @RequestBody ProcesoDto request) {
        try {
            return procesosService.editProceso(request);

        } catch (Exception e) {
            logger.error("Error al intentar editar el proceso", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_CONSULTAR_PROCESO')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getProceso(@PathVariable Long id) {
        try {
            return procesosService.getProcesoService(id);
        } catch (Exception e) {
            logger.warn("Error al obtener el Proceso: "+id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
