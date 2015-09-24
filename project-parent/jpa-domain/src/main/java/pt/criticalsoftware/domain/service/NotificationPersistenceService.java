package pt.criticalsoftware.domain.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pt.criticalsoftware.domain.entities.NotificationEntity;
import pt.criticalsoftware.domain.proxies.IEntityAware;
import pt.criticalsoftware.domain.proxies.NotificationProxy;
import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.persistence.INotificationPersistenceService;

@Stateless
public class NotificationPersistenceService implements INotificationPersistenceService {

	@PersistenceContext(unitName = "Jobs")
	private EntityManager em;

	@Override
	public void create(INotification notif) {
		try {
			NotificationEntity entity = getEntity(notif);
			em.persist(entity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(List<INotification> notifications) {
		for (INotification notif : notifications) {
			try {
				NotificationEntity entity = getEntity(notif);
				em.persist(entity);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	private NotificationEntity getEntity(INotification notification) throws IllegalStateException {
		if (notification instanceof IEntityAware<?>) {
			return ((IEntityAware<NotificationEntity>) notification).getEntity();
		}

		throw new IllegalStateException();
	}

	@Override
	public List<INotification> getAllNotifications() {
		List<INotification> notifications = new ArrayList<>();
		TypedQuery<NotificationEntity> query = em.createNamedQuery("Notification.getAll", NotificationEntity.class);
		List<NotificationEntity> entities = query.getResultList();
		entities.forEach(entity -> notifications.add(new NotificationProxy(entity)));
		return notifications;
	}

	@Override
	public INotification findById(int id) {
		return new NotificationProxy(em.find(NotificationEntity.class, id));
	}

}
