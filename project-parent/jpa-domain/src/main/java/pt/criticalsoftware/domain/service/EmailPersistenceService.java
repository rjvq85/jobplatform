package pt.criticalsoftware.domain.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.IllegalStateException;

import pt.criticalsoftware.domain.entities.EmailEntity;
import pt.criticalsoftware.domain.proxies.EmailProxy;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.service.model.IEmail;
import pt.criticalsoftware.service.persistence.IEmailPersistenceService;

@Stateless
public class EmailPersistenceService implements IEmailPersistenceService {

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public IEmail addSettings(IEmail newSettings) {
		EmailEntity entity;
		if (newSettings.getActive() && (Long) em.createNamedQuery("Email.areActive").getSingleResult() > 0) {
			removeActive();
		}
		entity = getEntity(newSettings);
		em.persist(entity);
		return new EmailProxy(entity);
	}

	@Override
	public IEmail getActiveSettings() {
		TypedQuery<EmailEntity> query = em.createNamedQuery("Email.getActive", EmailEntity.class);
		try {
			EmailEntity entity = query.getResultList().get(0);
			return new EmailProxy(entity);
		} catch (Exception e) {
			return null;
		}
	}

	private void removeActive() {
		EmailEntity entity = em.createNamedQuery("Email.getActive", EmailEntity.class).getSingleResult();
		entity.setActive(false);
		em.merge(entity);
	}

	@SuppressWarnings("unchecked")
	private EmailEntity getEntity(IEmail email) {
		if (email instanceof IEntityAware<?>) {
			return ((IEntityAware<EmailEntity>) email).getEntity();
		}
		return null;
	}

}
