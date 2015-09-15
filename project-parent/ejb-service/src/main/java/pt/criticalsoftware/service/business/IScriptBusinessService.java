package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateTitleException;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;

public interface IScriptBusinessService {

	List<IScript> getAllScripts();

	List<IScript> getScriptsByTitle(String title);

	List<IScript> getScriptsByReference(String reference);

	void createScript(String title, List<Integer> questions) throws DuplicateTitleException;

	void verifyTitle(String title) throws DuplicateTitleException;

	List<IQuestion> getAllQuestionsById(Integer id);

	void update(Integer id, String title, List<Integer> questions);

	boolean verifyEditTitle(String title, Integer id);

	void deleteScript(IScript script, List<Integer> questions);

	List<IScript> getAll();

	IScript getScriptByID(int id);

	void deleteScript(IScript script);

}
