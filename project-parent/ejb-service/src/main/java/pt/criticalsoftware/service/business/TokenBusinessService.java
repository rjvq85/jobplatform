package pt.criticalsoftware.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.IToken;
import pt.criticalsoftware.service.persistence.ITokenPersistenceService;

@Stateless
public class TokenBusinessService implements ITokenBusinessService {
	
	@EJB
	private ITokenPersistenceService persistence;

	@Override
	public IToken getToken(String token) {
		return persistence.findByToken(token);
	}

	@Override
	public void createToken(IToken builtToken) {
		persistence.create(builtToken);
	}

	@Override
	public List<IToken> getAll() {
		return persistence.getAllTokens();
	}

	@Override
	public void remove(String token) {
		persistence.delete(token);
	}
	
	

}
