package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.roles.Role;

public interface IUserPersistenceService {

	Integer getUserId(String username);
	
	void verifyEmail(String email) throws DuplicateEmailException;

	List<Role> getRoles(Integer id);

	IUser create(IUser user);

	void verifyUsername(String username) throws DuplicateUsernameException;

}
