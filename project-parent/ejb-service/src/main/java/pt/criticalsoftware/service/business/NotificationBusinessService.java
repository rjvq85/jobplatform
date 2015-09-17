package pt.criticalsoftware.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.INotificationBuilder;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.INotificationPersistenceService;

@Stateless
public class NotificationBusinessService implements INotificationBusinessService {

	@EJB
	private INotificationPersistenceService persistence;
	@EJB
	private INotificationBuilder builder;

	@Override
	public void createNotification(String description, IUser user, String text) {
		INotification notif = builder.date().receptor(user).situation(description).text(text).build();
		persistence.create(notif);
	}

	@Override
	public List<INotification> getAll() {
		return persistence.getAllNotifications();
	}

	@Override
	public void createNotification(String description, List<IUser> interviewers, String text) {
		List<INotification> notifications = new ArrayList<>();
		interviewers.forEach(interviewer -> notifications
				.add(builder.date().receptor(interviewer).situation(description).text(text).build()));
	}

}
