package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.persistence.roles.Role;
 
public interface IUserBusinessService {

	Integer getUserId(String username);

	List<Role> getRoles(Integer id);
	
	boolean verifyEmail(String email);

	boolean createUser(String username, String password, String email, String fn, String ln, Role role);

}
