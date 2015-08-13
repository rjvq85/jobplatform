package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.PositionProxy;
import pt.criticalsoftware.domain.proxies.UserProxy;
import pt.criticalsoftware.service.exceptions.DuplicateEmailException;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.IPositionPersistenceService;

@Stateless
public class PositionPersistenceService implements IPositionPersistenceService{

	private final Logger logger = LoggerFactory.getLogger(PositionPersistenceService.class);
	
	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;
	
	@Override
	public List<IPosition> getAllPositions() {
		TypedQuery<PositionEntity> query=em.createNamedQuery("Position.getAll", PositionEntity.class);
		List<PositionEntity> entities=query.getResultList();
		List<IPosition> proxies=new ArrayList<>();
		for(PositionEntity pe:entities){
			PositionProxy positionProxy= new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		return proxies;
	}
	
	@Override
	public void verifyReference(String reference) throws DuplicateReferenceException {
		
		TypedQuery<PositionEntity> q = em.createNamedQuery("Position.verifyReference",PositionEntity.class)
				.setParameter("reference", reference);
		try {
			q.getSingleResult().getReference();
			throw new DuplicateReferenceException("A referência "+reference+" já existe!");
		} catch (NoResultException nre) {
			logger.info("A referência "+reference+" está disponível!");
		}
	}
	
	@Override
	public IPosition create(IPosition position) {
		PositionEntity entity;
		try {
			entity = getEntity(position);
			em.persist(entity);
			return new PositionProxy(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public PositionEntity getEntity(IPosition position) throws IllegalStateException {
		if (position instanceof IEntityAware<?>) {
			return ((IEntityAware<PositionEntity>) position).getEntity();
		}
		throw new IllegalStateException();
	}

	@Override
	public IPosition update(IPosition position) {
		PositionEntity entity;
		try {
			entity = getEntity(position);
			em.merge(entity);
			return new PositionProxy(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public IPosition delete(IPosition position) {
		PositionEntity entity;
		try {
			entity = getEntity(position);
			PositionEntity pos = em.find(PositionEntity.class, entity.getId());
	        if (pos != null) {
	            em.remove(pos);
	        }
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
	}
}
