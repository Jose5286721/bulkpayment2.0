package py.com.global.spm.model.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.model.repository.IUserDao;


@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
   private IUserDao iUserDao;


    public User getUserById(long id) {
        try {
            return iUserDao.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error al obtener el usuario --> id: " + id);
            throw e;
        }
    }


    public boolean saveorUpdate(User user) {
        try {
            iUserDao.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Error al saveOrUpdate user con id --> " + user.getIduserPk(), e);
            throw e;
        }
    }
}
