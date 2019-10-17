package py.com.global.spm.GUISERVICE.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IFirmanteDao;
import py.com.global.spm.GUISERVICE.dao.IPaymentOrderDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBPaymentOrder;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.Firmantes.FirmanteDto;
import py.com.global.spm.GUISERVICE.dto.Home.CompanyInfoDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.OrdenDePago.PaymentOrderDto;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.enums.EstadosPaymentorder;
import py.com.global.spm.GUISERVICE.exceptions.ObjectNotFoundException;
import py.com.global.spm.domain.entity.Approval;
import py.com.global.spm.domain.entity.Paymentorder;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.GUISERVICE.specifications.FirmantesSpecs;
import py.com.global.spm.GUISERVICE.specifications.PaymentOrderSpecs;
import py.com.global.spm.GUISERVICE.utils.FormatProvider;
import py.com.global.spm.GUISERVICE.utils.ListHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;

import java.math.BigDecimal;
import java.text.*;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class PaymentOrderService {
    private static final Logger logger = LogManager
            .getLogger(PaymentOrderService.class);
    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private SuperCompanyService superCompanyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private IPaymentOrderDao dao;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private FormatProvider formatProvider;

    @Autowired
    private IFirmanteDao firmanteDao;

    @Autowired
    private UserService userService;
    private static final String TIMEENDDAY = " 23:59:00";
    private static final Boolean ISLIST = true;


    public ResponseDto getPaymentOrderByFilter(CBPaymentOrder criteriosBusqueda, String direction, String properties,
                                               Integer pagina, Integer rows) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        logger.debug("Obteniendo Ordenes de Pago por Filtro {}", criteriosBusqueda);
        try {

            Specification<Paymentorder> where;
            if (!superCompanyService.isLoggedUserFromSuperCompany()) {
                Long idCompany = companyService.getLoggedUserIdCompany();
                where = Specification.where(PaymentOrderSpecs.getByCompanyId(idCompany));
            } else {
                where = Specification.where(PaymentOrderSpecs.getAll());

                if (criteriosBusqueda.getIdEmpresa() != null) {
                    where = where.and(PaymentOrderSpecs.getByCompanyId(criteriosBusqueda.getIdEmpresa()));
                }
            }
            if (criteriosBusqueda.getEstado() != null && !criteriosBusqueda.getEstado().isEmpty()) {
                where = where.and(PaymentOrderSpecs.getByEstado(criteriosBusqueda.getEstado()));
            }
            if (criteriosBusqueda.getIdTipoOrdenPago() != null) {
                where = where.and(PaymentOrderSpecs.getByTypePaymentOrderId(criteriosBusqueda.getIdTipoOrdenPago()));
            }
            if (criteriosBusqueda.getIdWorkflow() != null) {
                where = where.and(PaymentOrderSpecs.getByWorkflowId(criteriosBusqueda.getIdWorkflow()));
            }
            if (criteriosBusqueda.getIdOrdenPago() != null) {
                where = where.and(PaymentOrderSpecs.getById(criteriosBusqueda.getIdOrdenPago()));
            }
            Date start = null;
            Date end = null;
            try {
                if (criteriosBusqueda.getFechaDesde() != null && !criteriosBusqueda.getFechaDesde().isEmpty()) {
                    start = formatProvider.parseDateReverse(criteriosBusqueda.getFechaDesde());
                }
                if (criteriosBusqueda.getFechaHasta() != null && !criteriosBusqueda.getFechaHasta().isEmpty()) {
                    end = formatProvider.parseDateTimeReverse(criteriosBusqueda.getFechaHasta() + TIMEENDDAY);
                }
                if (start != null || end != null) {
                    where = where.and(PaymentOrderSpecs.getFechaDesdeHasta("creationdateTim", start, end));
                }
            } catch (ParseException e) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(), null);
            }
            if (criteriosBusqueda.getIdTipoOrdenPago() != null) {
                where = where.and(PaymentOrderSpecs.getByTypePaymentOrderId(criteriosBusqueda.getIdTipoOrdenPago()));
            }

            Page<Paymentorder> paymentorders = dao.findAll(where, pageRequest);

            String code;
            if (paymentorders.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("ordenpago", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (paymentorders.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("ordenpago");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("ordenpago", addPaymentOrderResponseDto(paymentorders.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(paymentorders.getSize(), paymentorders.getTotalPages(), paymentorders.getTotalElements()));
            logger.debug("Consulta de Ordenes de Pago por filtro exitosa");
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de beneficiarios", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }

    }

    public ResponseDto getPaymentOrderService(Long id) {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        logger.info("Consultando Orden de Pago ID: {} ", id);
        try {
            Paymentorder paymentorder = getPaymentOrder(id);

            if (paymentorder == null) {
                replaceText.add("PaymentOrder");
                replaceText.add(id.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }
            if(!(superCompanyService.isSuperCompany() || companyService.getLoggedUserIdCompany().equals(paymentorder.getCompany().getIdcompanyPk()))){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }

            replaceText.add("");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("ordenpago", addToPaymentOrderDto(paymentorder, !ISLIST));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la Orden de Pago. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }




    public List<PaymentOrderDto> addPaymentOrderResponseDto(List<Paymentorder> paymentorders) {
        List<PaymentOrderDto> paymentOrderDtoList = new ArrayList<>();
        if (ListHelper.hasElements(paymentorders)) {
            for (Paymentorder paymentorder : paymentorders) {
                paymentOrderDtoList.add(addToPaymentOrderDto(paymentorder, ISLIST));
            }
        }
        return paymentOrderDtoList;
    }

    public PaymentOrderDto addToPaymentOrderDto(Paymentorder paymentorder, boolean isForLista) {
        PaymentOrderDto dto = new PaymentOrderDto();
        if (paymentorder != null) {
            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            Format formatterHour = new SimpleDateFormat("HH:mm:ss");
            dto.setIdpaymentorderPk(paymentorder.getIdpaymentorderPk());
            dto.setIdCompany(paymentorder.getCompany().getIdcompanyPk());
            dto.setIdWorkflow(paymentorder.getWorkflow().getIdworkflowPk());
            dto.setIdDraft(paymentorder.getDraft().getIddraftPk());
            dto.setIdPaymentordertype(paymentorder.getPaymentordertype().getIdorderpaymenttypePk());
            dto.setCreationdateTim(formatter.format(paymentorder.getCreationdateTim()));
            dto.setCreationhour(formatterHour.format(paymentorder.getCreationdateTim()));
            dto.setUpdatedateTim(formatter.format(paymentorder.getUpdatedateTim()));
            dto.setUpdatedatehour(formatterHour.format(paymentorder.getUpdatedateTim()));
            dto.setMontoTotal(UtilService.toNumberFormat(new BigDecimal(paymentorder.getAmountChr()).toPlainString()));
            dto.setDescripcion(paymentorder.getDescriptionChr());
            dto.setNombreEmpresa(paymentorder.getCompany().getCompanynameChr());
            dto.setNombreTipoOrdenPago(paymentorder.getPaymentordertype().getNameChr());
            dto.setNombreWorkflow(paymentorder.getWorkflow().getWorkflownameChr());
            dto.setAmountpaidChr(UtilService.toNumberFormat(paymentorder.getAmountpaidChr()));
            dto.setBeneficiarieslength(paymentorder.getCantBeneficiaries());
            dto.setPaymenterrorNum(paymentorder.getPaymenterrorNum());
            dto.setPaymentpartsucNum(paymentorder.getPaymentpartsucNum());
            dto.setPaymentsuccessNum(paymentorder.getPaymentsuccessNum());
            dto.setTotalpaymentNum(paymentorder.getTotalpaymentNum());
            dto.setSignerslength(paymentorder.getApprovals().size());
            dto.setStateChr(paymentorder.getStateChr().trim());
            if (isForLista) {
                EstadosPaymentorder estado = EstadosPaymentorder.getStateForCode(paymentorder.getStateChr().trim());
                dto.setStateChr(estado != null ? estado.getDescriptionOrKey() : "");
            } else {
                //Ordenar los firmantes por Turno
                List<Approval> list = paymentorder.getApprovals().stream().sorted((Approval o1, Approval o2)->Math.toIntExact(o1.getStepPk()-o2.getStepPk())).collect(Collectors.toList());
                if (list != null) {
                    List<FirmanteDto> firmanteDtoList = new ArrayList<>();
                    Long idUser = userService.getLoggedUser().getIduserPk();
                    Long stepCurrentUser = null;
                    Boolean esFirmante=false;
                    for (Approval approval : list) {
                        if(approval!=null) {
                            FirmanteDto firmante = new FirmanteDto();
                            User user = approval.getWorkflowdet().getUser();
                            if (user != null) {
                                firmante.setId(approval.getStepPk());
                                firmante.setNombre(user.getUsernameChr() + " " + user.getUserlastnameChr());
                                if (idUser != null && idUser.equals(user.getIduserPk())) {
                                    stepCurrentUser = approval.getStepPk();
                                    esFirmante=true;
                                }
                            }
                            firmante.setFirma(approval.isSignedNum());
                            firmanteDtoList.add(firmante);
                        }
                    }
                    Boolean esTurno = isCurrentUsrTurn(paymentorder.getIdpaymentorderPk(), stepCurrentUser);
                    dto.setCurrentUserTurn(esTurno);
                    dto.setSigner(esFirmante);
                    dto.setFirmantes(firmanteDtoList);
                }
            }
        }
        return dto;
    }

    public Boolean isCurrentUsrTurn(Long paymentorder, Long step) {
        if(paymentorder!=null && step!=null) {
            Specification<Approval> where = Specification.where(FirmantesSpecs.getByPaymentOrderId(paymentorder));
            Approval approval = firmanteDao.findOne(where.and(FirmantesSpecs.getByUserId(userService.getLoggedUser().getIduserPk()))).orElse(null);
            return (approval != null) && approval.getStepPk() == step;
        }
        return false;
    }

    public Boolean isFullysinged(Long paymentorder) {
        Specification<Approval> where = Specification.where(FirmantesSpecs.getByPaymentOrderId(paymentorder));
        long cant = firmanteDao.count(where.and(FirmantesSpecs.getBySignedNumId(false)));
        return cant == 0;
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto, de manera ascendente*/
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "idpaymentorderPk";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }
    public ResponseDto getInfoCompany(Long idCompany){
        ResponseDto responseDto = new ResponseDto();
        Map<String,Object> body = new HashMap<>();
        DataDTO dataDTO = new DataDTO();
        List<String> replaceText = new ArrayList<>();
        Messages msg;
        try {
            Calendar toDate = Calendar.getInstance();
            CompanyInfoDTO infoDTO = new CompanyInfoDTO();
            Calendar fromDate = Calendar.getInstance();
            fromDate.set(Calendar.DAY_OF_MONTH, 1);
            fromDate.set(Calendar.HOUR_OF_DAY, 0);
            fromDate.set(Calendar.MINUTE, 0);
            fromDate.set(Calendar.SECOND, 0);
            fromDate.set(Calendar.MILLISECOND, 0);
            List<Paymentorder> paymentorders = this.getPaymentsByCompanyAndDateAndState(idCompany, fromDate.getTime(), toDate.getTime(), EstadosPaymentorder.SATISFACTORIO.getCodigo0());
            if (paymentorders.isEmpty()) {
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("infoCompany", null);
            } else {
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                infoDTO.setTotalPayment(paymentorders.size());
                infoDTO.setTotalAmount(UtilService.toNumberFormat(getTotalAmount(paymentorders).toPlainString()));
                infoDTO.setLastPaymentDate(paymentorders.get(0).getUpdatedateTim());
                body.put("infoCompany", infoDTO);
                responseDto.setData(dataDTO);
            }
            dataDTO.setBody(body);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            return responseDto;
        }catch (Exception e){
            logger.error("Error al contar la cantidad de ordenes de pago", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    private BigDecimal getTotalAmount(List<Paymentorder> paymentorders){
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(Paymentorder paymentorder: paymentorders){
            totalAmount = totalAmount.add(paymentorder.getAmountNum());
        }
        return totalAmount;
    }
    public List<Paymentorder> getPaymentsByCompanyAndDateAndState(Long idCompany, Date fromDate, Date toDate, String state ){
        try{
            return dao.findByCompanyIdcompanyPkAndUpdatedateTimBetweenAndStateChrOrderByUpdatedateTimDesc(idCompany,fromDate,toDate,state);
        }catch (Exception e){
            logger.error("Error al obtener ordenes de pago", e);
            return null;
        }
    }

    public ResponseDto countByStateAndFirmante(String state){
        ResponseDto responseDto = new ResponseDto();
        Messages messages = new Messages();
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        Integer quantity = 0;
        try{
            List<Paymentorder> po = dao.findByCompanyIdcompanyPkAndStateChr(companyService.getLoggedUserIdCompany(), state);
            if(userService.getLoggedUser().getEsFirmante()){
                quantity = po.size();
            }
            Map<String, Object> body = new HashMap<>();
            body.put("cantidad", quantity);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            return responseDto;

        }catch (Exception e){
            logger.error("Error al contar la cantidad de ordenes de pago", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }

    }

    public Paymentorder getPaymentOrder(Long id) {
        Optional<Paymentorder> paymentOrder = Optional.ofNullable(dao.findByidpaymentorderPk(id));
        List<String> replaceText = new ArrayList<>();
        replaceText.add("PaymentOrder");
        replaceText.add(id.toString());
        return paymentOrder.orElseThrow(()->
                new ObjectNotFoundException(messageUtil.getMensaje(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText)));
    }

}
