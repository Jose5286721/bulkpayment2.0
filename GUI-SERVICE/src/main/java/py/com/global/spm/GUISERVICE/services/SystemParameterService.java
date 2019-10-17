package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.ISystemParameterDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBSystemParameter;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.SystemParameters.SystemParameterRequestDto;
import py.com.global.spm.GUISERVICE.dto.SystemParameters.SystemParamteterResponseDto;
import py.com.global.spm.GUISERVICE.specifications.SystemParameterSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.domain.entity.SystemparameterId;
import py.com.global.spm.domain.entity.Process;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Transactional
@Service
@CacheConfig(cacheNames = "systemParameter")
public class SystemParameterService {

    private static final Logger logger = LoggerFactory
            .getLogger(SystemParameterService.class);


    @Autowired
    private ISystemParameterDao dao;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private ProcesosService procesosService;

    @Autowired
    private MessageUtil messageUtil;


    @Autowired
    private PrintResponses printer;

    public Systemparameter saveOrUpdate(Systemparameter systemparameter) {
            return dao.save(systemparameter);
    }
    @Cacheable(key = "#request", unless = "#result==null")
    public Systemparameter findByParameter(String request){
        return dao.findByIdParameterPk(request.trim());

    }

    Systemparameter findById(SystemparameterId id){
        return dao.findById(id);
    }

    @Cacheable(key = "#processId+'-'+#parameterPk", unless = "#result.equals(#defaultValue)")
    public String getSystemParameterValue(Long processId, String parameterPk, String defaultValue) {
        try {
            Systemparameter sp = findById(new SystemparameterId(parameterPk,processId));
            if (sp == null) {
                return defaultValue;
            }
            return sp.getValueChr();
        }catch (Exception e){
            logger.error("Error al obtener el valor del parametro de sistema",e);
            return defaultValue;
        }

    }


    @CacheEvict(key = "#request.parametro", allEntries = true)
    public ResponseDto addSystemParameter(@NotNull(message = "0030") SystemParameterRequestDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje = null;
        List<String> replaceText = new ArrayList<>();
        logger.info("Agreando nuevo SystemParameter---> {}", request);
        try {

            Process process=procesosService.getProceso(request.getIdProceso());

            if (process==null) {
                replaceText.add("proceso");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }

            if(existParametro(request.getParametro(),request.getIdProceso()))
                return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(), null);


            Systemparameter systemparameter = setCommonData(request);
            systemparameter.setProcess(process);
            systemparameter.setId(new SystemparameterId(request.getParametro(),process.getIdprocessPk()));

            Systemparameter sys = saveOrUpdate(systemparameter);

            replaceText.add("SystemParameter");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("systemparameter", addToSystemParameterResponseDto(sys));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("{}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar crear el SystemParameter: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    @CacheEvict(key = "#request.parametro", allEntries = true)
    public ResponseDto editSystemParamter(@NotNull(message = "0030") SystemParameterRequestDto request){
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje = null;
        List<String> replaceText = new ArrayList<>();
        logger.info("Editando SystemParameter---> {}", request);
        try {

            Process process=procesosService.getProceso(request.getIdProceso());
            if (process==null) {
                replaceText.add("proceso");
                logger.error("No existe el Proceso Especificado para el SystemParameter");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }
            if(!existParametro(request.getParametro(),request.getIdProceso())) {
                logger.info("El SystemParameter que intenta editar NO EXISTE");
                replaceText.add("Systemparameter");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            Systemparameter systemparameter = setCommonData(request);
            systemparameter.setProcess(process);
            systemparameter.setId(new SystemparameterId(request.getParametro(),process.getIdprocessPk()));
            Systemparameter sys = saveOrUpdate(systemparameter);
            replaceText.add("SystemParameter");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("systemparameter", addToSystemParameterResponseDto(sys));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("{}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
        logger.error("Error al intentar editar el SystemParameter: ", e);
        replaceText.add(e.getMessage());
        return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
    }
    }
    public ResponseDto deleteSystemParameter(SystemParameterRequestDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje = null;
        List<String> replaceText = new ArrayList<>();
        logger.info("Eliminando SystemParameter---> {}", request);
        try {

            Process process = procesosService.getProceso(request.getIdProceso());
            if (process == null) {
                replaceText.add("proceso");
                logger.error("No existe el Proceso Especificado para el SystemParameter");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }
            if (!existParametro(request.getParametro(), request.getIdProceso())) {
                logger.info("El SystemParameter que intenta eliminar NO EXISTE");
                replaceText.add("Systemparameter");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), null);
            }

            Systemparameter sys = findById(new SystemparameterId(request.getParametro(),request.getIdProceso()));
            logger.info("Se elimina el Siguiente Parámetro de Systema ---> IdProceso: {} Parámetro: {} FechaCreacion: {} Descripcion: {} Valor: {}",
                    sys.getId().getIdprocessPk(),sys.getId().getParameterPk(),sys.getCreationdateTim(),
                    sys.getDescripcionChr(),sys.getValueChr());
            dao.delete(sys);
            replaceText.add("SystemParameter");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.DELETEEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("systemparameter", addToSystemParameterResponseDto(sys));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("{}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar editar el SystemParameter: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }
// Descomentar lo siguiente si se desea filtro cacheado en el pametro del sistema.
//  @Cacheable(key = "#p0.parametro", condition = "#p0.parametro != null ")
    public ResponseDto getSystemParameterByFilter(@NotNull(message = "0030") CBSystemParameter criteriosBusqueda, String direction, String properties,
                                                  Integer pagina, Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        Pageable pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.info("Pagina: "+pagina+ " Rows: "+rows);
        logger.info("Request Lista SystemParameters By Filter: {}", criteriosBusqueda);

        try {

            Specification<Systemparameter> where = null;
            Page<Systemparameter> systemparameters;
            if (!criteriosBusqueda.getAllByPage()) {
                if (criteriosBusqueda.getIdProceso() != null) {
                    where = Specification.where(SystemParameterSpecs.getByProcessId(criteriosBusqueda.getIdProceso()));
                }
                if (criteriosBusqueda.getParametro() != null && !criteriosBusqueda.getParametro().isEmpty()) {
                    if (where == null)
                        where = Specification.where(SystemParameterSpecs.getByParametro(criteriosBusqueda.getParametro()));
                    else
                        where = where.and(SystemParameterSpecs.getByParametro(criteriosBusqueda.getParametro()));
                }
                if (criteriosBusqueda.getValor() != null && !criteriosBusqueda.getValor().isEmpty()) {
                    if (where == null)
                        where = Specification.where(SystemParameterSpecs.getByValor(criteriosBusqueda.getValor()));
                    else
                        where = where.and(SystemParameterSpecs.getByValor(criteriosBusqueda.getValor()));
                }
                systemparameters = dao.findAll(where,pageRequest);

            } else {
                systemparameters = dao.findAll(pageRequest);
            }
            String code;
            if (systemparameters.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("systemparameters", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (systemparameters.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("systemparameters");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("systemparameters", addToSystemParameterResponseDtoList(systemparameters.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto
                    .setMeta(new MetaDTO(systemparameters.getSize(), systemparameters.getTotalPages(), systemparameters.getTotalElements()));

            logger.info("Obteniendo Lista de SystemParameters Por Filtro : {}", printer.printResponseDto(responseDto));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de SystemParameters", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public ResponseDto getSystemparameterService(@NotNull(message = "0030") SystemParameterRequestDto request) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        logger.info("Consultando SystemParameter ID: {} ", request);
        try {

            Systemparameter systemparameter = findById(new SystemparameterId(request.getParametro(),request.getIdProceso()));

            if (systemparameter == null) {
                replaceText.add("SystemParamter");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            replaceText.add("systemparameter");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("systemparameter", addToSystemParameterResponseDto(systemparameter));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("SystemParameter Obtenido con Éxito {}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el SystemParameter. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }


    Long getSuperCompanyId() {
        Systemparameter systemparameter = findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETERS_SUPER_COMPANY_ID);
        return Long.parseLong(systemparameter.getValueChr());
    }

    Long getSuperUserId() {
        Systemparameter systemparameter = findByParameter(SPM_GUI_Constants.SYSTEM_PARAMETERS_USER_DEFAULT);
        return Long.parseLong(systemparameter.getValueChr());
    }
    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto por fechaDesde, de manera ascendente*/
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "id";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

   private Systemparameter setCommonData(SystemParameterRequestDto dto){
        Systemparameter systemparameter=new Systemparameter();
        systemparameter.setDescripcionChr(dto.getDescripcion());
        systemparameter.setCreationdateTim(new Date());
        systemparameter.setValueChr(dto.getValor());
        return systemparameter;
   }

   private boolean existParametro(String param, Long idProcess){
       Systemparameter systemparameter = findById(new SystemparameterId(param,idProcess));
       return  systemparameter!=null;
   }
    private SystemParamteterResponseDto addToSystemParameterResponseDto(Systemparameter sys){
       SystemParamteterResponseDto responseDto=new SystemParamteterResponseDto();
       responseDto.setIdProcess(sys.getProcess().getIdprocessPk());
       responseDto.setParametro(sys.getId().getParameterPk());
       responseDto.setValor(sys.getValueChr());
       responseDto.setProcessnameChr(sys.getProcess().getProcessnameChr());
       responseDto.setProceso(procesosService.addToProcessDto(sys.getProcess()));
       responseDto.setDescripcion(sys.getDescripcionChr());
       return responseDto;
    }
    private List<SystemParamteterResponseDto>addToSystemParameterResponseDtoList(List<Systemparameter> list){
        List<SystemParamteterResponseDto> responseDtos=new ArrayList<>();
        if(list!=null){
            for(Systemparameter sys:list){
                responseDtos.add(addToSystemParameterResponseDto(sys));
            }
        }
        return responseDtos;
    }

    ConcurrentHashMap<String, String> getDriversParameters() {
        ConcurrentHashMap<String, String> parameters = null;
        try {
            List<Systemparameter> list = dao.findByProcessIdprocessPk(SPM_GUI_Constants.DRIVER_NOTIFICATIONS);
            if (list != null && !list.isEmpty()) {
                parameters = new ConcurrentHashMap<>();
                for (Systemparameter parameter : list) {
                    parameters.put(parameter.getId().getParameterPk(), parameter.getValueChr());
                }
            }
            return parameters;
        } catch (Exception e) {
            logger.error("Error al obtener los parametros por IdProcess {}", SPM_GUI_Constants.DRIVER_NOTIFICATIONS);
            throw e;
        }
    }

}
