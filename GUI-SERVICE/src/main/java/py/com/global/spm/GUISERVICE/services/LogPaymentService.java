package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.ILogPaymentDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogPayment;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Log.LogPaymentDto;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.report.PdfBuilder;
import py.com.global.spm.domain.entity.Logpayment;
import py.com.global.spm.GUISERVICE.specifications.LogPaymentSpecs;
import py.com.global.spm.GUISERVICE.utils.FormatProvider;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogPaymentService {
    private static final Logger logger = LoggerFactory
            .getLogger(LogPaymentService.class);

    @Autowired
    GeneralHelper generalHelper;
    @Autowired
    SuperCompanyService superCompanyService;
    @Autowired
    CompanyService companyService;
    @Autowired
    private FormatProvider formatProvider;

    @Autowired
    private ILogPaymentDao logPaymentDao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;


    private static final String TIMEENDDAY = " 23:59:00";



    public ResponseDto filterLogpaymentPag(CBLogPayment filter, String direction, String properties, Integer pagina, Integer rows){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        Sort sort = generalHelper.ordenamiento(direction, properties,"DESC","creationdateTim");
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.info("Obteniendo Logs de Pagos por Filtro {}", filter);
        try {


        Specification<Logpayment> where = getSpecification(filter);
        Page<Logpayment> logpayment = logPaymentDao.findAll(where, pageRequest);
        String code;
        if (logpayment.getTotalElements() == 0 && logpayment.getContent().isEmpty()) {
            code = DataDTO.CODE.OKNODATA.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
            body.put("logpayment", null);
            dataDTO.setBody(body);
        } else {
            code = DataDTO.CODE.OK.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            body.put("logpayment", logpayment.getContent().stream().map(LogPaymentDto::new).collect(Collectors.toList()));
            dataDTO.setBody(body);
        }
        dataDTO.setDataCode(code);
        dataDTO.setMessage(msg);
        responseDto.setData(dataDTO);
        responseDto.setErrors(null);
        responseDto.setMeta(new MetaDTO(logpayment.getSize(), logpayment.getTotalPages(), logpayment.getTotalElements()));
        logger.debug("Consulta de Logs de Pagos por filtro exitosa");
        return responseDto;
        }catch (ParseException e){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);
        }catch (Exception e){
            logger.warn("Error en la generacion de lista de logs de pagos", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }

    }
    public List<Logpayment> filterLogPaymentReport(CBLogPayment filter,String properties, String direction){
        List<Logpayment> logpayments = new ArrayList<>();
        try {
            Sort sort = generalHelper.ordenamiento(direction, properties, PdfBuilder.DIRECTION_ASC , "idpaymentNum");
            Specification<Logpayment> where = getSpecification(filter);
            logpayments =  logPaymentDao.findAll(where, sort);

        } catch (Exception e) {
            logger.error("filterLogPayment : {}", e.getMessage());
        }
        return logpayments;

    }
    private Specification<Logpayment> getSpecification(CBLogPayment filter) throws ParseException{
        Specification<Logpayment> where ;
        try {
            if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                Long idCompany = companyService.getLoggedUserIdCompany();
                where = Specification.where(LogPaymentSpecs.getByCompanyId(idCompany));
            } else {
                where = Specification.where(LogPaymentSpecs.getAll());

                if (filter.getIdcompanyPk() != null) {
                    where = where.and(LogPaymentSpecs.getByCompanyId(filter.getIdcompanyPk()));
                }
            }
            if (filter.getStateChr() != null) {
                where = where.and(LogPaymentSpecs.getByEstado(filter.getStateChr()));
            }
            if (filter.getIdpaymentorderPk() != null) {
                where = where.and(LogPaymentSpecs.getByIdPaymentOrder(filter.getIdpaymentorderPk()));
            }
            if (filter.getIdpaymentNum() != null) {
                where = where.and(LogPaymentSpecs.getById(filter.getIdpaymentNum()));
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
                where = where.and(LogPaymentSpecs.getFechaDesdeHasta("creationdateTim", start, end));
            }
        } catch (ParseException e) {
            logger.error("Invalod date Format Filter: {}", e.getMessage());
            throw e;
        }
        return where;
    }
}
