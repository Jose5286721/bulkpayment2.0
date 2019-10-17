package py.com.global.spm.model.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Draft;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;


@Repository
public interface IDraftDao extends JpaRepository<Draft, Long> , JpaSpecificationExecutor<Draft> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Draft> getByRecurrentNumAndStateChrAndPaiddateTimBefore(boolean recurrent, String stateChr, Date paiddateTim);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Draft> findAll(Specification<Draft> specification);
}
