package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.ICompanyDao;
import py.com.global.spm.GUISERVICE.dto.*;
import py.com.global.spm.GUISERVICE.dto.Company.*;
import py.com.global.spm.GUISERVICE.dto.CriteriosBusqueda.CBCompany;
import py.com.global.spm.GUISERVICE.enums.EstadosCompany;
import py.com.global.spm.GUISERVICE.security.TokenUtil;
import py.com.global.spm.GUISERVICE.security.service.CustomWebAuthenticationDetails;
import py.com.global.spm.GUISERVICE.specifications.CompanySpecs;
import py.com.global.spm.GUISERVICE.utils.*;
import py.com.global.spm.domain.entity.*;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by global on 3/14/18.
 */

@Transactional
@Service
public class CompanyService {

    private static final Logger logger = LoggerFactory
            .getLogger(CompanyService.class);

    private final ICompanyDao dao;

    private final MessageUtil messageUtil;

    private final ResponseDtoService responseDtoService;

    private final GeneralHelper generalHelper;

    private final SuperCompanyService superCompanyService;

    private final TokenUtil jwtTokenUtil;

    public CompanyService(ICompanyDao dao, MessageUtil messageUtil, ResponseDtoService responseDtoService, GeneralHelper generalHelper, SuperCompanyService superCompanyService, TokenUtil jwtTokenUtil) {
        this.dao = dao;
        this.messageUtil = messageUtil;
        this.responseDtoService = responseDtoService;
        this.generalHelper = generalHelper;
        this.superCompanyService = superCompanyService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Crear o actualizar un Company
     *
     * @param company
     * @return {@link Company}
     */
    public Company saveOrUpdate(Company company)  {
        return dao.save(company);
    }

    /**
     * Obtener company por su id.
     *
     * @param id
     * @return {@link Company}
     */
    public Company getCompanyById(Long id) {
        logger.debug("Obteniendo compañia ID: {}",id);
        return dao.findByIdcompanyPk(id);
    }

    public Company getLoggedUserCompany(){
        try {
            CustomWebAuthenticationDetails usernamePasswordAuthenticationToken =(CustomWebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
            Long id=jwtTokenUtil.getIdCompanyFromToken(usernamePasswordAuthenticationToken.getToken());
            logger.debug("CompanyFromLoggedUser : {}",id);
            return getCompanyById(id);
        }catch (Exception e){
            logger.error("Error GetLoggedCompany", e);
        }
        return null;
    }

    public Long getLoggedUserIdCompany(){
        try {
            CustomWebAuthenticationDetails usernamePasswordAuthenticationToken =(CustomWebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
            Long id=jwtTokenUtil.getIdCompanyFromToken(usernamePasswordAuthenticationToken.getToken());
            logger.debug("CompanyFromLoggedUser : {} TOKEN: {}",id,usernamePasswordAuthenticationToken.getToken());
            return id;
        }catch (Exception e){
            logger.error("Error GetLoggedCompany", e);
        }
        return null;
    }

    public boolean existCompanyByName(String nameCompany) {
        List<Company> l = dao.findByCompanynameChr(nameCompany);
        return !l.isEmpty();
    }

    public boolean existCompanyByNameAndId(String nameCompany,Long id) {
        List<Company> l = dao.findByCompanynameChr(nameCompany);

        if(l!= null && l.size()==1 && l.get(0).getIdcompanyPk()==id){
            return false;
        }else if(l!= null && l.size()> 0){
            return true;
        }else{
            return false;
        }
    }

    public ResponseDto addCompany(CompanyAddDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        if(!superCompanyService.isLoggedUserFromSuperCompany()){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(),null);
        }

        if (!generalHelper.isValidPhoneNumber(request.getPhonenumberChr())) {
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPHONE.getValue(),null);
        }

        if (!generalHelper.isValidAccountValue(request.getMtsusrChr())) {
            return responseDtoService.errorResponse(ErrorsDTO.CODE.MTSUSRINVALID.getValue(),null);
        }

        if(existCompanyByName(request.getCompanynameChr())){
            return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(),null);
        }

        Company company =  setCommonData(request);
        company = saveOrUpdate(company);

        replaceText.add("Empresa");
        mensaje = messageUtil.getMensaje(DataDTO.CODE.CREATEEXITO.getValue(), replaceText);
        body.put("mensaje", mensaje);
        body.put("empresa",addToCompanyResponseDto(company));

        dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
        dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
        dataDTO.setBody(body);
        responseDto.setData(dataDTO);
        logger.info( "Empresa creada con éxito. ID: {} Nombre: {}",company.getIdcompanyPk(), company.getCompanynameChr());

        return responseDto;
    }

    public Company setCommonData(CompanyAddDto requestDto) {
        Company c = new Company();
        c.setCompanynameChr(requestDto.getCompanynameChr());
        c.setContactnameChr(requestDto.getContactnameChr());
        c.setDescriptionChr(requestDto.getDescriptionChr());
        c.setPhonenumberChr(requestDto.getPhonenumberChr());
        c.setEmailChr(requestDto.getEmailChr());
        c.setTrxdaylimitNum(requestDto.getTrxdaylimitNum());
        c.setTrxmonthlimitNum(requestDto.getTrxmonthlimitNum());
        c.setTrxtotallimitNum(requestDto.getTrxtotallimitNum());
        c.setPercentcommissionNum(requestDto.getPercentcommissionNum());
        c.setMtsusrChr(requestDto.getMtsusrChr());
        c.setMtsbandChr(requestDto.getMtsbandChr());
        c.setMtsrolbindChr(requestDto.getMtsrolbindChr());
        c.setPriority(requestDto.getPriority());

        c.setCreationdateTim(new Date());
        c.setStateChr(EstadosCompany.ACTIVE.getCodigo0());
        c.setTrxdaycountNum((long) 0);
        c.setTrxmonthcountNum((long) 0);
        c.setTrxtotalcountNum((long) 0);
        c.setLastopdateTim(new Date());
//      Seteamos el password con TRIM previo
        c.setMtspasswordChr(requestDto.getMtspasswordChr().trim());
        c.setMtspasswordChr01(GeneralHelper.toSHA1(requestDto.getMtspasswordChr().trim().getBytes()));
        return c;
    }

    public ResponseDto getCompanyByFilter(@NotNull(message = "0030") CBCompany criteriosBusqueda, String direction, String properties, Integer pagina,
                                          Integer rows) {

        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Messages msg;
        Map<String, Object> body = new HashMap<>();
        String respuesta;
        List<String> replaceText = new ArrayList<>();
        Sort sort = ordenamiento(direction, properties);
        PageRequest pageRequest = PageRequest.of(pagina - 1, rows, sort);

        Specification<Company> where = getSpecification(criteriosBusqueda);
        Page<Company> companies = dao.findAll(where, pageRequest);
        if (companies.getTotalElements() > 0) {
            for (Company c : companies) {
                this.updateTrxCounters(c);
            }
        }

        String code;
        if (companies.getTotalElements() == 0) {
            code = DataDTO.CODE.OKNODATA.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
            body.put("empresas", null);
            dataDTO.setBody(body);
        } else {
            code = DataDTO.CODE.OK.getValue();
            msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            if (companies.getContent().isEmpty()) {
                code = DataDTO.CODE.OKNODATA.getValue();
                msg = messageUtil.getMessageWithPattern(DataDTO.CODE.OKNODATA.getValue(), null);
                respuesta = responseDtoService.listContentEmpty(pagina);
            } else {
                replaceText.add("empresas");
                respuesta = messageUtil.getMensaje(DataDTO.CODE.LISTEXITO.getValue(), replaceText);
            }
            body.put("message", respuesta);
            body.put("empresas", addToCompanyListResponseDto(companies.getContent()));
            dataDTO.setBody(body);
        }
        dataDTO.setDataCode(code);
        dataDTO.setMessage(msg);
        responseDto.setData(dataDTO);
        responseDto.setErrors(null);
        responseDto.setMeta(new MetaDTO(companies.getSize(), companies.getTotalPages(), companies.getTotalElements()));
        return responseDto;

    }

    private Specification<Company> getSpecification(CBCompany criteriosBusqueda){
        Specification<Company> where = Specification.where(CompanySpecs.getByActivos(criteriosBusqueda.getStateChr()));

        if (criteriosBusqueda.getCompanynameChr() != null && !criteriosBusqueda.getCompanynameChr().isEmpty()) {
            where = where.and(CompanySpecs.getByNombre(criteriosBusqueda.getCompanynameChr()));
        }

        if(criteriosBusqueda.getMtsfield5Chr() != null && !criteriosBusqueda.getMtsfield5Chr().isEmpty()){
            where = where.and(CompanySpecs.getByMtsfield5Chr(criteriosBusqueda.getMtsfield5Chr()));
        }

        if (!superCompanyService.isLoggedUserFromSuperCompany()) {
            where = where.and(CompanySpecs.getByIdCompany(getLoggedUserIdCompany()));
        }
        return where;
    }

    private void updateTrxCounters(Company company) {
        try {
            GregorianCalendar timestamp = new GregorianCalendar();
            int nowDay = timestamp.get(Calendar.DAY_OF_YEAR);
            int nowMonth = timestamp.get(Calendar.MONTH);
            int nowYear = timestamp.get(Calendar.YEAR);

            // Ultima fecha del ultimo cobro
            Date lastTime = company.getLastopdateTim();
            timestamp.setTimeInMillis(lastTime.getTime());
            int lastDay = timestamp.get(Calendar.DAY_OF_YEAR);
            int lastMonth = timestamp.get(Calendar.MONTH);
            int lastYear = timestamp.get(Calendar.YEAR);

            if (nowYear > lastYear) {
                company.setTrxdaycountNum(0L);
                company.setTrxmonthcountNum(0L);
            } else {
                if (nowMonth > lastMonth) {
                    company.setTrxdaycountNum(0L);
                    company.setTrxmonthcountNum(0L);
                } else {
                    if (nowDay > lastDay) {
                        company.setTrxdaycountNum(0L);
                    }
                }
            }
            saveOrUpdate(company);
        }catch (Exception e){
            logger.error("Updating company trx counters --> {}",company);
        }
    }

    private Sort ordenamiento(String direction, String properties) {

        String dire = direction;
        String prope = properties;

        if (direction == null || "".equals(direction)) {
            dire = "DESC";
        }
        if (properties == null || "".equals(properties)) {
            prope = "idcompanyPk";
        }
        return new Sort(Sort.Direction.fromString(dire), prope);
    }

    public List<CompanyResponseListDto> addToCompanyListResponseDto(List<Company> companyList) {

        List<CompanyResponseListDto> dtoList = new ArrayList<>();

        if (ListHelper.hasElements(companyList)) {
            for (Company c : companyList) {
                CompanyResponseListDto dto = new CompanyResponseListDto();
                dto.setIdcompanyPk(c.getIdcompanyPk());
                dto.setCompanynameChr(c.getCompanynameChr());
                dto.setCreationdateTim(c.getCreationdateTim());
                dto.setDescriptionChr(c.getDescriptionChr());
                dto.setStateChr(c.getStateChr());
                dto.setContactnameChr(c.getContactnameChr());
                dto.setEmailChr(c.getEmailChr());
                dto.setPhonenumberChr(c.getPhonenumberChr());
                dto.setTrxdaycountNum(c.getTrxdaycountNum());
                dto.setTrxmonthcountNum(c.getTrxmonthcountNum());
                dto.setTrxtotalcountNum(c.getTrxtotalcountNum());
                dto.setLastopdateTim(c.getLastopdateTim());

                dtoList.add(dto);
            }
        }
        return dtoList;
    }


    public CompanyDto addToCompanyResponseDto(Company c) {

        CompanyDto dto = new CompanyDto();
        dto.setIdcompanyPk(c.getIdcompanyPk());
        dto.setCompanynameChr(c.getCompanynameChr());
        dto.setCreationdateTim(c.getCreationdateTim());
        dto.setStateChr(c.getStateChr());
        dto.setDescriptionChr(c.getDescriptionChr());
        dto.setContactnameChr(c.getContactnameChr());
        dto.setEmailChr(c.getEmailChr());
        dto.setPhonenumberChr(c.getPhonenumberChr());
        dto.setPercentcommissionNum(c.getPercentcommissionNum());
        dto.setLastopdateTim(c.getLastopdateTim());
        dto.setMtsusrChr(c.getMtsusrChr());
        dto.setMtsbandChr(c.getMtsbandChr());
        dto.setMtsrolbindChr(c.getMtsrolbindChr());
        dto.setTrxdaycountNum(c.getTrxdaycountNum());
        dto.setTrxmonthcountNum(c.getTrxmonthcountNum());
        dto.setTrxtotalcountNum(c.getTrxtotalcountNum());
        dto.setTrxdaylimitNum(c.getTrxdaylimitNum());
        dto.setTrxmonthlimitNum(c.getTrxmonthlimitNum());
        dto.setTrxtotallimitNum(c.getTrxtotallimitNum());
        dto.setPriority(c.getPriority());
        return dto;
    }


    /**
     * Este metodo se encarga de editar una empresa.
     *
     * @param request: s
     * @return {@link ResponseDto}
     */
    public ResponseDto editCompany(CompanyEditDto request) {
        ResponseDto responseDto = new ResponseDto();
        DataDTO dataDTO = new DataDTO();
        Map<String, Object> body = new HashMap<>();
        String mensaje;
        List<String> replaceText = new ArrayList<>();

        if (!generalHelper.isValidUserCompany(request.getIdcompanyPk()))
            return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(),null);

        Company company = dao.findByIdcompanyPk(request.getIdcompanyPk());

        if (company == null) {
            replaceText.add("Empresa");
            return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALFEM.getValue(), replaceText);
        }

        if (!generalHelper.isValidPhoneNumber(request.getPhonenumberChr()))
            return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDPHONE.getValue(),null);


        if (!generalHelper.isValidAccountValue(request.getMtsusrChr()))
            return responseDtoService.errorResponse(ErrorsDTO.CODE.MTSUSRINVALID.getValue(),null);


        if(existCompanyByNameAndId(request.getCompanynameChr(),request.getIdcompanyPk()))
            return responseDtoService.errorResponse(ErrorsDTO.CODE.EXIST.getValue(),null);

        if(superCompanyService.isLoggedUserFromSuperCompany()){
            company.setCompanynameChr(request.getCompanynameChr());
            company.setMtsbandChr(request.getMtsbandChr());
            company.setMtsrolbindChr(request.getMtsrolbindChr());
            company.setDescriptionChr(request.getDescriptionChr());
            company.setStateChr(request.getStateChr());
            company.setTrxdaylimitNum(request.getTrxdaylimitNum());
            company.setTrxmonthlimitNum(request.getTrxmonthlimitNum());
            company.setTrxtotallimitNum(request.getTrxtotallimitNum());
            company.setPercentcommissionNum(request.getPercentcommissionNum());
            company.setPriority(request.getPriority());

        }

        company.setContactnameChr(request.getContactnameChr());
        company.setEmailChr(request.getEmailChr());
        company.setPhonenumberChr(request.getPhonenumberChr());
        company.setMtsusrChr(request.getMtsusrChr());

        if(request.getMtspasswordChr() != null && !(request.getMtspasswordChr().isEmpty() ||
                request.getMtspasswordChr().contentEquals(" "))){
            company.setMtspasswordChr(request.getMtspasswordChr().trim());
            company.setMtspasswordChr01(GeneralHelper.toSHA1(request.getMtspasswordChr().trim().getBytes()));
        }

        company = saveOrUpdate(company);

        replaceText.add("Empresa");
        mensaje = messageUtil.getMensaje(DataDTO.CODE.EDITEXITOFEM.getValue(), replaceText);
        body.put("mensaje", mensaje);
        body.put("empresa", addToCompanyResponseDto(company));

        dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
        dataDTO.setMessage(messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null));
        dataDTO.setBody(body);
        responseDto.setData(dataDTO);
        logger.info("Empresa editada con éxito. ID: " + company.getIdcompanyPk() +
                " Nombre: " + company.getCompanynameChr());

        return responseDto;
    }

    public boolean isCompanyInUser(User user, Long idCompany){
        if(user==null || idCompany ==null) return false;
        for(Company c: user.getCompany()){
            if(idCompany.longValue() == c.getIdcompanyPk().longValue())
                return true;
        }
        return false;
    }
    public ResponseDto getCompanyByIdForResponse(Long id){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        try {
            Company company = getCompanyById(id);

            if(company == null){
                replaceText.add(id.toString());
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTSPECIFICFEM.getValue(), replaceText);
            }
            if(!(superCompanyService.isSuperCompany() || getLoggedUserIdCompany().equals(company.getIdcompanyPk()))){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }
            replaceText.add("empresa");
            String mensaje = messageUtil.getMensaje(DataDTO.CODE.GETFEM.getValue(), replaceText);
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("empresa", addToCompanyResponseDto(company));

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la empresa. ", e);
            return null;
        }
    }

    public ResponseDto getAllCompanies(){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        List<Company> companyList;
        String mensaje;
        try {
            if(!superCompanyService.isLoggedUserFromSuperCompany())
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(),null);
            companyList = dao.findAllByStateChr(SPM_GUI_Constants.ACTIVO);
            if(!ListHelper.hasElements(companyList)){
                mensaje = messageUtil.getMensaje(DataDTO.CODE.OKNODATA.getValue(), null);
            }else {
                replaceText.add("empresa");
                mensaje = messageUtil.getMensaje(DataDTO.CODE.GETFEM.getValue(), replaceText);
            }

            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
            body.put("empresas", companyList);

            messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);

            responseDto.setData(dataDTO);

            return responseDto;
        } catch (Exception e) {
            logger.error("Error al obtener la lista de empresa. ", e);
            return null;
        }
    }


}
