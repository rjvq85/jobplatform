package pt.criticalsoftware.domain.proxies;

import java.time.LocalDate;

import pt.criticalsoftware.domain.entities.NotificationEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.INotification;
import pt.criticalsoftware.service.model.IUser;

public class NotificationProxy implements IEntityAware<NotificationEntity>, INotification {

	private NotificationEntity notification;
	
	public NotificationProxy() {
		this(null);
	}
	
	public NotificationProxy(NotificationEntity entity) {
		this.notification = (null != entity) ? entity : new NotificationEntity();
	}

	@Override
	public NotificationEntity getEntity() {
		return notification;
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

	@Override
	@SuppressWarnings("unchecked")
	public void setReceptor(IUser receptor) {
		if (receptor instanceof IEntityAware<?>) {
			notification.setReceptor(((IEntityAware<UserEntity>) receptor).getEntity());
		}
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
