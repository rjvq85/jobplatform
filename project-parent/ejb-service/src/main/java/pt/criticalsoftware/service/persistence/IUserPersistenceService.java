package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.dtos.UserDTO;
import pt.criticalsoftware.service.persistence.roles.Role;

public interface IUserPersistenceService {

	Integer getUserId(String username);

	List<Role> getRoles(Integer id);

	IUser create(IUser user);

}
