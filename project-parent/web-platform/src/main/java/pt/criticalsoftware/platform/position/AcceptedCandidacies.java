package pt.criticalsoftware.platform.position;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.candidacy.Reason;
import pt.criticalsoftware.service.persistence.states.PositionState;

@Named
@SessionScoped
public class AcceptedCandidacies implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AcceptedCandidacies.class);

	@EJB
	private IPositionBusinessService bness;
	@EJB
	private ICandidacyBusinessService candidacyBness;

	private IPosition position;
	private List<ICandidacy> acceptedCandidacies;
	private List<ICandidacy> rejectedCandidacies;
	private List<ICandidacy> candidaciesList;

	public IPosition getPosition() {
		return position;
	}

	public void setPosition(IPosition position) {
		this.position = position;
	}

	public List<ICandidacy> getAcceptedCandidacies() {
		return acceptedCandidacies;
	}

	public void setAcceptedCandidacies(List<ICandidacy> acceptedCandidacies) {
		this.acceptedCandidacies = acceptedCandidacies;
	}

	public void save() {
		Integer vacancies = position.getVacancies();
		Integer availableVacancies = vacancies - position.getAcceptedCandidacies().size();
		if (availableVacancies >= acceptedCandidacies.size()) {
			position.setAcceptedCandidacies(acceptedCandidacies);
			bness.setFirstHiring(position);
			bness.update(position);
			setHiringDate(acceptedCandidacies);
			candidacyBness.updateMultipleAccepted(acceptedCandidacies);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Candidatos guardados.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tentou seleccionar " + acceptedCandidacies.size() + " candidatos mas só há " + availableVacancies + " vagas disponíveis.",
					null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public String saveReasons() {
		try {
			candidacyBness.updateMultipleRejected(rejectedCandidacies);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Candidaturas actualizadas com sucesso.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "savedreasons";
		} catch (Exception e) {
			logger.error("Erro ao guardar motivos de rejeição para a posição: " + position.getReference());
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao actualizar candidaturas (motivos de rejeição)", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public List<ICandidacy> getCandidaciesList() {
		if (position.getAcceptedCandidacies().size() < 1) {
			candidaciesList = position.getCandidacies();
		} else {
			List<ICandidacy> candidacies = position.getCandidacies();
			candidacies.removeAll(position.getAcceptedCandidacies());
			candidaciesList = candidacies;
		}
		return candidaciesList;
	}

	public void setCandidaciesList(List<ICandidacy> candidaciesList) {
		this.candidaciesList = candidaciesList;
	}

	public Boolean stillAvailable() {
		if (null != position.getAcceptedCandidacies() && position.getAcceptedCandidacies().size() >= position.getVacancies()) {
			return false;
		}
		return true;
	}
	
	public Boolean noMoreCandidacies() {
		if (null != position.getAcceptedCandidacies() && position.getAcceptedCandidacies().size() == position.getCandidacies().size()) {
			return true;
		}
		return false;
	}

	public String close() {
		try {
			position.setState(PositionState.FECHADA);
			bness.update(position);
			rejectedCandidacies = position.getCandidacies();
			rejectedCandidacies.removeAll(acceptedCandidacies);
			return "positionclosed";
		} catch (Exception e) {
			return null;
		}
	}
	
	public String viewPosition(IPosition position) {
		this.position = position;
		return "viewposition";
	}

	public List<ICandidacy> getRejectedCandidacies() {
		return rejectedCandidacies;
	}

	public void setRejectedCandidacies(List<ICandidacy> rejectedCandidacies) {
		this.rejectedCandidacies = rejectedCandidacies;
	}
	
	public Reason[] getReasons() {
		return Reason.values();
	}
	
	private void setHiringDate(List<ICandidacy> candidacies) {
		candidacies.stream().forEach(candidacy -> candidacy.setHiringDate(LocalDate.now()));
	}

}
