package pt.criticalsoftware.service.persistence;

import java.util.List;

import pt.criticalsoftware.service.model.INotification;

public interface INotificationPersistenceService {

	void create(INotification notif);

	List<INotification> getAllNotifications();

	void create(List<INotification> notif);

	INotification findById(int id);

}
