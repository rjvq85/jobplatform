package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.model.IScript;

public interface IScriptBusinessService {

	List<IScript> getAll();

	IScript getScriptByID(int id);

}
