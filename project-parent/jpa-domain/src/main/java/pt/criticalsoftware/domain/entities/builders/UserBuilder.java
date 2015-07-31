package pt.criticalsoftware.domain.entities.builders;

import java.util.ArrayList;

import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.domain.entities.roles.Roles;

public class UserBuilder {
	
	private UserEntity user;
	
	public UserBuilder() {
		user = new UserEntity();
	}
	
	public UserBuilder firstName(String name) {
		user.setFirstName(name);
		return this;
	}
	
	public UserBuilder lastName(String name) {
		user.setLastName(name);
		return this;
	}
	
	public UserBuilder email(String email) {
		user.setEmail(email);
		return this;
	}
	
	public UserBuilder password(String password) {
		user.setPassword(password);
		return this;
	}
	
	public UserBuilder username(String username) {
		user.setUsername(username);
		return this;
	}
	
	public UserBuilder role(Roles role) {
		if (null == user.getRoles()) user.setRoles(new ArrayList<Roles>());
		user.getRoles().add(role);
		return this;
	}
	
	public UserEntity build() {
		return user;
	}

}
