package py.com.global.spm.GUISERVICE.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import py.com.global.spm.GUISERVICE.csvimporter.CSVRecord;
import py.com.global.spm.GUISERVICE.csvimporter.CsvImport;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.DataException;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.InvalidAmountException;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.InvalidColumnsException;
import py.com.global.spm.GUISERVICE.csvimporter.exceptions.WalletDontMatchException;
import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiaryDraftDetailDto;
import py.com.global.spm.GUISERVICE.dto.DataDTO;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.GUISERVICE.dto.ResponseDto;
import py.com.global.spm.domain.entity.Company;
import py.com.global.spm.domain.entity.Workflow;
import py.com.global.spm.GUISERVICE.utils.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DraftFileImporterCSV {
	private static final Logger log = LoggerFactory.getLogger(DraftFileImporterCSV.class);

	private final SystemParameterService systemParameterService;

	private final SuperCompanyService superCompanyService;

	private final WorkflowService workflowService;

	private final ResponseDtoService responseDtoService;

    private final MessageUtil messageUtil;

    private final CompanyService companyService;

	private final UtilService utilService;

	private final GeneralHelper helper;

	private final CsvImport csvImport;

	public DraftFileImporterCSV(SystemParameterService systemParameterService, SuperCompanyService superCompanyService, WorkflowService workflowService, ResponseDtoService responseDtoService, MessageUtil messageUtil, CompanyService companyService, UtilService utilService, GeneralHelper helper, CsvImport csvImport) {
		this.systemParameterService = systemParameterService;
		this.superCompanyService = superCompanyService;
		this.workflowService = workflowService;
		this.responseDtoService = responseDtoService;
		this.messageUtil = messageUtil;
		this.companyService = companyService;
		this.utilService = utilService;
		this.helper = helper;
		this.csvImport = csvImport;
	}

	public Boolean verificarCompanyReposicionGuardaSaldo(Long idCompany) {
		String idCompanyForReposicionGuardaSaldo =systemParameterService
				.getSystemParameterValue(
						SPM_GUI_Constants.DEFAULT_GUI_PROCESS_ID,
						SPM_GUI_Constants.ID_COMPANY_FOR_REPOSICION_GUARDA_SALDO,null);

		return isCompanyReposicionGuardaSaldo(idCompany, idCompanyForReposicionGuardaSaldo);
	}
	/**
	 * Aca se realiza la importacion
	 */
	public ResponseDto validFile(MultipartFile file, Long idCompany, Long idWorkflow) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        Messages messages;
        DataDTO dataDTO = new DataDTO();
        String mensaje;
        Map<String, Object> body = new HashMap<>();
		List<BeneficiaryDraftDetailDto> beneficiary;
		Company company;
		Workflow workflow;
		try {

			if(superCompanyService.isSuperCompany()){
				company = companyService.getCompanyById(idCompany);
			}else{
				company = companyService.getLoggedUserCompany();
			}
			if(company ==  null){
				return null;
			}
			workflow = workflowService.getWorkflowById(idWorkflow);
            if(workflow == null){
                return null;
            }
			Boolean companyReposicionGuardaSaldo = verificarCompanyReposicionGuardaSaldo(company.getIdcompanyPk());


            // VERIFICAR SI ES UN TAMANHO PERMITIDO
			if (file.getContentType().length() < utilService.maxFileSizeValue())
			{
				// VERIFICAR SI ES UN TIPO PERMITIDO
				if (Boolean.TRUE.equals(helper.isMimeTypeAllowed(file))) {
					List<CSVRecord> csvRecords = csvImport.processRecords(file.getInputStream(),workflow.getWalletChr());
					beneficiary=csvRecords.stream().map(BeneficiaryDraftDetailDto::new).collect(Collectors.toList());
				} else {
					return responseDtoService.errorResponse(ErrorsDTO.CODE.INVALIDEXTENSION.getValue(), null);
				}
			} else {
				List<String> replaceText = new ArrayList<>();
				replaceText.add(String.valueOf(utilService.maxFileSizeValue()));
				return responseDtoService.errorResponse(ErrorsDTO.CODE.MAXLENGTH.getValue(), replaceText);
			}

            mensaje = messageUtil.getMensaje(DataDTO.CODE.OK.getValue(), null);
            body.put("mensaje", mensaje);
            body.put("beneficiarios",beneficiary);
			messages = messageUtil.getMessageWithPattern(DataDTO.CODE.OK.getValue(), null);
            dataDTO.setDataCode(DataDTO.CODE.OK.getValue());
            dataDTO.setMessage(messages);
            dataDTO.setBody(body);
			responseDto.setData(dataDTO);

			return responseDto;
		} catch (InvalidAmountException e) {
			ResponseDto dto = responseDtoService.errorResponse(ErrorsDTO.CODE.AMOUNTINVALIDCSV.getValue(), Collections.singletonList(String.valueOf(e.getRow())));
			dataDTO.setBody(body);
			dto.setData(dataDTO);
			return dto;
		}catch (InvalidColumnsException e) {
			ResponseDto dto = responseDtoService.errorResponse(ErrorsDTO.CODE.COLUMNSINVALIDCSV.getValue(), Collections.singletonList(String.valueOf(e.getRow())));
			dataDTO.setBody(body);
			dto.setData(dataDTO);
			return dto;
		}catch (DataException e){
			ResponseDto dto = responseDtoService.errorResponse(e.getCode(), e.getReplaceText());
			dataDTO.setBody(body);
			dto.setData(dataDTO);
			return dto;
		} catch (WalletDontMatchException e) {
			List<String> replaceText = new ArrayList<>();
			replaceText.add(e.getWallet());
			ResponseDto dto =  responseDtoService.errorResponse(ErrorsDTO.CODE.WALLETNOTMATCH.getValue(),	replaceText);
			dataDTO.setBody(body);
			dto.setData(dataDTO);
			return dto;
		}catch (Exception e) {
			log.error("Error al procesar archivo ", e);
            throw e;
		}
	}



	@SuppressWarnings("static-access")
	public boolean isCompanyReposicionGuardaSaldo(Long idcompanyPk,
			String idCompanyForReposicionGuardaSaldo) {

		log.info("idEmpresaActual --> {} idCompanyForReposicionGuardaSaldo --> {}",idcompanyPk, idCompanyForReposicionGuardaSaldo);
		boolean res = false;

		String idcompany = Long.toString(idcompanyPk);
		String[] idCompanies = idCompanyForReposicionGuardaSaldo.split(",");
		for (String id : idCompanies) {
			if (id.equals(idcompany)) {
				res = true;
				log.info("[COMPANY REPOSICION GUARDA SALDO: {} ]",res);
				break;
			}
		}

		return res;
	}

}
