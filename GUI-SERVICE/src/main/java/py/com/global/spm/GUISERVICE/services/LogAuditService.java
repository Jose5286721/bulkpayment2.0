package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.ILogAuditDao;
import py.com.global.spm.GUISERVICE.dto.Auditoria.AuditDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBAudit;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.specifications.AuditSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.Logaudit;

import java.text.ParseException;
import java.util.*;


/**
 * Created by global on 3/14/18.
 */

@Service
public class LogAuditService {

    private static final Logger logger = LoggerFactory
            .getLogger(LogAuditService.class);

    @Autowired
    private ILogAuditDao dao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private PrintResponses printer;

    @Autowired
    private FormatProvider formatProvider;

    private static final String TIMEENDDAY = " 23:59:00";

    /**
     * Crear o actualizar un logaudit
     *
     * @param logaudit
     * @return {@link Logaudit}
     * @throws Exception
     */
    public Logaudit saveOrUpdate(Logaudit logaudit) throws Exception {
        try {
            String params = logaudit.getParamsChr();
            logaudit.setParamsChr(params.length()>SPM_GUI_Constants.MAX_LENGTH_AUDIT_PARAMS? params.substring(SPM_GUI_Constants.MAX_LENGTH_AUDIT_PARAMS-1):params);
            return dao.save(logaudit);

        } catch (Exception e) {
            logger.warn("Error en actualizar/guardar Auditoria", e);
            throw e;
        }
    }

    /**
     * Obtener toda la auditoria
     * @return responseDto
     * @throws Exception
     */
    public ResponseDto getAllLogAudit() throws Exception{
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Logaudit> LogAuditList = new ArrayList<>();
        String mensaje = null;
        try{

            LogAuditList = dao.findAll();
            if (ListHelper.hasElements(LogAuditList)){
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            }else {
                replaceText.add("LogAudit");
                mensaje = messageUtil.getMensaje(ErrorsDTO.CODE.NONELISTFEM.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("auditoria", addToLogAuditListResponseDto(LogAuditList));
            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            return responseDto;

        }catch (Exception e){
            logger.error("Error al obtener Auditoria. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);

        }
    }

    public List<AuditDto> addToLogAuditListResponseDto(List<Logaudit> logaudit){
        List<AuditDto> response=new ArrayList<>();
        if(logaudit!=null){
            for(Logaudit la:logaudit){
                response.add((addToLogAuditResponseDto(la)));
            }

        }
        return response;

    }

    public AuditDto addToLogAuditResponseDto(Logaudit logaudit){
        String acceso = null;
        if (logaudit.getAccesstypeChr() !=null && !logaudit.getAccesstypeChr().isEmpty()){
            String access = logaudit.getAccesstypeChr();
            switch (access){
                case "audit_persist":
                    acceso = "Creacion";
                    break;
                case "audit_delete":
                    acceso = "Eliminacion";
                    break;
                case "audit_update":
                    acceso = "Modificacion";
                    break;
            }
        }
        AuditDto dto=new AuditDto();
        dto.setIp(logaudit.getIpChr());
        dto.setUsernameChr(logaudit.getUsernameChr());
        dto.setAccessType(acceso);
        dto.setFechaCreacion(logaudit.getFechacreacionTim());
        dto.setParams(logaudit.getParamsChr());
        dto.setDescription(logaudit.getDescriptionChr());
        return dto;

    }

    public List<Logaudit> filterLogAudit(CBAudit filter, String direction, String properties) {

        List<Logaudit> logaudits = new ArrayList<>();
        try {
            Sort sort = ordenamiento(direction, properties);
            if (!filter.getAllByPage()) {
                Specification<Logaudit> where = getSpecification(filter);
                logaudits = dao.findAll(where,sort);
            } else {
                logaudits = dao.findAll(sort);
            }
        } catch (Exception e) {
            logger.error("filterLogAudit : " + e.getMessage());
        } finally {
            return logaudits;
        }
    }
    /**
     * Obtener auditoria segun filtro
     * @param criteriosBusqueda
     * @param direction
     * @param properties
     * @param pagina
     * @param rows
     * @return {@link ResponseDto}
     */
    public ResponseDto getLogAuditByFilter(CBAudit criteriosBusqueda, String direction,
                                           String properties, Integer pagina, Integer rows){

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        try {

            if (criteriosBusqueda == null || criteriosBusqueda.getAllByPage() == null) {
                return responseDtoService.errorResponse
                        (ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), null);
            }

            Specification<Logaudit> where;
            Page<Logaudit> logAudits;

            if (!criteriosBusqueda.getAllByPage()) {
                where = getSpecification(criteriosBusqueda);
                logAudits = dao.findAll(where, pageRequest);
            } else {
                logAudits = dao.findAll(pageRequest);
            }

            String code;
            if (logAudits.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("auditoria", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (logAudits.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("auditoria");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("auditoria", addToLogAuditListResponseDto(logAudits.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(logAudits.getSize(), logAudits.getTotalPages(),
                    logAudits.getTotalElements()));
            logger.info("Obteniendo lista de auditoria por Filtro : {}", printer.printResponseDto(responseDto));

            return responseDto;
        }catch (Exception e){
            logger.warn("Error en la generacion de lista de Auditoria", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }
    }

    private Sort ordenamiento(String direction, String properties) {
        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto por fechaDesde, de manera descend*/
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "fechacreacionTim";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);

    }


    private Specification<Logaudit> getSpecification(CBAudit criteriosBusqueda)throws ParseException{
        Specification<Logaudit> where;
        try{

        if (criteriosBusqueda.getUsernameChr() != null) {
            where = Specification.where(AuditSpecs.getByUsuario(criteriosBusqueda.getUsernameChr()));
        }else
            where = Specification.where(AuditSpecs.getAll());
        if (criteriosBusqueda.getIpChr() != null && !criteriosBusqueda.getIpChr().isEmpty()) {
            where = where.and(AuditSpecs.getByIp(criteriosBusqueda.getIpChr()));
        }
        if (criteriosBusqueda.getAccesstypeChr() != null && !criteriosBusqueda.getAccesstypeChr().isEmpty()) {
            where = where.and(AuditSpecs.getByAccessType(criteriosBusqueda.getAccesstypeChr()));
        }
        Date end = null;
        Date start = null;
        if (criteriosBusqueda.getSinceDate() != null && !criteriosBusqueda.getSinceDate().isEmpty()) {
            start = formatProvider.parseDateReverse(criteriosBusqueda.getSinceDate());
        }
        if (criteriosBusqueda.getToDate() != null && !criteriosBusqueda.getToDate().isEmpty()) {
            end = formatProvider.parseDateTimeReverse(criteriosBusqueda.getToDate() + TIMEENDDAY);
        }
        if (start != null || end != null) {
            where = where.and(AuditSpecs.getByRangeDate(start, end));
        }
    } catch (ParseException e) {
        logger.error("Invalod date Format Filter: " + e.getMessage());
        throw e;
    }
        return where;
    }


}
