package py.com.global.spm.GUISERVICE.dao.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import py.com.global.spm.GUISERVICE.dto.redis.UserPinLogin;

@Repository
public interface IUserPinLoginDao extends CrudRepository<UserPinLogin, Long>{
}
