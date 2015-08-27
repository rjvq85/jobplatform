package pt.criticalsoftware.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.IScriptPersistenceService;

@Stateless
public class ScriptBusinessService implements IScriptBusinessService {
	
	@EJB
	private IScriptPersistenceService persistence;
	
	@Override
	public List<IScript> getAll() {
		return persistence.getAllScripts();
	}

	@Override
	public IScript getScriptByID(int id) {
		return persistence.getById(id);
	}
	

}
