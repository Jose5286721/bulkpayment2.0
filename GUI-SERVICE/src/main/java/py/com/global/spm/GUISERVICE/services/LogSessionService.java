package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.ILogSessionDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBLogSession;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Log.LogSessionDto;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.specifications.LogSessionSpecs;
import py.com.global.spm.GUISERVICE.utils.FormatProvider;
import py.com.global.spm.GUISERVICE.utils.GeneralHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.domain.entity.Logsession;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogSessionService {
    private static final Logger logger = LoggerFactory.getLogger(LogSessionService.class);

    @Autowired
    private ILogSessionDao dao;

    @Autowired
    private GeneralHelper generalHelper;

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private FormatProvider formatProvider;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;


    private static final String TIMEENDDAY = " 23:59:00";


    /**
     * Crear o actualizar un logsession
     *
     * @param logsession
     * @return {@link Logsession}
     * @throws Exception
     */
    public Logsession saveOrUpdate(Logsession logsession) throws Exception {
        try {
            return dao.save(logsession);
        } catch (Exception e) {
            logger.warn("Error en guardar Session", e);
            throw e;
        }
    }

    /**
     * Obtener sesion segun filtro
     * @param filter
     * @param direction
     * @param properties
     * @param pagina
     * @param rows
     * @return {@link ResponseDto}
     */
    public ResponseDto getLogSessionByFilter(CBLogSession filter, String direction, String properties, Integer pagina, Integer rows){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        Sort sort = generalHelper.ordenamiento(direction, properties,"DESC","logindateTim");
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        if (!superCompanyService.isLoggedUserFromSuperCompany()) {
            return responseDtoService.errorResponse(ErrorsDTO.CODE.DENIEDACCES.getValue(),null);
        }
        logger.info("Obteniendo Log de Sesion por Filtro {}", filter);
        try {
            Specification<Logsession> where = getSpecification(filter);
            Page<Logsession> logsession = dao.findAll(where, pageRequest);
            String code;
            if (logsession.getTotalElements() == 0 && logsession.getContent().isEmpty()) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("logsession", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                body.put("logsession", logsession.getContent().stream().map(LogSessionDto::new).collect(Collectors.toList()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(logsession.getSize(), logsession.getTotalPages(), logsession.getTotalElements()));
            logger.debug("Consulta de Log de Sesion por filtro exitosa");
            return responseDto;
        }catch (ParseException e){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);
        }catch (Exception e){
            logger.warn("Error en la generacion de lista de log de sesion", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public List<Logsession> filterLogSession(CBLogSession filter, String direction, String properties) {

        List<Logsession> logaudits = new ArrayList<>();
        try {
            Sort sort = generalHelper.ordenamiento(direction, properties,"DESC","logindateTim");
                Specification<Logsession> where = getSpecification(filter);
                logaudits = dao.findAll(where,sort);
        } catch (Exception e) {
            logger.error("filterLogMts : {}",e.getMessage());
        }
        return logaudits;
    }
    private Specification<Logsession> getSpecification(CBLogSession filter) throws ParseException{
        Specification<Logsession> where ;
        try {
            if (filter.getUserNameChr() != null && !filter.getUserNameChr().isEmpty()) {
                where = Specification.where(LogSessionSpecs.getByUsername(filter.getUserNameChr()));
            }else
                where = Specification.where(LogSessionSpecs.getAll());
            Date end = null;
            Date start = null;

            if (filter.getSinceDate() != null && !filter.getSinceDate().isEmpty()) {
                start = formatProvider.parseDateReverse(filter.getSinceDate());
            }
            if (filter.getToDate() != null && !filter.getToDate().isEmpty()) {
                end = formatProvider.parseDateTimeReverse(filter.getToDate() + TIMEENDDAY);
            }
            if (start != null || end != null) {
                where = where.and(LogSessionSpecs.getFechaDesdeHasta("logindateTim", start, end));
            }
        } catch (ParseException e) {
            logger.error("Invalod date Format Filter: " + e.getMessage());
            throw e;
        }
        return where;
    }

}
