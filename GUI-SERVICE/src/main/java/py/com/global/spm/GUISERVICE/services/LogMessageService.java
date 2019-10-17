package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import py.com.global.spm.GUISERVICE.dao.ILogMessageDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogMessage;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Log.LogMessageDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.GUISERVICE.report.PdfBuilder;
import py.com.global.spm.domain.entity.Logmessage;
import py.com.global.spm.GUISERVICE.specifications.LogMessageSpecs;
import py.com.global.spm.GUISERVICE.utils.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogMessageService {
    public static final Logger logger = LoggerFactory.getLogger(LogMessageService.class);

    private ResponseDtoService responseDtoService;
    private ILogMessageDao dao;
    private SuperCompanyService superCompanyService;
    private CompanyService companyService;
    private FormatProvider formatProvider;
    private MessageUtil messageUtil;
    private GeneralHelper generalHelper;

    public LogMessageService(ResponseDtoService responseDtoService, ILogMessageDao dao, SuperCompanyService superCompanyService,
                             CompanyService companyService, FormatProvider formatProvider, MessageUtil messageUtil,GeneralHelper generalHelper
    ){
        this.responseDtoService = responseDtoService;
        this.dao = dao;
        this.superCompanyService = superCompanyService;
        this.companyService = companyService;
        this.formatProvider = formatProvider;
        this.messageUtil = messageUtil;
        this.generalHelper = generalHelper;
    }

    public ResponseDto filterLogMessage(Integer page, Integer size, String properties, String direction, CBLogMessage filter) {
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        ResponseDto response = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        String code;
        Messages message;
        try{
            Sort sort = new Sort(Sort.Direction.valueOf(direction), properties);
            PageRequest pageRequest =PageRequest.of(page - 1, size, sort);
            Specification<Logmessage> where = getSpecification(filter);
            Page<Logmessage> logmessages = dao.findAll(where, pageRequest);
            if (logmessages.getTotalElements() == 0 && logmessages.getContent().isEmpty()) {
                code = DataDTO.CODE.OKNODATA.getValue();
                message = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("logmessage", null);
            } else {
                code = DataDTO.CODE.OK.getValue();
                message = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                List<LogMessageDTO> resultList = logmessages.getContent()
                        .stream()
                        .map(LogMessageDTO::new)
                        .collect(Collectors.toList());
                body.put("logmessage", resultList);
            }
            dataDTO.setBody(body);
            dataDTO.setDataCode(code);
            dataDTO.setMessage(message);
            response.setData(dataDTO);
            response.setErrors(null);
            response.setMeta(new MetaDTO(logmessages.getSize(), logmessages.getTotalPages(), logmessages.getTotalElements()));

        }catch (ParseException e){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);

        }catch (AuthorizationException e){
            logger.warn("Error de autorizacion");
            throw e;
        }catch (Exception e){
            logger.warn("Error en la generacion de lista de log draftop", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }

        return response;
    }

    public List<LogMessageDTO> filterLogMessageReport(CBLogMessage filter,String properties, String direction){
        List<LogMessageDTO> logmessages = new ArrayList<>();
        try {
            Sort sort = generalHelper.ordenamiento(direction, properties, PdfBuilder.DIRECTION_ASC , "idmessagePk");
            Specification<Logmessage> where = getSpecification(filter);
            logmessages =  dao.findAll(where, sort).stream().map(LogMessageDTO::new).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("filterLogMessage : {}", e.getMessage());
        }
        return logmessages;

    }
    private Specification<Logmessage> getSpecification(CBLogMessage filter) throws ParseException {
        Specification<Logmessage> where;
        Date fromDate = null;
        Date toDate = null;
        if (superCompanyService.isLoggedUserFromSuperCompany()) {
            if (filter.getCompanyId() != null) {
                where = Specification.where(LogMessageSpecs.getByCompanyId(filter.getCompanyId()));
            } else
                where = Specification.where(LogMessageSpecs.getAll());
        } else {
            Long idCompany = companyService.getLoggedUserIdCompany();
            if (idCompany == null)
                throw new AuthorizationException(ErrorsDTO.CODE.NOCOMPANYLOGGEDIN.getValue());
            where = Specification.where(LogMessageSpecs.getByCompanyId(idCompany));
        }

        if (filter.getMessageId() != null)
            where = where.and(LogMessageSpecs.getById(filter.getMessageId()));

        if (filter.getPaymentOrderId() != null)
            where = where.and(LogMessageSpecs.getByPaymentOrderId(filter.getPaymentOrderId()));

        if (filter.getNotificationEventChr() != null && !filter.getNotificationEventChr().isEmpty())
            where = where.and(LogMessageSpecs.getByNotificationEvent(filter.getNotificationEventChr()));

        if (filter.getStateChr() != null)
            where = where.and(LogMessageSpecs.getByState(filter.getStateChr()));

        if (filter.getCuentaOrigen() != null)
            where = where.and(LogMessageSpecs.getByCuentaOrigen(filter.getCuentaOrigen()));

        if (filter.getSinceDate() != null && !filter.getSinceDate().isEmpty())
            fromDate = formatProvider.parseDateReverse(filter.getSinceDate());

        if (filter.getToDate() != null && !filter.getToDate().isEmpty())
            toDate = formatProvider.parseDateTimeReverse(filter.getToDate() + SPM_GUI_Constants.TIMEENDDAY);

        if (fromDate != null || toDate != null)
            where = where.and(LogMessageSpecs.getByRangeDate(fromDate, toDate));

        return where;
    }
}
