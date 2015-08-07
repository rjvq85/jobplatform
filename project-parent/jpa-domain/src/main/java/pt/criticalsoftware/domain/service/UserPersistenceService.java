package pt.criticalsoftware.domain.service;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.UserProxy;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateUsernameException;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Stateless
public class UserPersistenceService implements IUserPersistenceService {
	
	private final Logger logger = LoggerFactory.getLogger(UserPersistenceService.class);
	
	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;
	
	@Override
	public Integer getUserId(String username) {
		TypedQuery<Integer> query = em.createNamedQuery("User.findIdByUsername",Integer.class)
				.setParameter("username", username);
		return query.getSingleResult();
		
	}

	@Override
	public List<Role> getRoles(Integer id) {
		TypedQuery<Role> query = em.createNamedQuery("User.getRoles", Role.class)
				.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	public IUser create(IUser user) {
		UserEntity entity;
		try {
			entity = getEntity(user);
			entity = em.merge(entity);
			return new UserProxy(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public UserEntity getEntity(IUser user) throws IllegalStateException {
		if (user instanceof IEntityAware<?>) {
			return ((IEntityAware<UserEntity>) user).getEntity();
		}
		throw new IllegalStateException();
	}

	@Override
	public void verifyEmail(String email) throws DuplicateEmailException {
		TypedQuery<UserEntity> q = em
				.createNamedQuery("User.verifyEmail",UserEntity.class)
				.setParameter("email", email);
		try {
			q.getSingleResult().getEmail();
			throw new DuplicateEmailException("O endereço de e-mail "+email+" não está disponível");
		} catch (NoResultException nre) {
			logger.info("O e-mail "+email+" está disponível para registo");
		}
	}

	@Override
	public void verifyUsername(String username) throws DuplicateUsernameException {
		TypedQuery<UserEntity> q = em.createNamedQuery("User.verifyUsername",UserEntity.class)
				.setParameter("username", username);
		try {
			q.getSingleResult();
			throw new DuplicateUsernameException("O username "+username+" não está disponível");
		} catch (NoResultException nre) {
			logger.info("O username "+username+" está disponível para registo");
		}
	}

	
	
}
