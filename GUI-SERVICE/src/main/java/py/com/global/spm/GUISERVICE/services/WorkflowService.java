package py.com.global.spm.GUISERVICE.services;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import py.com.global.spm.GUISERVICE.dao.IPaymentOrderDao;
import py.com.global.spm.GUISERVICE.dao.IWorkflowDao;
import py.com.global.spm.GUISERVICE.dao.IWorkflowDetalleDao;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBWorkflow;
import py.com.global.spm.GUISERVICE.dto.User.UserFirmanteResponseDto;
import py.com.global.spm.GUISERVICE.dto.User.UserResponseListDto;
import py.com.global.spm.GUISERVICE.dto.Workflow.*;
import py.com.global.spm.GUISERVICE.specifications.WorkflowSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.*;

import javax.validation.constraints.NotNull;

@Transactional
@Service
public class WorkflowService {
    private static final Logger logger = LoggerFactory
            .getLogger(WorkflowService.class);

    @Autowired
    private IWorkflowDao dao;

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private GeneralHelper generalHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private IWorkflowDetalleDao detalleDao;

    @Autowired
    private PrintResponses printer;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private IPaymentOrderDao paymentOrderDao;

    public Workflow saveOrUpdate(Workflow workflow) throws Exception {
        try {
            return dao.save(workflow);
        } catch (Exception e) {
            logger.error("Error al Guardar/Actualizar el Workflow", e);
            throw e;
        }
    }

    /**
     * Obtener un workflow por su id.
     *
     * @param id
     * @return {@link User}
     */
    public Workflow getWorkflowById(Long id) {
        return dao.findByidworkflowPk(id);
    }

    public boolean existWorkflowByName(String workflow) {
        List<Workflow> l = dao.findByworkflownameChr(workflow);
        return (l != null && !l.isEmpty());
    }

    private Workflow setCommonData(WorkflowResponseDto requestDto,Company company) {
        Workflow workflow = new Workflow();
        if(superCompanyService.isLoggedUserFromSuperCompany()){
            workflow.setCompany(company);
        }else{
            workflow.setCompany(superCompanyService.getLoggedUserCompany());
        }
        if(requestDto.getWalletChr()!=null)
        {
            workflow.setWalletChr(requestDto.getWalletChr());
        }else{
            workflow.setWalletChr(systemParameterService.findById(new SystemparameterId(SPM_GUI_Constants.WORKFLOW_DEFAULT_WALLET,SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID)).getValueChr());
        }
        workflow.setDescriptionChr(requestDto.getDescriptionChr());
        workflow.setWorkflownameChr(requestDto.getWorkflownameChr());
        workflow.setStateChr(requestDto.getStateChr());
        if (requestDto.getIdworkflowPk() != null)
            workflow.setIdworkflowPk(requestDto.getIdworkflowPk());
        return workflow;
    }

    public WorkflowDto addToWorkflowResponseDto(Workflow workflow) {
        WorkflowDto dto = new WorkflowDto();
        dto.setIdcompany(workflow.getCompany().getIdcompanyPk());
        dto.setCompany(companyService.addToCompanyResponseDto(workflow.getCompany()));
        dto.setCompanyNameChr(workflow.getCompany().getCompanynameChr());
        dto.setDescriptionChr(workflow.getDescriptionChr());
        dto.setIdworkflowPk(workflow.getIdworkflowPk());
        dto.setStateChr(workflow.getStateChr());
        dto.setWalletChr(workflow.getWalletChr());
        dto.setWorkflownameChr(workflow.getWorkflownameChr());
        dto.setIsLoggedSuperCompany(superCompanyService.isLoggedUserFromSuperCompany());
        return dto;
    }


    public List<Workflowdet> addToDetails(List<UserResponseListDto> firmantes) {
        try {
            List<Workflowdet> detalle = new ArrayList<>();
            if (firmantes != null) {

                for (UserResponseListDto user : firmantes) {
                    User userById = userService.getUserById(user.getIduserPk());
                    if(userById != null){
                        Workflowdet det = new Workflowdet();
                        det.setUser(userById);
                        detalle.add(det);
                    }
                }
            }
            return detalle;
        } catch (Exception e) {
            logger.error("Error al agregar el Detalle del Workflow", e);
            return null;
        }
    }


    public ResponseDto addWorkflow(@NotNull(message = "0030") WorkflowResponseDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();
        logger.info("Agreando nuevo Workflow---> {}", request);
        try {
            Company company = companyService.getCompanyById(request.getIdcompany());
            if(superCompanyService.isLoggedUserFromSuperCompany()){
                if(request.getIdcompany() == null){
                    replaceText.add("empresa");
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);

                }
                if(company == null){
                    return responseDtoService.errorResponse(ErrorsDTO.CODE.NOCOMPANYASOC.getValue(), null);

                }
            }


            if (superCompanyService.isLoggedUserFromSuperCompany() && request.getIdcompany() == null){
                replaceText.add("empresa");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }

            if (!ListHelper.hasElements(request.getListaFirmantes())) {
                replaceText.add("Campo Firmantes no puede estar vacio");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), replaceText);
            }
            if (existWorkflowByName(request.getWorkflownameChr())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(), null);
            }
            if (!EstadosWorkflow.ACTIVE.getCodigo0().equals(request.getStateChr())
                    && (!EstadosWorkflow.INACTIVE.getCodigo0().equals(request.getStateChr()))) {
                logger.info("Estado Ivalido-->: {}",request.getStateChr());
                replaceText.add("estado: " + request.getStateChr());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            Workflow workflow = setCommonData(request, company);
            Workflow w = saveOrUpdate(workflow);
            List<Workflowdet> detalle = addToDetails(request.getListaFirmantes());
            saveDetails(detalle, w);
            replaceText.add("Workflow");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("workflow", addToWorkflowResponseDto(w));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Workflow creado con éxito. ID: {}",  w.getIdworkflowPk());
            logger.info("{}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar crear Workflow: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public void saveDetails(List<Workflowdet> detalle, Workflow w) {
        int step = 0; //Codigo heredado
        for (Workflowdet det : detalle) {
            step++;
            WorkflowdetId id = new WorkflowdetId(det.getUser().getIduserPk(), w.getIdworkflowPk());
            det.setId(id);
            det.setStep(step);
            det.setWorkflow(w);
            detalleDao.save(det);
        }
    }

    public ResponseDto getWorkflowService(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        logger.info("Consultando Workflow ID: {} ", id);
        try {

            Workflow workflow = getWokflow(id);

            if (workflow == null) {
                replaceText.add("Workflow");
                replaceText.add(id.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }
            if(!(superCompanyService.isSuperCompany() || companyService.getLoggedUserIdCompany().equals(workflow.getCompany().getIdcompanyPk()))){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }

            replaceText.add("workflow");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("workflow", addToWorkflowResponseDto(workflow));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("Workflow Obtenido con Éxito {}", printer.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el workflow. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public Workflow getWokflow(Long id) {
        return dao.findByidworkflowPk(id);
    }


    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto por fechaDesde, de manera ascendente*/
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "idworkflowPk";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    public List<WorkflowDto> addToWorkflowListResponseDto(List<Workflow> workflow) {
        List<WorkflowDto> response = new ArrayList<>();
        if (workflow != null) {
            for (Workflow w : workflow) {
                response.add(addToWorkflowResponseDto(w));
            }

        }

        return response;
    }

    public ResponseDto getAllWorkflows() {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Workflow> workflowList;
        String mensaje;
        logger.info("Obteniendo Todos los Workflows activos......");
        try {
            // Si es super company listar la totalidad
            if(superCompanyService.isLoggedUserFromSuperCompany()){
                workflowList = dao.findAllByStateChr(SPM_GUI_Constants.ACTIVO);
            }else{
                // Si no, listar solo las de su empresa
                workflowList = dao.findAllByStateChrAndCompanyIdcompanyPk(SPM_GUI_Constants.ACTIVO,
                        companyService.getLoggedUserIdCompany());
            }

            if (ListHelper.hasElements(workflowList)) {
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            } else {
                replaceText.add("workflow");
                mensaje = messageUtil.getMensaje(ErrorsDTO.CODE.NONELISTMASC.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("workflows", workflowList);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("Se obtuvieron TODOS los Workflows Exitosamente");
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de workflows. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }


    /**
     *
     * @return
     */
    public ResponseDto getAllWorkflowsByCompany(Long companyId) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Workflow> workflowList;
        String mensaje;
        logger.info("Obteniendo Todos los Workflows activos de una empresa......");
        try {
            Company company = companyService.getCompanyById(companyId);
            // Si no es super company no puede realizar la consulta
            if(!superCompanyService.isLoggedUserFromSuperCompany()){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }

            if(company == null){
                replaceText.add("Empresa");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALFEM.getValue(), null);
            }

            //Realizando la consulta
            workflowList = dao.findAllByStateChrAndCompanyIdcompanyPk(SPM_GUI_Constants.ACTIVO, companyId);


            if (ListHelper.hasElements(workflowList)) {
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            } else {
                replaceText.add("workflow");
                mensaje = messageUtil.getMensaje(ErrorsDTO.CODE.NONELISTMASC.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("workflows", workflowList);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("Se obtuvieron TODOS los Workflows exitosamente");
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de workflows. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    /**
     * Este metodo se encarga de editar un Workflow.
     *
     * @param request
     * @return {@link ResponseDto}
     */
    public ResponseDto editWorkflow(@NotNull(message = "0030") WorkflowResponseDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();
        logger.info("Editando Workflow -->{}", request);
        try {
            if (!generalHelper.isValidUserCompany(request.getIdcompany())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }

            if (!ListHelper.hasElements(request.getListaFirmantes())) {
                replaceText.add("Campo firmantes no puede ser nulo");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), replaceText);
            }
            if (!EstadosWorkflow.ACTIVE.getCodigo0().equals(request.getStateChr())
                    && (!EstadosWorkflow.INACTIVE.getCodigo0().equals(request.getStateChr()))) {
                replaceText.add("estado: " + request.getStateChr());
                logger.info("Estado Ivalido-->: {}",request.getStateChr());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            Workflow workflow = dao.findByidworkflowPk(request.getIdworkflowPk());
            if (workflow == null) {
                replaceText.add("Workflow");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }
            Long paymentorder=paymentOrderDao.countByWorkflowIdworkflowPk(workflow.getIdworkflowPk());
            if(paymentorder == null || paymentorder.longValue() != 0L){
                logger.warn("Workflow ya posee una orden de pago asociada");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOWORKFLOW.getValue(), null);
            }

           Company company = companyService.getCompanyById(request.getIdcompany());
            if (company == null && superCompanyService.isLoggedUserFromSuperCompany()){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOCOMPANYASOC.getValue(), null);
            }

            List<Workflowdet> detalleOld = detalleDao.findByWorkflow(workflow);
            detalleDao.deleteAll(detalleOld);
            Workflow newWorkflow;
            newWorkflow = setCommonData(request, company);
            List<Workflowdet> detalle = addToDetails(request.getListaFirmantes());
            saveDetails(detalle, newWorkflow);
            Workflow w = saveOrUpdate(newWorkflow);
            replaceText.add("Workflow");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("workflow", addToWorkflowResponseDto(w));

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Workflow editado con éxito. Id: {} Nombre: {}", w.getIdworkflowPk(), w.getWorkflownameChr());
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar editar el workflow: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }

    }


    public ResponseDto getWorkflowByFilter(@NotNull(message = "0030") CBWorkflow criteriosBusqueda, String direction, String properties,
                                           Integer pagina, Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.info("Consultando Workflow por Filtro {} ", criteriosBusqueda);
        try {

            if (criteriosBusqueda.getStateChr() == null) {
                replaceText.add("estado");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }
            if (!EstadosWorkflow.ACTIVE.getCodigo0().equals(criteriosBusqueda.getStateChr())
                    && (!EstadosWorkflow.INACTIVE.getCodigo0().equals(criteriosBusqueda.getStateChr()))) {
                replaceText.add("estado: " + criteriosBusqueda.getStateChr());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            Specification<Workflow> where = Specification
                    .where(WorkflowSpecs.getByEstado(criteriosBusqueda.getStateChr()));

            if (criteriosBusqueda.getWorkflownameChr() != null && !criteriosBusqueda.getWorkflownameChr().isEmpty()) {
                where = where.and(WorkflowSpecs.getByNombre(criteriosBusqueda.getWorkflownameChr()));
            }


            if (criteriosBusqueda.getDescriptionChr() != null && !criteriosBusqueda.getDescriptionChr().isEmpty()) {
                where = where.and(WorkflowSpecs.getByDescripcion(criteriosBusqueda.getDescriptionChr()));
            }

            if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                where = where.and(WorkflowSpecs.getByCompanyId(companyService.getLoggedUserIdCompany()));
            }
            Page<Workflow> workflows = dao.findAll(where, pageRequest);

            String code;
            if (workflows.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("workflows", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (workflows.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("workflows");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("workflows", addToWorkflowListResponseDto(workflows.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto
                    .setMeta(new MetaDTO(workflows.getSize(), workflows.getTotalPages(), workflows.getTotalElements()));
            logger.info("Consulta de Workflows por filtro exitosa. {}", printer.printResponseDto(responseDto));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de workflows", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public ResponseDto getFirmantesByWorkflowId(Long id) {
        ResponseDto response = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        logger.info("Obteniendo Firmantes, Id Workflow: {}", id);
        try {
            if (id == null) {
                replaceText.add("IdWorkflow");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), replaceText);

            }

            Workflow workflow = getWokflow(id);
            if (workflow == null || workflow.getWorkflowdets() == null) {
                replaceText.add("workflow");
                replaceText.add(id.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }
            List<UserFirmanteResponseDto> firmantes = addUserFirmanteResponseDto(workflow.getWorkflowdets());
            replaceText.add("firmantes");
            respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
            body.put("message", respuesta);
            body.put("firmantes", firmantes);
            dataDTO.setBody(body);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            response.setData(dataDTO);
            logger.info("Se obtuvo la lista de Firmantes Exitosamente. {}", printer.printResponseDto(response));
            return response;
        } catch (Exception e) {
            logger.error("Error al Obterner Firmantes por Workflow: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public List<UserFirmanteResponseDto> addUserFirmanteResponseDto(Set<Workflowdet> workflowdets){
        List<UserFirmanteResponseDto> userFirmanteResponseDtoList = new ArrayList<>();
        if(ListHelper.hasElements(workflowdets)){
            for (Workflowdet det : workflowdets) {
                UserFirmanteResponseDto firmante = new UserFirmanteResponseDto();
                firmante.setIduserPk(det.getUser().getIduserPk());
                firmante.setUsernameChr(det.getUser().getUsernameChr());
                firmante.setUserlastnameChr(det.getUser().getUserlastnameChr());
                firmante.setStep(det.getStep());
                userFirmanteResponseDtoList.add(firmante);
            }
        }
        return userFirmanteResponseDtoList;
    }

    /**
     * Eliminar detalles de workflow segun usuario
     * @param user: usuario asociado a detalle de workflow
     */
    public void removeDetailsByUser(User user){
        try{
            detalleDao.deleteByUser(user);
        }catch (Exception e){
            logger.error("Error al intentar eliminar detalle de workflow, usuario:{} ", user.getIduserPk());
            logger.debug("Error al intentar eliminar detalle de workflow ", e);
            throw e;
        }

    }



}
