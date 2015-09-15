package pt.criticalsoftware.domain.service;

import java.util.ArrayList;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.domain.entities.QuestionEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.QuestionProxy;
import pt.criticalsoftware.domain.proxies.ScriptProxy;
import pt.criticalsoftware.domain.utils.GenerateReferenceValue;
import pt.criticalsoftware.service.exceptions.DuplicateTitleException;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.IScriptPersistenceService;

@Stateless
public class ScriptPersistenceService implements IScriptPersistenceService {

	private final Logger logger = LoggerFactory.getLogger(ScriptPersistenceService.class);

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public List<IScript> getAllScripts() {

		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.getAll", ScriptEntity.class);
		List<ScriptEntity> entities = query.getResultList();
		List<IScript> proxies = new ArrayList<>();
		for (ScriptEntity pe : entities) {
			ScriptProxy scriptProxy = new ScriptProxy(pe);
			proxies.add(scriptProxy);
		}
		return proxies;

	}

	@Override
	public List<IScript> getScriptsByTitle(String title) {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.findByTitle", ScriptEntity.class)
				.setParameter("title", title);
		;
		List<ScriptEntity> entities = query.getResultList();
		List<IScript> proxies = new ArrayList<>();
		for (ScriptEntity pe : entities) {
			ScriptProxy scriptProxy = new ScriptProxy(pe);
			proxies.add(scriptProxy);
		}
		return proxies;
	}

	@Override
	public List<IScript> getScriptsByReference(String reference) {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.findByReference", ScriptEntity.class)
				.setParameter("reference", reference);
		;
		List<ScriptEntity> entities = query.getResultList();
		List<IScript> proxies = new ArrayList<>();
		for (ScriptEntity pe : entities) {
			ScriptProxy scriptProxy = new ScriptProxy(pe);
			proxies.add(scriptProxy);
		}
		return proxies;
	}

	@Override
	public void verifyTitle(String title) throws DuplicateTitleException {
		TypedQuery<ScriptEntity> q = em.createNamedQuery("ScriptEntity.verifyTitle", ScriptEntity.class)
				.setParameter("title", title);
		try {
			q.getSingleResult().getTitle();
			throw new DuplicateTitleException("O título " + title + " já existe!");
		} catch (NoResultException nre) {
			logger.info("O título" + title + " está disponível!");
		}

	}

	@Override
	public IScript create(IScript script, List<Integer> questions) {
		ScriptEntity entityScript;
		List<QuestionEntity> questionsEnt;
		questionsEnt = getListQuestionsEnt(questions);
		Integer id;
		try {
			entityScript = getEntity(script);
			em.persist(entityScript);
			entityScript.setQuestions(questionsEnt);
			entityScript.setReference(GenerateReferenceValue.genReference("G", entityScript.getId()));
			em.merge(entityScript);
			id = entityScript.getId();
			questionsAddId(questionsEnt, entityScript);
			return new ScriptProxy(entityScript);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}

	}

	private void questionsAddId(List<QuestionEntity> questionsEnt, ScriptEntity entityScript) {
		int i = 1;
		for (QuestionEntity q : questionsEnt) {
			q.setScript(entityScript);
			q.setNumber(i);
			i++;
		}
	}

	private List<QuestionEntity> getListQuestionsEnt(List<Integer> questions) {
		QuestionEntity question;
		List<QuestionEntity> qEnt = new ArrayList<QuestionEntity>();
		for (Integer q : questions) {
			question = findById(q);
			qEnt.add(question);

		}
		return qEnt;
	}

	private QuestionEntity findById(Integer id) {

		TypedQuery<QuestionEntity> query = em.createNamedQuery("QuestionEntity.findById", QuestionEntity.class)
				.setParameter("id", id);
		return query.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	private ScriptEntity getEntity(IScript script) throws IllegalStateException {
		if (script instanceof IEntityAware<?>) {
			return ((IEntityAware<ScriptEntity>) script).getEntity();
		}
		throw new IllegalStateException();
	}

	@Override
	public List<IQuestion> getAllQuestionsById(Integer id) {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.getAllQuestionsById", ScriptEntity.class)
				.setParameter("id", id);
		ScriptEntity entitie = query.getSingleResult();
		entitie.getQuestions();
		List<QuestionEntity> questionEnt = new ArrayList<QuestionEntity>();
		List<IQuestion> proxies = new ArrayList<>();
		for (QuestionEntity qe : questionEnt) {
			QuestionProxy questionProxy = new QuestionProxy(qe);
			proxies.add(questionProxy);
		}
		return proxies;
	}

	@Override
	public boolean verifyEditTitle(String title, Integer id) {

		TypedQuery<ScriptEntity> q = em.createNamedQuery("ScriptEntity.verifyTitle", ScriptEntity.class)
				.setParameter("title", title);
		try {
			q.getSingleResult().getTitle();
			if (q.getSingleResult().getId() == id)
				return true;
			else
				return false;
		} catch (NoResultException nre) {
			logger.info("O título" + title + " está disponível!");
			return true;
		}
	}

	@Override
	public IScript update(Integer id, String title, List<Integer> questions) {
		ScriptEntity entity;
		List<QuestionEntity> questionsEnt;
		questionsEnt = getListQuestionsEnt(questions);
		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.getScriptById", ScriptEntity.class)
				.setParameter("id", id);
		ScriptEntity entitie = query.getSingleResult();
		logger.info("encontrou" + entitie.getTitle());
		entitie.setTitle(title);
		entitie.setQuestions(questionsEnt);
		em.merge(entitie);
		questionsAddId(questionsEnt, entitie);
		logger.info("Persisitu");
		return new ScriptProxy(entitie);
	}

	@Override
	public void delete(IScript script, List<Integer> questions) {
		ScriptEntity entity;
		List<QuestionEntity> questionsEnt;
		questionsEnt = getListQuestionsEnt(questions);
		for (QuestionEntity q : questionsEnt) {
			QuestionEntity qt = em.find(QuestionEntity.class, q.getId());
			if (qt != null)
				em.remove(qt);
		}

		try {
			entity = getEntity(script);
			ScriptEntity sc = em.find(ScriptEntity.class, entity.getId());
			if (sc != null)
				em.remove(sc);
		} catch (IllegalStateException e) {
			e.printStackTrace();

		}

	}
	
	@Override
	public void delete(IScript script) {
		try {
			ScriptEntity entity = getEntity(script);
			em.remove(em.merge(entity));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public IScript getById(int id) {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("ScriptEntity.findById", ScriptEntity.class)
				.setParameter("param", id);
		ScriptEntity entity = query.getResultList().get(0);
		return new ScriptProxy(entity);
	}

}
