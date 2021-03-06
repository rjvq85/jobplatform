package pt.criticalsoftware.service.model;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.persistence.candidacy.Reason;
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

	LocalDate getDate();

	void setDate(LocalDate date);

	void setReference(String ref);

	String getReference();

	Reason getRejectionReason();

	void setRejectionReason(Reason rejectionReason);

	LocalDate getHiringDate();

	void setHiringDate(LocalDate hiringDate);

}
