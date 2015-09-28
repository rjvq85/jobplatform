package pt.criticalsoftware.service.business;

import pt.criticalsoftware.service.model.ICandidate;

public interface ICandidateBusinessService {

	ICandidate getCandidateByUsername(String username);
	
	ICandidate getCandidateById(Integer id);

	ICandidate addCandidate(ICandidate candidate);

	ICandidate getCandidateByEmail(String email);

	void updateCandidate(ICandidate candidate);

	void updatePassword(String password, ICandidate candidate);
	
	void updateEmail(String email, ICandidate candidate);
	
	void updateCV(String filePath, ICandidate candidate);
	
	void deleteUser(ICandidate candidate);

}
