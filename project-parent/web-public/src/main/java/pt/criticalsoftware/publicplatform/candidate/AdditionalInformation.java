package pt.criticalsoftware.publicplatform.candidate;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.model.ICandidate;

/**
 * Add additional courses, schools and degrees to candidate
 */
@Named
@RequestScoped
public class AdditionalInformation {

	@EJB
	private ICandidateBusinessService bness;

	private ICandidate candidate;
	private String course;
	private String degree;
	private String school;

	public void update() {
		try {
			candidate = getCandidate();
			candidate.getCourse().add(course);
			candidate.getUniversity().add(school);
			candidate.getDegree().add(degree);
			bness.updateCandidate(candidate);
			RequestContext.getCurrentInstance().addCallbackParam("updated", true);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().addCallbackParam("updated", false);
			e.printStackTrace();
		}

	}

	public ICandidate getCandidate() {
		candidate = bness.getCandidateById(getUserId());
		return candidate;
	}

	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}

	private Integer getUserId() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return (Integer) req.getSession().getAttribute("userId");
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

}
