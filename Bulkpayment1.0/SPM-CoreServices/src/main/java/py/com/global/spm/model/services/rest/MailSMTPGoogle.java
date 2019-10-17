package py.com.global.spm.model.services.rest;

import org.apache.log4j.Logger;
import py.com.global.spm.model.services.ConstantsDefaults;
import py.com.global.spm.model.services.dto.SimpleEmailDto;
import py.com.global.spm.model.services.service.MailService;
import py.com.global.spm.model.services.service.ResponseDtoService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;


@Path("mailgoogle")
@RequestScoped
public class MailSMTPGoogle {

    private static Logger logger = Logger.getLogger(MailSMTPGoogle.class);

    @EJB
    private MailService mailService;

    @Inject
    private ResponseDtoService responseDtoService;

    @Resource
    ManagedExecutorService executorService;


    @POST
    @Path("/send")
    @Produces("application/json")
    @Consumes("application/json")
    public void asyncMail(@Valid final SimpleEmailDto dto, @Suspended final AsyncResponse asyncResponse) {

        try {
            asyncResponse.setTimeout(ConstantsDefaults.SERVICE_TIMEOUT, TimeUnit.SECONDS);
            asyncResponse.setTimeoutHandler(ar -> ar.resume(
                    Response.status(SERVICE_UNAVAILABLE).entity(responseDtoService.getDtoTimeout()).build()));
            //Se Hace la LLamada para enviar el correo
            executorService.submit(() -> asyncResponse.resume(
                    (mailService.sendMail(
                            ConstantsDefaults.MAIL_SERVER,
                            ConstantsDefaults.MAIL_PASS,
                            dto.getDestinatario(),dto.getMensaje(),
                            dto.getAsunto()))));
        }catch (Exception e){
            logger.error("Error enviando Mail ", e);
        }
    }
}