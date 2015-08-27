package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.domain.proxies.ScriptProxy;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.IScriptPersistenceService;

@Stateless
public class ScriptPersistenceService implements IScriptPersistenceService {
	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public List<IScript> getAllScripts() {
		List<IScript> scripts = new ArrayList<>();
		TypedQuery<ScriptEntity> query  = em.createNamedQuery("ScriptEntity.findAll",ScriptEntity.class);
		List<ScriptEntity> entities = query.getResultList();
		for (ScriptEntity se : entities) scripts.add(new ScriptProxy(se));
		return scripts;
	}

	@Override
	public IScript getById(int id) {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.findById",ScriptEntity.class)
				.setParameter("param", id);
		return new ScriptProxy(query.getSingleResult()); 
	}

}
