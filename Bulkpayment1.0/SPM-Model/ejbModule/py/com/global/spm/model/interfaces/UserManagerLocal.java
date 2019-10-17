package py.com.global.spm.model.interfaces;

import java.util.List;

import javax.ejb.Local;

import py.com.global.spm.entities.User;

@Local
public interface UserManagerLocal {

	public User getUserById(Long idUser);

	public User getSingleUserByPhoneNumber(String phoneNumber);

	public boolean updateUser(User user);

	public List<User> getUsersByMisdn(String msisdn);

}
