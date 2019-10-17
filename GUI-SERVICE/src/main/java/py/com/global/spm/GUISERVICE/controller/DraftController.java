package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBDraft;
import py.com.global.spm.GUISERVICE.dto.Draft.DraftAddDto;
import py.com.global.spm.GUISERVICE.dto.Draft.DraftEditDto;
import py.com.global.spm.GUISERVICE.exceptions.DraftAmountException;
import py.com.global.spm.domain.entity.Draft;
import py.com.global.spm.GUISERVICE.services.DraftDetailService;
import py.com.global.spm.GUISERVICE.services.DraftFileImporterCSV;
import py.com.global.spm.GUISERVICE.services.DraftService;


import javax.validation.Valid;

/**
 * @author christiandelgado on 18/06/18
 * @project GOP
 */

@Api(tags = "Borradores")
@RestController
@RequestMapping(value = "/api/drafts")
public class DraftController extends BaseController<Draft, Long>{

    private static final Logger logger = LogManager
            .getLogger(DraftController.class);

    @Autowired
    private DraftService draftService;
    @Autowired
    private DraftFileImporterCSV draftFileImporter;
    @Autowired
    private DraftDetailService draftDetailService;


    @PreAuthorize("hasRole('ROLE_MODIFICAR_BORRADOR')")
    @PostMapping(value = "/beneficiaries/validfile")
    public Object validFile(@RequestParam("file") MultipartFile file,
                            @RequestParam(value = "idCompany", required = false) Long idCompany,
                            @RequestParam("idWorkflow") Long idWorkflow) {
        try {
            return draftFileImporter.validFile(file, idCompany, idWorkflow);
        } catch (Exception e) {
            logger.warn("Error al subir el archivo csv", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_BORRADOR')")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
    public Object addDraft( @Valid @RequestBody DraftAddDto requestDto) {
        try {
            return draftService.agregarBorrador(requestDto);
        }catch (DraftAmountException e){
            throw e;
        }catch (Exception e) {
            logger.warn("Error al crear el borrador", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_BORRADOR')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getRolListByFilter(@Valid @RequestBody CBDraft cbDraft,
                                     @RequestParam("direction") String direction,
                                     @RequestParam("properties") String properties,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("size") Integer size) {
        try {
            return draftService.getDraftByFilter(cbDraft, direction, properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener los borradores", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_CONSULTAR_BORRADOR')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getDraftById(@PathVariable Long id) {
        try {
            return draftService.getDraftByIdForResponse(id);
        }catch (Exception e) {
            logger.error("Error al intentar obtener el borrador", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_BORRADOR')")
    @GetMapping(value = "/beneficiaries/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getBeneficiariesByDraft(@PathVariable Long id,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        try {
            return draftService.getBeneficiariesByDraft(id,direction,properties,page,size);
        }catch (Exception e) {
            logger.error("Error al intentar obtener el borrador", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_BORRADOR')")
    @DeleteMapping(value = "/details/draft/{iddraftPk}/beneficiary/{idbeneficiaryPk}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteDraftDetail(@PathVariable Long iddraftPk, @PathVariable Long idbeneficiaryPk) {
        try {
            return draftDetailService.eliminarDraftDetail(iddraftPk, idbeneficiaryPk);
        }catch (Exception e) {
            logger.error("Error al eliminar detalle del borrador", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_BORRADOR')")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object editarDraft(@PathVariable Long id, @Valid @RequestBody DraftEditDto requestDto) {
        try {
            return draftService.editarBorrador(id, requestDto);
        }catch (Exception e) {
            logger.warn("Error al crear el borrador", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
