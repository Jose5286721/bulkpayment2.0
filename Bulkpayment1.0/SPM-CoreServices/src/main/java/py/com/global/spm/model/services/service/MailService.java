package py.com.global.spm.model.services.service;

import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;

@Stateless
public class MailService {

    private final Logger log = Logger.getLogger(MailService.class);

    @Inject
    ResponseDtoService responseDtoService;

    @Inject
    SessionSingleton singleton;

    public Response sendMail(final String username, final String password, String mail, String mensaje, String asunto){

        Response response=null;
		try {
        Message message = new MimeMessage(singleton.getSession());
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mail));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);
        log.info("Envio correcto de correo, a: "+ mail+ " Mensaje: "+ mensaje) ;
        response=Response.status(Response.Status.OK).entity(responseDtoService.getDtoOK()).build();
    } catch (MessagingException e) {
        log.error("Error l enviar el Correo", e);
           response= Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseDtoService.getDtoERROR()).build();
        }finally {
		    return  response;
        }
    }
}

