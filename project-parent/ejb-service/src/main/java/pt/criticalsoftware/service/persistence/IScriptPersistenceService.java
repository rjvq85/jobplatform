package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateTitleException;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;

public interface IScriptPersistenceService {

	List<IScript> getAllScripts();

	List<IScript> getScriptsByTitle(String title);

	List<IScript> getScriptsByReference(String reference);

	void verifyTitle(String title) throws DuplicateTitleException;

	IScript create(IScript script, List<Integer> questions);

	List<IQuestion> getAllQuestionsById(Integer id);

	IScript update(Integer id, String title, List<Integer> questions);

	void delete(IScript script, List<Integer> questions);

	boolean verifyEditTitle(String title, Integer id);

	IScript getById(int id);

	void delete(IScript script);

}