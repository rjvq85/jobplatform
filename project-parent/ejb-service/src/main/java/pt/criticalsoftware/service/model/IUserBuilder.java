package pt.criticalsoftware.service.model;

import pt.criticalsoftware.service.persistence.roles.Role;
public interface IUserBuilder {

	IUserBuilder firstName(String name);

	IUserBuilder lastName(String name);

	IUserBuilder email(String email);

	IUserBuilder password(String password);

	IUserBuilder username(String username);

	IUserBuilder role(Role role);

	IUser build();

}
