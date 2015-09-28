package pt.criticalsoftware.service.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.io.impl.Base64;

import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Stateless
public class UserBusinessService implements IUserBusinessService {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(UserBusinessService.class);

	@EJB
	private IUserPersistenceService userpersistence;

	@SuppressWarnings("cdi-ambiguous-dependency")
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
	
	@Override
	public IUser createUser(IUser user) throws DuplicateEmailException, DuplicateUsernameException {
		verifyEmail(user.getEmail());
		verifyUsername(user.getUsername());
		return userpersistence.create(user);
	}


	private void verifyEmail(String email) throws DuplicateEmailException {
		userpersistence.verifyEmail(email);
	}

	private void verifyUsername(String username) throws DuplicateUsernameException {
		userpersistence.verifyUsername(username);

	}

	@Override
	public List<IUser> getAllUsersByRole(Role role) {
		return userpersistence.getAllUsersByRole(role);
		
	}

	@Override
	public IUser getUserByID(Object id) {
		return userpersistence.findByID(id);
	}

	@Override
	public List<IUser> getAllUsers() {
		return userpersistence.getAll();
	}

	@Override
	public void delUser(IUser user) {
		userpersistence.delete(user);
	}
	
	@Override
	public void changePassword(String newPassword, IUser user) {
		user.setPassword(encryptPassword(newPassword));
		userpersistence.update(user);
	}
	
	private String encryptPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();
			byte[] data2 = Base64.encode(byteData);
			String securedPassword = new String(data2);
			return securedPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}
