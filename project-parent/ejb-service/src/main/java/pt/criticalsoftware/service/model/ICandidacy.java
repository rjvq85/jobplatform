package pt.criticalsoftware.service.model;

import java.util.List;

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

	IPosition getPositionCandidacy();

	void setPositionCandidacy(IPosition position);

	List<IInterview> getInterviews();

	void setInterviews(List<IInterview> interviews);

}
