package py.com.global.model.interfaces;

import py.com.global.spm.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserManagerLocal {

	public User getUserById(Long idUser);

	public User getSingleUserByPhoneNumber(String phoneNumber);

	public boolean updateUser(User user);

	public List<User> getUsersByMisdn(String msisdn);

}
