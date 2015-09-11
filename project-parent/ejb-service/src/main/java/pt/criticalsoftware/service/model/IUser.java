package pt.criticalsoftware.service.model;

import java.util.List;

import pt.criticalsoftware.service.persistence.roles.Role;

public interface IUser {

	Integer getId();

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

	List<IPosition> getPositions();

	void setPositions(List<IPosition> positions);

	void setRoles(List<Role> roles);

	List<Role> getRoles();

}
