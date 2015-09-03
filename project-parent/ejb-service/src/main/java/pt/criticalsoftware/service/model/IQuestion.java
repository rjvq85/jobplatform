package pt.criticalsoftware.service.model;

import pt.criticalsoftware.service.persistence.questions.AnswerType;

public interface IQuestion {
	
	Integer getId();

	void setQuestionStr(String questionStr);

	String getQuestionStr();

	void setAnswerType(AnswerType answer);

	AnswerType getAnswer();
	
	void setScript(IScript script);

	IScript getScript();
	
	void setNumber(Integer number);

	Integer getNumber();
	
}
