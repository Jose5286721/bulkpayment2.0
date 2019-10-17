package py.com.global.spm.GUISERVICE.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.IPaymentorderdetailDao;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.Report.PaymentVoucherBodyDto;
import py.com.global.spm.GUISERVICE.dto.Report.PaymentVoucherResDto;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.enums.EstadosPaymentorder;
import py.com.global.spm.GUISERVICE.exceptions.AuthorizationException;
import py.com.global.spm.GUISERVICE.specifications.PaymentOrderDetailSpecs;
import py.com.global.spm.GUISERVICE.utils.FormatProvider;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
import py.com.global.spm.domain.entity.Paymentorderdetail;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentVoucherService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentVoucherService.class);
    private MessageUtil messageUtil;
    private FormatProvider formatProvider;
    private SuperCompanyService superCompanyService;
    private CompanyService companyService;
    private IPaymentorderdetailDao dao;
    private ResponseDtoService responseDtoService;

    public PaymentVoucherService(MessageUtil messageUtil, FormatProvider formatProvider,
                                 SuperCompanyService superCompanyService, CompanyService companyService, IPaymentorderdetailDao dao,
                                 ResponseDtoService responseDtoService){
        this.messageUtil = messageUtil;
        this.formatProvider = formatProvider;
        this.superCompanyService = superCompanyService;
        this.companyService = companyService;
        this.dao = dao;
        this.responseDtoService = responseDtoService;
    }

    /**
     * Generacion del cuerpo para comprobante de pago
     * @param paymentOrderId: id de orden de pago
     * @param ci: cedula de identidad del beneficiario
     * @return PaymentVoucherResDto
     */
    public PaymentVoucherResDto paymentVoucherBody(Long paymentOrderId, String ci,String phonenumberChr, Long companyId, String startDate, String endDate,
                                                   Integer page, Integer linesPerPage, String column, String direction){

        PaymentVoucherResDto response = new PaymentVoucherResDto();
        try{
            validateFilterSize(page, linesPerPage);
            Sort sort = ordenamiento(direction, column);
            Specification<Paymentorderdetail> where = getSpecification(paymentOrderId, ci, phonenumberChr, companyId, startDate, endDate);
            PageRequest pageRequest = PageRequest.of(page -1, linesPerPage, sort);
            Page<Paymentorderdetail> logPaymentdetails = dao.findAll(where, pageRequest);

            response.setPaymentVoucherBody(logPaymentdetails.getContent()
                    .stream()
                    .map(PaymentVoucherBodyDto::new)
                    .collect(Collectors.toList())
            );

        }catch (Exception e){
            logger.error("Al generar cuerpo para el comprobante de pagos: ",e);
        }
        return response;

    }

    public ResponseDto filterPaymentVoucher(Long paymentOrderId, String ci, String phonenumberChr, Long companyId, String sinceDate, String toDate, Integer page, Integer linesPerPage,
                                            String column, String direction){
        ResponseDto response = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        List<String> replaceText = new ArrayList<>();
        String code;
        Messages message;
        try{
            Sort sort = new Sort(Sort.Direction.valueOf(direction), column);
            PageRequest pageRequest = PageRequest.of(page - 1, linesPerPage, sort);
            Specification<Paymentorderdetail> where = getSpecification(paymentOrderId, ci, phonenumberChr, companyId, sinceDate, toDate);
            Page<Paymentorderdetail> paymentVouchers = dao.findAll(where, pageRequest);
            if (paymentVouchers.getTotalElements() == 0 && paymentVouchers.getContent().isEmpty()) {
                code = DataDTO.CODE.OKNODATA.getValue();
                message = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                body.put("paymentVouchers", null);
            } else {
                code = DataDTO.CODE.OK.getValue();
                message = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
                List<PaymentVoucherBodyDto> resultList = paymentVouchers.getContent()
                        .stream()
                        .map(PaymentVoucherBodyDto::new)
                        .collect(Collectors.toList());
                body.put("paymentVouchers", resultList);
            }
            dataDTO.setBody(body);
            dataDTO.setDataCode(code);
            dataDTO.setMessage(message);
            response.setData(dataDTO);
            response.setErrors(null);
            response.setMeta(new MetaDTO(paymentVouchers.getSize(), paymentVouchers.getTotalPages(), paymentVouchers.getTotalElements()));
        }catch (ParseException e){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDDATEFORMAT.getValue(),null);

        }catch (AuthorizationException e){
            logger.warn("Error de autorizacion");
            throw e;

        }catch (Exception e){
            logger.warn("Error en la generacion de listado de comprobantes de pago", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);

        }
        return response;
    }


    private Specification<Paymentorderdetail> getSpecification(Long paymentOrderId, String ci , String phonenumberChr, Long companyId,
                                                                 String sinceDateStr, String endDateStr) throws ParseException {
        Specification<Paymentorderdetail> where;
        Date fromDate = null;
        Date toDate = null;

        if(superCompanyService.isLoggedUserFromSuperCompany()){
            if(companyId != null)
                where = Specification.where(PaymentOrderDetailSpecs.getByCompanyId(companyId));
            else
                where = Specification.where(PaymentOrderDetailSpecs.getAll());
        }
        else{
            Long idCompany = companyService.getLoggedUserIdCompany();
            if (idCompany == null)
                throw new AuthorizationException(ErrorsDTO.CODE.NOCOMPANYLOGGEDIN.getValue());
            where = Specification.where(PaymentOrderDetailSpecs.getByCompanyId(idCompany));
        }

        where = where.and(PaymentOrderDetailSpecs.getByStateChr(EstadosPaymentorder.SATISFACTORIO.getCodigo0()));

        if(paymentOrderId != null)
            where = where.and(PaymentOrderDetailSpecs.getByPaymentOrderId(paymentOrderId));

        if(ci != null  && !ci.isEmpty())
            where = where.and(PaymentOrderDetailSpecs.getByBeneficiaryCI(ci));

        if(phonenumberChr != null && !phonenumberChr.isEmpty())
            where = where.and(PaymentOrderDetailSpecs.getByPhoneNumberChr(phonenumberChr));

        if (sinceDateStr != null && !endDateStr.isEmpty())
            fromDate = formatProvider.parseDateReverse(sinceDateStr);

        if (endDateStr != null && !endDateStr.isEmpty())
            toDate = formatProvider.parseDateTimeReverse(endDateStr + SPM_GUI_Constants.TIMEENDDAY);

        if (fromDate != null || toDate != null)
            where = where.and(PaymentOrderDetailSpecs.getByRangeDate(fromDate, toDate));

        return where;

    }




    private void validateFilterSize(Integer page, Integer linesPerPage) {
        if (page < 0 || linesPerPage < 1){
            List<String> replaceText = new ArrayList<>();
            if(page < 0){
                replaceText.add("paginas");
            }
            if( replaceText.isEmpty() && linesPerPage < 1){
                replaceText.add("filas");
            }
            String mensaje = messageUtil.getMensaje(ErrorsDTO.CODE.INVALIDFIELDLENGTH.getValue(), replaceText);
            throw new IllegalArgumentException(mensaje);

        }
    }

    private Sort ordenamiento(String direction, String properties) {
        String dire = direction;
        String prope = properties;

        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "paymentorder.updatedateTim";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);

    }
}
