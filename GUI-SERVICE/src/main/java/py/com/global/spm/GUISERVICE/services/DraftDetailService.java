package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.global.spm.GUISERVICE.dao.IDraftDetailDao;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.GUISERVICE.utils.MessageUtil;
import py.com.global.spm.GUISERVICE.utils.Messages;
import py.com.global.spm.domain.entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author christiandelgado on 06/07/18
 * @project GOP
 */
@Service
@Transactional
public class DraftDetailService {

    private static final Logger logger = LoggerFactory
            .getLogger(DraftDetailService.class);


    private final IDraftDetailDao dao;

    private final SuperCompanyService superCompanyService;

    private final DraftService draftService;

    private final BeneficiarioService beneficiaryService;

    private final ResponseDtoService responseDtoService;

    private final MessageUtil messageUtil;

    private final CompanyService companyService;

    public DraftDetailService(IDraftDetailDao dao, SuperCompanyService superCompanyService, DraftService draftService, BeneficiarioService beneficiaryService, ResponseDtoService responseDtoService, MessageUtil messageUtil, CompanyService companyService) {
        this.dao = dao;
        this.superCompanyService = superCompanyService;
        this.draftService = draftService;
        this.beneficiaryService = beneficiaryService;
        this.responseDtoService = responseDtoService;
        this.messageUtil = messageUtil;
        this.companyService = companyService;
    }

    public void saveAll(List<Draftdetail> draftdetails){
        dao.saveAll(draftdetails);
    }
    public Page<Draftdetail> getDraftDetailListByIddraft(Long id, PageRequest pageRequest){
        return dao.findByDraftIddraftPk(id,pageRequest);
    }

    public BigDecimal getTotalAmount(Long idDraft){
        return dao.getTotalAmount(idDraft);
    }


    public ResponseDto eliminarDraftDetail(Long iddraftPk, Long idbeneficiaryPk){
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        List<String> replaceText = new ArrayList<>();
        DataDTO dataDTO = new DataDTO();
        Company company;
        Draft draft;
        Beneficiary beneficiary;
        String mensaje;
        Draftdetail draftdetail;
        try {
            company = companyService.getLoggedUserCompany();
            draft = draftService.getByIddraftPk(iddraftPk);
            beneficiary = beneficiaryService.getBeneficiaryById(idbeneficiaryPk);
            if(draft == null){
                replaceText.add("Borrador");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            if(beneficiary == null){
                replaceText.add("Beneficiario");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            if(company == null){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.USERNOCOMPANYASOC.getValue(), null);
            }
            if(!(superCompanyService.isSuperCompany() || company.getIdcompanyPk().equals(draft.getCompany().getIdcompanyPk()))){
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOTAUTHORIZED.getValue(), null);
            }
            draftdetail = dao.findById( new DraftdetailId(iddraftPk, idbeneficiaryPk)).orElse(null);
            if(draftdetail != null){
                dao.delete(draftdetail);
                replaceText.add("Beneficiario");
                mensaje = messageUtil.getMensaje(DataDTO.CODE.DELETEEXITOMASCBODY.getValue(), replaceText);
            }else{
                replaceText.add("Detalle del Borrador");
                return responseDtoService.errorResponse(ErrorsDTO.CODE.NOEXISTGRALMASC.getValue(), replaceText);
            }
            Map<String, Object> body = new HashMap<>();
            body.put("mensaje", mensaje);
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


    public void deleteDraftDetail(Long iddraftPk, Long idbeneficiaryPk ){
        dao.deleteById(new DraftdetailId(iddraftPk,idbeneficiaryPk));
    }
}
