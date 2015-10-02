package pt.criticalsoftware.service.persistence;

import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidate;

public interface ICandidatePersistenceService {

	ICandidate findByUsername(String username);

	ICandidate findById(Integer id);

	ICandidate create(ICandidate candidate) throws DuplicateCandidateException;

	ICandidate findByEmail(String email);

	void update(ICandidate candidate);
	
	void updateEmail(String email,ICandidate candidate);
	
	void updateCV(String filePath, ICandidate candidate);
	
	void deleteUser(ICandidate candidate);

}
