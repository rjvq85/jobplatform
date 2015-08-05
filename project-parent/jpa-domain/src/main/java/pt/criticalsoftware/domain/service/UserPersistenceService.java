package pt.criticalsoftware.domain.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import pt.criticalsoftware.domain.entities.NotificationEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.UserProxy;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.model.dtos.DozerMapperSingl;
import pt.criticalsoftware.service.model.dtos.NotificationDTO;
import pt.criticalsoftware.service.model.dtos.UserDTO;
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
	
	@Override
	public Collection<IUser> getAllUsers() {
		TypedQuery<UserEntity> query = em.createNamedQuery("User.getAll", UserEntity.class);
		Collection<UserEntity> entities = query.getResultList();
		Collection<IUser> proxies = new ArrayList<>();
		for (UserEntity ue : entities) {
			UserDTO u = DozerMapperSingl.dozerMapper.map(ue, UserDTO.class);
			UserProxy up = new UserProxy(ue);
			up.setRoles(u.getRoles());
			proxies.add(new UserProxy(ue));
		}
		return proxies;
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

	@Override
	public void saveUsers(Collection<IUser> users) {
		UserEntity entity;
		for (IUser user : users) {
			try {
				entity = getEntity(user);
				em.merge(entity);
			} catch (IllegalStateException e) {
				System.out.println("Falhou ao fazer update");
				e.printStackTrace();
			}
		}
	}

	
	
}
