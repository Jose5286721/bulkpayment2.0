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
import org.springframework.web.multipart.MultipartFile;
import py.com.global.spm.domain.entity.ConfParameters;
import py.com.global.spm.GUISERVICE.services.TemplateExampleService;

import javax.servlet.http.HttpServletResponse;

//TODO plantear hacer mas generico (exponer en UtilController servicio para consultar fileNameParameter y asi poder subir manuales, etc)
@RestController
@RequestMapping(value = "/api/template/example")
@Api(tags = "Ejemplo de documentos")
public class TemplateExamplesController {
    private static final Logger logger = LoggerFactory.getLogger(TemplateExamplesController.class);

    @Autowired
    private TemplateExampleService service;

    /**
     *
     * @param file archivo a ser cargado
     * @param fileParameterName nombre del parametro ddae configuracion
     * {@link ConfParameters}
     */
    @PreAuthorize("hasRole('ROLE_MODIFICAR_EJEMPLO_DE_PLANTILLA')")
    @PostMapping(value = "/uploadfile")
    public Object uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileNameParameter") String fileParameterName) {
        try{
            return service.processExampleFile(file, fileParameterName);

        }catch (Exception e){
            logger.warn("Error al subir el archivo: " + file.getName(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     *
     * @param fileParameterName nombre del parametro de configuracion
     * {@link ConfParameters}
     */
    @PreAuthorize("hasRole('ROLE_EJEMPLO_DE_PLANTILLA')")
    @GetMapping(value = "/downloadfile", produces = MediaType.TEXT_PLAIN_VALUE)
    public void downloadExample(@RequestParam("fileNameParameter") String fileParameterName, HttpServletResponse response) {
        try{
            service.downloadExample(response, fileParameterName);
        }catch (Exception e){
            logger.warn("Error al descargar archivo", e);

        }
    }

}
