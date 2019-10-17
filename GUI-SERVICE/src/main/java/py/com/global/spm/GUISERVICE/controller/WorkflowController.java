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

import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBWorkflow;
import py.com.global.spm.GUISERVICE.dto.Workflow.WorkflowResponseDto;
import py.com.global.spm.domain.entity.Workflow;
import py.com.global.spm.GUISERVICE.services.WorkflowService;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.validation.Valid;

@Api(tags = "Workflows")
@RestController
@RequestMapping(value = "/api/workflow")
public class WorkflowController extends BaseController<Workflow, Long>{
	private static final Logger logger = LoggerFactory
			.getLogger(WorkflowController.class);

    @Autowired
    WorkflowService workflowService;

    @PreAuthorize("hasRole('ROLE_MODIFICAR_WORKFLOW')")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addWorkflow(@Valid @RequestBody WorkflowResponseDto request) {
        try {
            return workflowService.addWorkflow(request);

        } catch (Exception e) {
            logger.warn("Error al crear Workflow", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_WORKFLOW')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getWorkflow(@PathVariable Long id) {
        try {
            return workflowService.getWorkflowService(id);
		} catch (Exception e) {
			logger.warn("Error al obtener el Workflow: "+id, e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PreAuthorize("hasRole('ROLE_CONSULTAR_WORKFLOW')")
    @JsonView({ScopeViews.Basics.class})
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllWorkflows() {
        try {
            return workflowService.getAllWorkflows();
        } catch (Exception e) {
            logger.error("Error al intentar obtener el workflow", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_WORKFLOW')")
    @JsonView({ScopeViews.Details.class})
    @GetMapping(value = "/get/all/company/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllWorkflowsByCompany(@PathVariable String companyId) {
        try {
            return workflowService.getAllWorkflowsByCompany(Long.parseLong(companyId));
        } catch (Exception e) {
            logger.error("Error al intentar obtener el workflow", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_WORKFLOW')")
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editWorkflow(@Valid @RequestBody WorkflowResponseDto request) {
        try {
            return workflowService.editWorkflow(request);

        } catch (Exception e) {
            logger.error("Error al intentar editar el workflow", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_WORKFLOW')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getWorkflowListByFilter(@Valid @RequestBody CBWorkflow cbWorkflow,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("properties") String properties,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        try {
            return workflowService.getWorkflowByFilter(cbWorkflow, direction, properties, page, size);

		} catch (Exception e) {
			logger.warn("Error al obtener los Workflows ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getFirmantes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getFirmantesByWorkflow(@PathVariable Long id) {
		try {
			return workflowService.getFirmantesByWorkflowId(id);

		}catch (Exception e) {
			logger.error("Error al intentar obtener el workflow", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
