package pt.criticalsoftware.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;

import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.persistence.ICandidacyPersistenceService;

@Stateful
public class CandidacyBusinessService implements ICandidacyBusinessService {
	
	@EJB
	private ICandidacyPersistenceService persistence;

	@Override
	public List<ICandidacy> getAllCandidacies() {
		try {
			return persistence.getAllCandidacies();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public void createCandidacy(ICandidacy icandidacy) {
		persistence.newCandidacy(icandidacy);
	}

}
