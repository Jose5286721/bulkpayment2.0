package py.com.global.spm.model.services.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.com.global.spm.model.services.dto.ResponseDto;
import py.com.global.spm.model.utils.SpmConstants;

import javax.ejb.Stateless;

@Stateless
public class ResponseDtoService {

    public ResponseDto getDtoOK(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_OK);
        responseDto.setDescripcion(SpmConstants.OK_DSC);
        return responseDto;
    }
    public ResponseDto getDtoERROR(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.ERROR_DSC);
        return responseDto;
    }
    public ResponseDto getDtoPaymentOrderStateSignError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_STATE_SIGN_ERROR);
        return responseDto;
    }
    public ResponseDto getDtoPaymentOrderStateRetryError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_STATE_RETRY_ERROR);
        return responseDto;
    }
    public ResponseDto getDtoPaymentOrderStateCancelError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_STATE_CANCEL_ERROR);
        return responseDto;
    }
    public ResponseDto getDtoPaymentOrderUserTurnError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_TURN_ERROR);
        return responseDto;
    }
    public ResponseDto getDtoEmpty(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.EMPTY_ENTITY);
        return responseDto;
    }
    public ResponseDto getDtoTimeout(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.TIMEOUT);
        return responseDto;
    }
    public ResponseDto getDtoErrorSendToFlowManagerQueue(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.SEND_ERROR_FLOW_MANAGER_DSC);
        return responseDto;
    }
    public ResponseDto getDtoErrorSendToTrasferQueue(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.SEND_ERROR_TRANSFER_DSC);
        return responseDto;
    }
    public ResponseDto getDtoErrorFirmaNoAutorizada(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.FIRMA_NO_AUTORIZADA);
        return responseDto;
    }


}
