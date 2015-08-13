package pt.criticalsoftware.service.business;

import java.util.Collection;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.roles.Role;
 
public interface IUserBusinessService {

	Integer getUserId(String username);

	List<Role> getRoles(Integer id);
	
	void createUser(String username, String password, String email, String fn, String ln, Role role) throws DuplicateEmailException, DuplicateUsernameException;

	void verifyEmail(String email) throws DuplicateEmailException;

	void verifyUsername(String username) throws DuplicateUsernameException;
	
	List<IUser> getAllUsersByRole(Role role);


}
