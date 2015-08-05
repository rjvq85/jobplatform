package pt.criticalsoftware.service.model;

import java.time.LocalDate;

public interface INotificationBuilder {

	INotificationBuilder date(LocalDate date);

	INotificationBuilder receptor(IUser user);

	INotificationBuilder situation(String situation);

	INotificationBuilder text(String text);

	INotification build();

}
