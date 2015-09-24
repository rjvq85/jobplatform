package pt.criticalsoftware.service.persistence;

import pt.criticalsoftware.service.model.ICandidate;

public interface ICandidatePersistenceService {

	ICandidate findByUsername(String username);

	ICandidate findById(Integer id);

	ICandidate create(ICandidate candidate);

	ICandidate findByEmail(String email);

	void update(ICandidate candidate);

}
