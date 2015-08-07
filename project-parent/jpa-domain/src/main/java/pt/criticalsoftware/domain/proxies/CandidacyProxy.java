package pt.criticalsoftware.domain.proxies;

import java.util.List;

import javax.inject.Inject;

import pt.criticalsoftware.domain.entities.CandidacyEntity;
import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IModelFactory;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

public class CandidacyProxy implements ICandidacy, IEntityAware<CandidacyEntity> {
	
	private CandidacyEntity candidacy;
	
	@Inject
	private IModelFactory modelfactory;
	
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
	public Integer getId() {
		return candidacy.getId();
	}

	@Override
	public PositionEntity getPositionCandidacy() {
		return candidacy.getPositionCandidacy();
	}

	@Override
	public void setPositionCandidacy(PositionEntity positionCandidacy) {
		candidacy.setPositionCandidacy(positionCandidacy);
	}

	@Override
	public List<InterviewEntity> getInterviews() {
		return candidacy.getInterviews();
	}
	
	@Override
	public void setInterviews(List<InterviewEntity> interviews) {
		candidacy.setInterviews(interviews);
	}
	
	

}
