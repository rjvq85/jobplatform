package pt.criticalsoftware.service.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Stateless
public class UserBusinessService implements IUserBusinessService {

	@EJB
	private IUserPersistenceService userpersistence;

	@Inject
	private IUserBuilder userbuilder;

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

	public boolean createUser(String username, String password, String email, String fn, String ln, Role role) {
		System.out.println("Entrou");
		if (!verifyEmail(email)){
			//build new user
			IUser user = userbuilder
					.username(username)
					.password(password)
					.email(email)
					.firstName(fn)
					.lastName(ln)
					.role(role)
					.build();
			userpersistence.create(user);
			return true;
		}
		else
			return false;

	}

	@Override
	public boolean verifyEmail(String email) {
		userpersistence.verifyEmail(email);
		//falta o true
		return false;
	}
	
	@Override
	public Collection<IUser> getAllUsers() {
		return userpersistence.getAllUsers();
	}

	@Override
	public void saveUsers(Collection<IUser> users) {
		userpersistence.saveUsers(users);
	}
	

}
