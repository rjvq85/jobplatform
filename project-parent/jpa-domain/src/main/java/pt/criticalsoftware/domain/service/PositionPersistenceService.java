package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
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
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;

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

	@Override
	public List<IPosition> getPositionsByWord(String positionWord,String searchCode) {

		TypedQuery<PositionEntity> query = null;

		if (positionWord.equals("Código"))
			query=em.createNamedQuery("Position.getPositionsByReference", PositionEntity.class)
			.setParameter("reference", searchCode);
		else if (positionWord.equals("Título")) 
			query=em.createNamedQuery("Position.getPositionsByTitle", PositionEntity.class)
			.setParameter("title", searchCode);
		else if (positionWord.equals("Estado")) 
			query=em.createNamedQuery("Position.getPositionsByState", PositionEntity.class)
			.setParameter("state", searchCode);
		else if (positionWord.equals("Empresa")) 
			query=em.createNamedQuery("Position.getPositionsByCompany", PositionEntity.class)
			.setParameter("company", searchCode);
		else if (positionWord.equals("Área Técnica")){
			TechnicalAreaType area = null;
			if (positionWord.equals("SSPA"))
				area=TechnicalAreaType.SSPA;
			else if (positionWord.equalsIgnoreCase(".Net Development"))
				area=TechnicalAreaType.NET_DEVELOPMENT;
			else if (positionWord.equalsIgnoreCase("Java Development"))
				area=TechnicalAreaType.JAVA_DEVELOPMENT;
			else if (positionWord.equalsIgnoreCase("Safety Critical"))
				area=TechnicalAreaType.SAFETY_CRITICAL;
			else if (positionWord.equalsIgnoreCase("Project Management"))
				area=TechnicalAreaType.PROJECT_MANAGEMENT;
			else if (positionWord.equalsIgnoreCase("Integration"))
				area=TechnicalAreaType.INETGRATION;
			query=em.createNamedQuery("Position.getPositionsByTechnicalArea", PositionEntity.class)
					.setParameter("technicalArea", area);
		}
		List<PositionEntity> entities=query.getResultList();
		List<IPosition> proxies=new ArrayList<>();
		for(PositionEntity pe:entities){
			PositionProxy positionProxy= new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		return proxies;
	}
}
