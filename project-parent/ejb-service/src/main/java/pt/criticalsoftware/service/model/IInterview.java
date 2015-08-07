package pt.criticalsoftware.service.model;

import java.time.LocalDate;

public interface IInterview {

	LocalDate getDate();

	void setDate(LocalDate date);

	String getFeedback();

	void setFeedback(String feedback);

	IUser getInterviewer();

	void setInterviewer(IUser interviewer);

	Integer getId();

}
