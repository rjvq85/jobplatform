package pt.criticalsoftware.publicplatform.social;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import pt.criticalsoftware.service.business.ICandidateBusinessService;

@Named
@RequestScoped
public class LinkedInStatus {

	@EJB
	private ICandidateBusinessService bness;

	public Boolean hasLinkedIn() {
		if (null != getUserId()) {
			if (null != bness.getCandidateById(getUserId()).getLinkedInPicture())
				return false;
		}
		return true;
	}

	private Integer getUserId() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return (Integer) req.getSession().getAttribute("userId");
	}

}
