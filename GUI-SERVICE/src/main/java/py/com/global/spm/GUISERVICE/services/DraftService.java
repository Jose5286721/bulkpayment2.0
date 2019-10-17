package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IDraftDao;
import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiaryDraftDetailDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBDraft;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.Draft.DraftAddDto;
import py.com.global.spm.GUISERVICE.dto.Draft.DraftEditDto;
import py.com.global.spm.GUISERVICE.dto.Draft.DraftResponseDto;
import py.com.global.spm.GUISERVICE.dto.Draft.DraftResponseListDto;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.exceptions.DraftAmountException;
import py.com.global.spm.GUISERVICE.message.BeneficiaryImporterCsvMessage;
import py.com.global.spm.GUISERVICE.specifications.DraftSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author christiandelgado on 31/05/18
 * @project GOP
 */
@Service
@Transactional
public class DraftService {


    private static final Logger logger = LoggerFactory
            .getLogger(DraftService.class);

    private static final String TIMEENDDAY = " 23:59:00";

    @Autowired
    private IDraftDao dao;

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentOrderTypeService paymentOrderTypeService;

    @Autowired
    private FormatProvider formatProvider;

    @Autowired
    private GeneralHelper generalHelper;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private BeneficiarioService beneficiaryService;

    @Autowired
    private DraftDetailService draftDetailService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private SystemParameterService systemParameterService;

    @Autowired
    private  JmsTemplate jmsTemplate;


    /**
     * Agrega un nuevo borrador
     * @param requestDto
     * @return
     */
    public ResponseDto agregarBorrador(DraftAddDto requestDto){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        List<Draftdetail> draftdetails;
        Draft draft = new Draft();
        Company company;
        List<String> replaceText = new ArrayList<>();
        String minValue;
        String maxValue;
        String mensaje;
        try {
            if (superCompanyService.isSuperCompany()) {
                company = companyService.getCompanyById(requestDto.getIdCompany());
            } else {
                company = companyService.getLoggedUserCompany();
            }
            Workflow workflow = workflowService.getWorkflowById(requestDto.getIdWorkflow());
            if (company == null) {
                replaceText.add("Empresa");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALFEM.getValue(), replaceText);
            }
            if (workflow == null) {
                replaceText.add("Workflow");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            if (!generalHelper.isValidUserCompany(company.getIdcompanyPk())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }
            setPrePersistSaveValues(draft, requestDto, company, workflow);
            draft = dao.save(draft);
            if(Boolean.TRUE.equals(requestDto.getFastDraft())){
                minValue = systemParameterService.findByParameter(SPM_GUI_Constants.DRAFT_MIN_AMOUNT_VALUE)
                        .getValueChr();

                maxValue = systemParameterService.findByParameter(SPM_GUI_Constants.DRAFT_MAX_AMOUNT_VALUE)
                        .getValueChr();

                draftdetails = prepareDetailsToSave(draft, requestDto.getBeneficiariesToAdd(), minValue, maxValue);
                draftDetailService.saveAll(draftdetails);
                dao.flush();
            }else {
                BeneficiaryImporterCsvMessage message = new BeneficiaryImporterCsvMessage();
                message.setDraft(draft);
                message.setNewBeneficiaries(requestDto.getBeneficiariesToAdd());
                jmsTemplate.convertAndSend(SPM_GUI_Constants.IMPORTER_CSV_QUEUE, message);

            }
            replaceText.add("Borrador");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            return responseDto;
        }catch (DraftAmountException e){
            logger.error("Error en validacion de monto en los detalles del borrador: {}", e.getMessage());
            throw e;
        }catch (ParseException e){
            logger.error("Error al parsear fecha", e);
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(), null);
        }catch (Exception e){
            logger.error("Error al crear borrador", e);
            throw e;
        }
    }

    public ResponseDto editarBorrador(Long iddraftPk, DraftEditDto requestDto){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        DataDTO dataDTO = new DataDTO();
        String mensaje;
        Map<String, Object> body = new HashMap<>();
        Draft draft;
        List<String> replaceText = new ArrayList<>();
        List<Draftdetail> draftdetails;
        String minValue;
        String maxValue;
        try {
            draft = dao.findByIddraftPk(iddraftPk);
            if(draft == null){
                replaceText.add("Borrador");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            Workflow workflow = workflowService.getWorkflowById(requestDto.getIdWorkflow());
            if(workflow == null){
                replaceText.add("Workflow");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            if (!generalHelper.isValidUserCompany(draft.getCompany().getIdcompanyPk())) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(),null);
            }
            minValue = systemParameterService.findByParameter(SPM_GUI_Constants.DRAFT_MIN_AMOUNT_VALUE)
                    .getValueChr();

            maxValue = systemParameterService.findByParameter(SPM_GUI_Constants.DRAFT_MAX_AMOUNT_VALUE)
                    .getValueChr();
            //Setear valores en los campos del draft
            setPrePersistUpdateValues(draft, requestDto, workflow);

            //Guardamos el borrador
            draft = dao.save(draft);
            dao.flush();

            //Guardamos los draftDetails de los nuevos beneficiarios
            draftdetails = prepareDetailsToSave(draft, requestDto.getBeneficiariesToAdd(), minValue, maxValue);
            draftDetailService.saveAll(draftdetails);

            //Eliminamos los beneficiarios
            requestDto.getBeneficiariesToDelete().forEach( b -> draftDetailService.deleteDraftDetail(iddraftPk,b.getIdbeneficiaryPk()));
//            draftDetailService.eliminarDraftDetailPorIdDraft(draft.getIddraftPk());

            replaceText.add("Borrador");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            return responseDto;
        }catch (ParseException e){
            logger.error("Error al parsear fecha", e);
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(), null);
        }catch (Exception e){
            logger.error("Error al crear borrador", e);
            throw e;
        }
    }


    private Draft setPrePersistSaveValues(Draft draft, DraftAddDto requestDto, Company company, Workflow workflow)
            throws ParseException {
        if (draft.getIddraftPk() == null) {
            draft.setCreationdateTim(new Date());
            draft.setStateChr(SPM_GUI_Constants.ACTIVO);
            draft.setCompany(company);
            User user = userService.getLoggedUser();
            draft.setUser(user);
        }
        draft.setWorkflow(workflow);
        if (requestDto.getIdPaymentordertype() != null) {
            Paymentordertype pot = paymentOrderTypeService.getTipoOrdenPagoByIdPaymentordertype(requestDto.getIdPaymentordertype());
            draft.setPaymentordertype(pot);
        }
        if (Boolean.TRUE.equals(requestDto.getRecurrentNum())) {
            Date d = GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getFromdateTim()),
                    "00:00:00", null);
            draft.setFromdateTim(d);
            Date h = GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getTodateTim()),
                    "23:59:59", null);
            draft.setTodateTim(h);
            Date paidDate=GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getPaiddateTim()),
                    "00:00:00", null);
            draft.setPaiddateTim(paidDate);
            draft.setGeneratedayNum(requestDto.getGenerateDayNum());
        } else {
            Date pd = GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getPaiddateTim()),
                    "00:00:00", null);
            draft.setPaiddateTim(pd);
            draft.setFromdateTim(null);
            draft.setTodateTim(null);
        }
        draft.setRecurrentNum(requestDto.getRecurrentNum());
        draft.setDescriptionChr(requestDto.getDescriptionChr());
        draft.setNotifybenficiaryNum(requestDto.isNotifybenficiaryNum());
        draft.setNotifycancelNum(requestDto.isNotifycancelNum());
        draft.setNotifygenNum(requestDto.isNotifygenNum());
        draft.setNotifysignerNum(requestDto.isNotifysignerNum());
        return draft;

    }

    private void setPrePersistUpdateValues(Draft draft, DraftEditDto requestDto, Workflow workflow)
            throws ParseException {
        draft.setStateChr(requestDto.getStateChr());
        draft.setWorkflow(workflow);
        draft.setDescriptionChr(requestDto.getDescriptionChr());
        if (requestDto.getIdPaymentordertype() != null) {
            Paymentordertype pot = paymentOrderTypeService.
                    getTipoOrdenPagoByIdPaymentordertype(requestDto.getIdPaymentordertype());
            draft.setPaymentordertype(pot);
        }
        if (Boolean.TRUE.equals(requestDto.getRecurrentNum())) {
            Date d = GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getFromdateTim()),
                    "00:00:00", null);
            draft.setFromdateTim(d);
            Date h = GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getTodateTim()),
                    "23:59:59", null);
            draft.setTodateTim(h);
            draft.setGeneratedayNum(requestDto.getGenerateDayNum());
        } else {
            Date pd = GeneralHelper.setTimeOfDate(formatProvider.parseDateReverse(requestDto.getPaiddateTim()),
                    "00:00:00", null);
            draft.setPaiddateTim(pd);
            draft.setFromdateTim(null);
            draft.setTodateTim(null);
        }
        draft.setRecurrentNum(requestDto.getRecurrentNum());
        draft.setDescriptionChr(requestDto.getDescriptionChr());
        draft.setNotifybenficiaryNum(requestDto.isNotifybenficiaryNum());
        draft.setNotifycancelNum(requestDto.isNotifycancelNum());
        draft.setNotifygenNum(requestDto.isNotifygenNum());
        draft.setNotifysignerNum(requestDto.isNotifysignerNum());

    }

    public List<Draftdetail> prepareDetailsToSave(Draft draft, List<BeneficiaryDraftDetailDto> beneficiaryDraftDetailDtoList
            , String minValue, String maxValue){
        List<Draftdetail> details = new ArrayList<>();
        BigDecimal minAmountValue = new BigDecimal((minValue != null)? minValue: SPM_GUI_Constants.DRAFT_DEF_MIN_AMOUNT_VALUE);
        BigDecimal maxAmountValue = new BigDecimal((maxValue != null)? maxValue: SPM_GUI_Constants.DRAFT_DEF_MAX_AMOUNT_VALUE);
        for (BeneficiaryDraftDetailDto det : beneficiaryDraftDetailDtoList) {
            if(!validMaxValue(det.getAmount(), maxAmountValue)) {
                String code = ErrorsDTO.CODE.MAXAMOUNT.getValue();
                throw new DraftAmountException("monto maximo", code, maxValue);

            }

            if(!validMinValue(det.getAmount(), minAmountValue)){
                String code = ErrorsDTO.CODE.MINAMOUNT.getValue();
                throw new DraftAmountException("monto minimo", code, minValue);

            }
            Beneficiary b=null;
            if (det.getIdbeneficiaryPk() == null) {
                if (!beneficiaryService.existBeneficiarioByPhoneNumberAndCompany(det.getPhonenumberChr(), draft.getCompany().getIdcompanyPk())) {
                    b=createBeneficiaryFromDetail(det, draft.getCompany(), draft.getWorkflow());

                }else{
                    b=beneficiaryService.getBeneficiaryByPhoneNumberAndCompany(det.getPhonenumberChr(), draft.getCompany().getIdcompanyPk());
                    if(det.getSubscriberciChr()!=null && !det.getSubscriberciChr().isEmpty()) {
                        b.setSubscriberciChr(det.getSubscriberciChr());
                        try {
                            beneficiaryService.saveOrUpdate(b);
                        }catch ( Exception e){
                            logger.error("Error al actualizar ci del beneficiario",e);
                        }
                    }

                }
            }
            //TODO Validar nullpointer
            DraftdetailId id = new DraftdetailId(draft.getIddraftPk(), (det.getIdbeneficiaryPk()!=null)?det.getIdbeneficiaryPk():b.getIdbeneficiaryPk());
            Draftdetail draftdetail = new Draftdetail();
            draftdetail.setId(id);
            draftdetail.setDraft(draft);
            //Se setea el monto encriptado en amountChr
            draftdetail.setAmountNum(det.getAmount());
            Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(det.getIdbeneficiaryPk());
            draftdetail.setBeneficiary((beneficiary!=null)?beneficiary:b);
            details.add(draftdetail);
        }

        return details;
    }

    public ResponseDto getDraftByFilter(CBDraft criteriosBusqueda, String direction, String properties, Integer pagina,
                                      Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);

        try {
            Company company= companyService.getLoggedUserCompany();
            if (company==null) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOCOMPANY.getValue(), null);
            }

            if (criteriosBusqueda == null) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(), null);
            }

            Specification<Draft> where;
            if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                Long idCompany = company.getIdcompanyPk();
                where = Specification.where(DraftSpecs.getByCompanyId(idCompany));
            }else{
                where = Specification.where(DraftSpecs.getAll());

                if (criteriosBusqueda.getIdcompanyPk() != null) {
                    where = where.and(DraftSpecs.getByCompanyId(criteriosBusqueda.getIdcompanyPk()));
                }
            }

            if (criteriosBusqueda.getIddraftPk() != null) {
                where = where.and(DraftSpecs.getByIdDraft(criteriosBusqueda.getIddraftPk()));
            }

            if(criteriosBusqueda.getIdorderpaymenttypePk() != null){
                where = where.and(DraftSpecs.getByPaymentorderTypeId(criteriosBusqueda.getIdorderpaymenttypePk()));
            }

            if(criteriosBusqueda.getIdworkflowPk() != null){
                where = where.and(DraftSpecs.getByWorkflowId(criteriosBusqueda.getIdworkflowPk()));
            }

            if(criteriosBusqueda.getRecurrentNum() != null && criteriosBusqueda.getRecurrentNum()){
                where = where.and(DraftSpecs.getByRecurrentNum(criteriosBusqueda.getRecurrentNum()));
            }

            if(criteriosBusqueda.getDescriptionChr() != null && !criteriosBusqueda.getDescriptionChr().isEmpty()){
                where = where.and(DraftSpecs.getByDescription(criteriosBusqueda.getDescriptionChr()));
            }

            Date start = null;
            Date end = null;
            try {
                if (criteriosBusqueda.getCreationdateTimStart() != null && !criteriosBusqueda.getCreationdateTimStart().isEmpty()) {
                    start = formatProvider.parseDateReverse(criteriosBusqueda.getCreationdateTimStart());
                }
                if (criteriosBusqueda.getCreationdateTimEnd() != null && !criteriosBusqueda.getCreationdateTimEnd().isEmpty()) {
                    end = formatProvider.parseDateTimeReverse(criteriosBusqueda.getCreationdateTimEnd() + TIMEENDDAY);
                }
                if (start != null || end != null) {
                    where = where.and(DraftSpecs.getByCreationDateBetween("creationdateTim", start, end));
                }
            } catch (ParseException e) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);
            }

            Page<Draft> drafts = dao.findAll(where, pageRequest);

            String code;
            if (drafts.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("borradores", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (drafts.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("borradores");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("borradores", addToDraftListResponseDto(drafts.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(drafts.getSize(), drafts.getTotalPages(), drafts.getTotalElements()));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de borradores", e);
            throw e;
        }
    }

    public ResponseDto getBeneficiariesByDraft(Long idDraft, String direction, String properties, Integer pagina,
                                        Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        try {
            Page<Draftdetail> beneficiaries = draftDetailService.getDraftDetailListByIddraft(idDraft,pageRequest);
            String code;
            if (beneficiaries.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("beneficiarios", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (beneficiaries.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("beneficiarios");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("beneficiarios", beneficiaries.stream().map(BeneficiaryDraftDetailDto::new).collect(Collectors.toList()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(beneficiaries.getSize(), beneficiaries.getTotalPages(), beneficiaries.getTotalElements()));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de borradores", e);
            throw e;
        }
    }

    private Beneficiary createBeneficiaryFromDetail(BeneficiaryDraftDetailDto dto,Company company,Workflow workflow) {
        try {
            Beneficiary b = new Beneficiary(null, dto.getPhonenumberChr(), dto.getBeneficiarynameChr(),
                    dto.getBeneficiarylastnameChr(), null,
                    SPM_GUI_Constants.ACTIVO, new Date(), null,
                    null);
            b.setCompany(company);
            b.setSubscriberciChr(dto.getSubscriberciChr());
            b.setWalletChr(workflow.getWalletChr());
            b.setAmount(dto.getAmount());
           return beneficiaryService.saveOrUpdate(b);
        } catch (Exception e) {
            logger.error("Error en la creacion de beneficiario en Draftservice {}", e.getMessage());
        }
        return null;
    }

    public ResponseDto getDraftByIdForResponse(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        Company company;
        Draft draft;
        try {
            company = companyService.getLoggedUserCompany();
            draft = dao.findByIddraftPk(id);

            if(draft == null){
                replaceText.add("Borrador");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            if(company == null){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.USERNOCOMPANYASOC.getValue(), null);
            }
            if(!(superCompanyService.isSuperCompany() || company.getIdcompanyPk().equals(draft.getCompany().getIdcompanyPk()))){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }

            replaceText.add("borrador");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("borrador", mapDraftToDto(draft));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el borrador. ", e);
            throw e;
        }
    }

    private DraftResponseDto mapDraftToDto(Draft draft){
        DraftResponseDto dto = new DraftResponseDto();
        dto.setCompany(draft.getCompany());
        dto.setCreationdateTim(draft.getCreationdateTim());
        dto.setDescriptionChr(draft.getDescriptionChr());
        dto.setFromdateTim(draft.getFromdateTim());
        dto.setFromtemplateNum(draft.isFromtemplateNum());
        dto.setIddraftPk(draft.getIddraftPk());
        dto.setNotifybenficiaryNum(draft.isNotifybenficiaryNum());
        dto.setNotifycancelNum(draft.isNotifycancelNum());
        dto.setNotifygenNum(draft.isNotifygenNum());
        dto.setNotifysignerNum(draft.isNotifysignerNum());
        dto.setPaiddateTim(draft.getPaiddateTim());
        dto.setPaymentordertype(draft.getPaymentordertype());
        dto.setRecurrentNum(draft.isRecurrentNum());
        dto.setStateChr(draft.getStateChr());
        dto.setTodateTim(draft.getTodateTim());
        dto.setWorkflow(draft.getWorkflow());
        dto.setGeneratedayNum(draft.getGeneratedayNum());
        dto.setAmount(draftDetailService.getTotalAmount(draft.getIddraftPk()));
        return dto;
    }

    /**
     * Formato de respuesta de la lista de borradores que sera paginado
     * @param draftList
     * @return
     */
    public List<DraftResponseListDto> addToDraftListResponseDto(List<Draft> draftList) {
        List<DraftResponseListDto> dtoList = new ArrayList<>();
        if (ListHelper.hasElements(draftList)) {
            for (Draft d : draftList) {
                DraftResponseListDto dto = new DraftResponseListDto();
                dto.setIddraftPk(d.getIddraftPk());
                dto.setCompany(d.getCompany().getCompanynameChr());
                dto.setWorkflow(d.getWorkflow() != null ? d.getWorkflow().getWorkflownameChr() : null);
                dto.setPaymentordertype(d.getPaymentordertype().getNameChr());
                dto.setDescriptionChr(d.getDescriptionChr());
                dto.setCreationdateTim(d.getCreationdateTim());
                dto.setFromdateTim(d.getFromdateTim());
                dto.setPaiddateTim(d.getPaiddateTim());
                dto.setRecurrentNum(d.isRecurrentNum());
                dto.setStateChr(d.getStateChr());
                dto.setTodateTim(d.getTodateTim());
                dto.setUser(d.getUser() != null ? d.getUser().getUsernameChr() : null);
                dto.setGeneratedayNum(d.getGeneratedayNum());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "iddraftPk";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    public Draft getByIddraftPk(Long iddraftPk){
        return dao.findByIddraftPk(iddraftPk);
    }



    public boolean validMinValue(BigDecimal amount,BigDecimal minValue){
        return (amount.doubleValue()>= minValue.doubleValue());

    }

    public boolean validMaxValue(BigDecimal amount,BigDecimal maxAmount){
        return (amount.doubleValue() <= maxAmount.doubleValue());

    }
}
