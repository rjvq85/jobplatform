package pt.criticalsoftware.service.model;

import java.time.LocalDate;

public interface INotificationBuilder {

	INotificationBuilder receptor(IUser receptor);

	INotificationBuilder situation(String situation);

	INotificationBuilder text(String text);

	INotification build();

	INotificationBuilder date();

}
