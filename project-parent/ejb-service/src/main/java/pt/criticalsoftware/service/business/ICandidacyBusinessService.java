package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;

public interface ICandidacyBusinessService {

	List<ICandidacy> getAllCandidacies();

	void createCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException;

	List<ICandidacy> getSearchedCandidaciesAdmin(String param);

}
