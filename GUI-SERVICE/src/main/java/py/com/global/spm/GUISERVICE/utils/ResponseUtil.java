package py.com.global.spm.GUISERVICE.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;

import java.util.List;

@Component
public class ResponseUtil {

    @Autowired
    private MessageUtil messageUtil;

    public ResponseDto buildErrorResponseDTO(String code, List<String> toReplace) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages = messageUtil.getMessageWithPattern(code, toReplace);
        ErrorsDTO errorsDTO = new ErrorsDTO(code, messages);
        responseDto.setErrors(errorsDTO);
        return responseDto;
    }
}

