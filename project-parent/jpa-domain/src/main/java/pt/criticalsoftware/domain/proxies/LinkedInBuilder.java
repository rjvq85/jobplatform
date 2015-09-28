package pt.criticalsoftware.domain.proxies;


import javax.ejb.Stateless;

import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ILinkedInBuilder;

@Stateless
public class LinkedInBuilder implements ILinkedInBuilder {

	private CandidateEntity candidate;
	
	@SuppressWarnings("unchecked")
	@Override
	public ILinkedInBuilder candidate(ICandidate candidate) {
		if (candidate instanceof IEntityAware<?>) {
			this.candidate = ((IEntityAware<CandidateEntity>) candidate).getEntity();
			return this;
		}
		return null;
	}
	
	@Override
	public ILinkedInBuilder headline(String headline) {
		return this;
	}
	
	@Override
	public ILinkedInBuilder summary(String summary) {
		return this;
	}
	
	@Override
	public ILinkedInBuilder picture(String picture) {
		return this;
	}
	
	@Override
	public ILinkedInBuilder profile(String profile) {
		return this;
	}
	
	@Override
	public ILinkedInBuilder connections(Integer connections) {
		return this;
	}
	
	@Override
	public ICandidate build() {
		return new CandidateProxy(candidate);
	}

}
