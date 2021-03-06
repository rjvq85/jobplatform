package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Stateless
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
	public ICandidacyBuilder candidate(ICandidate candidate) {
		candidacy.setCandidate(candidate);
		return this;
	}
	
	@Override 
	public ICandidacyBuilder position(IPosition position) {
			candidacy.setPositionCandidacy(position);
			return this;
	}

	@Override
	public ICandidacy build() {
		candidacy.setDate(LocalDate.now());
		return candidacy;
	}

}
