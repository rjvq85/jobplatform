package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.criticalsoftware.domain.entities.CandidacyEntity;
import pt.criticalsoftware.domain.proxies.CandidacyProxy;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.persistence.ICandidacyPersistenceService;

@Stateless
public class CandidacyPersistenceService implements ICandidacyPersistenceService {

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public List<ICandidacy> getAllCandidacies() {
		List<ICandidacy> candidacies = new ArrayList<>();
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.findAll",CandidacyEntity.class);
		List<CandidacyEntity> entities = query.getResultList();
		for (CandidacyEntity ce:entities) {
			candidacies.add(new CandidacyProxy(ce));
		}
		return candidacies;
	}

	@Override
	public void newCandidacy(ICandidacy icandidacy) {
		CandidacyEntity entity;
		try {
			entity = getEntity(icandidacy);
			em.merge(entity);
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

}
