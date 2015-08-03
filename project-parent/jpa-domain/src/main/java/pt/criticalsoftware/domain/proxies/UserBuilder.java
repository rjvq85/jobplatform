package pt.criticalsoftware.domain.proxies;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.roles.Role;

@RequestScoped
public class UserBuilder implements IUserBuilder {
	
	private UserProxy user;
	
	public UserBuilder() {
		user = new UserProxy();
	}
	
	@Override
	public IUserBuilder firstName(String name) {
		user.setFirstName(name);
		return this;
	}
	
	@Override
	public IUserBuilder lastName(String name) {
		user.setLastName(name);
		return this;
	}
	
	@Override
	public IUserBuilder email(String email) {
		user.setEmail(email);
		return this;
	}
	
	@Override
	public IUserBuilder password(String password) {
		user.setPassword(password);
		return this;
	}
	
	@Override
	public IUserBuilder username(String username) {
		user.setUsername(username);
		return this;
	}
	
	@Override
	public IUserBuilder role(Role role) {
		if (null == user.getRoles()) user.setRoles(new ArrayList<Role>());
		user.getRoles().add(role);
		return this;
	}
	
	@Override
	public IUser build() {
		return user;
	}

}
