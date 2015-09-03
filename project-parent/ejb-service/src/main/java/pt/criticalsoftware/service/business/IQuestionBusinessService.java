package pt.criticalsoftware.service.business;
import java.util.List;

import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.questions.AnswerType;

public interface IQuestionBusinessService {
	IQuestion createQuestion(String questionStr, AnswerType answer);
	
	IQuestion createNewQuestion(String questionStr, AnswerType answer, IScript id);
	
	List <IQuestion> getAllQuestionsByScript(IScript script);
	
	void delete(IQuestion question);
	
	void update(IQuestion question, Integer id);
}
