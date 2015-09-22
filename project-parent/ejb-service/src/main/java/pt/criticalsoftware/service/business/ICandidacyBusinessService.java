package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;

public interface ICandidacyBusinessService {

	List<ICandidacy> getAllCandidacies();
	
	ICandidacy createCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException;

	List<ICandidacy> getSearchedCandidaciesAdmin(String param);

	ICandidacy assignCandidacy(ICandidacy cand) throws UniqueConstraintException;

	List<ICandidacy> getSearchedDatesCandidaciesAdmin(LocalDate date);

	List<ICandidacy> getManagerCandidacies(Integer id);

	List<ICandidacy> getSearchedDatesCandidaciesManager(LocalDate date, Integer currentUserID);

	void updateEntity(ICandidacy candidacy);

	void deleteCandidacy(ICandidacy candidacy);

	List<IInterview> getCandidacyInterviews(Integer integer);

	ICandidacy getCandidacyById(int id);

	void updateMultipleRejected(List<ICandidacy> rejectedCandidacies);

	void updateMultipleAccepted(List<ICandidacy> acceptedCandidacies);
	
}
