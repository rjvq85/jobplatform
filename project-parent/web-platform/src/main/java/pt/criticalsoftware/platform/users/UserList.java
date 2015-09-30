package pt.criticalsoftware.platform.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IUser;

@Named
@RequestScoped
public class UserList {

	@EJB
	private IUserBusinessService business;

	private List<IUser> allUsers;

	private String searchText;

	public List<IUser> getAllUsers() {
		allUsers = (null != allUsers) ? allUsers : business.getAllUsers();
		return allUsers;
	}

	public void setAllUsers(List<IUser> allUsers) {
		this.allUsers = allUsers;
	}

	public void deleteUser(IUser user) {
		try {
			business.delUser(user);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Utilizador removido com sucesso.", null);
			FacesContext.getCurrentInstance().addMessage("usermanagement", msg);
			allUsers = business.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"O utilizador " + user.getUsername() + " não pode ser removido.", null);
			FacesContext.getCurrentInstance().addMessage("usermanagement", msg);
		}
	}

	public void doSearch() {
		Collection<IUser> searchedUsers = new HashSet<IUser>();
		if (null != searchText || !searchText.equals("")) {
			String[] params = searchText.split(" ");
			List<IUser> users = new ArrayList<>();
			users = getAllUsers();
			for (String s : params) {
				String sLower = s.toLowerCase();
				for (IUser user : users) {
					if (user.getFirstName().toLowerCase().contains(sLower) || user.getLastName().contains(sLower)
							|| user.getEmail().contains(sLower) || user.getUsername().contains(sLower)
							|| user.getRoles().get(0).getDetail().contains(sLower)) {
						searchedUsers.add(user);
					}
				}
			}
		}
		if (searchedUsers.size() != 0) {
			List<IUser> finalList = new ArrayList<>(searchedUsers);
			allUsers = finalList;
		} else
			allUsers = new ArrayList<>();
	}

	public void clearSearch() {
		allUsers = null;
		searchText = null;
	}

	public String getUserRole(IUser user) {

		return null;
	}

	public void doNothing() {
		//
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
