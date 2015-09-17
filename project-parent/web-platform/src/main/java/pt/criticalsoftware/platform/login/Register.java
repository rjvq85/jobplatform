package pt.criticalsoftware.platform.login;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.persistence.roles.Role;

@Named
@RequestScoped
public class Register implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(Register.class);

	@EJB
	private IUserBusinessService userservice;

	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String role;
	private Role roleUser;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void doRegister() {

		if (this.role.equals("Admin"))
			this.roleUser = Role.ADMIN;
		else if (this.role.equals("Entrevistador"))
			this.roleUser = Role.ENTREVISTADOR;
		else if (this.role.equals("Gestor"))
			this.roleUser = Role.GESTOR;

		try {
			userservice.createUser(this.username, this.password, this.email, this.firstName, this.lastName,
					this.roleUser);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					"O Utilizador " + this.firstName + " " + this.lastName + " foi criado com sucesso"));
		} catch (DuplicateEmailException dee) {
			logger.error("Tentativa de registo com um e-mail ("+this.email+") já existente");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail já existente.", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (DuplicateUsernameException due) {
			logger.error("Tentativa de registo com um username ("+this.username+") já existente");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username já existente.", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

}
