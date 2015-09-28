package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.model.IToken;

public interface ITokenBusinessService {

	IToken getToken(String token);

	void createToken(IToken builtToken);

	List<IToken> getAll();

	void remove(String token);

}
