package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.ICodigoEventos;
import py.com.global.spm.GUISERVICE.dto.CodigoEventos.CodigoEventoRequestDto;
import py.com.global.spm.GUISERVICE.dto.CodigoEventos.CodigoEventosDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBCodigoEventos;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.domain.entity.Eventcode;
import py.com.global.spm.domain.entity.EventcodeId;
import py.com.global.spm.GUISERVICE.specifications.CodigoEventosSpecs;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.GUISERVICE.utils.PrintResponses;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CodigoEventosService {
    private static final Logger logger = LoggerFactory
            .getLogger(CodigoEventosService.class);

    private final ICodigoEventos dao;

    private final ResponseDtoService responseDtoService;

    private final MessageUtil messageUtil;

    private final PrintResponses printer;

    private final ProcesosService procesosService;

    public CodigoEventosService(ICodigoEventos dao, ResponseDtoService responseDtoService, MessageUtil messageUtil, PrintResponses printer, ProcesosService procesosService) {
        this.dao = dao;
        this.responseDtoService = responseDtoService;
        this.messageUtil = messageUtil;
        this.printer = printer;
        this.procesosService = procesosService;
    }

    public Eventcode saveOrUpdate(Eventcode eventcode) {
        return dao.save(eventcode);
    }
    public ResponseDto getEventCodeByFilter(@NotNull(message = "0030") CBCodigoEventos criteriosBusqueda, String direction, String properties,
                                                  Integer pagina, Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.info("Request Lista Codigo Eventos By Filter: {}", criteriosBusqueda);
        try {
            Page<Eventcode> eventcodes;
            if (Boolean.FALSE.equals(criteriosBusqueda.getAllByPage())) {
                Specification<Eventcode> where = getSpecifications(criteriosBusqueda);
                eventcodes = dao.findAll(where,pageRequest);
            } else
                eventcodes = dao.findAll(pageRequest);

            String code;
            if (eventcodes.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("eventcodes", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (eventcodes.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("eventcodes");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("eventcodes", addToEventcodeResponseDtoList(eventcodes.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto
                    .setMeta(new MetaDTO(eventcodes.getSize(), eventcodes.getTotalPages(), eventcodes.getTotalElements()));

            logger.info("Obteniendo Lista de EventCodes Por Filtro : {}", printer.printResponseDto(responseDto));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de EventCodes", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public Specification<Eventcode> getSpecifications(CBCodigoEventos criteriosBusqueda ){
        Specification<Eventcode> where = null;
        if (criteriosBusqueda.getIdProceso() != null) {
            where = Specification.where(CodigoEventosSpecs.getByProcessId(criteriosBusqueda.getIdProceso()));
        }
        if (criteriosBusqueda.getEvento() != null) {
            if (where == null)
                where = Specification.where(CodigoEventosSpecs.getByParametro(criteriosBusqueda.getEvento()));
            else
                where = where.and(CodigoEventosSpecs.getByParametro(criteriosBusqueda.getEvento()));
        }
        return where;
    }

    public ResponseDto editCodigoEvento(@NotNull(message = "0030") CodigoEventoRequestDto request) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje = null;
        List<String> replaceText = new ArrayList<>();
        logger.info("Editando SystemParameter---> {}", request);
        try {

            if (!existId(request.getIdprocessPk(), request.getIdeventcodeNum())) {
                logger.info("El Eventcode que intenta editar NO EXISTE");
                replaceText.add("Eventcode");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            Eventcode eventcode = setCommonData(request);
            Eventcode eventcodeSaved = saveOrUpdate(eventcode);
            replaceText.add("Eventcode");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("eventcode", addToEventCodeDto(eventcodeSaved));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("{}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar editar el Event Code: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public ResponseDto getCodigoEventoService(@NotNull(message = "0030") CodigoEventoRequestDto request) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        logger.info("Consultando  ID: {} ", request);
        try {

            Eventcode eventcode = dao.findById(new EventcodeId(request.getIdprocessPk(),request.getIdeventcodeNum()));

            if (eventcode == null) {
                replaceText.add("EventCode");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            replaceText.add("Eventcode");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("eventcode", addToEventCodeDto(eventcode));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("EventCode Obtenido con Éxito {}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el EventCode. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public boolean existId(Long idprocess,Long evento){
        Eventcode eventcode=dao.findById(new EventcodeId(idprocess,evento));
        return (eventcode != null);
    }

    public List<CodigoEventosDto> addToEventcodeResponseDtoList(List<Eventcode> eventcodes){
        List<CodigoEventosDto> codigoEventosDto= new ArrayList<>();
        if(eventcodes!=null){
          for(Eventcode eventcode:eventcodes){
              codigoEventosDto.add(addToEventCodeDto(eventcode));
          }
        }
        return codigoEventosDto;
    }

    public Eventcode setCommonData(CodigoEventoRequestDto dto){
        Eventcode eventcode=new Eventcode();
        if(dto!=null) {
            eventcode.setDescriptionChr(dto.getDescriptionChr());
            eventcode.setId(new EventcodeId(dto.getIdprocessPk(), dto.getIdeventcodeNum()));
            eventcode.setProcess(procesosService.getProceso(dto.getIdprocessPk()));
        }
        return  eventcode;
    }

    public CodigoEventosDto addToEventCodeDto(Eventcode eventcode){
        CodigoEventosDto dto=new CodigoEventosDto();
        if(eventcode!=null) {
            dto.setDescriptionChr(eventcode.getDescriptionChr());
            dto.setIdeventcodeNum(eventcode.getId().getIdeventcodeNum());
            dto.setIdprocessPk(eventcode.getId().getIdprocessPk());
            dto.setProceso(procesosService.addToProcessDto(eventcode.getProcess()));
        }
        return dto;
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "id";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }
}
