package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import pt.criticalsoftware.domain.entities.TokenEntity;
import pt.criticalsoftware.service.model.IToken;

public class TokenProxy implements IEntityAware<TokenEntity>, IToken {

	private TokenEntity token;

	public TokenProxy() {
		this(null);
	}

	public TokenProxy(TokenEntity token) {
		this.token = (null != token) ? token : new TokenEntity();
	}

	@Override
	public TokenEntity getEntity() {
		return token;
	}

	@Override
	public String getEmail() {
		return token.getEmail();
	}

	@Override
	public void setEmail(String email) {
		token.setEmail(email);
	}

	@Override
	public String getToken() {
		return token.getToken();
	}

	@Override
	public void setToken(String token) {
		this.token.setToken(token);
	}

	@Override
	public LocalDate getExpiringDate() {
		return token.getExpiringDate();
	}

	@Override
	public void setExpiringDate(LocalDate expiringDate) {
		token.setExpiringDate(expiringDate);
	}

}
