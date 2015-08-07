package pt.criticalsoftware.domain.proxies;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;

@RequestScoped
public class CandidateBuilder implements ICandidateBuilder {
	
	private CandidateProxy candidate;
	
	public CandidateBuilder() {
		candidate = new CandidateProxy();
	}
	
	@Override
	public ICandidate build() {
		return candidate;
	}

}
