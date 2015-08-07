package pt.criticalsoftware.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Stateless
public class UserBusinessService implements IUserBusinessService {

	private final Logger logger = LoggerFactory.getLogger(UserBusinessService.class);

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

	public void createUser(String username, String password, String email, String fn, String ln, Role role) throws DuplicateEmailException, DuplicateUsernameException {
		verifyEmail(email);
		verifyUsername(username);
		IUser user = userbuilder.username(username).password(password).email(email).firstName(fn).lastName(ln)
				.role(role).build();
		userpersistence.create(user);
	}


	public void verifyEmail(String email) throws DuplicateEmailException {
		userpersistence.verifyEmail(email);
	}

	@Override
	public void verifyUsername(String username) throws DuplicateUsernameException {
		userpersistence.verifyUsername(username);

	}

}
