package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pt.criticalsoftware.domain.entities.CandidacyEntity;
import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IScript;
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
	public List<IUser> getInterviewers() {
		List<IUser> interviewers = new ArrayList<>();
		for (UserEntity ie : interview.getInterviewers()) {
			interviewers.add(new UserProxy(ie));
		}
		return interviewers;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setInterviewers(List<IUser> interviewers) {
		List<UserEntity> users = new ArrayList<>();
		for (IUser user : interviewers) {
			if (user instanceof IEntityAware<?>) {
				users.add(((IEntityAware<UserEntity>) user).getEntity());
			}
		}
		interview.setInterviewers(users);
	}

	@Override
	public IScript getScript() {
		return new ScriptProxy(interview.getScript());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setScript(IScript script) {
		if (script instanceof IEntityAware<?>) {
			interview.setScript(((IEntityAware<ScriptEntity>) script).getEntity());
		}
	}

	@Override
	public IPosition getPosition() {
		return new PositionProxy(interview.getPosition());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPosition(IPosition pos) {
		if (pos instanceof IEntityAware<?>) {
			interview.setPosition(((IEntityAware<PositionEntity>) pos).getEntity());
		}
	}

	@Override
	public ICandidacy getCandidacy() {
		return new CandidacyProxy(interview.getCandidacy());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCandidacy(ICandidacy cand) {
		if (cand instanceof IEntityAware<?>) {
			interview.setCandidacy(((IEntityAware<CandidacyEntity>) cand).getEntity());
		}
	}

	@Override
	public String getReference() {
		return interview.getInterviewRef();
	}

	@Override
	public void setReference(String reference) {
		interview.setInterviewRef(reference);
	}

	@Override
	public Integer getId() {
		return interview.getId();
	}

	@Override
	public String toString() {
		return interview.getId().toString();
	}

	@Override
	public Integer getGlobalRating() {
		return interview.getGlobalRating();
	}

	@Override
	public void setGlobalRating(Integer globalRating) {
		interview.setGlobalRating(globalRating);
	}

	@Override
	public String getGlobalRatingString() {
		String evaluation = (interview.getGlobalRating() > 0) ? "Positiva"
				: (interview.getGlobalRating() < 0) ? "Negativa" : "Neutra";
		return evaluation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInterviewer(IUser interviewer) {
		if (interviewer instanceof IEntityAware<?>) {
			interview.getInterviewers().add(((IEntityAware<UserEntity>) interviewer).getEntity());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteInterviewer(IUser interviewer) {
		if (interviewer instanceof IEntityAware<?>) {
			interview.getInterviewers().remove(((IEntityAware<UserEntity>) interviewer).getEntity());
		}
	}

}
