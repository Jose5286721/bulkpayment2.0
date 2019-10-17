package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
import py.com.global.spm.domain.entity.Approval;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.domain.entity.User;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static py.com.global.spm.GUISERVICE.report.PdfBuilder.DATE_FORMAT;

/**
 *
 * @since 18/10/17
 * @author Global SI
 */

@Service
public class UtilService {
    private static final Logger logger = LoggerFactory.getLogger(UtilService.class);


    private ResponseDtoService responseDtoService;
    private MessageUtil messageUtil;
    private SystemParameterService systemParameterService;

    public UtilService(ResponseDtoService responseDtoService, MessageUtil messageUtil,
                       SystemParameterService systemParameterService) {
        this.responseDtoService = responseDtoService;
        this.messageUtil = messageUtil;
        this.systemParameterService = systemParameterService;
    }


    /**
     * Obtener expresion regular del numero telefonico
     * A utilizar en el front del sistema
     * @return responseDto {en el cuerpo se encuentra lo que se desea}
     * @see ResponseDto
     */
    public ResponseDto getNumberPhoneRegExp(){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        Map<String, Object> body = new HashMap<>();
        try{
            String phoneRegExpr = getAccountRegularExpression();
            String regExpr = (phoneRegExpr == null ? SPM_GUI_Constants.PHONE_DEF_REG_EXPR : phoneRegExpr);
            body.put("NumberPhoneregExp", regExpr);
            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setBody(body);
            dataDTO.setMessage(messages);
            responseDto.setData(dataDTO);
            return responseDto;

        }catch (Exception e){
            logger.error("Error al obtener expresion regular: {} ", e.getMessage());
            logger.debug("Error al obtener expresion regular: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }
    }

    public ResponseDto getCSVSizeValue(){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        Map<String, Object> body = new HashMap<>();
        try{
            body.put("csvSizeValue", maxFileSizeValue());
            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setBody(body);
            dataDTO.setMessage(messages);
            responseDto.setData(dataDTO);
            return responseDto;

        }catch (Exception e){
            replaceText.add(e.getMessage());
            logger.error("Error al intentar obtener tamanio maximo de archivo csv: {}", e.getMessage());
            logger.debug("Error al intentar obtener tamanio maximo de archivo csv: ", e);
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }
    }

    public String getAccountRegularExpression(){
        Systemparameter param = systemParameterService.findByParameter(SPM_GUI_Constants.ACCOUNT_REG_EXPR);
        return (param==null)? null : param.getValueChr();

    }

    public String getTimeRemaining(LocalDateTime fromDate, LocalDateTime toDate){
        StringBuilder time = new StringBuilder();
        LocalDateTime tempDate= LocalDateTime.from( fromDate );
        long hours = tempDate.until( toDate, ChronoUnit.HOURS);
        tempDate = tempDate.plusHours( hours );
        long minutes = tempDate.until( toDate, ChronoUnit.MINUTES);
        tempDate = tempDate.plusMinutes( minutes );
        long seconds = tempDate.until( toDate, ChronoUnit.SECONDS);
        if(hours>0){
            time.append(hours);
            time.append(" horas ");
        }
        if(minutes>0){
            time.append(minutes);
            time.append(" minutos ");
        }
        if(seconds>0){
            time.append(seconds);
            time.append(" segundos");
        }
        return time.toString();
    }

    public String getPhoneNumberRegularExpression(){
        Systemparameter param = systemParameterService.findByParameter(SPM_GUI_Constants.PHONE_REG_EXPR);
        return (param==null)? null : param.getValueChr();
    }
    public String getYearMonthFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear()+"-"+ localDate.getMonthValue();
    }

    public static String getDate(Date date) {
        try {
            SimpleDateFormat timeSdf = new SimpleDateFormat(DATE_FORMAT);
            return timeSdf.format(date);
        } catch (Exception e) {
            logger.info("getDate: {}" , e.getMessage());
            return null;
        }

    }
    public String getFirmantesStr(Set<Approval> approvals){
        StringBuilder str = new StringBuilder();
        for(Approval approval: approvals){
            User user=approval.getWorkflowdet().getUser();
            str.append(user.getUsernameChr()).append(" ").append(user.getUserlastnameChr()).append(", ");
        }
        return str.substring(0,str.length()-2);
    }

    public String getCOREURL() {
        Systemparameter parameter;
        String endpointUrl = null;
        try {
            parameter = systemParameterService.findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETER_CTX_ENDPOINT_CORE_URL);
            endpointUrl = (parameter == null) ? SPM_GUI_Constants.DEFAULT_CORE_ENDPOINT : parameter.getValueChr();
        } catch (Exception e) {
            logger.error("Al obtener url de los servicios del core", e);
        }

        return endpointUrl;
    }

    /**
     * Obtencion de tamanio maximo de archivo csv
     */
     Integer maxFileSizeValue() {
        String max = systemParameterService.getSystemParameterValue(
                SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
                SPM_GUI_Constants.MAX_FILE_UPLOAD_SIZE,
                SPM_GUI_Constants.CSVFILE_DEF_VALUE);
        Integer maxFileUpload = Integer.valueOf(max);
        logger.debug("MAX FILE SIZE IN BYTES: {} ",max);
        return maxFileUpload;
    }

    public String getFileName(String parameter){
        Systemparameter fileName = systemParameterService.findByParameter(parameter);
        return fileName!= null ? fileName.getValueChr(): "default";
    }

    public static String toNumberFormat(String number){
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("es","ES"));
        return df.format(Double.parseDouble(number));
    }

}
