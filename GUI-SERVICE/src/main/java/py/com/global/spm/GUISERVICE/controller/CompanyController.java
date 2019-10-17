
package py.com.global.spm.GUISERVICE.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.dto.Company.CompanyAddDto;
import py.com.global.spm.GUISERVICE.dto.Company.CompanyEditDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBCompany;
import py.com.global.spm.domain.utils.ScopeViews;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.GUISERVICE.services.CompanyService;
import py.com.global.spm.GUISERVICE.services.SuperCompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by global on 3/14/18.
 */

@Api(tags = "Empresas")
@RestController
@RequestMapping(value = "/api/company")
public class CompanyController extends BaseController<Company, Long>{

    private static final Logger logger = LoggerFactory
            .getLogger(CompanyController.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    SuperCompanyService superCompanyService;

    @PreAuthorize("hasRole('ROLE_MODIFICAR_EMPRESA')")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addCompany(@Valid @RequestBody CompanyAddDto request) {
        try {
            return companyService.addCompany(request);
        }catch (Exception e) {
            logger.warn("Error al crear la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_EMPRESA')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getCompanyListByFilter(@Valid @RequestBody CBCompany CBCompany,
                                         @RequestParam("direction") String direction,
                                         @RequestParam("properties") String properties,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size, HttpServletRequest request) {
        try {
            return companyService.getCompanyByFilter(CBCompany, direction, properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener las empresas", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_EMPRESA')")
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editCompany(@Valid @RequestBody CompanyEditDto request) {
        try {
            return companyService.editCompany(request);

        }catch (Exception e) {
            logger.error("Error al intentar editar la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_EMPRESA')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCompany(@PathVariable Long id) {
        try {
            return companyService.getCompanyByIdForResponse(id);
        }catch (Exception e) {
            logger.error("Error al intentar obtener la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/supercompany")
    public Object isSuperCompany() {
        try {
            return superCompanyService.isLoggedUserFromSuperCompanyResponse();
        }catch (Exception e) {
            logger.error("Error al intentar obtener superCompany", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @JsonView(ScopeViews.Basics.class)
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllCompanies() {
        try {
            return companyService.getAllCompanies();
        }catch (Exception e) {
            logger.error("Error al intentar obtener la empresa", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
