package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import pt.criticalsoftware.domain.entities.CandidacyEntity;
import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.candidacy.Reason;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

public class CandidacyProxy implements ICandidacy, IEntityAware<CandidacyEntity> {

	private CandidacyEntity candidacy;

	@Override
	public CandidacyEntity getEntity() {
		return candidacy;
	}

	public CandidacyProxy() {
		this(null);
	}

	public CandidacyProxy(CandidacyEntity entity) {
		candidacy = entity != null ? entity : new CandidacyEntity();
	}

	@Override
	public String getMotivationLetter() {
		return candidacy.getMotivationLetter();
	}

	@Override
	public void setMotivationLetter(String motivationLetter) {
		candidacy.setMotivationLetter(motivationLetter);
	}

	@Override
	public String getSource() {
		return candidacy.getSource();
	}

	@Override
	public void setSource(String source) {
		candidacy.setSource(source);
	}

	@Override
	public CandidacyState getState() {
		return candidacy.getState();
	}

	@Override
	public void setState(CandidacyState state) {
		candidacy.setState(state);
	}

	@Override
	public ICandidate getCandidate() {
		ICandidate icand = new CandidateProxy(candidacy.getCandidate());
		return icand;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setCandidate(ICandidate candidate) {
		if (candidate instanceof IEntityAware<?>) {
			candidacy.setCandidate(((IEntityAware<CandidateEntity>) candidate).getEntity());
		}
	}

	@Override
	public LocalDate getDate() {
		return candidacy.getDate();
	}

	@Override
	public void setDate(LocalDate date) {
		candidacy.setDate(date);
	}

	@Override
	public Integer getId() {
		return candidacy.getId();
	}

	@Override
	public IPosition getPositionCandidacy() {
		return new PositionProxy(candidacy.getPositionCandidacy());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPositionCandidacy(IPosition position) {
		if (position instanceof IEntityAware<?>) {
			candidacy.setPositionCandidacy(((IEntityAware<PositionEntity>) position).getEntity());
		}
	}

	@Override
	public void setReference(String ref) {
		candidacy.setReference(ref);
	}

	@Override
	public String getReference() {
		return candidacy.getReference();
	}

	@Override
	public List<IInterview> getInterviews() {
		List<IInterview> interviews = new ArrayList<>();
		for (InterviewEntity interview : candidacy.getInterviews()) {
			interviews.add(new InterviewProxy(interview));
		}
		return interviews;
	}

	@Override
	public Reason getRejectionReason() {
		return candidacy.getRejectionReason();
	}

	@Override
	public void setRejectionReason(Reason rejectionReason) {
		this.candidacy.setRejectionReason(rejectionReason);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setInterviews(List<IInterview> interviews) {
		List<InterviewEntity> interviewsEnt = new ArrayList<>();
		for (IInterview interview : interviews) {
			if (interview instanceof IEntityAware<?>) {
				interviewsEnt.add(((IEntityAware<InterviewEntity>) interview).getEntity());
			}
		}
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null && getClass() == other.getClass() && candidacy.getId() != null)
				? candidacy.getId().equals(((CandidacyProxy) other).getId()) : (other == this);
	}

	@Override
	public int hashCode() {
		return (candidacy.getId() != null) ? (getClass().hashCode() + candidacy.getId().hashCode()) : super.hashCode();
	}
	
	@Override
	public String toString() {
		return String.valueOf(getId());
	}

}
