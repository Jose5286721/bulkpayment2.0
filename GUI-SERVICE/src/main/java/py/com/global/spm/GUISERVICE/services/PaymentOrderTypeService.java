package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.IPaymentOrderTypeDao;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBPaymentOrderType;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.dto.TipoOrdenPago.TipoOrdenPagoAddDto;
import py.com.global.spm.GUISERVICE.dto.TipoOrdenPago.TipoOrdenPagoDto;
import py.com.global.spm.GUISERVICE.specifications.PaymentordertypeSpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.Paymentordertype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentOrderTypeService {

    private static final Logger logger = LoggerFactory
            .getLogger(PaymentOrderTypeService.class);

    @Autowired
    private IPaymentOrderTypeDao dao;

    @Autowired
    private ResponseDtoService responseDtoService;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private PrintResponses printResponses;


    public ResponseDto getPaymentOderTypeListByFilter(CBPaymentOrderType cb, String direction,
                                                      String properties, Integer pagina, Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);

        try {
            logger.info("Criterios de busqueda. => {}", cb);
            Specification<Paymentordertype> where = Specification.where(PaymentordertypeSpecs.getAll());

            if (cb.getNameChr() != null && !cb.getNameChr().isEmpty()) {
                where = where.and(PaymentordertypeSpecs.getByNameChr(cb.getNameChr()));
            }

            if (cb.getDescriptionChr() != null && !cb.getDescriptionChr().isEmpty()) {
                where = where.and(PaymentordertypeSpecs.getByDescription(cb.getDescriptionChr()));
            }
            Page<Paymentordertype> paymentordertypes = dao.findAll(where, pageRequest);

            String code;
            if (paymentordertypes.getTotalElements() == 0) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("tipoOrdenPago", null);
                dataDTO.setBody(body);
            } else {
                code = DataDTO.CODE.OK.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                if (paymentordertypes.getContent().isEmpty()) {
                    code = DataDTO.CODE.OKNODATA.getValue();
                    msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                    respuesta = responseDtoService.listContentEmpty(pagina);
                } else {
                    replaceText.add("tipo de orden de pago");
                    respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
                }
                body.put("message", respuesta);
                body.put("tipoOrdenPago", addToPaymentordertypeListResponseDto(paymentordertypes.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto.setMeta(new MetaDTO(paymentordertypes.getSize(), paymentordertypes.getTotalPages(),
                    paymentordertypes.getTotalElements()));
            logger.info("Se obtiene lista de {}. Total de elementos {}.", Paymentordertype.class.getSimpleName(),
                    paymentordertypes.getTotalElements());

            return responseDto;
        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de empresas", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);
        }
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "idorderpaymenttypePk";
        }
        logger.info("Ordenar lista por {} en orden {}", prope, dire);
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    private List<TipoOrdenPagoDto> addToPaymentordertypeListResponseDto(List<Paymentordertype> paymentordertypeList) {

        List<TipoOrdenPagoDto> dtoList = new ArrayList<>();

        if (ListHelper.hasElements(paymentordertypeList)) {
            for (Paymentordertype p : paymentordertypeList) {
                TipoOrdenPagoDto dto = new TipoOrdenPagoDto();
                dto.setIdorderpaymenttypePk(p.getIdorderpaymenttypePk());
                dto.setNameChr(p.getNameChr());
                dto.setDescriptionChr(p.getDescriptionChr());

                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    public  ResponseDto getTipoOrdenPagoById(Long id){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {
            Paymentordertype paymentordertype = getTipoOrdenPagoByIdPaymentordertype(id);

            if(paymentordertype == null){
                replaceText.add("tipo orden pago");
                replaceText.add(id.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICMASC.getValue(), replaceText);
            }

            replaceText.add("tipo orden pago");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETMASC.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("tipoOrdenPago", paymentordertype);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Respuesta del servicio. => {}", printResponses.printResponseDto(responseDto));
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener el tipo de orden de pago. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);
        }
    }


    public Paymentordertype getTipoOrdenPagoByIdPaymentordertype(Long id){
        return dao.findByIdorderpaymenttypePk(id);
    }

    /**
     * Agregar un nuevo tipo de orden de pago.
     * @param request
     * @return
     */
    public ResponseDto addTipoOrdenPago(TipoOrdenPagoAddDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje = null;
        List<String> replaceText = new ArrayList<>();
        try {

            if (request.getNameChr()==null) {
                return responseDtoService.errorResponse(ErrorsDTO.CODE.OBLIGATORIESFIELDS.getValue(),null);
            }

            Paymentordertype paymentordertype = new Paymentordertype();
            paymentordertype.setNameChr(request.getNameChr());
            paymentordertype.setDescriptionChr(request.getDescriptionChr());

            paymentordertype = saveOrUpdate(paymentordertype);
            replaceText.add("Tipo Orden Pago");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITO.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("tipoOrdenPago", paymentordertype);

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Tipo orden de pago creado exitosamente. => {}", printResponses.printResponseDto(responseDto));

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar crear el tipo de orden de pago: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);
        }
    }

    /**
     * Edita un tipo de orden de pago
     * @param request
     * @return
     * @throws Exception
     */
    public ResponseDto editTipoOrdenPago(TipoOrdenPagoDto request) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();
        try {

            Paymentordertype paymentordertype = dao.findByIdorderpaymenttypePk(request.getIdorderpaymenttypePk());

            if (paymentordertype == null) {
                replaceText.add("Tipo de orden de pago");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }

            if (request.getNameChr() == null) {
                replaceText.add("nombre");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(),replaceText);
            }

            paymentordertype.setNameChr(request.getNameChr());
            paymentordertype.setDescriptionChr(request.getDescriptionChr());

            paymentordertype = saveOrUpdate(paymentordertype);

            replaceText.add("Tipo Orden Pago");
            mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOMASC.getValue(), replaceText);
            body.put("mensaje", mensaje);
            body.put("tipoOrdenPago", paymentordertype);

            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
            dataDTO.setBody(body);
            responseDto.setData(dataDTO);
            logger.info("Tipo orden de pago editado exitosamente. => {}", printResponses.printResponseDto(responseDto));

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al intentar editar el tipo de orden de pago: ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(),replaceText);
        }
    }

    /**
     * Persiste un tipo de orden de pado
     * @param paymentordertype
     * @return
     * @throws Exception
     */
    public Paymentordertype saveOrUpdate(Paymentordertype paymentordertype) throws Exception {
        logger.info("Guardando paymentOrderType... ");
        try {
            return dao.save(paymentordertype);
        }catch (Exception e) {
            logger.error("Error al intentar guardar tipo de orden de pago.");
            throw new Exception();
        }
    }

    public ResponseDto getAllPaymentOrderType() {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Paymentordertype> paymentordertypeList;
        String mensaje;
        logger.info("Obteniendo Todos los tipos de ordenes de pagos activos......");
        try {
            paymentordertypeList = dao.findAll();
            if (ListHelper.hasElements(paymentordertypeList)) {
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            } else {
                replaceText.add("workflow");
                mensaje = messageUtil.getMensaje(ErrorsDTO.CODE.NONELISTMASC.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("tipoOrdenPago", paymentordertypeList);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);
            logger.info("Se obtuvieron TODOS los tipos de ordenes de pago Exitosamente");
            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de tipo de ordenes de pago. ", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }


}
