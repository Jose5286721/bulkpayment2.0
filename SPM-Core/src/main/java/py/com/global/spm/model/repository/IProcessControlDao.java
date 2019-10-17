package py.com.global.spm.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Processcontrol;
import py.com.global.spm.domain.entity.ProcesscontrolId;

@Repository
public interface IProcessControlDao extends JpaRepository<Processcontrol, ProcesscontrolId> {
}
