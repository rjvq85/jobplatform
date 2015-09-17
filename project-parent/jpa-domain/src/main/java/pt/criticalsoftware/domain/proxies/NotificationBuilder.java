package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import javax.ejb.Stateless;

import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.INotificationBuilder;
import pt.criticalsoftware.service.model.IUser;

@Stateless
public class NotificationBuilder implements INotificationBuilder {

	private NotificationProxy notif;
	
	public NotificationBuilder() {
		notif = new NotificationProxy();
	}

	@Override
	public INotificationBuilder date() {
		notif.setDate(LocalDate.now());
		return this;
	}

	@Override
	public INotificationBuilder receptor(IUser receptor) {
		notif.setReceptor(receptor);
		return this;
	}

	@Override
	public INotificationBuilder situation(String situation) {
		notif.setSituation(situation);
		return this;
	}

	@Override
	public INotificationBuilder text(String text) {
		notif.setText(text);
		return this;
	}

	@Override
	public INotification build() {
		return notif;
	}

}
