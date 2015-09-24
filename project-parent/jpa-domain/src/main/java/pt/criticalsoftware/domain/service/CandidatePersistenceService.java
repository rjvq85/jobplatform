package pt.criticalsoftware.domain.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.domain.proxies.CandidateProxy;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.persistence.ICandidatePersistenceService;

@Stateless
public class CandidatePersistenceService implements ICandidatePersistenceService {
	
	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;
	
	@Override
	public ICandidate findByUsername(String username) {
		try {
			TypedQuery<CandidateEntity> query = em.createNamedQuery("Candidate.findByUsername",CandidateEntity.class)
					.setParameter("param", username);
			return new CandidateProxy(query.getSingleResult());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ICandidate findById(Integer id) {
		TypedQuery<CandidateEntity> query = em.createNamedQuery("Candidate.findById",CandidateEntity.class)
				.setParameter("candidateId", id);
		return new CandidateProxy(query.getSingleResult());
	}

	@Override
	public ICandidate findByEmail(String email) {
		TypedQuery<CandidateEntity> query = em.createNamedQuery("Candidate.findByEmail",CandidateEntity.class)
				.setParameter("param", email);
		List<CandidateEntity> candidates = query.getResultList();
		if (candidates.size() == 1) {
			return new CandidateProxy(candidates.get(0));
		}
		return null;
	}

	@Override
	public ICandidate create(ICandidate candidate) {
		try {
			CandidateEntity entity = getEntity(candidate);
			return new CandidateProxy(em.merge(entity));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private CandidateEntity getEntity(ICandidate cand) throws IllegalStateException {
		if (cand instanceof IEntityAware<?>) {
			return ((IEntityAware<CandidateEntity>) cand).getEntity();
		}
		throw new IllegalStateException();
	}

	@Override
	public void update(ICandidate candidate) {
		CandidateEntity entity;
		try {
			entity = getEntity(candidate);
			em.merge(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}


}
