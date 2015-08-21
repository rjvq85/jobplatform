package pt.criticalsoftware.service.business;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;

public interface ICandidacyBusinessService {

	List<ICandidacy> getAllCandidacies();

	void createCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException;

	List<ICandidacy> getSearchedCandidaciesAdmin(String param);

	void assignCandidacy(ICandidacy cand) throws UniqueConstraintException;

	List<ICandidacy> getSearchedDatesCandidaciesAdmin(LocalDate date);

}
