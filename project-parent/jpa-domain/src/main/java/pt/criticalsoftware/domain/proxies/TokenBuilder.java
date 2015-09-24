package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import javax.ejb.Stateless;

import pt.criticalsoftware.service.candidate.utils.TokenGenerator;
import pt.criticalsoftware.service.model.IToken;
import pt.criticalsoftware.service.model.ITokenBuilder;

@Stateless
public class TokenBuilder implements ITokenBuilder {

	private TokenProxy token;

	public TokenBuilder() {
		token = new TokenProxy();
	}
	
	public TokenBuilder(String email) {
		token = new TokenProxy();
		email(email);
		generateToken();
		expiringDate();
	}

	@Override
	public ITokenBuilder email(String email) {
		token.setEmail(email);
		return this;
	}

	private void generateToken() {
		TokenGenerator generator = new TokenGenerator();
		token.setToken(generator.getToken());
	}

	private void expiringDate() {
		LocalDate nowDate = LocalDate.now();
		token.setExpiringDate(nowDate.plusDays(1));
	}

	@Override
	public IToken build() {
		generateToken();
		expiringDate();
		return token;
	}

}
