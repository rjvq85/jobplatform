package pt.criticalsoftware.domain.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.PositionProxy;
import pt.criticalsoftware.domain.utils.GenerateReferenceValue;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.IPositionPersistenceService;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

@Stateless
public class PositionPersistenceService implements IPositionPersistenceService {

	private final Logger logger = LoggerFactory.getLogger(PositionPersistenceService.class);

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public IPosition find(Object id) {
		return new PositionProxy(em.find(PositionEntity.class, id));
	}

	@Override
	public List<IPosition> getAllPositions() {
		TypedQuery<PositionEntity> query = em.createNamedQuery("Position.getAll", PositionEntity.class);
		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		return proxies;
	}

	@Override
	public void verifyReference(String reference) throws DuplicateReferenceException {

		TypedQuery<PositionEntity> q = em.createNamedQuery("Position.verifyReference", PositionEntity.class)
				.setParameter("reference", reference);
		try {
			q.getSingleResult().getReference();
			throw new DuplicateReferenceException("A referência " + reference + " já existe!");
		} catch (NoResultException nre) {
			logger.info("A referência " + reference + " está disponível!");
		}
	}

	@Override
	public IPosition create(IPosition position) {
		try {
			PositionEntity entity = getEntity(position);
			em.persist(entity);
			entity.setReference(GenerateReferenceValue.genReference("P", entity.getId()));
			PositionEntity mergedPosition = em.merge(entity);
			return new PositionProxy(mergedPosition);
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
	public List<IPosition> getPositionsByWord(String positionWord, String searchCode) {

		TypedQuery<PositionEntity> query = null;

		if (positionWord.equals("Código"))
			query = em.createNamedQuery("Position.getPositionsByReference", PositionEntity.class)
					.setParameter("reference", searchCode);
		else if (positionWord.equals("Título"))
			query = em.createNamedQuery("Position.getPositionsByTitle", PositionEntity.class).setParameter("title",
					searchCode);
		else if (positionWord.equals("Localizacao"))
			query = em.createNamedQuery("Position.getPositionsByLocale", PositionEntity.class).setParameter("locale",
					searchCode);
		else if (positionWord.equals("Estado")) {
			PositionState state = null;
			if (searchCode.equalsIgnoreCase("aberta"))
				state = PositionState.ABERTA;
			else if (searchCode.equalsIgnoreCase("fechada"))
				state = PositionState.FECHADA;
			else if (searchCode.equalsIgnoreCase("em espera"))
				state = PositionState.EM_ESPERA;
			query = em.createNamedQuery("Position.getPositionsByState", PositionEntity.class).setParameter("state",
					state);
		} else if (positionWord.equals("Empresa"))
			query = em.createNamedQuery("Position.getPositionsByCompany", PositionEntity.class).setParameter("company",
					searchCode);
		else if (positionWord.equals("Área Técnica")) {
			TechnicalAreaType area = null;
			if (positionWord.equals("SSPA"))
				area = TechnicalAreaType.SSPA;
			else if (positionWord.equalsIgnoreCase(".Net Development"))
				area = TechnicalAreaType.NET_DEVELOPMENT;
			else if (positionWord.equalsIgnoreCase("Java Development"))
				area = TechnicalAreaType.JAVA_DEVELOPMENT;
			else if (positionWord.equalsIgnoreCase("Safety Critical"))
				area = TechnicalAreaType.SAFETY_CRITICAL;
			else if (positionWord.equalsIgnoreCase("Project Management"))
				area = TechnicalAreaType.PROJECT_MANAGEMENT;
			else if (positionWord.equalsIgnoreCase("Integration"))
				area = TechnicalAreaType.INTEGRATION;
			query = em.createNamedQuery("Position.getPositionsByTechnicalArea", PositionEntity.class)
					.setParameter("technicalArea", area);
		}
		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		return proxies;
	}

	@Override
	public List<IPosition> getManagerPositions(Integer currentUserID) {
		TypedQuery<PositionEntity> query = em.createNamedQuery("Position.getPositionByManager", PositionEntity.class)
				.setParameter("param", currentUserID);
		List<PositionEntity> entities = query.getResultList();
		List<IPosition> interfaces = new ArrayList<>();
		for (PositionEntity p : entities) {
			interfaces.add(new PositionProxy(p));
		}
		return interfaces;
	}

	public List<IPosition> getPositionsByDate(String positionWord, Date closeDate) {
		TypedQuery<PositionEntity> query = null;
		query = em.createNamedQuery("Position.getPositionsByDate", PositionEntity.class).setParameter("closeDate",
				closeDate);

		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		return proxies;

	}

	@Override
	public List<IPosition> getPositionsByKeyWords(String positionWord, String searchCode) {

		boolean aux = false;
		TypedQuery<PositionEntity> query = null;
		if (searchCode.equalsIgnoreCase("aberta") || searchCode.equalsIgnoreCase("fechada")
				|| searchCode.equalsIgnoreCase("em espera")) {
			PositionState state = null;
			aux = true;
			if (searchCode.equalsIgnoreCase("aberta"))
				state = PositionState.ABERTA;
			else if (searchCode.equalsIgnoreCase("fechada"))
				state = PositionState.FECHADA;
			else if (searchCode.equalsIgnoreCase("em espera"))
				state = PositionState.EM_ESPERA;

			query = em.createNamedQuery("Position.getPositionsByState", PositionEntity.class).setParameter("state",
					state);
		} else if (searchCode.equalsIgnoreCase("SSPA") || searchCode.equalsIgnoreCase(".Net Development")
				|| searchCode.equalsIgnoreCase("Java Development") || searchCode.equalsIgnoreCase("Safety Critical")
				|| searchCode.equalsIgnoreCase("Project Management") || searchCode.equalsIgnoreCase("Integration")) {
			TechnicalAreaType area = null;
			aux = true;
			if (searchCode.equalsIgnoreCase("SSPA"))
				area = TechnicalAreaType.SSPA;
			else if (searchCode.equalsIgnoreCase(".Net Development"))
				area = TechnicalAreaType.NET_DEVELOPMENT;
			else if (searchCode.equalsIgnoreCase("Java Development"))
				area = TechnicalAreaType.JAVA_DEVELOPMENT;
			else if (searchCode.equalsIgnoreCase("Safety Critical"))
				area = TechnicalAreaType.SAFETY_CRITICAL;
			else if (searchCode.equalsIgnoreCase("Project Management"))
				area = TechnicalAreaType.PROJECT_MANAGEMENT;
			else if (searchCode.equalsIgnoreCase("Integration"))
				area = TechnicalAreaType.INTEGRATION;
			query = em.createNamedQuery("Position.getPositionsByTechnicalArea", PositionEntity.class)
					.setParameter("technicalArea", area);
		}
		if (aux) {
			List<PositionEntity> entities = query.getResultList();
			List<IPosition> proxies = new ArrayList<>();
			for (PositionEntity pe : entities) {
				PositionProxy positionProxy = new PositionProxy(pe);
				proxies.add(positionProxy);
			}
			return proxies;
		} else {
			TypedQuery<PositionEntity> query1 = em
					.createNamedQuery("Position.getPositionsByReference", PositionEntity.class)
					.setParameter("reference", searchCode);
			TypedQuery<PositionEntity> query2 = em
					.createNamedQuery("Position.getPositionsByTitle", PositionEntity.class)
					.setParameter("title", searchCode);
			TypedQuery<PositionEntity> query3 = em
					.createNamedQuery("Position.getPositionsByLocale", PositionEntity.class)
					.setParameter("locale", searchCode);
			TypedQuery<PositionEntity> query4 = em
					.createNamedQuery("Position.getPositionsByCompany", PositionEntity.class)
					.setParameter("company", searchCode);
			List<PositionEntity> entities1 = query1.getResultList();
			List<PositionEntity> entities2 = query2.getResultList();
			List<PositionEntity> entities3 = query3.getResultList();
			List<PositionEntity> entities4 = query4.getResultList();
			if (entities1.size() >= 1) {
				List<IPosition> proxies1 = new ArrayList<>();
				for (PositionEntity pe : entities1) {
					PositionProxy positionProxy = new PositionProxy(pe);
					proxies1.add(positionProxy);
				}
				aux = true;
				return proxies1;
			}
			if (entities2.size() >= 1) {
				List<IPosition> proxies2 = new ArrayList<>();
				for (PositionEntity pe : entities2) {
					PositionProxy positionProxy = new PositionProxy(pe);
					proxies2.add(positionProxy);
				}
				aux = true;
				return proxies2;
			}
			if (entities3.size() >= 1) {
				List<IPosition> proxies3 = new ArrayList<>();
				for (PositionEntity pe : entities3) {
					PositionProxy positionProxy = new PositionProxy(pe);
					proxies3.add(positionProxy);
				}
				aux = true;
				return proxies3;
			}
			if (entities4.size() >= 1) {
				List<IPosition> proxies4 = new ArrayList<>();
				for (PositionEntity pe : entities4) {
					PositionProxy positionProxy = new PositionProxy(pe);
					proxies4.add(positionProxy);
				}
				aux = true;
				return proxies4;
			}
		}

		if (!aux) {
			LocalDate openDate = LocalDate.parse(searchCode);
			List<IPosition> entities = getPositionsByOpenDate(positionWord, openDate);
			Date ld = convertStringToDate(searchCode);
			List<IPosition> entities1 = getPositionsByDate(positionWord, ld);
			entities.addAll(entities1);
			return entities;
		}
		return null;
	}

	@Override
	public List<IPosition> getPositionsByOpenDate(String positionWord, LocalDate openDate) {
		TypedQuery<PositionEntity> query = null;
		query = em.createNamedQuery("Position.getPositionsByOpenDate", PositionEntity.class).setParameter("openDate",
				openDate);

		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		logger.info("tamanho" + proxies.size());
		return proxies;

	}

	@Override
	public List<IPosition> getPositionsByLocaleAndArea(String locale, String technicalAreaStr) {

		TypedQuery<PositionEntity> query = null;
		TechnicalAreaType area = null;

		if (technicalAreaStr.equalsIgnoreCase("SSPA"))
			area = TechnicalAreaType.SSPA;
		else if (technicalAreaStr.equalsIgnoreCase(".Net Development"))
			area = TechnicalAreaType.NET_DEVELOPMENT;
		else if (technicalAreaStr.equalsIgnoreCase("Java Development"))
			area = TechnicalAreaType.JAVA_DEVELOPMENT;
		else if (technicalAreaStr.equalsIgnoreCase("Safety Critical"))
			area = TechnicalAreaType.SAFETY_CRITICAL;
		else if (technicalAreaStr.equalsIgnoreCase("Project Management"))
			area = TechnicalAreaType.PROJECT_MANAGEMENT;
		else if (technicalAreaStr.equalsIgnoreCase("Integration"))
			area = TechnicalAreaType.INTEGRATION;

		query = em.createNamedQuery("Position.getPositionsByLocaleAndArea", PositionEntity.class)
				.setParameter("locale", locale).setParameter("technicalArea", area);

		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}

		return proxies;
	}

	@Override
	public List<IPosition> getPositionsByLocale(String locale) {
		TypedQuery<PositionEntity> query = null;
		query = em.createNamedQuery("Position.getPositionsByLocale", PositionEntity.class).setParameter("locale",
				locale);

		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}

		return proxies;
	}

	@Override
	public List<IPosition> getPositionsByTechnicalArea(String technicalArea) {
		TypedQuery<PositionEntity> query = null;
		TechnicalAreaType area = null;

		if (technicalArea.equalsIgnoreCase("SSPA"))
			area = TechnicalAreaType.SSPA;
		else if (technicalArea.equalsIgnoreCase(".Net Development"))
			area = TechnicalAreaType.NET_DEVELOPMENT;
		else if (technicalArea.equalsIgnoreCase("Java Development"))
			area = TechnicalAreaType.JAVA_DEVELOPMENT;
		else if (technicalArea.equalsIgnoreCase("Safety Critical"))
			area = TechnicalAreaType.SAFETY_CRITICAL;
		else if (technicalArea.equalsIgnoreCase("Project Management"))
			area = TechnicalAreaType.PROJECT_MANAGEMENT;
		else if (technicalArea.equalsIgnoreCase("Integration"))
			area = TechnicalAreaType.INTEGRATION;

		query = em.createNamedQuery("Position.getPositionsByTechnicalArea", PositionEntity.class)
				.setParameter("technicalArea", area);

		List<PositionEntity> entities = query.getResultList();
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}

		return proxies;
	}

	@Override
	public List<IPosition> getPositionsByLast() {
		TypedQuery<PositionEntity> query = null;

		query = em.createNamedQuery("Position.getLastPositions", PositionEntity.class);

		List<PositionEntity> entities = query.getResultList();
		logger.info("Tamanho" + entities.size());
		List<IPosition> proxies = new ArrayList<>();
		for (PositionEntity pe : entities) {
			PositionProxy positionProxy = new PositionProxy(pe);
			proxies.add(positionProxy);
		}
		return proxies;
	}

	private Date convertStringToDate(String dateString) {
		String expectedPattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		try {
			String dateInput = dateString;
			Date date = formatter.parse(dateInput);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<IPosition> getAllOpenPositions() {
		TypedQuery<PositionEntity> query = em.createNamedQuery("Position.getAllOpen",PositionEntity.class);
		List<PositionEntity> entities = query.getResultList();
		List<IPosition> positions = new ArrayList<>();
		entities.stream().forEach(entity -> positions.add(new PositionProxy(entity)));
		return positions;
	}
}
