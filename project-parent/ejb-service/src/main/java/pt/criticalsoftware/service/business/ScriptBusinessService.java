package pt.criticalsoftware.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.exceptions.DuplicateTitleException;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IScriptBuilder;
import pt.criticalsoftware.service.persistence.IScriptPersistenceService;
@Stateless
public class ScriptBusinessService implements IScriptBusinessService{

	private final Logger logger = LoggerFactory.getLogger(ScriptBusinessService.class);

	@EJB
	private IScriptPersistenceService scriptPersistence;

	@Inject
	private IScriptBuilder scriptBuilder;

	@Override
	public List<IScript> getAllScripts() {
		return scriptPersistence.getAllScripts();
	}

	@Override
	public List<IScript> getScriptsByTitle(String title) {
		return scriptPersistence.getScriptsByTitle(title);
	}

	@Override
	public List<IScript> getScriptsByReference(String reference) {
		return scriptPersistence.getScriptsByReference(reference);
	}

	@Override
	public void createScript(String title, List<Integer> questions)
			throws DuplicateTitleException {

		verifyTitle(title);
		IScript script = scriptBuilder.title(title).reference("g").build();
		scriptPersistence.create(script, questions);

	}

	@Override
	public void verifyTitle(String title) throws DuplicateTitleException {
		scriptPersistence.verifyTitle(title);

	}

	@Override
	public List<IQuestion> getAllQuestionsById(Integer id) {

		return scriptPersistence.getAllQuestionsById(id);
	}

	@Override
	public void update(Integer id,String title, List<Integer> questions) {
			scriptPersistence.update(id,title, questions);	
	}
	@Override
	public boolean verifyEditTitle(String title, Integer id) {
		return scriptPersistence.verifyEditTitle(title, id);	
	}
	
	@Override
	public void deleteScript(IScript script, List<Integer> questions) {
		scriptPersistence.delete(script, questions);
		
	}
	
	@Override
	public void deleteScript(IScript script) {
		scriptPersistence.delete(script);
	}
	
	@Override
	public List<IScript> getAll() {
		return scriptPersistence.getAllScripts();
	}

	@Override
	public IScript getScriptByID(int id) {
		return scriptPersistence.getById(id);
	}

}
