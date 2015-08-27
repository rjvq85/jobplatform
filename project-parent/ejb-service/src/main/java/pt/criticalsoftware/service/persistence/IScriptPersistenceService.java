package pt.criticalsoftware.service.persistence;

import pt.criticalsoftware.service.model.IScript;

import java.util.List;

public interface IScriptPersistenceService {
	
	List<IScript> getAllScripts();

	IScript getById(int id);

}
