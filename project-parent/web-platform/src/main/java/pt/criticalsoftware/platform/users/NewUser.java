package pt.criticalsoftware.platform.users;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.IUserBuilder;
import pt.criticalsoftware.service.persistence.roles.Role;

@Named
@RequestScoped
public class NewUser {

	@EJB
	private IUserBusinessService business;
	@EJB
	private IUserBuilder builder;

	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String username;
	private Role role;

	public void createUser() {
		IUser user = builder.firstName(firstname).lastName(lastname).email(email).password(password).username(username)
				.role(role).build();
		try {
			business.createUser(user);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Utilizador criado com sucesso.",null);
			FacesContext.getCurrentInstance().addMessage("usermanagement", msg);
			RequestContext.getCurrentInstance().addCallbackParam("created", true);
		} catch (DuplicateEmailException | DuplicateUsernameException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Utilizador / E-mail já existente.",null);
			FacesContext.getCurrentInstance().addMessage("usermanagement", msg);
			RequestContext.getCurrentInstance().addCallbackParam("created", false);
			e.printStackTrace();
		}
	}
	
	public void clear() {
		 firstname = null;
		 lastname = null;
		 email = null;
		 password = null;
		 username = null;
		 role = null;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role[] getRoles() {
		return Role.values();
	}

}
