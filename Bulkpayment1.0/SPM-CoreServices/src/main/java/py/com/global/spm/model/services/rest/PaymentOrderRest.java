package py.com.global.spm.model.services.rest;



import org.apache.log4j.Logger;
import py.com.global.spm.model.services.dto.CancelPaymentOrderDto;
import py.com.global.spm.model.services.dto.RetryPaymentOrder;
import py.com.global.spm.model.services.dto.SignRequestDto;
import py.com.global.spm.model.services.service.PaymentOrderService;
import py.com.global.spm.model.services.service.ResponseDtoService;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;

import javax.ws.rs.core.Response;

@Path("paymentorder")
@RequestScoped
public class PaymentOrderRest {

    private Logger logger = Logger.getLogger(PaymentOrderRest.class);

    @Inject
    ResponseDtoService responseDtoService;

    @Inject
    PaymentOrderService paymentOrderService;


    @POST
    @Path("/sign")
    @Produces("application/json")
    @Consumes("application/json")
    public Response signPaymentOrder(@Valid final SignRequestDto dto) {

        try {
            return paymentOrderService.sign(dto);
        } catch (Exception e) {
            logger.error("Error Firmando Orden de Pago ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
        }
    }


    @POST
    @Path("/cancel")
    @Produces("application/json")
    @Consumes("application/json")
    public Response cancelPaymentOrder(@Valid CancelPaymentOrderDto cancelPaymentOrderDto) {
        try {
            return paymentOrderService.cancel(cancelPaymentOrderDto);
        } catch (Exception e) {
            logger.error("Error Cancelando la  Orden de Pago Id: " + cancelPaymentOrderDto, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
        }
    }

    @POST
    @Path("/retry")
    @Produces("application/json")
    @Consumes("application/json")
    public Response retryPaymentOrder(@Valid RetryPaymentOrder retryPaymentOrder) {
        try {
            return  paymentOrderService.reintentar(retryPaymentOrder);
        } catch (Exception e) {
            logger.error("Error Reintentando la  Orden de Pago "+retryPaymentOrder , e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
        }
    }

}