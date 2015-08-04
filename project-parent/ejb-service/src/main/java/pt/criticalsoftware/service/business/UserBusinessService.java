package pt.criticalsoftware.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Stateless
public class UserBusinessService implements IUserBusinessService {
	
	@EJB
	private IUserPersistenceService userpersistence;
	
	@Override
	public Integer getUserId(String username) {
		return userpersistence.getUserId(username);
	}

	@Override
	public List<Role> getRoles(Integer id) {
		List<Role> roles = new ArrayList<>();
		roles = userpersistence.getRoles(id);
		return roles;
	}

	@Override
	public void createUser(IUser user) {
		userpersistence.create(user);		
	}

}
