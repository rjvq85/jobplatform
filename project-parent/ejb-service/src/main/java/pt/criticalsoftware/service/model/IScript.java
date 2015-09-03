package pt.criticalsoftware.service.model;

import java.util.Collection;

import pt.criticalsoftware.service.persistence.questions.Question;


public interface IScript {
	
	Integer getId();

	void setTitle(String title);

	String getTitle();
	
	void setReference(String reference);

	String getReference();
	
	void setQuestions(Collection<IQuestion> questions);

	Collection<IQuestion> getQuestions();

	
}
