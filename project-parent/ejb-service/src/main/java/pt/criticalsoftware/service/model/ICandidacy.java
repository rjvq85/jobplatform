package pt.criticalsoftware.service.model;

import pt.criticalsoftware.service.persistence.states.CandidacyState;

public interface ICandidacy {

	String getMotivationLetter();

	void setMotivationLetter(String motivationLetter);

	String getSource();

	void setSource(String source);

	CandidacyState getState();

	void setState(CandidacyState state);

	ICandidate getCandidate();

	void setCandidate(ICandidate candidate);

	Integer getId();

}
