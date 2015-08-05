package pt.criticalsoftware.platform.testdtos;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IUser;

@Named
@ViewScoped
public class ListUsers implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IUserBusinessService service;
	
	private Collection<IUser> users;
	
	private IUser selectedUser;
	
	private boolean canEdit = false;
	
	public ListUsers() {
	}
	
	public Collection<IUser> showUsers() {
		return service.getAllUsers();
	}

	public Collection<IUser> getUsers() {
		if (null == users) {
			users = service.getAllUsers();
		}
		return users;
	}

	public IUser getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(IUser selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public String edit(IUser user) {
		user.setEditable(true);
		return null;
	}
	
	public String save() {
		for (IUser user:users) {
			user.setEditable(false);
		}
		service.saveUsers(users);
		return null;
	}
	
	
	
	
	

}
