package pt.criticalsoftware.platform.candidate;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;

@Named
@ViewScoped
public class CandidateInterviews implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IInterviewBusinessService intervBness;
	@EJB
	private ICandidateBusinessService candidateBness;

	private List<IInterview> candInterviews;
	private ICandidate candidate;
	private Integer candidateId;

	public ICandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}

	public List<IInterview> getCandInterviews() {
		candInterviews = intervBness.getDoneByCandidate(candidateId);
		return candInterviews;
	}

	public void setCandInterviews(List<IInterview> candInterviews) {
		this.candInterviews = candInterviews;
	}

	public String getInterviewDate(IInterview interv) {
		return interv.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

}
