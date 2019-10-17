package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Company;


@Repository
public interface ICompanyDao extends JpaRepository<Company, Long> {
    public Company findByIdcompanyPk(Long id);
}
