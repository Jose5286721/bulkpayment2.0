package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Process;

import java.util.List;
@Repository
public interface IProcessDao extends JpaRepository<Process, Long>, JpaSpecificationExecutor<Process> {



    Process findByidprocessPk(Long id);
    List<Process> findByprocessnameChr(String name);
    List<Process> findBydescriptionChr(String name);

}
