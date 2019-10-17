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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogPayment;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.GUISERVICE.services.LogPaymentService;

import javax.validation.constraints.NotNull;

@Api(tags = "LogPayment")
@RestController
@RequestMapping(value = "/api/logPayment")

public class LogPaymentController extends BaseController<Logpayment, Long>{
    private static final Logger logger = LoggerFactory.getLogger(LogPaymentController.class);
    @Autowired
    private LogPaymentService logPaymentService;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_REGISTRO_DE_PAGO')")
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getLogMtsListByFilter(@NotNull(message = "0030") @RequestBody CBLogPayment cbLogPayment,
                                                  @RequestParam(value="direction",required = false) String direction,
                                                  @RequestParam(value="properties",required = false) String properties,
                                                  @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        try {
            return logPaymentService.filterLogpaymentPag(cbLogPayment, direction,
                    properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener la lista de log del Mts", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}
