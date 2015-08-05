package pt.criticalsoftware.platform.login;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Named
@RequestScoped
public class Register {

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

	public void doRegister(){
		
		
		if (this.role.equals("Admin"))
			this.roleUser=Role.ADMIN;
		else if (this.role.equals("Entrevistador"))
			this.roleUser=Role.ENTREVISTADOR;
		else if (this.role.equals("Gestor"))
			this.roleUser=Role.GESTOR;
				
		boolean success=userservice.createUser(this.username, this.password, this.email, this.firstName, this.lastName, this.roleUser);
		
//				if(success){
//					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
//							"User registered successfully.", "");
//					FacesContext.getCurrentInstance().addMessage(null, message);
//				} else {
//					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//							"E-mail already registered", "");
//					FacesContext.getCurrentInstance().addMessage(null, message);
//				}
	}

}
