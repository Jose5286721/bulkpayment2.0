package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.ILogdraftOpDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogdraftOp;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Log.LogdraftOpDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.GUISERVICE.report.PdfBuilder;
import py.com.global.spm.domain.entity.Logdraftop;
import py.com.global.spm.GUISERVICE.specifications.LogdraftOpSpecs;
import py.com.global.spm.GUISERVICE.utils.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogDraftOpService {
    private static final Logger logger = LoggerFactory.getLogger(LogDraftOpService.class);

    private SuperCompanyService superCompanyService;
    private CompanyService companyService;
    private ILogdraftOpDao dao;
    private MessageUtil messageUtil;
    private ResponseDtoService responseDtoService;
    private FormatProvider formatProvider;
    private GeneralHelper generalHelper;


    public LogDraftOpService(SuperCompanyService superCompanyService, CompanyService companyService,
                             ILogdraftOpDao dao, MessageUtil messageUtil, ResponseDtoService responseDtoService,
                             FormatProvider formatProvider, GeneralHelper generalHelper
    ){

        this.superCompanyService = superCompanyService;
        this.companyService = companyService;
        this.dao = dao;
        this.messageUtil = messageUtil;
        this.responseDtoService = responseDtoService;
        this.formatProvider = formatProvider;
        this.generalHelper = generalHelper;
    }

    public ResponseDto filterLogDraftOp(Integer page, Integer linesPerPage, String column
            , String direction, CBLogdraftOp filter){
        ResponseDto response = new ResponseDto();
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        String code;
        Messages message;
        try {
            Sort sort = new Sort(Sort.Direction.valueOf(direction), column);
            PageRequest pageRequest = PageRequest.of(page - 1, linesPerPage, sort);
            Specification<Logdraftop> where = getSpecification(filter);
            Page<Logdraftop> logdraftops = dao.findAll(where, pageRequest);
            if (logdraftops.getTotalElements() == 0 && logdraftops.getContent().isEmpty()) {
                code = DataDTO.CODE.OKNODATA.getValue();
                message = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("logdraftop", null);
            } else {
                code = DataDTO.CODE.OK.getValue();
                message = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                List<LogdraftOpDTO> resultList = logdraftops.getContent()
                        .stream()
                        .map(LogdraftOpDTO::new)
                        .collect(Collectors.toList());
                body.put("logdraftop", resultList);
            }
            dataDTO.setBody(body);
            dataDTO.setDataCode(code);
            dataDTO.setMessage(message);
            response.setData(dataDTO);
            response.setErrors(null);
            response.setMeta(new MetaDTO(logdraftops.getSize(), logdraftops.getTotalPages(), logdraftops.getTotalElements()));
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

    public List<LogdraftOpDTO> filterLogDraftOpReport(CBLogdraftOp filter,String properties, String direction){
        List<LogdraftOpDTO> logdraftOpDTOS = new ArrayList<>();
        try {
            Sort sort = generalHelper.ordenamiento(direction, properties, PdfBuilder.DIRECTION_ASC , "iddrafopPk");
            Specification<Logdraftop> where = getSpecification(filter);
            logdraftOpDTOS =  dao.findAll(where, sort).stream().map(LogdraftOpDTO::new).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("filterLogMts : {}",e.getMessage());
        }
        return logdraftOpDTOS;

    }



    private Specification<Logdraftop> getSpecification(CBLogdraftOp filter) throws ParseException {
        Specification<Logdraftop> where;
        Date fromDate = null;
        Date toDate = null;
        if(superCompanyService.isLoggedUserFromSuperCompany()){
            if(filter.getIdCompany() != null){
                where = Specification.where(LogdraftOpSpecs.getByCompanyId(filter.getIdCompany()));
            }else
                where = Specification.where(LogdraftOpSpecs.getAll());
        }
        else{
            Long idCompany = companyService.getLoggedUserIdCompany();
            if (idCompany == null)
                throw new AuthorizationException(ErrorsDTO.CODE.NOCOMPANYLOGGEDIN.getValue());
            where = Specification.where(LogdraftOpSpecs.getByCompanyId(idCompany));
        }


        where = where.and(LogdraftOpSpecs.getByProcess(SPM_GUI_Constants.PROCESS_PO_MANAGER));
        if(filter.getIdDraftOp() != null)
            where = where.and(LogdraftOpSpecs.getById(filter.getIdDraftOp()));

        if(filter.getIdDraft() != null)
            where = where.and(LogdraftOpSpecs.getByIdDraft(filter.getIdDraft()));

        if(filter.getIdEventCode() != null)
            where = where.and(LogdraftOpSpecs.getByIdEventCode(filter.getIdEventCode()));

        if (filter.getSinceDate() != null && !filter.getSinceDate().isEmpty()) {
            fromDate = formatProvider.parseDateReverse(filter.getSinceDate());
        }
        if (filter.getToDate() != null && !filter.getToDate().isEmpty()) {
            toDate = formatProvider.parseDateTimeReverse(filter.getToDate() + SPM_GUI_Constants.TIMEENDDAY);
        }
        if (fromDate != null || toDate != null) {
            where = where.and(LogdraftOpSpecs.getByRangeDate(fromDate, toDate));
        }
        return where;

    }

}
