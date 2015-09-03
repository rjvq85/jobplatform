package pt.criticalsoftware.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IQuestionBuilder;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IScriptBuilder;
import pt.criticalsoftware.service.persistence.IQuestionPersistenceService;
import pt.criticalsoftware.service.persistence.IScriptPersistenceService;
import pt.criticalsoftware.service.persistence.questions.AnswerType;
@Stateless
public class QuestionBusinessService implements IQuestionBusinessService{

	
	@EJB
	private IQuestionPersistenceService questionPersistence;
	
	@Inject
	private IQuestionBuilder questionBuilder;
	
	@Override
	public IQuestion createQuestion(String questionStr, AnswerType answer) {
		IQuestion question = questionBuilder.answer(answer).questionStr(questionStr).build();
		return questionPersistence.create(question);
	}

	@Override
	public List<IQuestion> getAllQuestionsByScript(IScript script) {
		return questionPersistence.getAllQuestionsByScript(script);
	}

	@Override
	public IQuestion createNewQuestion(String questionStr, AnswerType answer,
			IScript id) {
		IQuestion question = questionBuilder.answer(answer).questionStr(questionStr).build();
		return questionPersistence.createNewQuestion(question, id);
	}

	@Override
	public void delete(IQuestion question) {
		questionPersistence.delete(question);		
	}

	@Override
	public void update(IQuestion question, Integer id) {
		questionPersistence.update(question, id);
		
	}

}
