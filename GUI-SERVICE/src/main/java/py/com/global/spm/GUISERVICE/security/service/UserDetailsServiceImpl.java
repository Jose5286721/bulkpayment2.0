package py.com.global.spm.GUISERVICE.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import py.com.global.spm.GUISERVICE.dao.IUserDao;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;
import py.com.global.spm.domain.entity.User;
import py.com.global.spm.GUISERVICE.security.UserFactory;


/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserDao dao;

    /**
     * UserDetailService tiene username como identificador de un usuario, pasaremos el email
     * a este atributo para ser considerado como identificador de autenticacion
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email){
        User user = dao.findByEmailChr(email);

        if (user == null) {
            throw new UsernameNotFoundException(ErrorsDTO.CODE.BADCREDENTIALS.getValue());
        }else
            return UserFactory.create(user);
    }
}
