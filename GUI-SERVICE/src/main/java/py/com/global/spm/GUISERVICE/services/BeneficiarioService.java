package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IBeneficiarioDao;
import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiarioDto;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBBeneficiario;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.MetaDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.enums.EstadosBeneficiario;
import py.com.global.spm.GUISERVICE.specifications.BeneficiarioSpecs;
import py.com.global.spm.GUISERVICE.utils.ListHelper;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.GUISERVICE.utils.PrintResponses;
import py.com.global.spm.domain.entity.Beneficiary;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.validation.constraints.NotNull;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class BeneficiarioService {

    private static final Logger logger = LoggerFactory
            .getLogger(BeneficiarioService.class);

    private final IBeneficiarioDao dao;

    private final ResponseDtoService responseDtoService;

    private final SuperCompanyService superCompanyService;

    private final MessageUtil messageUtil;

    private final PrintResponses printer;

    private final CompanyService companyService;

    public BeneficiarioService(IBeneficiarioDao dao, ResponseDtoService responseDtoService, SuperCompanyService superCompanyService, MessageUtil messageUtil, PrintResponses printer, CompanyService companyService) {
        this.dao = dao;
        this.responseDtoService = responseDtoService;
        this.superCompanyService = superCompanyService;
        this.messageUtil = messageUtil;
        this.printer = printer;
        this.companyService = companyService;
    }

    public Beneficiary saveOrUpdate(Beneficiary beneficiary) {
            return dao.save(beneficiary);
    }

    public boolean existBeneficiarioByPhoneNumberAndCompany(String phonenumber, Long id){
        Specification<Beneficiary> where = Specification
                .where(BeneficiarioSpecs.getByPhoneNumber(phonenumber))
                .and(BeneficiarioSpecs.getByCompanyId(id));
        long count=dao.count(where);
        return count != 0;
    }

    public ResponseDto getBeneficiarioByFilter(@NotNull(message = "0030") CBBeneficiario criteriosBusqueda, String direction, String properties,
                                               Integer pagina, Integer rows) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);
        try {

            if (criteriosBusqueda.getStateChr() == null) {
                replaceText.add("estado");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.REQUIREDFIELD.getValue(), replaceText);
            }
            if (!EstadosBeneficiario.ACTIVE.getCodigo0().equals(criteriosBusqueda.getStateChr())
                    && (!EstadosBeneficiario.INACTIVE.getCodigo0().equals(criteriosBusqueda.getStateChr()))) {
                replaceText.add("estado: " + criteriosBusqueda.getStateChr());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            Specification<Beneficiary> where = Specification
                    .where(BeneficiarioSpecs.getByEstado(criteriosBusqueda.getStateChr()));
            if(!superCompanyService.isLoggedUserFromSuperCompany()){
                Long idCompany = companyService.getLoggedUserIdCompany();
                where = where.and(BeneficiarioSpecs.getByCompanyId(idCompany));
            }else{
                where = where.and(BeneficiarioSpecs.getAll());

                if (criteriosBusqueda.getIdCompany() != null) {
                    where = where.and(BeneficiarioSpecs.getByCompanyId(criteriosBusqueda.getIdCompany()));
                }
            }

            if (criteriosBusqueda.getPhonenumberChr() != null && !criteriosBusqueda.getPhonenumberChr().isEmpty()) {
                where = where.and(BeneficiarioSpecs.getByPhoneNumber(criteriosBusqueda.getPhonenumberChr()));
            }

            Page<Beneficiary> beneficiaries = dao.findAll(where, pageRequest);

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
                body.put("beneficiarios", addBeneficiarioResponseDto(beneficiaries.getContent()));
                dataDTO.setBody(body);
            }
            dataDTO.setDataCode(code);
            dataDTO.setMessage(msg);
            responseDto.setData(dataDTO);
            responseDto.setErrors(null);
            responseDto
                    .setMeta(new MetaDTO(beneficiaries.getSize(), beneficiaries.getTotalPages(), beneficiaries.getTotalElements()));
            logger.info("Consulta de Beneficiarios por filtro exitosa. {}", printer.printResponseDto(responseDto));
            return responseDto;

        } catch (Exception e) {
            logger.warn("Error en la generacion de lista de beneficiarios", e);
            replaceText.add(e.getMessage());
            return responseDtoService.errorResponse(ErrorsDTO.CODE.UNKNOWNERROR.getValue(), replaceText);
        }
    }

    public List<BeneficiarioDto> addBeneficiarioResponseDto(List<Beneficiary> beneficiaries){
        List<BeneficiarioDto> beneficiarioDtoList=new ArrayList<>();
        if(ListHelper.hasElements(beneficiaries)){
           for (Beneficiary beneficiary:beneficiaries){
               beneficiarioDtoList.add(addToBeneficiarioDto(beneficiary));
           }
        }
        return beneficiarioDtoList;
    }
    public BeneficiarioDto addToBeneficiarioDto(Beneficiary beneficiary){
        BeneficiarioDto beneficiarioDto=new BeneficiarioDto();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        beneficiarioDto.setId(beneficiary.getIdbeneficiaryPk());
        beneficiarioDto.setPhonenumberChr(beneficiary.getPhonenumberChr());
        beneficiarioDto.setFechaCreacion(formatter.format(beneficiary.getCreationdateTim()));
        beneficiarioDto.setEstado(beneficiary.getStateChr());
        beneficiarioDto.setIdCompany(beneficiary.getCompany().getIdcompanyPk());
        beneficiarioDto.setNotificarSms(beneficiary.isNotifysmsNum());
        beneficiarioDto.setCompanyName(beneficiary.getCompany().getCompanynameChr());
        return beneficiarioDto;
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        /*Ordenamiento por defecto, de manera ascendente*/
        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "idbeneficiaryPk";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    public Beneficiary getBeneficiaryByPhoneNumberAndCompany(String phoneNumberChr, Long idCompany){
        try {
            return dao.findByPhonenumberChrAndIdcompanyPk(phoneNumberChr.trim(), idCompany);
        }catch (NoResultException e) {
            logger.warn("Beneficiary not found --> {}",phoneNumberChr);
            throw e;
        } catch (NonUniqueResultException e) {
            logger.warn("Hay mas de un beneficiario con este nro--> {} "
                    , phoneNumberChr);
            throw  e;
        }
    }


    public Beneficiary getBeneficiaryByPhoneNumberChr(String phoneNumberChr){
        try {
            return dao.findByPhonenumberChr(phoneNumberChr);
        }catch (NoResultException e) {
            logger.warn("Beneficiary not found --> {}" , phoneNumberChr);
            throw e;
        } catch (NonUniqueResultException e) {
            logger.warn("Hay mas de un beneficiario con este nro--> "
                    + phoneNumberChr);
            throw  e;
        }
    }

    public void saveAll(List<Beneficiary> beneficiaryList){
        dao.saveAll(beneficiaryList);
    }

    public Beneficiary getBeneficiaryById(Long id){
        return dao.findByidbeneficiaryPk(id);
    }


}
