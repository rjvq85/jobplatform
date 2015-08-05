package pt.criticalsoftware.service.persistence;

import pt.criticalsoftware.service.model.INotification;

public interface INotificationPersistenceService {

	INotification createNotification(INotification notif);

}
