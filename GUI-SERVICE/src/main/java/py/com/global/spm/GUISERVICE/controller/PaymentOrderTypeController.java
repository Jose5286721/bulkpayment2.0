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
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBPaymentOrderType;
import py.com.global.spm.GUISERVICE.dto.TipoOrdenPago.TipoOrdenPagoAddDto;
import py.com.global.spm.GUISERVICE.dto.TipoOrdenPago.TipoOrdenPagoDto;
import py.com.global.spm.domain.entity.Paymentordertype;
import py.com.global.spm.GUISERVICE.services.PaymentOrderTypeService;
import py.com.global.spm.domain.utils.ScopeViews;

import javax.validation.Valid;

@Api(tags = "Tipos de Ordenes de Pagos")
@RestController
@RequestMapping(value = "/api/paymentOrderType")
public class PaymentOrderTypeController extends BaseController<Paymentordertype, Long>{

    private static final Logger logger = LoggerFactory
            .getLogger(PaymentOrderTypeController.class);

    @Autowired
    PaymentOrderTypeService paymentOrderTypeService;

    @PreAuthorize("hasRole('ROLE_CONSULTAR_TIPO_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getPaymentOrderTypeListByFilter(@Valid @RequestBody CBPaymentOrderType cbPaymentOrderType,
                                                  @RequestParam("direction") String direction,
                                                  @RequestParam("properties") String properties,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        try {
            return paymentOrderTypeService.getPaymentOderTypeListByFilter(cbPaymentOrderType, direction,
                    properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener la lista de ordenes de pago", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_CONSULTAR_TIPO_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Basics.class})
    @GetMapping(value = "/get/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getTipoOrdenPagoById(@PathVariable Long id) {
        try {
            return paymentOrderTypeService.getTipoOrdenPagoById(id);

        }catch (Exception e) {
            logger.error("Error al intentar obtener el tipo de orden de pago", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_TIPO_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addPaymentOrderType(@Valid @RequestBody TipoOrdenPagoAddDto request) {
        try {
            return paymentOrderTypeService.addTipoOrdenPago(request);
        }catch (Exception e) {
            logger.warn("Error al crear el tipo de orden de pago. ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_TIPO_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Details.class})
    @RequestMapping(value = "/edit",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object editPaymentOrderType(@Valid @RequestBody TipoOrdenPagoDto request) {
        try {
            return paymentOrderTypeService.editTipoOrdenPago(request);
        }catch (Exception e) {
            logger.error("Error al intentar editar el tipo de orden de pago. ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_TIPO_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Basics.class})
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllPaymentOrderType() {
        try {
            return paymentOrderTypeService.getAllPaymentOrderType();
        } catch (Exception e) {
            logger.error("Error al intentar obtener el tipo de orden de pago.", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
