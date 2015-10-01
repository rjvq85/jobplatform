package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.notifications.IMailSender;
import pt.criticalsoftware.service.persistence.ICandidacyPersistenceService;

@Stateless
public class CandidacyBusinessService implements ICandidacyBusinessService {

	@EJB
	private ICandidacyPersistenceService persistence;

	@EJB
	private IMailSender notif;

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
	public ICandidacy assignCandidacy(ICandidacy cand) throws UniqueConstraintException {
		ICandidacy assigned = persistence.assignCandidacy(cand);
		return assigned;
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

	@Override
	public ICandidacy getCandidacyById(int id) {
		return persistence.find(id);
	}

	@Override
	public void updateMultipleRejected(List<ICandidacy> rejectedCandidacies) {
		persistence.updateMultipleRejected(rejectedCandidacies);
	}

	@Override
	public void updateMultipleAccepted(List<ICandidacy> acceptedCandidacies) {
		persistence.updateMultipleAccepted(acceptedCandidacies);
	}

	public List<ICandidacy> getCandidaciesByDatePeriod(LocalDate initDate, LocalDate finalDate) {
		return persistence.getCandidaciesByDatePeriod(initDate, finalDate);

	}

	@Override
	public List<ICandidacy> getCandidaciesByPosition(Integer positionID) {
		return persistence.getCandidaciesByPosition(positionID);
	}

	@Override
	public List<ICandidacy> getCandidaciesSpontaneousByDatePeriod(LocalDate initDate, LocalDate finalDate) {
		return persistence.getCandidaciesSpontaneousByDatePeriod(initDate, finalDate);
	}

	@Override
	public List<ICandidacy> getNonAdmitedCandidaciesByDatePeriodAndPosition(
			LocalDate dateInit, LocalDate dateFinal, Integer positionID) {
	
		return persistence.getNonAdmitedCandidaciesByDatePeriodAndPosition(dateInit,dateFinal,
				positionID);
	}

	@Override
	public List<ICandidacy> getNonAdmitedCandidaciesByDatePeriod(
			LocalDate dateInit, LocalDate dateFinal) {
		return persistence.getNonAdmitedCandidaciesByDatePeriod(dateInit,dateFinal);
	}
	
	@Override
	public Long getSpontaneousCandidacyByCandidate(Integer id) {
		return persistence.getSpontaneousByCandidate(id);
	}

	@Override
	public ICandidacy createSpontaneousCandidacy(ICandidacy icandidacy) {
		return persistence.newSpontaneousCandidacy(icandidacy);
	}

}
