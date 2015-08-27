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

	List<IUser> getAllUsersByRole(Role role);
	
	IUser getUserByID(Object id);

	IUser createUser(IUser user) throws DuplicateEmailException, DuplicateUsernameException;

	List<IUser> getAllUsers();


}
