package pt.criticalsoftware.service.persistence;

import pt.criticalsoftware.service.model.ICandidate;

public interface ICandidatePersistenceService {

	ICandidate findByUsername(String username);

}
