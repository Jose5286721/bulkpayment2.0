package py.com.global.spm.model.interfaces;

import javax.ejb.Local;

import py.com.global.spm.entities.Company;

@Local
public interface CompanyManagerLocal {
	
	public Company getCompanyById(long idCompany);
	
	public boolean updateCompany(Company company);

}
