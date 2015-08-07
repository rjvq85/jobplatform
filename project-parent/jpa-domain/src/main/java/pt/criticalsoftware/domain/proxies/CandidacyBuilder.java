package pt.criticalsoftware.domain.proxies;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@RequestScoped
public class CandidacyBuilder implements ICandidacyBuilder {
	
	private CandidacyProxy candidacy;
	
	public CandidacyBuilder() {
		candidacy = new CandidacyProxy();
	}
	
	@Override
	public ICandidacyBuilder motivationalLetter(String letter) {
		candidacy.setMotivationLetter(letter);
		return this;
	}
	
	@Override
	public ICandidacyBuilder source(String source) {
		candidacy.setSource(source);
		return this;
	}
	
	@Override
	public ICandidacyBuilder state(CandidacyState state) {
		candidacy.setState(state);
		return this;
	}
	
	@Override
	public ICandidacy build() {
		return candidacy;
	}
	
	
	

}
