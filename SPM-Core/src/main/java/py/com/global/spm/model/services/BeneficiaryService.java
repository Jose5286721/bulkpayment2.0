package py.com.global.spm.model.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.Beneficiary;
import py.com.global.spm.model.repository.IBeneficiaryDao;

import java.util.Optional;

@Service
public class BeneficiaryService {
    private final Logger log = LoggerFactory.getLogger(BeneficiaryService.class);

    private final IBeneficiaryDao dao;

    @Autowired
    public BeneficiaryService(IBeneficiaryDao dao) {
        this.dao = dao;
    }

    public Beneficiary getBeneficiaryById(Long idBeneficiary) {
        try {
            Optional<Beneficiary> b = dao.findById(idBeneficiary);
            return b.isPresent()? b.get(): null;
        } catch (Exception e) {
            log.error("Finding Beneficiary --> idBeneficiary=" + idBeneficiary, e);
            return null;
        }
    }
}
