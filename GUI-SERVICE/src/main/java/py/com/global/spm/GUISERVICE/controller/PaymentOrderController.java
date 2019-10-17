package py.com.global.spm.GUISERVICE.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import py.com.global.spm.GUISERVICE.dto.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBPaymentOrder;
import py.com.global.spm.GUISERVICE.dto.OrdenDePago.CancelPaymentOrderCoreDto;
import py.com.global.spm.GUISERVICE.dto.OrdenDePago.CancelPaymentOrderDto;
import py.com.global.spm.GUISERVICE.dto.OrdenDePago.RetryPaymentOrderCore;
import py.com.global.spm.GUISERVICE.dto.OrdenDePago.SignRequestDto;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.utils.ScopeViews;
import py.com.global.spm.GUISERVICE.services.*;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "Ordenes de Pagos")
@RestController
@RequestMapping(value = "/api/paymentOrder")
public class PaymentOrderController extends BaseController<Paymentorder, Long> {
    private static final Logger logger = LogManager
            .getLogger(PaymentOrderController.class);

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    private UtilService utilService;

    @Autowired
    RestTemplate template;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private MessageUtil messageUtil;


    @PreAuthorize("hasRole('ROLE_CONSULTAR_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Basics.class})
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getPaymentOrderTypeListByFilter(@NotNull(message = "0030") @RequestBody CBPaymentOrder cbPaymentOrder,
                                                  @RequestParam("direction") String direction,
                                                  @RequestParam("properties") String properties,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        try {
            return paymentOrderService.getPaymentOrderByFilter(cbPaymentOrder, direction,
                    properties, page, size);

        } catch (Exception e) {
            logger.warn("Error al obtener la lista de ordenes de pago", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_CONSULTAR_ORDEN_DE_PAGO')")
    @JsonView({ScopeViews.Details.class})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getOrdenDePago(@PathVariable Long id) {
        try {
            return paymentOrderService.getPaymentOrderService(id);
        } catch (Exception e) {
            logger.warn("Error al obtener la orden de Pago: " + id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_ORDEN_DE_PAGO')")
    @PostMapping(value = "/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object firmarOrdenDePago(@RequestParam("id") Long id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            DataDTO dataDTO = new DataDTO();
            Map<String, Object> body = new HashMap<>();
            ResponseCoreDto response = template.postForObject(utilService.getCOREURL() + "/paymentorder/sign",
                    new SignRequestDto(id, userService.getLoggedUser().getIduserPk()), ResponseCoreDto.class);
            if (SPM_GUI_Constants.OK_CORE.equals(response.getStatus())) {
                body.put("mensaje", response.getDescripcion());
                dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
                dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
                dataDTO.setBody(body);
                responseDto.setData(dataDTO);
            } else {
                logger.warn("Core-Service-FirmaOrdenPago, Response Error: {}",response);
                responseDto = responseDtoService.errorResponse(ErrorsDTO.CODE.ERRORCORE.getValue(), null);
                responseDto.getErrors().getMessage().setDetail(response.getDescripcion());
            }
            return responseDto;
        } catch (RestClientException e) {
            logger.warn("RestClientException Firma Orden Pago:", e);
            if (e instanceof ResourceAccessException) {
                responseDto = responseDtoService.errorResponse(ErrorsDTO.CODE.CONNECTION.getValue(), null);
                responseDto.getErrors().getMessage().setDetail(SPM_GUI_Constants.NOT_AVAILABLE);
                return responseDto;
            }
            return responseDtoService.errorResponse(ErrorsDTO.CODE.ERRORCORE.getValue(), null);
        } catch (Exception e) {
            logger.warn("Error al consultar WS de Firma", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_ORDEN_DE_PAGO')")
    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cancelarOrdenDePago(@Valid @RequestBody CancelPaymentOrderDto dto) {
        ResponseDto responseCancelarDto = new ResponseDto();
        try {
            if (dto != null) {
                DataDTO dataDTO = new DataDTO();
                Map<String, Object> body = new HashMap<>();
                ResponseCoreDto response = template.postForObject(utilService.getCOREURL() + "/paymentorder/cancel",
                        new CancelPaymentOrderCoreDto(dto.getIdPaymentOrder(), dto.getMotivo(),
                                userService.getLoggedUser().getIduserPk()), ResponseCoreDto.class);
                if (SPM_GUI_Constants.OK_CORE.equals(response.getStatus())) {
                    body.put("mensaje", response.getDescripcion());
                    dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
                    dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
                    dataDTO.setBody(body);
                    responseCancelarDto.setData(dataDTO);
                } else {
                    logger.warn("Core-Service-CancelPaymentOrder, Response Error: {}", response);
                    responseCancelarDto = responseDtoService.errorResponse(ErrorsDTO.CODE.ERRORCORE.getValue(), null);
                    responseCancelarDto.getErrors().getMessage().setDetail(response.getDescripcion());
                }
                return responseCancelarDto;
            } else {
                return new ResponseEntity<>("Null Request", HttpStatus.BAD_REQUEST);
            }
        } catch (RestClientException e) {
            logger.warn("RestClientException Cancelar Orden Pago:", e);
            if (e instanceof ResourceAccessException) {
                responseCancelarDto = responseDtoService.errorResponse(ErrorsDTO.CODE.CONNECTION.getValue(), null);
                responseCancelarDto.getErrors().getMessage().setDetail(SPM_GUI_Constants.NOT_AVAILABLE);
                return responseCancelarDto;
            }
            return responseDtoService.errorResponse(ErrorsDTO.CODE.ERRORCORE.getValue(), null);
        } catch (Exception e) {
            logger.warn("Error al consultar WS de Firma", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_MODIFICAR_ORDEN_DE_PAGO')")
    @PostMapping(value = "/retry", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object reintenarOrdenDePago(@RequestParam("id") Long id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            DataDTO dataDTO = new DataDTO();
            Map<String, Object> body = new HashMap<>();
            ResponseCoreDto response = template.postForObject(utilService.getCOREURL() + "/paymentorder/retry",
                    new RetryPaymentOrderCore(id, userService.getLoggedUser().getIduserPk()), ResponseCoreDto.class);

            if (SPM_GUI_Constants.OK_CORE.equals(response.getStatus())) {
                body.put("mensaje", response.getDescripcion());
                dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
                dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
                dataDTO.setBody(body);
                responseDto.setData(dataDTO);
            } else {
                logger.warn("Core-Service, ReintentarOrdenPago Response Error: {}",response);
                responseDto = responseDtoService.errorResponse(ErrorsDTO.CODE.ERRORCORE.getValue(), null);
                responseDto.getErrors().getMessage().setDetail(response.getDescripcion());
            }
            return responseDto;
        } catch (RestClientException e) {
            logger.warn("RestClientException Reintentar Orden Pago:", e);
            if (e instanceof ResourceAccessException) {
                responseDto = responseDtoService.errorResponse(ErrorsDTO.CODE.CONNECTION.getValue(), null);
                responseDto.getErrors().getMessage().setDetail(SPM_GUI_Constants.NOT_AVAILABLE);
                return responseDto;
            }
            return responseDtoService.errorResponse(ErrorsDTO.CODE.ERRORCORE.getValue(), null);
        } catch (Exception e) {
            logger.warn("Error al consultar WS de Firma", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/count/{state}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object countPaymentOrderPerUser(@Valid @PathVariable @Size(max = 2, message = "0037") String state){
        try{
            return paymentOrderService.countByStateAndFirmante(state);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping(value = "/infoCompany/{idCompany}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getInfoCompany(@PathVariable("idCompany") Long idCompany){
        try{
            return paymentOrderService.getInfoCompany(idCompany);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
