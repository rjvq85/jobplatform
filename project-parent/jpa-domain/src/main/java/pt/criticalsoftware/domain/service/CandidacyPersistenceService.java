package pt.criticalsoftware.domain.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.exception.ConstraintViolationException;

import pt.criticalsoftware.domain.entities.CandidacyEntity;
import pt.criticalsoftware.domain.proxies.CandidacyProxy;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.persistence.ICandidacyPersistenceService;

@Stateless
public class CandidacyPersistenceService implements ICandidacyPersistenceService {

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public List<ICandidacy> getAllCandidacies() {
		List<ICandidacy> candidacies = new ArrayList<>();
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.findAll", CandidacyEntity.class);
		List<CandidacyEntity> entities = query.getResultList();
		for (CandidacyEntity ce : entities) {
			candidacies.add(new CandidacyProxy(ce));
		}
		return candidacies;
	}

	@Override
	public void newCandidacy(ICandidacy icandidacy) throws DuplicateCandidateException {
		CandidacyEntity entity;
		try {
			entity = getEntity(icandidacy);
			if ((Long) em.createNamedQuery("Candidate.findDuplicateByUsername")
					.setParameter("param", icandidacy.getCandidate().getUsername().toUpperCase()).getSingleResult() < 1
					&& (Long) em.createNamedQuery("Candidate.findDuplicateByEmail")
							.setParameter("param", icandidacy.getCandidate().getEmail().toUpperCase())
							.getSingleResult() < 1) {
				em.merge(entity);
			} else
				throw new DuplicateCandidateException("Username / E-mail já existente");
		} catch (IllegalStateException ise) {

		}
	}

	@SuppressWarnings("unchecked")
	private CandidacyEntity getEntity(ICandidacy candidacy) {
		if (candidacy instanceof IEntityAware<?>) {
			return ((IEntityAware<CandidacyEntity>) candidacy).getEntity();
		}

		throw new IllegalStateException();
	}

	@Override
	public List<ICandidacy> searchAdminCandidacies(String param) {
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.search", CandidacyEntity.class)
				.setParameter("param", param);
		List<ICandidacy> results = new ArrayList<>();
		for (CandidacyEntity ce : query.getResultList()) {
			results.add(new CandidacyProxy(ce));
		}
		return results;
	}

	@Override
	public void assignCandidacy(ICandidacy cand) throws UniqueConstraintException {
		CandidacyEntity candidacy;
		try {
			candidacy = getEntity(cand);
			Integer positionId = cand.getPositionCandidacy().getId();
			Integer candidateId = cand.getCandidate().getId();
			if ((Long) em.createNamedQuery("Candidacy.uniqueConstraintViolation")
					.setParameter("positionId", positionId)
					.setParameter("candidateId", candidateId).getSingleResult() > 0) throw new UniqueConstraintException("O candidato já tem uma candidatura associada a esta posição.");
			em.merge(candidacy);
		} catch (IllegalStateException ies) {
			// log
		}

	}

	@Override
	public List<ICandidacy> searchAdminCandidaciesDate(LocalDate localDate) {
		List<ICandidacy> icand = new ArrayList<>();
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.searchDate",CandidacyEntity.class)
				.setParameter("param", localDate);
		List<CandidacyEntity> ce = query.getResultList();
		for (CandidacyEntity c:ce) {
			icand.add(new CandidacyProxy(c));
		}
		return icand;
	}
	
	@Override
	public List<ICandidacy> searchManagerCandidaciesDate(LocalDate localDate, Integer id) {
		List<ICandidacy> icand = new ArrayList<>();
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.searchDate",CandidacyEntity.class)
				.setParameter("param", localDate);
		List<CandidacyEntity> ce = query.getResultList();
		for (CandidacyEntity c:ce) {
			if (c.getPositionCandidacy().getResponsable().getId() == id) icand.add(new CandidacyProxy(c));
		}
		return icand;
	}

	@Override
	public List<ICandidacy> getManagerCandidacies(Integer id) {
		TypedQuery<CandidacyEntity> query = em.createNamedQuery("Candidacy.manager",CandidacyEntity.class)
				.setParameter("param", id);
		List<CandidacyEntity> entitiesList = query.getResultList();
		List<ICandidacy> interfaceList = new ArrayList<>();
		for (CandidacyEntity ce:entitiesList) {
			interfaceList.add(new CandidacyProxy(ce));
		}
		return interfaceList;
	}

}
