package py.com.global.spm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.User;


@Repository
public interface IUserDao extends JpaRepository<User, Long> {
    User findByIduserPk(long id);
}
