package pt.criticalsoftware.platform.interviews;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.scriptxml.ScriptListXML;
import pt.criticalsoftware.service.scriptxml.XMLParser;

@Named
@RequestScoped
public class MyInterviews {

	@EJB
	private IInterviewBusinessService business;

	private List<IInterview> myInterviews;

	public List<IInterview> getMyInterviews() {
		return (null == myInterviews) ? business.getInterviewsByInterviewer(getId()) : myInterviews;

	}

	public void setMyInterviews(List<IInterview> myInterviews) {
		this.myInterviews = myInterviews;
	}

	public boolean isToday(IInterview interview) {
		if (LocalDate.now().isEqual(interview.getDate())) {
			if (!doneInterview(interview))
				return true;
			else
				return false;
		}
		return false;
	}

	public Boolean doneInterview(IInterview interview) {
		String fb = interview.getFeedback();
		if (null != fb) {
			XMLParser parser = new XMLParser();
			ScriptListXML feedbacks = (ScriptListXML) parser.asXML(fb);
			if (feedbacks.getScripts().containsKey(getId()))
				return true;
			else
				return false;
		}
		return false;
	}

	// private methods

	private HttpServletRequest getRequest() {
		FacesContext faces = FacesContext.getCurrentInstance();
		return (HttpServletRequest) faces.getExternalContext().getRequest();
	}

	private HttpSession getSession() {
		return getRequest().getSession();
	}

	@SuppressWarnings("unused")
	private Role getRole() {
		return (Role) getSession().getAttribute("userROLE");
	}

	private Integer getId() {
		return (Integer) getSession().getAttribute("userID");
	}

	public String goToInterview() {
		return (FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
				+ "/Authorized/Scripts/scriptInterview.xhtml?faces-redirect=true");
	}

}
