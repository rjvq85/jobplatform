package pt.criticalsoftware.domain.service;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.UserProxy;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IUserPersistenceService;
import pt.criticalsoftware.service.persistence.roles.Role;

@Stateless
public class UserPersistenceService implements IUserPersistenceService {
	
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
	public boolean verifyEmail(String email) {
		Query q = em
				.createQuery("select u.email from UserEntity u where u.email= :email");
		q.setParameter("email", email);
		try {
			String emailTemp = (String) q.getSingleResult();
			//log.error("O mail existe");
			return true;
		} catch (NoResultException e) {
			//log.info("O mail não existe");
			return false;
		}
	}

	
	
}
