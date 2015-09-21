package pt.criticalsoftware.service.persistence;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;

public interface ICandidacyPersistenceService {

	List<ICandidacy> getAllCandidacies();

	ICandidacy newCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException;

	List<ICandidacy> searchAdminCandidacies(String param);

	ICandidacy assignCandidacy(ICandidacy cand) throws UniqueConstraintException;

	List<ICandidacy> searchAdminCandidaciesDate(LocalDate date);

	List<ICandidacy> getManagerCandidacies(Integer id);

	List<ICandidacy> searchManagerCandidaciesDate(LocalDate localDate, Integer id);

	List<ICandidacy> getCandidaciesByDatePeriod(LocalDate initDate,LocalDate finalDate );

	List<ICandidacy> getCandidaciesSpontaneousByDatePeriod(LocalDate initDate,LocalDate finalDate );
	
	ICandidacy update(ICandidacy entity);

	void delete(ICandidacy candidacy);

	List<IInterview> getInterviews(Integer id);
	
	List<ICandidacy> getCandidaciesByPosition(Integer positionID);

}
