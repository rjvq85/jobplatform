package pt.criticalsoftware.publicplatform.access;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class Login {
	
	private String username;
	private String password;
	
	private final String AUTH_URL = "Authorized/";

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
			getSession().setAttribute("userIsLogged", true);
			return AUTH_URL + "index.xhtml?faces-redirect=true";
		} catch (Exception e) {
			return "loginerror";
		}
	}
	
	public String logout() {
		try {
			getRequest().logout();
			getSession().removeAttribute("userIsLogged");
			getSession().invalidate();
			return "login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			// log
			return null;
		}
	}
	
	public HttpServletRequest getRequest() {
		FacesContext faces = FacesContext.getCurrentInstance();
		return (HttpServletRequest) faces.getExternalContext().getRequest();
	}
	
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	

}
