package pt.criticalsoftware.publicplatform.access;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;

@Named
@RequestScoped
public class LoginPublic {

	private String username;
	private String password;

	@EJB
	private ICandidateBusinessService business;

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
			setUserID();
			getSession().setAttribute("userIsLogged", true);
			getSession().setAttribute("usersName", getUsersName(username));
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

	private HttpServletRequest getRequest() {
		FacesContext faces = FacesContext.getCurrentInstance();
		return (HttpServletRequest) faces.getExternalContext().getRequest();
	}

	private HttpSession getSession() {
		return getRequest().getSession();
	}

	private String getUsersName(String username) {
		ICandidate candidate = business.getCandidateByUsername(username);
		return (candidate.getFirstName() + " " + candidate.getLastName());
	}

	private RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}

	public void registerLogin() {
		try {
			getRequest().login(username, password);
			getSession().setAttribute("userIsLogged", true);
			getSession().setAttribute("usersName", getUsersName(username));
			setUserID();
		} catch (Exception e) {
		}
	}

	public void loginDialog() {
		try {
			getRequest().login(username, password);
			getSession().setAttribute("userIsLogged", true);
			getRequestContext().addCallbackParam("loggedIn", true);
			getSession().setAttribute("usersName", getUsersName(username));
			setUserID();
		} catch (Exception e) {
		}
	}
	
	private void setUserID() {
		Integer userid = business.getCandidateByUsername(username).getId();
		getSession().setAttribute("userId", userid);
	}

}
