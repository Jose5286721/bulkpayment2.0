package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Beneficiary;

@Local
public interface BeneficiaryManagerLocal {
	public Beneficiary getBeneficiaryById(Long idBeneficary);

}
