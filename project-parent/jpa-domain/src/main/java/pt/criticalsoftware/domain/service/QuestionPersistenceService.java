package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.entities.QuestionEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.PositionProxy;
import pt.criticalsoftware.domain.proxies.QuestionProxy;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IQuestion;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.IQuestionPersistenceService;

@Stateless
public class QuestionPersistenceService implements IQuestionPersistenceService{

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;


	@Override
	public IQuestion create(IQuestion question)  {
		QuestionEntity entity;
		try {
			entity = getEntity(question);
			em.persist(entity);
			return new QuestionProxy(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private QuestionEntity getEntity(IQuestion question)throws IllegalStateException{
		if (question instanceof IEntityAware<?>){
			return ((IEntityAware<QuestionEntity>) question).getEntity();
		}
		throw new IllegalStateException();
	}

	@SuppressWarnings("unchecked")
	private ScriptEntity getEntityScr(IScript script)throws IllegalStateException{
		if (script instanceof IEntityAware<?>){
			return ((IEntityAware<ScriptEntity>) script).getEntity();
		}
		throw new IllegalStateException();
	}

	@Override
	public List<IQuestion> getAllQuestionsByScript(IScript script){
		try {

			ScriptEntity scriptEnt=getEntityScr(script);
			TypedQuery<QuestionEntity> query = em.createNamedQuery("QuestionEntity.getAllQuestionsByScript", QuestionEntity.class)
					.setParameter("script", scriptEnt);
			List<QuestionEntity> entities = query.getResultList();
			List<IQuestion> proxies = new ArrayList<>();
			for (QuestionEntity qe : entities) {
				QuestionProxy questionProxy = new QuestionProxy(qe);
				proxies.add(questionProxy);
			}
			return proxies;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}



	}

	@Override
	public IQuestion createNewQuestion(IQuestion question, IScript script) {
		QuestionEntity entity;
		ScriptEntity scriptEnt;
		try {
			entity = getEntity(question);
			scriptEnt= getEntityScr(script);
			entity.setScript(scriptEnt);
			em.persist(entity);
			return new QuestionProxy(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public void delete(IQuestion question) {
		QuestionEntity entity;
		try {
			entity = getEntity(question);
			QuestionEntity qt = em.find(QuestionEntity.class, entity.getId());
			if (qt!=null)
				em.remove(qt);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(IQuestion question, Integer id) {
		QuestionEntity entity;
		try {
			entity = getEntity(question);
			em.merge(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
	}
}
