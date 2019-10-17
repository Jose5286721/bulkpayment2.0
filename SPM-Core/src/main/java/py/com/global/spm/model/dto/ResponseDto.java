package py.com.global.spm.model.dto;

import py.com.global.spm.model.util.SpmConstants;

public class ResponseDto {
    private String status;
    private String descripcion;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public static ResponseDto getDtoOK(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_OK);
        responseDto.setDescripcion(SpmConstants.OK_DSC);
        return responseDto;
    }
    public static ResponseDto getDtoERROR(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.ERROR_DSC);
        return responseDto;
    }
    public static ResponseDto getDtoPaymentOrderStateSignError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_STATE_SIGN_ERROR);
        return responseDto;
    }
    public static ResponseDto getDtoPaymentOrderStateRetryError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_STATE_RETRY_ERROR);
        return responseDto;
    }
    public static ResponseDto getDtoPaymentOrderStateCancelError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_STATE_CANCEL_ERROR);
        return responseDto;
    }
    public static ResponseDto getDtoPaymentOrderUserTurnError(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.PAYMENT_ORDER_TURN_ERROR);
        return responseDto;
    }
    public static ResponseDto getDtoEmpty(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.EMPTY_ENTITY);
        return responseDto;
    }
    public static ResponseDto getDtoTimeout(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.TIMEOUT);
        return responseDto;
    }
    public static ResponseDto getDtoErrorSendToFlowManagerQueue(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.SEND_ERROR_FLOW_MANAGER_DSC);
        return responseDto;
    }
    public static ResponseDto getDtoErrorSendToTrasferQueue(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.SEND_ERROR_TRANSFER_DSC);
        return responseDto;
    }
    public static ResponseDto getDtoErrorFirmaNoAutorizada(){
        ResponseDto responseDto=new ResponseDto();
        responseDto.setStatus(SpmConstants.STATUS_ERROR);
        responseDto.setDescripcion(SpmConstants.FIRMA_NO_AUTORIZADA);
        return responseDto;
    }


    @Override
    public String toString() {
        return "ResponseDto{" +
                "status='" + status + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
