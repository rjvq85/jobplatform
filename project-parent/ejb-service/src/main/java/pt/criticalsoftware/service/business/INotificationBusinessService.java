package pt.criticalsoftware.service.business;

import java.util.List;

import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.IUser;

public interface INotificationBusinessService {

	void createNotification(String description, IUser user, String text);

	List<INotification> getAll();

	void createNotification(String description, List<IUser> interviewers, String text);

}
