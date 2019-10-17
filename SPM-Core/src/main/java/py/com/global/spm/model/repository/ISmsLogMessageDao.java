package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.Smslogmessage;


@Repository
public interface ISmsLogMessageDao extends JpaRepository<Smslogmessage, Long> {
}
