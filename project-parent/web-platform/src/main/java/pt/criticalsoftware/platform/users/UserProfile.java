package pt.criticalsoftware.platform.users;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IUser;

@Named
@RequestScoped
public class UserProfile {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfile.class);

	@EJB
	private IUserBusinessService bness;

	private IUser user;
	private Integer userid;
	private String password;

	public String init() {
		user = bness.getUserByID(userid);
		if (null != user)
			return null;
		else
			return "userprofileerror.xhtml?faces-redirect=true";
	}
	
	public void changePassword() {
		userid = (Integer) getSession().getAttribute("userID");
		user = bness.getUserByID(userid);
		try {
			bness.changePassword(password, user);
			RequestContext.getCurrentInstance().addCallbackParam("changed", true);
		} catch (Exception e) {
			logger.error("Erro ao alterar password do utilizador "+user.getUsername());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro",null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().addCallbackParam("changed", false);
		}
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUserRole() {
		return user.getRoles().get(0).getDetail().substring(0, 1).toUpperCase()
				+ user.getRoles().get(0).getDetail().substring(1).toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private HttpSession getSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getSession();
	}

}
