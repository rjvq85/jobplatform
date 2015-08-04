package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.roles.Role;

public interface IUserBusinessService {

	Integer getUserId(String username);

	List<Role> getRoles(Integer id);

	void createUser(IUser user);

}
