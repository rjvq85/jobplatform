package pt.criticalsoftware.domain.proxies;

import java.util.ArrayList;
import java.util.List;

import pt.criticalsoftware.domain.entities.PositionEntity;
import pt.criticalsoftware.domain.entities.UserEntity;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.roles.Role;

public class UserProxy implements IEntityAware<UserEntity>, IUser {

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
		return user.getRoles();
	}

	@Override
	public void setRoles(List<Role> roles) {
		user.setRoles(roles);
	}

	@Override
	public List<IPosition> getPositions() {
		List<IPosition> positions = new ArrayList<>();
		for (PositionEntity pe : user.getPositions()) {
			positions.add(new PositionProxy(pe));
		}
		return positions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setPositions(List<IPosition> positions) {
		List<PositionEntity> entities = new ArrayList<>();
		for (IPosition ip : positions) {
			if (ip instanceof IEntityAware<?>) {
				entities.add(((IEntityAware<PositionEntity>) ip).getEntity());
			}
		}
		user.setPositions(entities);
	}

	@Override
	public Integer getId() {
		return user.getId();
	}

	@Override
	public String toString() {
		return String.valueOf(getId());
	}
	
	@Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && user.getId() != null)
            ? user.getId().equals(((UserProxy) other).getId())
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (user.getId() != null) 
            ? (getClass().hashCode() + user.getId().hashCode())
            : super.hashCode();
    }

}
