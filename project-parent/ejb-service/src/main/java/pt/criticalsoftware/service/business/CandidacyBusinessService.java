package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
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
	public List<ICandidacy> getSearchedCandidaciesAdmin(String param) {
		try {
			return persistence.searchAdminCandidacies(param);
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Override
	public List<ICandidacy> getSearchedDatesCandidaciesAdmin(LocalDate date) {
		try {
			return persistence.searchAdminCandidaciesDate(date);
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public ICandidacy createCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException {
		return persistence.newCandidacy(icandidacy);
	}
	
	@Override
	public void assignCandidacy(ICandidacy cand) throws UniqueConstraintException {
		persistence.assignCandidacy(cand);
	}

	@Override
	public List<ICandidacy> getManagerCandidacies(Integer id) {
		return persistence.getManagerCandidacies(id);
	}

	@Override
	public List<ICandidacy> getSearchedDatesCandidaciesManager(LocalDate date, Integer currentUserID) {
		return persistence.searchManagerCandidaciesDate(date, currentUserID);
	}

	@Override
	public void updateEntity(ICandidacy candidacy) {
		persistence.update(candidacy);
	}

	@Override
	public void deleteCandidacy(ICandidacy candidacy) {
		persistence.delete(candidacy);
	}

	@Override
	public List<IInterview> getCandidacyInterviews(Integer id) {
		return persistence.getInterviews(id);
	}
	
}
