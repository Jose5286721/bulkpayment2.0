//package py.com.global.spm.GUISERVICE.controller;
//
//import io.swagger.annotations.Api;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import py.com.global.spm.GUISERVICE.dto.utils.PasswordNotificationDto;
//import py.com.global.spm.GUISERVICE.services.MailService;
//
//import javax.validation.Valid;
//
//@Api(tags = "Notifications")
//@RestController
//@RequestMapping(value = "/api/notifications")
//
//public class NotificationController {
//
//    (RolController.class);
//
//    @Autowired
//    MailService mailService;
//
//    @RequestMapping(value = "/password",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Object addMailMessage(@Valid @RequestBody PasswordNotificationDto request) {
//        try {
//            return mailService.trySendNotification(request);
//        }catch (Exception e) {
//            logger.error("Error al intentar agregar el usuario", e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
