package pt.criticalsoftware.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.ScriptEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.InterviewProxy;
import pt.criticalsoftware.domain.proxies.ScriptProxy;
import pt.criticalsoftware.domain.proxies.UserProxy;
import pt.criticalsoftware.domain.utils.GenerateReferenceValue;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IInterviewPersistenceService;

@Stateless
public class InterviewPersistenceService implements IInterviewPersistenceService {

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public List<IInterview> getAll() {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findAll", InterviewEntity.class);
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> interviews = new ArrayList<>();
		for (InterviewEntity ie : entities) {
			interviews.add(new InterviewProxy(ie));
		}
		return interviews;
	}

	@Override
	public List<IInterview> getByInterviewer(Integer id) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findByInterviewer", InterviewEntity.class)
				.setParameter("param", id);
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> interviews = new ArrayList<>();
		for (InterviewEntity ie : entities) {
			interviews.add(new InterviewProxy(ie));
		}
		return interviews;
	}

	@Override
	public List<IInterview> getByReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IInterview> getByPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IInterview> getByDate(LocalDate date) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findByDate", InterviewEntity.class)
				.setParameter("param", date);
		List<IInterview> interviews = new ArrayList<>();
		List<InterviewEntity> entities = query.getResultList();
		for (InterviewEntity ie : entities) {
			interviews.add(new InterviewProxy(ie));
		}
		return interviews;
	}

	@Override
	public List<IInterview> getByDate(LocalDate date, Object id) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.findByDate", InterviewEntity.class)
				.setParameter("param", date);
		List<IInterview> interviews = Collections.synchronizedList(new ArrayList<IInterview>());
		List<InterviewEntity> entities = query.getResultList();
		for (InterviewEntity ie : entities) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (UserEntity interviewer : ie.getInterviewers()) {
						if (interviewer.getId() == (Integer) id)
							interviews.add(new InterviewProxy(ie));
					}
				}
			}).start();
		}
		return interviews;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IInterview create(IInterview entity) {
		if (entity instanceof IEntityAware<?>) {
			InterviewEntity ent = em.merge(((IEntityAware<InterviewEntity>) entity).getEntity());
			ent.setInterviewRef(GenerateReferenceValue.genReference("I",ent.getId()));
			return new InterviewProxy(em.merge(ent));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IInterview update(IInterview selectedInterview) {
		if (selectedInterview instanceof IEntityAware<?>) {
			System.out.println("\n\n\n ENTROU PARA O UPDATE \n\n\n");
			InterviewProxy ip = new InterviewProxy(em.merge(((IEntityAware<InterviewEntity>) selectedInterview).getEntity()));
			em.flush();
			return ip;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(IInterview selectedInterview) {
		if (selectedInterview instanceof IEntityAware<?>) {
			em.remove(em.merge(((IEntityAware<InterviewEntity>) selectedInterview).getEntity()));
		}
	}
	
	@Override
	public List<IUser> getAvailableInterviewers(Integer id){
		TypedQuery<UserEntity> query = em.createNamedQuery("Interview.availableInterviewers",UserEntity.class)
				.setParameter("param", id);
		List<UserEntity> entities = query.getResultList();
		List<IUser> users = new ArrayList<>();
		for (UserEntity ue:entities) {
			users.add(new UserProxy(ue));
		}
		return users;
	}

	@Override
	public List<IScript> getAvailableScripts(Integer id) {
		TypedQuery<ScriptEntity> query = em.createNamedQuery("Interview.availableScripts",ScriptEntity.class)
				.setParameter("param", id);
		List<ScriptEntity> entities = query.getResultList();
		List<IScript> scripts = new ArrayList<>();
		for (ScriptEntity script : entities) {
			scripts.add(new ScriptProxy(script));
		}
		return scripts;
	}

	@Override
	public List<IInterview> getByCandidate(ICandidate candidate) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.byCandidate",InterviewEntity.class)
				.setParameter("param", candidate.getId());
		List<InterviewEntity> entities = query.getResultList();
		List<IInterview> intervs = new ArrayList<>();
		for (InterviewEntity entity : entities) {
			intervs.add(new InterviewProxy(entity));
		}
		return intervs;
	}

	public List<IInterview> getInterviewsByDatePeriod(LocalDate dateInit,
			LocalDate dateFinal) {
		TypedQuery<InterviewEntity> query = em.createNamedQuery("Interview.searchByPeriodDate", InterviewEntity.class)
				.setParameter("initDate", dateInit)
				.setParameter("finalDate", dateFinal);
		List<IInterview> interviews = new ArrayList<>();
		List<InterviewEntity> entities = query.getResultList();
		for (InterviewEntity ie : entities) {
			interviews.add(new InterviewProxy(ie));
		}
		return interviews;
	}

	@Override
	public IInterview find(Integer id) {
		return new InterviewProxy(em.find(InterviewEntity.class, id));
	}
}
