package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Systemparameter;
import py.com.global.spm.domain.entity.SystemparameterId;

import java.util.List;


@Repository
public interface ISystemParameterDao extends JpaRepository<Systemparameter, SystemparameterId> {
    List<Systemparameter> findByProcessIdprocessPk(Long idProcess);
}
