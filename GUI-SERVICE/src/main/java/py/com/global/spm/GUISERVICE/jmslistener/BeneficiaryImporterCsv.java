package py.com.global.spm.GUISERVICE.jmslistener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.dto.Beneficiarios.BeneficiaryDraftDetailDto;
import py.com.global.spm.GUISERVICE.enums.EstadosBeneficiario;
import py.com.global.spm.GUISERVICE.message.BeneficiaryImporterCsvMessage;
import py.com.global.spm.GUISERVICE.services.BeneficiarioService;
import py.com.global.spm.GUISERVICE.services.DraftDetailService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;
import py.com.global.spm.domain.entity.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.*;

@Component
public class BeneficiaryImporterCsv {
    private static final Logger logger = LogManager
            .getLogger(BeneficiaryImporterCsv.class);

	@Autowired
	private BeneficiarioService beneficiaryService;

	@Autowired
	private DraftDetailService draftDetailService;

    @JmsListener(destination = SPM_GUI_Constants.IMPORTER_CSV_QUEUE, containerFactory = "myFactory")
    public void receive (BeneficiaryImporterCsvMessage message){
    	try {
			logger.info("Received --> {}", message);
			List<Beneficiary> beneficiaries = processBeneficiaries(message.getNewBeneficiaries(),message.getDraft().getCompany());
			saveDraftDetails(beneficiaries,message.getDraft());
			logger.info("Exitoso");

		}catch (Exception e){
			logger.error("Error en la cola {}",SPM_GUI_Constants.IMPORTER_CSV_QUEUE,e);
		}


    }

	private List<Beneficiary> processBeneficiaries(List<BeneficiaryDraftDetailDto> csvRecords, Company company){
		List<Beneficiary> result = Collections.synchronizedList(new ArrayList<>());
		long date = System.currentTimeMillis();
		csvRecords.parallelStream().forEach(csvRecord -> {
			Beneficiary beneficiary;
			try {
				if (company.getIdcompanyPk() != null) {
					beneficiary = beneficiaryService.getBeneficiaryByPhoneNumberAndCompany(csvRecord.getPhonenumberChr(), company.getIdcompanyPk());
				}else{
					beneficiary = beneficiaryService.getBeneficiaryByPhoneNumberChr(csvRecord.getPhonenumberChr());
				}
			} catch (NoResultException e) {
				logger.warn("Beneficiary not found --> {}", csvRecord.getPhonenumberChr());
				beneficiary = null;
			} catch (NonUniqueResultException e) {
				logger.warn("Hay mas de un beneficiario con este nro--> "
						+ csvRecord.getPhonenumberChr());
				beneficiary = null;
			}
			if (beneficiary == null) {
				beneficiary = new Beneficiary();
				beneficiary.setCreationdateTim(new Date());
				beneficiary.setStateChr(SPM_GUI_Constants.ACTIVO);
				beneficiary.setCompany(company);
			}
			beneficiary.setEmailChr(csvRecord.getEmailChr());
			beneficiary.setPhonenumberChr(csvRecord
					.getPhonenumberChr());
			beneficiary.setAmount(csvRecord
					.getAmount());
			beneficiary.setCurrencyChr(csvRecord.getCurrency());
			beneficiary.setWalletChr(csvRecord.getWalletChr());
			beneficiary.setRolspChr(csvRecord.getRolspChr());

			if (csvRecord.getSubscriberciChr() != null
					&& !csvRecord.getSubscriberciChr().trim().isEmpty()) {
				beneficiary.setSubscriberciChr(csvRecord
						.getSubscriberciChr().trim());
			}

			if (csvRecord.getBeneficiarylastnameChr() != null
					&& !csvRecord.getBeneficiarylastnameChr().trim().isEmpty()) {
				beneficiary.setBeneficiarylastnameChr(csvRecord
						.getBeneficiarylastnameChr());
			}

			if (csvRecord.getBeneficiarynameChr() != null
					&& !csvRecord.getBeneficiarynameChr().trim().isEmpty()) {
				beneficiary.setBeneficiarynameChr(csvRecord
						.getBeneficiarynameChr());
			}
			//TODO: Averiguar la utilidad de los campos genericos
//			if (csvRecord.get() != null
//					&& !csvRecord.getGenerico1().trim().isEmpty())
//			{
//				beneficiary.setGenerico1Chr(csvRecord.getGenerico1());
//			}
//
//			if (csvRecord.getGenerico2() != null
//					&& !csvRecord.getGenerico2().trim().isEmpty())
//			{
//				beneficiary.setGenerico2Chr(csvRecord.getGenerico2());
//			}
			result.add(beneficiary);
		});
		logger.info("Final de la busqueda en la BD--> {}",(System.currentTimeMillis()-date));
		date = System.currentTimeMillis();
		beneficiaryService.saveAll(result);
		logger.info("Final add Result --> {}",(System.currentTimeMillis()-date));
		return result;
	}

	private void saveDraftDetails(List<Beneficiary> beneficiaries, Draft draft){
		List<Draftdetail> details = Collections.synchronizedList(new ArrayList<>());

    	beneficiaries.parallelStream().forEach(b -> {
			DraftdetailId id = new DraftdetailId(draft.getIddraftPk(), b.getIdbeneficiaryPk());
			Draftdetail draftdetail = new Draftdetail();
			draftdetail.setId(id);
			draftdetail.setDraft(draft);

			draftdetail.setAmountNum(b.getAmount());
			draftdetail.setBeneficiary(b);
			details.add(draftdetail);
		});
    	draftDetailService.saveAll(details);
	}

}
