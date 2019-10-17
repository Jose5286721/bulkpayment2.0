package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.GUISERVICE.utils.PrintResponses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by global on 3/16/18.
 */
@Service
public class ResponseDtoService {

    private static final Logger logger = LoggerFactory
            .getLogger(ResponseDtoService.class);

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private PrintResponses printResponses;


    /**
     * Metodo que retorna un error response con el mensaje y codigo pasado como parametro.
     * @param errorCode
     * @param replaceText
     * @return
     */
    public ResponseDto errorResponse(String errorCode, List<String> replaceText){
        ResponseDto responseDto = new ResponseDto();
        Messages msg;

        msg = messageUtil.getMessageWithPattern(errorCode, replaceText);
        ErrorsDTO errorsDTO = new ErrorsDTO(errorCode, msg);
        responseDto.setErrors(errorsDTO);
        responseDto.setData(null);
        responseDto.setMeta(null);
        logger.info("Respuesta del servicio => {}", printResponses.printResponseDto(responseDto));
        return responseDto;
    }

    public String listContentEmpty(Integer pagina) {
        String respuesta;

        List<String> pag = new ArrayList<>();
        pag.add(pagina.toString());
        respuesta = messageUtil.getMensaje(DataDTO.CODE.NOTELEMENTPAGE.getValue(), pag);

        return respuesta;
    }
}
