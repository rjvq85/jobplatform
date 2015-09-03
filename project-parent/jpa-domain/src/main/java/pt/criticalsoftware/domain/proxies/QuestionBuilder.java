package pt.criticalsoftware.domain.proxies;

import javax.enterprise.context.RequestScoped;

import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IQuestionBuilder;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.questions.AnswerType;

@RequestScoped
public class QuestionBuilder implements IQuestionBuilder{

	private QuestionProxy question;
	
	public QuestionBuilder() {
		question = new QuestionProxy();
	}
	
	@Override
	public IQuestionBuilder questionStr(String questionStr) {
		question.setQuestionStr(questionStr);
		return this;
	}

	@Override
	public IQuestionBuilder answer(AnswerType answer) {
		question.setAnswerType(answer);
		return this;
	}

	@Override
	public IQuestion build() {
		return question;
	}

	@Override
	public IQuestionBuilder script(IScript script) {
		question.setScript(script);
		return this;
	}

	@Override
	public IQuestionBuilder number(Integer number) {
		question.setNumber(number);
		return this;
	}

}
