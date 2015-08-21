package pt.criticalsoftware.domain.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.domain.proxies.CandidateProxy;
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

}
