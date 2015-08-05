package pt.criticalsoftware.domain.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.resource.spi.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.domain.entities.NotificationEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.NotificationProxy;
import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.persistence.INotificationPersistenceService;

public class NotificationPersistenceService implements INotificationPersistenceService {
	
	private final Logger logger = LoggerFactory.getLogger(NotificationPersistenceService.class);
	
	@PersistenceContext(unitName="Jobs")
	private EntityManager em;
	
	@Override
	public INotification createNotification(INotification notif) {
		NotificationEntity nEntity;
		try {
			nEntity = getEntity(notif);
			em.persist(nEntity);
			return new NotificationProxy(nEntity);
		} catch (IllegalStateException ise) {
			logger.error("Erro ao obter entidade do tipo Notificação");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public NotificationEntity getEntity(INotification notif) throws IllegalStateException {
		if (notif instanceof IEntityAware<?>) {
			return ((IEntityAware<NotificationEntity>) notif).getEntity();
		}
		throw new IllegalStateException();
	}

}
