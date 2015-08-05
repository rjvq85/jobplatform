package pt.criticalsoftware.domain.proxies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pt.criticalsoftware.domain.entities.InterviewEntity;
import pt.criticalsoftware.domain.entities.NotificationEntity;
import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.roles.Role;

public class UserProxy implements IEntityAware<UserEntity>,IUser {
	
	private UserEntity user;
	
	@Override
	public UserEntity getEntity() {
		return user;
	}
	
	public UserProxy() {
		this(null);
	}
	
	public UserProxy(UserEntity entity) {
		user = entity != null ? entity : new UserEntity();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public void setUsername(String username) {
		user.setUsername(username);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public void setPassword(String password) {
		user.setPassword(password);
	}

	@Override
	public String getEmail() {
		return user.getEmail();
	}

	@Override
	public void setEmail(String email) {
		user.setEmail(email);
	}

	@Override
	public String getFirstName() {
		return user.getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		user.setFirstName(firstName);
	}

	@Override
	public String getLastName() {
		return user.getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		user.setLastName(lastName);
	}

	@Override
	public List<Role> getRoles() {
		return (List<Role>) user.getRoles();
	}

	@Override
	public void setRoles(Collection<Role> roles) {
		user.setRoles(roles);
	}

	public Collection<InterviewEntity> getInterviews() {
		return user.getInterviews();
	}

	public void setInterviews(Collection<InterviewEntity> interviews) {
		user.setInterviews(interviews);
	}

	public Collection<PositionEntity> getPositions() {
		return user.getPositions();
	}

	public void setPositions(Collection<PositionEntity> positions) {
		user.setPositions(positions);
	}

	public Collection<NotificationEntity> getNotifications() {
		return user.getNotifications();
	}

	public void setNotifications(Collection<NotificationEntity> notifications) {
		user.setNotifications(notifications);
	}

	@Override
	public Integer getId() {
		return user.getId();
	}
	
	@Override
	public boolean isEditable() {
		return user.isEditable();
	}

	@Override
	public void setEditable(boolean editable) {
		user.setEditable(editable);
	}
	
	@Override
	public String toString() {
		return user.getFirstName() + " " + user.getLastName() + " ("+user.getUsername()+" - " + getRoles().get(0).getRole() + ")";
	}

}
