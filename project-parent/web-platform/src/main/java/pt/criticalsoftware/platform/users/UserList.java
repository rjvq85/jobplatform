package pt.criticalsoftware.platform.users;

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

	public List<IUser> getAllUsers() {
		allUsers = business.getAllUsers();
		return allUsers;
	}

	public void setAllUsers(List<IUser> allUsers) {
		this.allUsers = allUsers;
	}
	
	public void deleteUser(IUser user) {
		try {
			business.delUser(user);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Utilizador removido com sucesso.",null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao remover o utilizador "+user.getUsername(),null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public String getUserRole(IUser user) {
		
		return null;
	}
	
	public void doNothing() {
		//
	}

}
