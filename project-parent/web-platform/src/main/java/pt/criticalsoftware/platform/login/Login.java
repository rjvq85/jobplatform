package pt.criticalsoftware.platform.login;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IUser;
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
		try {
			getRequest().login(username, password);
			Integer id = userservice.getUserId(username);
			setUserLogged(id);
			setLoggedUsername(username, id);
			setRole();
			return landingPage(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "loginerror";
		}
	}

	public String logout() {
		FacesContext context = currentFacesContext();
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

	private String landingPage(Integer id) {
		StringBuilder result = new StringBuilder(AUTH_URL);
		List<Role> roles = userservice.getRoles(id);
		if (1 == roles.size()) {
			switch (roles.get(0).getRole()) {
			case "ADMIN": {
				result.append("index.xhtml?faces-redirect=true");
				break;
			}
			case "GESTOR": {
				result.append("index.xhtml?faces-redirect=true");
				break;
			}
			default: {
				result.append("Interviewer/index.xhtml?faces-redirect=true");
				break;
			}
			}
		} else {
			if (roles.contains(Role.ADMIN))
				result.append("index.xhtml?faces-redirect=true");
			else
				result.append("index.xhtml?faces-redirect=true");
		}
		return result.toString();
	}

	private void setUserLogged(Integer id) {
		getSession().setAttribute("userID", id);
	}
	
	private void setLoggedUsername(String username, Integer id) {
		String uname = username.substring(0,1).toUpperCase().concat(username.substring(1)).toLowerCase();
		getSession().setAttribute("userNAME", uname);
		IUser user = userservice.getUserByID(id);
		String name = user.getFirstName().concat(" ").concat(user.getLastName());
		getSession().setAttribute("userFNLN", name);
	}
	
	private FacesContext currentFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	private HttpSession getSession() {
		FacesContext context = currentFacesContext();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}
	
	private HttpServletRequest getRequest() {
		return (HttpServletRequest) currentFacesContext().getExternalContext().getRequest();
	}
	
	private void setRole() {
		FacesContext context = currentFacesContext();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		if (request.isUserInRole("ADMIN")) {
			getSession().setAttribute("userROLE", Role.ADMIN);
		} else if (request.isUserInRole("GESTOR")) {
			getSession().setAttribute("userROLE", Role.GESTOR);
		} else if (request.isUserInRole("ENTREVISTADOR")) {
			getSession().setAttribute("userROLE", Role.ENTREVISTADOR);
		}
	}

}
