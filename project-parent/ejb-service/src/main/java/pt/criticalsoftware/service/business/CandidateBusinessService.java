package pt.criticalsoftware.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.persistence.ICandidatePersistenceService;

@Stateless
public class CandidateBusinessService implements ICandidateBusinessService {
	
	@EJB
	private ICandidatePersistenceService persistence;
	
	@Override
	public ICandidate getCandidateByUsername(String username) {
		return persistence.findByUsername(username);
	}

	@Override
	public ICandidate getCandidateById(Integer id) {
		return persistence.findById(id);
	}
	
	@Override
	public ICandidate addCandidate(ICandidate candidate) {
		return persistence.create(candidate);
	}

}
