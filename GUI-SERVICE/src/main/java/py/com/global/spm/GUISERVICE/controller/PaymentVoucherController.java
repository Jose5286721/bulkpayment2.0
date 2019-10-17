package py.com.global.spm.GUISERVICE.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.GUISERVICE.services.PaymentVoucherService;

@RestController
@Api(tags = "Comprobante de Pago")
@RequestMapping(value = "/api/paymentVoucher")
public class PaymentVoucherController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentVoucherController.class);

    @Autowired
    PaymentVoucherService service;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object byFilter(@RequestParam(value = "paymentOrderId", required = false) Long paymentOrderId,
                           @RequestParam(value = "beneficiaryCI", required = false) String ci,
                           @RequestParam(value = "phonenumberChr",required = false) String phonenumberChr,
                           @RequestParam(value= "sinceDate", required = false) String sinceDate,
                           @RequestParam(value = "toDate", required = false) String toDate,
                           @RequestParam(value = "companyId", required = false) Long companyId,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "linesPerPage", defaultValue = "10", required = false) Integer linesPerPage,
                           @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                           @RequestParam(value = "orderBy", defaultValue = "paymentorder.updatedateTim") String columnName) {
        try{
            return service.filterPaymentVoucher(paymentOrderId, ci, phonenumberChr,companyId, sinceDate, toDate, page,
                    linesPerPage, columnName, direction);
        }catch (AuthorizationException e) {
            throw e;
        }catch (Exception e){
            logger.warn("Error al obtener las empresas", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
