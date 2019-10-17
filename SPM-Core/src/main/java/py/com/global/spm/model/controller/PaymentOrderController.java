package py.com.global.spm.model.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.model.dto.CancelPaymentOrderDto;
import py.com.global.spm.model.dto.ResponseDto;
import py.com.global.spm.model.dto.RetryPaymentOrder;
import py.com.global.spm.model.dto.SignRequestDto;
import py.com.global.spm.model.services.PaymentOrderService;

import javax.validation.Valid;

@Api(tags = "PaymentOrder")
@RestController
@RequestMapping(value = "SPM-CoreServices/core/v1/paymentorder")
public class PaymentOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentOrderController.class);

    @Autowired
    private PaymentOrderService paymentOrderService;


    @PostMapping(value = "/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object signPaymentOrder(@Valid @RequestBody SignRequestDto request) {
        try {
            return paymentOrderService.sign(request);
        }catch (Exception e) {
            logger.error("Error Firmando Orden de Pago", e);
            return new ResponseEntity<>(ResponseDto.getDtoERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cancelPaymentOrder(@Valid @RequestBody CancelPaymentOrderDto request) {
        try {
            return paymentOrderService.cancel(request);
        }catch (Exception e) {
            logger.error("Error Cancelando la  Orden de Pago", e);
            return new ResponseEntity<>(ResponseDto.getDtoERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/retry", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object retryPaymentOrder(@Valid @RequestBody RetryPaymentOrder request) {
        try {
            return paymentOrderService.reintentar(request);
        }catch (Exception e) {
            logger.error("Error Reintentando la  Orden de Pago", e);
            return new ResponseEntity<>(ResponseDto.getDtoERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
