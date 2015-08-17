package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.criticalsoftware.domain.entities.CandidacyEntity;
import pt.criticalsoftware.domain.entities.CandidateEntity;
import pt.criticalsoftware.domain.proxies.CandidacyProxy;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.persistence.ICandidacyPersistenceService;

@Stateless
public class CandidacyPersistenceService implements ICandidacyPersistenceService {

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public List<ICandidacy> getAllCandidacies() {
		List<ICandidacy> candidacies = new ArrayList<>();
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.findAll", CandidacyEntity.class);
		List<CandidacyEntity> entities = query.getResultList();
		for (CandidacyEntity ce : entities) {
			candidacies.add(new CandidacyProxy(ce));
		}
		return candidacies;
	}

	@Override
	public void newCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException {
		CandidacyEntity entity;
		try {
			entity = getEntity(icandidacy);
			if ((Long) em.createNamedQuery("Candidate.findDuplicateByUsername")
					.setParameter("param", icandidacy.getCandidate().getUsername().toUpperCase()).getSingleResult() < 1
					&& (Long) em.createNamedQuery("Candidate.findDuplicateByEmail")
							.setParameter("param", icandidacy.getCandidate().getEmail().toUpperCase())
							.getSingleResult() < 1) {
				em.merge(entity);
			} else throw new DuplicateCandidateException("Username / E-mail já existente");
		} catch (IllegalStateException ise) {

		}
	}

	@SuppressWarnings("unchecked")
	private CandidacyEntity getEntity(ICandidacy candidacy) {
		if (candidacy instanceof IEntityAware<?>) {
			return ((IEntityAware<CandidacyEntity>) candidacy).getEntity();
		}

		throw new IllegalStateException();
	}

	@Override
	public List<ICandidacy> searchAdminCandidacies(String param) {
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.search", CandidacyEntity.class)
				.setParameter("param", param);
		List<ICandidacy> results = new ArrayList<>();
		for (CandidacyEntity ce : query.getResultList()) {
			results.add(new CandidacyProxy(ce));
		}
		return results;
	}

}
