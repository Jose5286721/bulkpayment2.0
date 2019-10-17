package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.domain.entity.ConfParameters;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Implementacion de carga/descarga de ejemplos
 */
@Service
public class TemplateExampleService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateExampleService.class);

    private UtilService utilService;
    private ConfParameterService confParameterService;
    private MessageUtil messageUtil;
    private GeneralHelper helper;
    private ResponseDtoService responseDtoService;

    public TemplateExampleService(ConfParameterService confParameterService, MessageUtil messageUtil, UtilService utilService,
                                  GeneralHelper helper, ResponseDtoService responseDtoService){
        this.confParameterService = confParameterService;
        this.messageUtil = messageUtil;
        this.utilService = utilService;
        this.helper = helper;
        this.responseDtoService = responseDtoService;
    }

    public ResponseDto processExampleFile(MultipartFile file, String fileNameParameter){
        ResponseDto response = new ResponseDto();
        ConfParameters confParameters = null;
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        try{
            if (file.getContentType().length() < utilService.maxFileSizeValue()){
                if(helper.isMimeTypeAllowed(file)){
                    confParameters = confParameterService.saveFile(fileNameParameter, file.getBytes(), file.getOriginalFilename(), file.getContentType());

                } else {
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDEXTENSION.getValue(), null);
                }

            }else {
                List<String> replaceText = new ArrayList<>();
                replaceText.add(String.valueOf(utilService.maxFileSizeValue()));
                return responseDtoService.errorResponse(ErrorsDTO.CODE.MAXLENGTH.getValue(), replaceText);

            }
            body.put("confParameters", confParameters);
            dataDTO.setBody(body);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            response.setData(dataDTO);
        }catch (IOException e) {
            logger.error("Al examinar archivo");
        }
        catch (Exception e){
            logger.error("Al procesar importacion de archivo: {}",e.getMessage());
            logger.debug("Al procesar importacion de archivo: ",e);

        }
        return response;
    }

    public void downloadExample(HttpServletResponse response, String fileNameParameter){
        ConfParameters confParameters = confParameterService.byKey(fileNameParameter);
        byte [] template = confParameters.getTemplate();
        response.setContentType("text/plain; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+ confParameters.getValue());
        try {
            response.getWriter().write(new String(template).toCharArray());
        }
        catch (Exception e){
            logger.error("Error al descargar archivo CSV, fileNameParameter --> "+fileNameParameter,e);
        }
    }

}
