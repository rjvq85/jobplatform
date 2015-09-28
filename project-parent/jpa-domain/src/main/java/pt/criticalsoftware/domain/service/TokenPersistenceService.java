package pt.criticalsoftware.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.criticalsoftware.domain.entities.TokenEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.TokenProxy;
import pt.criticalsoftware.service.model.IToken;
import pt.criticalsoftware.service.persistence.ITokenPersistenceService;

@Stateless
public class TokenPersistenceService implements ITokenPersistenceService {

	private static final Logger logger = LoggerFactory.getLogger(TokenPersistenceService.class);

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	private TokenEntity getEntity(IToken token) throws IllegalStateException {
		if (token instanceof IEntityAware<?>) {
			return ((IEntityAware<TokenEntity>) token).getEntity();
		}
		throw new IllegalStateException();
	}

	@Override
	public String getEmail(String token) {
		TypedQuery<String> query = em.createNamedQuery("Token.email", String.class).setParameter("param", token);
		List<String> emails = query.getResultList();
		if (emails.size() == 1)
			return emails.get(0);
		return null;
	}

	@Override
	public LocalDate getDate(String token) {
		TypedQuery<LocalDate> query = em.createNamedQuery("Token.date", LocalDate.class).setParameter("param", token);
		List<LocalDate> dates = query.getResultList();
		if (dates.size() == 1)
			return dates.get(0);
		return null;
	}

	@Override
	public IToken findByToken(String token) {
		TypedQuery<TokenEntity> query = em.createNamedQuery("Token.token", TokenEntity.class).setParameter("param",
				token);
		List<TokenEntity> tokens = query.getResultList();
		if (tokens.size() == 1) {
			return new TokenProxy(tokens.get(0));
		}
		return null;
	}

	@Override
	public IToken create(IToken token) {
		try {
			TokenEntity entity = getEntity(token);
			return new TokenProxy(em.merge(entity));
		} catch (IllegalStateException e) {
			logger.error("Erro ao criar token");
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void delete(String token) {
		TypedQuery<TokenEntity> query = em.createNamedQuery("Token.token", TokenEntity.class).setParameter("param",
				token);
		List<TokenEntity> tokens = query.getResultList();
		if (tokens.size() == 1) {
			TokenEntity tokenToDelete = tokens.get(0);
			em.remove(tokenToDelete);
		}
	}

	@Override
	public List<IToken> getAllTokens() {
		List<IToken> tokens = new ArrayList<>();
		TypedQuery<TokenEntity> query = em.createNamedQuery("Token.all",TokenEntity.class);
		List<TokenEntity> entities = query.getResultList();
		entities.stream().forEach(entity -> tokens.add(new TokenProxy(entity)));
		return tokens;
	}

}
