package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.ISmsLogMessageDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBSmsLogMessage;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Log.SmsLogMessageDto;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.GUISERVICE.report.PdfBuilder;
import py.com.global.spm.domain.entity.Smslogmessage;
import py.com.global.spm.GUISERVICE.specifications.SmsLogMessageSpecs;
import py.com.global.spm.GUISERVICE.utils.FormatProvider;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SmsLogMessageService {
    private static final Logger logger = LoggerFactory
            .getLogger(SmsLogMessageService.class);

    @Autowired
    GeneralHelper generalHelper;
    @Autowired
    SuperCompanyService superCompanyService;
    @Autowired
    CompanyService companyService;
    @Autowired
    private FormatProvider formatProvider;

    @Autowired
    private ISmsLogMessageDao smsLogMessageDao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;

    private static final String TIMEENDDAY = " 23:59:00";



    public ResponseDto filterSmsLogMessagePag(CBSmsLogMessage filter, String direction, String properties, Integer pagina, Integer rows){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        Sort sort = generalHelper.ordenamiento(direction, properties,"DESC","creationdateTim");
        PageRequest pageRequest =PageRequest.of(pagina - 1, rows, sort);
        logger.info("Obteniendo Log de Mensajes Recibidos por Filtro {}", filter);
        try {


        Specification<Smslogmessage> where = getSpecification(filter);
        Page<Smslogmessage> smslogmessages = smsLogMessageDao.findAll(where, pageRequest);
        String code;
        if (smslogmessages.getTotalElements() == 0 && smslogmessages.getContent().isEmpty()) {
            code = DataDTO.CODE.OKNODATA.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
            body.put("smslogmessage", null);
            dataDTO.setBody(body);
        } else {
            code = DataDTO.CODE.OK.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            body.put("smslogmessage", smslogmessages.getContent().stream().map(SmsLogMessageDto::new).collect(Collectors.toList()));
            dataDTO.setBody(body);
        }
        dataDTO.setDataCode(code);
        dataDTO.setMessage(msg);
        responseDto.setData(dataDTO);
        responseDto.setErrors(null);
        responseDto.setMeta(new MetaDTO(smslogmessages.getSize(), smslogmessages.getTotalPages(), smslogmessages.getTotalElements()));
        logger.debug("Consulta de Log de Mensajes Recibidos por filtro exitosa");
        return responseDto;
        }catch (ParseException e){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);
        }catch (Exception e){
            logger.warn("Error en la generacion de lista de mensajes recibidos", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }

    }
    public List<SmsLogMessageDto> filterLogMessageReport(CBSmsLogMessage filter,String properties, String direction){
        List<SmsLogMessageDto> smsLogMessages = new ArrayList<>();
        try {
            Sort sort = generalHelper.ordenamiento(direction, properties, PdfBuilder.DIRECTION_ASC , "idmessagePk");
            Specification<Smslogmessage> where = getSpecification(filter);
            smsLogMessages =  smsLogMessageDao.findAll(where, sort).stream().map(SmsLogMessageDto::new).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("filterSmsLogMessage : {}", e.getMessage());
        }
        return smsLogMessages;

    }
    private Specification<Smslogmessage> getSpecification(CBSmsLogMessage filter) throws ParseException{
        Specification<Smslogmessage> where ;
        try {
            if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                Long idCompany = companyService.getLoggedUserIdCompany();
                if (idCompany == null)
                    throw new AuthorizationException(ErrorsDTO.CODE.NOCOMPANYLOGGEDIN.getValue());
                where = Specification.where(SmsLogMessageSpecs.getByCompanyId(idCompany));
            } else {
                where = Specification.where(SmsLogMessageSpecs.getAll());

                if (filter.getIdCompany() != null) {
                    where = where.and(SmsLogMessageSpecs.getByCompanyId(filter.getIdCompany()));
                }
            }
            if (filter.getStateChr() != null) {
                where = where.and(SmsLogMessageSpecs.getByEstado(filter.getStateChr()));
            }
            if (filter.getSourcenumberChr() != null && !filter.getSourcenumberChr().isEmpty()) {
                where = where.and(SmsLogMessageSpecs.getBySourceNumber(filter.getSourcenumberChr()));
            }

        Date end = null;
        Date start = null;

            if (filter.getSinceDate() != null && !filter.getSinceDate().isEmpty()) {
                start = formatProvider.parseDateReverse(filter.getSinceDate());
            }
            if (filter.getToDate() != null && !filter.getToDate().isEmpty()) {
                end = formatProvider.parseDateTimeReverse(filter.getToDate() + TIMEENDDAY);
            }
            if (start != null || end != null) {
                where = where.and(SmsLogMessageSpecs.getFechaDesdeHasta("creationdateTim", start, end));
            }
        } catch (ParseException e) {
            logger.error("Invalod date Format Filter: " + e.getMessage());
            throw e;
        }
        return where;
    }
}
