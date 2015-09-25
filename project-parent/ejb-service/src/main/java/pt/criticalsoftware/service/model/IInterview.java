package pt.criticalsoftware.service.model;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.persistence.states.InterviewState;

public interface IInterview {

	LocalDate getDate();

	void setDate(LocalDate date);

	String getFeedback();

	void setFeedback(String feedback);

	Integer getId();

	IScript getScript();

	void setScript(IScript script);

	List<IUser> getInterviewers();

	void setInterviewers(List<IUser> interviewers);

	IPosition getPosition();

	void setPosition(IPosition pos);

	ICandidacy getCandidacy();

	void setCandidacy(ICandidacy cand);

	void addInterviewer(IUser interviewer);

	void deleteInterviewer(IUser interviewer);

	String getReference();

	void setReference(String reference);

	Integer getGlobalRating();

	void setGlobalRating(Integer globalRating);

	String getGlobalRatingString();

	InterviewState getInterviewState();

	void setInterviewState(InterviewState interviewState);

	Integer getDoneNumber();

	void setDoneNumber(Integer doneNumber);

}
