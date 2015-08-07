package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IUser;

public class InterviewProxy implements IEntityAware<InterviewEntity>, IInterview {
	
	private InterviewEntity interview;
	
	public InterviewProxy() {
		this(null);
	}
	
	public InterviewProxy(InterviewEntity entity) {
		interview = entity != null ? entity : new InterviewEntity();
	}
	
	@Override
	public InterviewEntity getEntity() {
		return interview;
	}
	
	@Override
	public LocalDate getDate() {
		return interview.getDate();
	}

	@Override
	public void setDate(LocalDate date) {
		interview.setDate(date);
	}

	@Override
	public String getFeedback() {
		return interview.getFeedback();
	}

	@Override
	public void setFeedback(String feedback) {
		interview.setFeedback(feedback);
	}

	@Override
	public IUser getInterviewer() {
		return new UserProxy(interview.getInterviewer());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setInterviewer(IUser interviewer) {
		if (interviewer instanceof IEntityAware<?>) {
			interview.setInterviewer(((IEntityAware<UserEntity>) interviewer).getEntity());
		}
	}

//	public InterviewScriptEntity getScript() {
//		return script;
//	}
//
//	public void setScript(InterviewScriptEntity script) {
//		this.script = script;
//	}

	@Override
	public Integer getId() {
		return interview.getId();
	}

}
