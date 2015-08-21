package pt.criticalsoftware.service.model;

import pt.criticalsoftware.service.persistence.states.CandidacyState;

public interface ICandidacyBuilder {

	ICandidacyBuilder motivationalLetter(String letter);

	ICandidacyBuilder source(String source);

	ICandidacyBuilder state(CandidacyState state);

	ICandidacy build();

	ICandidacyBuilder candidate(ICandidate candidate);

	ICandidacyBuilder position(IPosition position);

}
