package pt.criticalsoftware.domain.proxies;

import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.service.model.ICandidate;

public class CandidateProxy implements ICandidate, IEntityAware<CandidateEntity> {
	
	private CandidateEntity candidate;

	@Override
	public CandidateEntity getEntity() {
		return candidate;
	}
	
	public CandidateProxy() {
		this(null);
	}
	
	public CandidateProxy(CandidateEntity entity) {
		candidate = entity != null ? entity : new CandidateEntity();
	}
	
	

}
