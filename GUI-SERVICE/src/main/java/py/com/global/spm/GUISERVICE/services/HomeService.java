package py.com.global.spm.GUISERVICE.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dto.DataDTO;

import py.com.global.spm.GUISERVICE.dto.ResponseDto;

import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;

import java.util.*;

@Service
public class HomeService {
    private static final Logger logger = LogManager
            .getLogger(HomeService.class);
    @Autowired
    private MessageUtil messageUtil;

    //TODO: Obtener saldo desde el CORE
    public ResponseDto getSaldoActual(String code, Long idCompany){

        ResponseDto responseDto = new ResponseDto();
        Map<String,Object> body = new HashMap<>();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
        dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
        body.put("saldoA","1.200.000");
        dataDTO.setBody(body);
        responseDto.setData(dataDTO);
        return responseDto;
    }


}
