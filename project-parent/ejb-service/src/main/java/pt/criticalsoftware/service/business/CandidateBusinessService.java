package pt.criticalsoftware.service.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sun.syndication.io.impl.Base64;

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

	@Override
	public ICandidate getCandidateByEmail(String email) {
		return persistence.findByEmail(email);
	}

	@Override
	public void updateCandidate(ICandidate candidate) {
		persistence.update(candidate);
	}
	
	@Override
	public void updatePassword(String password, ICandidate candidate) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();
			byte[] data2 = Base64.encode(byteData);
			String securedPassword = new String(data2);
			System.out.println(password);
			System.out.println(candidate);
			candidate.setPassword(securedPassword);
			updateCandidate(candidate);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
