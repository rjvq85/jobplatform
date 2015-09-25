package pt.criticalsoftware.service.model;

import java.util.Collection;
import java.util.List;

import pt.criticalsoftware.service.persistence.questions.Question;


public interface IScript {
	
	Integer getId();

	void setTitle(String title);

	String getTitle();
	
	void setReference(String reference);

	String getReference();
	
	void setQuestions(Collection<IQuestion> questions);

	Collection<IQuestion> getQuestions();

	List<IInterview> getInterviews();

	void setInterviews(List<IInterview> interviews);

	
}
