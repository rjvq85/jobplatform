package pt.criticalsoftware.service.model;

import pt.criticalsoftware.service.persistence.questions.AnswerType;

public interface IQuestionBuilder {

	IQuestionBuilder questionStr(String questionStr);

	IQuestionBuilder answer(AnswerType answer);
	
	IQuestionBuilder script(IScript script);
	
	IQuestionBuilder number(Integer number);

	IQuestion build();

}
