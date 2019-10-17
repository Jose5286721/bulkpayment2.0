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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBBeneficiario;
import py.com.global.spm.GUISERVICE.services.BeneficiarioService;
import py.com.global.spm.domain.entity.Beneficiary;

import javax.validation.Valid;

@Api(tags = "Beneficiarios")
@RestController
@RequestMapping(value = "/api/beneficiaries")
public class BeneficiarioController extends BaseController<Beneficiary, Long>{


    private static final Logger logger = LoggerFactory
            .getLogger(BeneficiarioController.class);

    @Autowired
    BeneficiarioService beneficiarioService;

    @PreAuthorize("hasRole('CONSULTAR_BENEFICIARIO')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getBeneficiarioListByFilter(@Valid @RequestBody CBBeneficiario cbBeneficiario,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        try {
            return beneficiarioService.getBeneficiarioByFilter(cbBeneficiario, direction, properties, page, size);
        } catch (Exception e) {
            logger.warn("Error al obtener los Beneficiarios ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
