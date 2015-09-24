package pt.criticalsoftware.service.persistence;

import java.time.LocalDate;
import java.util.List;

import pt.criticalsoftware.service.model.IToken;

public interface ITokenPersistenceService {

	String getEmail(String token);

	LocalDate getDate(String token);

	IToken findByToken(String token);

	IToken create(IToken token);

	void delete(String token);

	List<IToken> getAllTokens();

}
