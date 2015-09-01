package pt.criticalsoftware.service.business;

import pt.criticalsoftware.service.model.ICandidate;

public interface ICandidateBusinessService {

	ICandidate getCandidateByUsername(String username);
	
	ICandidate getCandidateById(Integer id);

}
