package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import javax.resource.spi.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.INotificationBuilder;
import pt.criticalsoftware.service.model.IUser;

public class NotificationBuilder implements INotificationBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationBuilder.class);
	
	private NotificationProxy notif;
	
	public NotificationBuilder() {
		notif = new NotificationProxy();
	}
	
	@Override
	public INotificationBuilder date(LocalDate date) {
		notif.setDate(date);
		return this;
	}
	
	@Override
	public INotificationBuilder receptor(IUser user) {
			notif.setReceptor(user);
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
