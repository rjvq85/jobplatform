package pt.criticalsoftware.platform.login;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Named
@RequestScoped
public class Login {

	@EJB
	private IUserBusinessService userservice;

	private final String AUTH_URL = "Authorized/";

	private String username;
	private String password;

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

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.login(username, password);
			Integer id = userservice.getUserId(username);
			setUserLogged(id);
			return landingPage(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "loginerror";
		}
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = request.getSession();
		try {
			request.logout();
			session.removeAttribute("userID");
			session.invalidate();
			return "/login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String landingPage(Integer id) {
		StringBuilder result = new StringBuilder(AUTH_URL);
		List<Role> roles = userservice.getRoles(id);
		if (1 == roles.size()) {
			switch (roles.get(0).getRole()) {
			case "ADMIN": {
				result.append("Admin/index.xhtml?faces-redirect=true");
				break;
			}
			case "GESTOR": {
				result.append("Manager/index.xhtml?faces-redirect=true");
				break;
			}
			default: {
				result.append("Interviewer/index.xhtml?faces-redirect=true");
				break;
			}
			}
		} else {
			if (roles.contains(Role.ADMIN))
				result.append("Admin/index.xhtml?faces-redirect=true");
			else
				result.append("Manager/index.xhtml?faces-redirect=true");
		}
		return result.toString();
	}

	public void setUserLogged(Integer id) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("userID", id);
	}
	
	
	// Apenas para testes com botão no xhtml que chama este método
	public void create() {
		String username = "aaa";
		String password = "1234";
		String email = "aaa";
		String fn = "aaa";
		String ln = "aaa";
		Role role = Role.ADMIN;
		userservice.createUser(username, password, email, fn, ln, role);
	}

}
