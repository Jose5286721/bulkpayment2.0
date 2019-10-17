package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.ILogMtsDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBReport;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Log.LogMtsDto;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.enums.EstadosMts;
import py.com.global.spm.GUISERVICE.specifications.LogMtsSpecs;
import py.com.global.spm.GUISERVICE.utils.FormatProvider;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.domain.entity.Logmts;

import java.text.ParseException;
import java.util.*;

import static py.com.global.spm.GUISERVICE.report.PdfBuilder.DIRECTION_ASC;
import static py.com.global.spm.GUISERVICE.report.PdfBuilder.ORDER_DEFAULT;

@Service
public class LogMtsService {
    private static final Logger logger = LoggerFactory
            .getLogger(LogMtsService.class);

    @Autowired
    GeneralHelper generalHelper;
    @Autowired
    SuperCompanyService superCompanyService;
    @Autowired
    CompanyService companyService;
    @Autowired
    private FormatProvider formatProvider;

    @Autowired
    private ILogMtsDao logMtsDao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private UtilService utilService;


    private static final String TIMEENDDAY = " 23:59:00";


    public List<Logmts> filterLogMts(CBReport filter, String direction, String properties) {

        List<Logmts> logmts = new ArrayList<>();
        try {
            Sort sort = generalHelper.ordenamiento(direction, properties, DIRECTION_ASC, ORDER_DEFAULT);
            Specification<Logmts> where = getSpecification(filter);
            logmts = logMtsDao.findAll(where, sort);
        } catch (Exception e) {
            logger.error("filterLogMts : " + e.getMessage());
        } finally {
            return logmts;
        }
    }

    public ResponseDto filterLogMtsPag(CBReport filter, String direction, String properties,Integer pagina, Integer rows){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        Sort sort = generalHelper.ordenamiento(direction, properties,"DESC","idlogmtsPk");
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.info("Obteniendo Ordenes de Pago por Filtro {}", filter);
        try {


        Specification<Logmts> where = getSpecification(filter);
        Page<Logmts> logmts = logMtsDao.findAll(where, pageRequest);
        String code;
        if (logmts.getTotalElements() == 0 && logmts.getContent().isEmpty()) {
            code = DataDTO.CODE.OKNODATA.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
            body.put("logmts", null);
            dataDTO.setBody(body);
        } else {
            code = DataDTO.CODE.OK.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            body.put("logmts", addLogResponseDTO(logmts.getContent()));
            dataDTO.setBody(body);
        }
        dataDTO.setDataCode(code);
        dataDTO.setMessage(msg);
        responseDto.setData(dataDTO);
        responseDto.setErrors(null);
        responseDto.setMeta(new MetaDTO(logmts.getSize(), logmts.getTotalPages(), logmts.getTotalElements()));
        logger.debug("Consulta de Log del Mts por filtro exitosa");
        return responseDto;
        }catch (ParseException e){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);
        }catch (Exception e){
            logger.warn("Error en la generacion de lista de log del mts", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }

    }
    private List<LogMtsDto> addLogResponseDTO(List<Logmts> logmts){
        List<LogMtsDto> logMtsDtos = new ArrayList<>();
        for(Logmts logmts1 : logmts){
          LogMtsDto dto = new LogMtsDto();
          dto.setAmountChr(UtilService.toNumberFormat(logmts1.getAmountChr()));
          String name = logmts1.getPaymentorderdetail().getBeneficiary().getBeneficiarynameChr();
          String lastName = logmts1.getPaymentorderdetail().getBeneficiary().getBeneficiarylastnameChr();
          StringBuilder be = new StringBuilder();
          if(name!=null) be.append(name);
          if(lastName!=null) be.append(lastName);
          dto.setBeneficiaryCi(logmts1.getPaymentorderdetail().getBeneficiary().getSubscriberciChr());
          dto.setBeneficiaryName(be.toString());
          dto.setCompanynameChr(logmts1.getCompany().getCompanynameChr());
          dto.setDateHour(UtilService.getDate(logmts1.getRequestdateTim()));
          dto.setIdlogmtsPk(logmts1.getIdlogmtsPk());
          dto.setIdpaymentorderPk(logmts1.getPaymentorderdetail().getId().getIdpaymentorderPk());
          dto.setIdtrxmtsChr(logmts1.getIdtrxmtsChr());
          dto.setStateChr(EstadosMts.enumOfCode(logmts1.getStateChr()).getDescription());
          dto.setPhoneNumber(logmts1.getPaymentorderdetail().getBeneficiary().getPhonenumberChr());
          dto.setYearAndMonth(utilService.getYearMonthFromDate(logmts1.getRequestdateTim()));
          logMtsDtos.add(dto);
        }
        return logMtsDtos;
    }
    private Specification<Logmts> getSpecification(CBReport filter) throws ParseException{
        Specification<Logmts> where ;
        try {
            if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                Long idCompany = companyService.getLoggedUserIdCompany();
                where = Specification.where(LogMtsSpecs.getByCompanyId(idCompany));
            } else {
                where = Specification.where(LogMtsSpecs.getAll());

                if (filter.getIdcompanyPk() != null) {
                    where = where.and(LogMtsSpecs.getByCompanyId(filter.getIdcompanyPk()));
                }
            }
            if (filter.getStateChr() != null && !filter.getStateChr().isEmpty()) {
                where = where.and(LogMtsSpecs.getByEstado(filter.getStateChr()));
            }
            if (filter.getEmailChr() != null) {
                where = where.and(LogMtsSpecs.getByEmailApproval(filter.getEmailChr()));
            }
        if (filter.getPhonenumberChr() != null && !filter.getPhonenumberChr().isEmpty()) {
            where = where.and(LogMtsSpecs.getByBeneficiaryPhoneNumber(filter.getPhonenumberChr()));
        }
        if (filter.getSubscriberciChr() != null && !filter.getSubscriberciChr().isEmpty()) {
            where = where.and(LogMtsSpecs.getByBeneficiaryIdentificator(filter.getSubscriberciChr()));
        }
        Date end = null;
        Date start = null;

            if (filter.getFechaDesde() != null && !filter.getFechaDesde().isEmpty()) {
                start = formatProvider.parseDateReverse(filter.getFechaDesde());
            }
            if (filter.getFechaHasta() != null && !filter.getFechaHasta().isEmpty()) {
                end = formatProvider.parseDateTimeReverse(filter.getFechaHasta() + TIMEENDDAY);
            }
            if (start != null || end != null) {
                where = where.and(LogMtsSpecs.getFechaDesdeHasta("requestdateTim", start, end));
            }
        } catch (ParseException e) {
            logger.error("Invalod date Format Filter: " + e.getMessage());
            throw e;
        }
        return where;
    }
}
