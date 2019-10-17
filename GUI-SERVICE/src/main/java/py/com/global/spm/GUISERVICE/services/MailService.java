//package py.com.global.spm.GUISERVICE.services;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import py.com.global.spm.GUISERVICE.dto.DataDTO;
//import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
//import py.com.global.spm.GUISERVICE.dto.ResponseDto;
//import py.com.global.spm.GUISERVICE.dto.utils.PasswordNotificationDto;
//import py.com.global.spm.GUISERVICE.enums.NotificationEventEnum;
//import py.com.global.spm.GUISERVICE.message.NotificationRequestMessage;
//import py.com.global.spm.domain.entity.User;
//import py.com.global.spm.GUISERVICE.utils.MessageUtil;
//import py.com.global.spm.GUISERVICE.utils.Messages;
//import py.com.global.spm.GUISERVICE.utils.QueueManager;
//import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//
//@Service
//@Transactional
//public class MailService {
//    private static final Logger logger = LoggerFactory
//            .getLogger(LogAuditService.class);
//
//    @Autowired ResponseDtoService responseDtoService;
//
//    @Autowired UserService userService;
//
//    @Autowired
//    MessageUtil messageUtil;
//
//
//
//
//    public ResponseDto trySendNotification(PasswordNotificationDto request) {
//        ResponseDto responseDto = new ResponseDto();
//        DataDTO dataDTO = new DataDTO();
//        Map<String, Object> body = new HashMap<>();
//        String mensaje = null;
//        Messages messages;
//        List<String> replaceText = new ArrayList<>();
//        logger.info("Enviando Notificacion---> {}", request);
//        //HelloWorldJMSClient jmsClient = new HelloWorldJMSClient();
//
//        try {
//
//            User user =userService.getUserById(request.getIdUserpk());
//            if(user==null){
//                replaceText.add("User");
//                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
//            }
//
//            NotificationRequestMessage msg = new NotificationRequestMessage();
//            msg.setEvent(NotificationEventEnum.NOTIFY_USER_CREATED);
//            msg.setPass(request.getPassword());
//            Long[] usersToNotify = { request.getIdUserpk() };
//            msg.setIdDestinatarios(usersToNotify);
//            Boolean sent=QueueManager.sendObject(msg, SPM_GUI_Constants.NOTIFICATION_QUEUE);
//            QueueManager.closeQueueConn(SPM_GUI_Constants.NOTIFICATION_QUEUE);
//
//
//            responseDto.setData(dataDTO);
//            if(sent) {
//                messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
//                dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
//                dataDTO.setMessage(messages);
//                dataDTO.setBody(body);
//                logger.info("Pedido de notificacion enviado satisfactoriaMente [ID_USER: "
//                        + request.getIdUserpk() + "]");
//            }
//            return responseDto;
//        } catch (Exception e) {
//            logger.error("No se pudo enviar a la cola de NotificationManager el pedido de envio de notificacion---> ",e);
//                replaceText.add(e.getMessage());
//                return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
//        }
//    }
//}
