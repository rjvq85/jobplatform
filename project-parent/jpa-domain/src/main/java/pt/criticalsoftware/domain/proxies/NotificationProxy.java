package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import javax.resource.spi.IllegalStateException;

import pt.criticalsoftware.domain.entities.NotificationEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.IUser;

public class NotificationProxy implements IEntityAware<NotificationEntity>, INotification {

	private NotificationEntity notification;

	@Override
	public NotificationEntity getEntity() {
		return notification;
	}

	public NotificationProxy() {
		this(null);
	}

	public NotificationProxy(NotificationEntity notif) {
		notification = notif != null ? notif : new NotificationEntity();
	}

	@Override
	public LocalDate getDate() {
		return notification.getDate();
	}

	@Override
	public void setDate(LocalDate date) {
		notification.setDate(date);
	}

	@Override
	public IUser getReceptor() {
		return new UserProxy(notification.getReceptor());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setReceptor(IUser receptor) {
		notification.setReceptor(((IEntityAware<UserEntity>) receptor).getEntity());
	}

	@Override
	public String getSituation() {
		return notification.getSituation();
	}

	@Override
	public void setSituation(String situation) {
		notification.setSituation(situation);
	}

	@Override
	public String getText() {
		return notification.getText();
	}

	@Override
	public void setText(String text) {
		notification.setText(text);
	}

	@Override
	public Integer getId() {
		return notification.getId();
	}

}
