package pt.criticalsoftware.domain.proxies;

import pt.criticalsoftware.domain.entities.QuestionEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.questions.AnswerType;

public class QuestionProxy implements IEntityAware<QuestionEntity>, IQuestion {

	private QuestionEntity question;
	
	public QuestionProxy() {
		this(null);
	}
	
	public QuestionProxy(QuestionEntity entity) {
		question = entity != null ? entity : new QuestionEntity();
	}
	@Override
	public Integer getId() {
		return question.getId();
	}

	@Override
	public void setQuestionStr(String questionStr) {
		question.setQuestionStr(questionStr);
		
	}

	@Override
	public String getQuestionStr() {
		
		return question.getQuestionStr();
	}

	@Override
	public void setAnswerType(AnswerType answer) {
		question.setAnswer(answer);
		
	}

	@Override
	public AnswerType getAnswer() {
		return question.getAnswer();
	}

	@Override
	public QuestionEntity getEntity() {
		return question;
	}

	@Override
	public void setScript(IScript script) {
		question.setScript((ScriptEntity) script);
	}

	@Override
	public IScript getScript() {
		return (IScript) question.getScript();
	}

	@Override
	public void setNumber(Integer number) {
		question.setNumber(number);
		
	}

	@Override
	public Integer getNumber() {
			return question.getNumber();
	}

	
}
