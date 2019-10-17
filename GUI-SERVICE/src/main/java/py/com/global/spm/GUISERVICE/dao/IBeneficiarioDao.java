package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Beneficiary;

import java.util.List;

@Repository
public interface IBeneficiarioDao extends JpaRepository<Beneficiary, Long>, JpaSpecificationExecutor<Beneficiary> {


    Beneficiary findByidbeneficiaryPk(Long id);

    /**
     * Beneficiario por numero de telefono
     * @param phoneNumberChr
     * @return
     */
    Beneficiary findByPhonenumberChr(String phoneNumberChr);


    /**
     * Obtiene un beneficiario que coincida con el numero de telefono, la compañia y no este en
     * la lista de excluidos.
     * @param phoneNumberChr
     * @param idCompany
     * @param excludeBeneficiaries
     * @return
     */
    Beneficiary findByPhonenumberChrAndCompanyIdcompanyPkAndIdbeneficiaryPkNotIn(String phoneNumberChr, Long idCompany,
                                                                                 List<Long> excludeBeneficiaries);

    /**
     * Obtiene un beneficiario que coincida con el numero de telefono y la compañia
     * @param phoneNumberChr
     * @param idCompany
     * @return
     */
    Beneficiary findByPhonenumberChrAndIdcompanyPk(String phoneNumberChr, Long idCompany);

    /**
     * Obtiene un beneficiario que coincida con el numero de telefono, la compañia y no este en
     * la lista de excluidos.
     * @param phoneNumberChr
     * @param excludeBeneficiaries
     * @return
     */
    Beneficiary findByPhonenumberChrAndIdbeneficiaryPkNotIn(String phoneNumberChr, List<Long> excludeBeneficiaries);
}
