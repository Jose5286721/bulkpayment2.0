package py.com.global.spm.GUISERVICE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IProcessDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBProceso;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.SystemParameters.ProcesoDto;
import py.com.global.spm.GUISERVICE.specifications.ProcesoSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

@Transactional
@Service
public class ProcesosService {

    private static final Logger logger = LoggerFactory
            .getLogger(ProcesosService.class);

    @Autowired
    IProcessDao processDao;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private MessageUtil messageUtil;


    @Autowired
    private PrintResponses printer;

    public ResponseDto getProcesoByFilter(@NotNull(message = "0030") CBProceso criteriosBusqueda, String direction, String properties,
                                          Integer pagina, Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.info("Request Lista Procesos By Filter: {}", criteriosBusqueda);

        try {

            Specification<Process> where = null;
            Page<Process> processes;
            if (Boolean.FALSE.equals(criteriosBusqueda.getAllByPage())) {
                if (criteriosBusqueda.getId() != null) {
                    where = Specification.where(ProcesoSpecs.getByIdProcess(criteriosBusqueda.getId()));
                }
                if (criteriosBusqueda.getDescripcion() != null && !criteriosBusqueda.getDescripcion().isEmpty()) {
                    if (where == null)
                        where = Specification.where(ProcesoSpecs.getByDescripcion(criteriosBusqueda.getDescripcion()));
                    else
                        where = where.and(ProcesoSpecs.getByDescripcion(criteriosBusqueda.getDescripcion()));
                }
                if (criteriosBusqueda.getNombre() != null && !criteriosBusqueda.getNombre().isEmpty()) {
                    if (where == null)
                        where = Specification.where(ProcesoSpecs.getByNombre(criteriosBusqueda.getNombre()));
                    else
                        where = where.and(ProcesoSpecs.getByNombre(criteriosBusqueda.getNombre()));
                }
                processes = processDao.findAll(where, pageRequest);

            } else {
                processes = processDao.findAll(pageRequest);
            }


            String code;
            if (processes.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("procesos", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (processes.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("procesos");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("procesos", addToProcesosListResponseDto(processes.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto
                    .setMeta(new MetaDTO(processes.getSize(), processes.getTotalPages(), processes.getTotalElements()));

            logger.info("Obteniendo Lista de procesos Por Filtro : {}", printer.printResponseDto(responseDto));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de Procesos", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public List<ProcesoDto> addToProcesosListResponseDto(List<Process> processes) {
        List<ProcesoDto> response = new ArrayList<>();
        if (ListHelper.hasElements(processes)) {
            for (Process p : processes) {
                response.add(addToProcesosDto(p));
            }
        }
        return response;
    }

    public ProcesoDto addToProcesosDto(Process process) {
        ProcesoDto dto = new ProcesoDto();
        if (process != null) {
            dto.setIdprocessPk(process.getIdprocessPk());
            dto.setProcessnameChr(process.getProcessnameChr());
            dto.setDescriptionChr(process.getDescriptionChr());
        }
        return dto;
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto por fechaDesde, de manera ascendente*/
        if (direction == null || "".equals(direction)) {
            dire = "ASC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "processnameChr";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    public ResponseDto getAllProcesos() {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Process> procesosList = new ArrayList<>();
        String mensaje = null;

        logger.info("Obteniendo TODOS los Procesos.....");
        try {
            procesosList = processDao.findAll();
            if (ListHelper.hasElements(procesosList)) {
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            } else {
                replaceText.add("procesos");
                mensaje = messageUtil.getMensaje(ErrorsDTO.CODE.NONELISTMASC.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("procesos", addToProcesosListResponseDto(procesosList));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("Obteniendo Lista de Procesos--> {}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de procesos. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    /**
     * Este metodo se encarga de editar un Proceso.
     *
     * @param request
     * @return {@link ResponseDto}
     */
    public ResponseDto editProceso(@NotNull(message = "0030") ProcesoDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje = null;
        List<String> replaceText = new ArrayList<>();
        logger.info("Editando Proceso -->{}", request);

        try {

            Process proceso = processDao.findByidprocessPk(request.getIdprocessPk());

            if (proceso == null) {
                replaceText.add("Proceso");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }
            logger.info("Editando Proceso-->> {}", proceso.getIdprocessPk());
            proceso.setDescriptionChr(request.getDescriptionChr());
            Process newprocess = saveOrUpdate(proceso);
            replaceText.add("proceso");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("proceso", addToProcesosDto(newprocess));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Proceso editado con éxito. ID: " + proceso.getIdprocessPk(), printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar editar el proceso: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public ResponseDto getProcesoService(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {
            Process process = getProceso(id);

            if (process == null) {
                replaceText.add("Proceso");
                replaceText.add(id.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }

            replaceText.add("proceso");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("proceso", addToProcesosDto(process));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el Proceso: " + id, e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public Process getProceso(Long id) {

        try {
            return processDao.findByidprocessPk(id);
        } catch (Exception e) {
            logger.error("Process DAO ERROR", e);
            throw e;
        }
    }

    public Process saveOrUpdate(Process process) throws Exception {
        try {
            return processDao.save(process);
        } catch (Exception e) {
            logger.error("Error al Guardar/Actualizar el Proceso", e);
            throw e;
        }
    }

    public ProcesoDto addToProcessDto(Process process) {
        ProcesoDto dto = new ProcesoDto();
        dto.setIdprocessPk(process.getIdprocessPk());
        dto.setProcessnameChr(process.getProcessnameChr());
        dto.setDescriptionChr(process.getDescriptionChr());
        return dto;
    }

}
