package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;

public interface IQuestionPersistenceService {
	
	IQuestion create(IQuestion question);
	
	void delete(IQuestion question);
	
	IQuestion createNewQuestion(IQuestion question,IScript id);
	
	List<IQuestion> getAllQuestionsByScript(IScript script);
	
	void update(IQuestion question, Integer id);
}
