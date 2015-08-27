package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IInterviewBuilder;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;

@Stateless
public class InterviewBuilder implements IInterviewBuilder {

	private InterviewProxy interview;

	public InterviewBuilder() {
		interview = new InterviewProxy();
	}

	@Override
	public IInterviewBuilder date(LocalDate date) {
		interview.setDate(date);
		return this;
	}

	@Override
	public IInterviewBuilder feedback(String feedback) {
		interview.setFeedback(feedback);
		return this;
	}

	@Override
	public IInterviewBuilder interviewers(List<IUser> interviewers) {
		interview.setInterviewers(interviewers);
		return this;
	}

	@Override
	public IInterviewBuilder script(IScript script) {
		interview.setScript(script);
		return this;
	}

	@Override
	public IInterviewBuilder position(IPosition pos) {
		interview.setPosition(pos);
		return this;
	}

	@Override
	public IInterviewBuilder candidacy(ICandidacy cand) {
		interview.setCandidacy(cand);
		return this;
	}

	@Override
	public IInterview build() {
		return interview;
	}

}
