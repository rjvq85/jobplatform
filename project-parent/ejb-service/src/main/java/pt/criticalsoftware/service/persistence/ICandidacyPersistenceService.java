package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;

public interface ICandidacyPersistenceService {

	List<ICandidacy> getAllCandidacies();

	void newCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException;

	List<ICandidacy> searchAdminCandidacies(String param);

}
