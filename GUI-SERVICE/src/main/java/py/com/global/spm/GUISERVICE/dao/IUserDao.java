package py.com.global.spm.GUISERVICE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import py.com.global.spm.domain.entity.User;

import java.util.List;
@Repository
public interface IUserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Buscar un usuario por id
     *
     * @param id
     * @return {@link User}
     */
    User findByiduserPk(Long id);


    /**
     * Devuelve usuario/s por nombre de usuario
     * @param username
     * @return {@link User}
     */
    User findByusernameChr(String username);


    /**
     * Devuelve usuario unico por email
     * @param email
     * @return
     */
    User findByEmailChr(String email);

    /***
     * Devuelve usuario/s por estado
     * @param estado
     * @return {@link User}
     */
    List<User> findBystateChr(String estado);

    /**
     * Devuelve usuario/s por empresa
     * @return {@link User}
     */
    List<User> findByCompanyIdcompanyPk(Long id);

    /**
     *Devuelve Usuarios por empresa y firmantes
     */
    List<User> findByEsFirmanteAndCompanyIdcompanyPkAndStateChr(Boolean esfirmante, Long id,String stateChr);



}
