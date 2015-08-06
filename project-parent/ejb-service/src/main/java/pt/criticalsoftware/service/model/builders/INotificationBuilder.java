package pt.criticalsoftware.service.model.builders;

import java.time.LocalDate;

import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.IUser;

public interface INotificationBuilder {

	INotificationBuilder date(LocalDate date);

	INotificationBuilder receptor(IUser user);

	INotificationBuilder situation(String situation);

	INotificationBuilder text(String text);

	INotification build();

}
