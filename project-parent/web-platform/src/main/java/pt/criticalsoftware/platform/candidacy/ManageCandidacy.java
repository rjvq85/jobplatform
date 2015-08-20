package pt.criticalsoftware.platform.candidacy;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Named
@ViewScoped
public class ManageCandidacy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ICandidacyBusinessService business;
	
	private ICandidacy candidacy;
	
	public void updateCandidacy() {
		try {
			business.updateEntity(candidacy);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado alterado com sucesso para '" + candidacy.getState().getName()+"'.",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao alterar o estado da candidatura.",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public String deleteCandidacy() {
		try {
			business.deleteCandidacy(candidacy);
			return "candidacies?faces-redirect=true";
		} catch (Exception e) {
			// log
			return null;
		}
	}
	
	public void reset() {
		candidacy = null;
	}
	
	public void setNewState(CandidacyState newState) {
		candidacy.setState(newState);
	}
	public ICandidacy getCandidacy() {
		return candidacy;
	}
	public void setCandidacy(ICandidacy candidacy) {
		this.candidacy = candidacy;
	}
	
	

}
