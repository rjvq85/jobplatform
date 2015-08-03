package pt.criticalsoftware.service.model;

import java.util.Collection;

import pt.criticalsoftware.service.persistence.roles.Role;

public interface IUser {

	Integer getId();

	void setRoles(Collection<Role> roles);

	Collection<Role> getRoles();

	void setLastName(String lastName);

	String getLastName();

	void setFirstName(String firstName);

	String getFirstName();

	void setEmail(String email);

	String getEmail();

	void setPassword(String password);

	String getPassword();

	void setUsername(String username);

	String getUsername();

}
