package pt.criticalsoftware.platform.candidacy;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.exceptions.UniqueConstraintException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.notifications.IMailSender;
import pt.criticalsoftware.service.persistence.states.CandidacyState;
import pt.criticalsoftware.service.sources.CandidacySource;

@Named
@SessionScoped
public class AssignCandidacy implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPosition position;
	private ICandidate candidate;
	private String username;
	private String letter;
	private CandidacySource source;
	private String selectedPosition;
	
	@EJB
	private ICandidacyBuilder candBuilder;
	@EJB
	private ICandidacyBusinessService business;
	@EJB
	private ICandidateBusinessService candidateBness;
	@EJB
	private IPositionBusinessService posBness;
	@EJB
	private IMailSender mailSender;
	
	public String assignCandidacy() {
		defineCandidate(username);
		ICandidacy candidacy = candBuilder
				.candidate(candidate)
				.motivationalLetter(letter)
				.position(position)
				.source(source.getDescription())
				.state(CandidacyState.SUBMETIDA)
				.build();
		try {
			ICandidacy assignedCandidacy = business.assignCandidacy(candidacy);
			mailSender.sendEmail(assignedCandidacy, assignedCandidacy.getPositionCandidacy().getResponsable(), 1);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Candidatura submetida com sucesso!",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			clear();
		} catch (UniqueConstraintException e) {
			// log
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return null;
	}
	
	private void clear() {
		position = null;
		candidate = null;
		username = null;
		letter = null;
		source = null;
		selectedPosition = null;
	}
	
	private void defineCandidate(String username) {
		candidate = candidateBness.getCandidateByUsername(username);
	}

	public IPosition getPosition() {
		return position;
	}

	public void setPosition(IPosition position) {
		this.position = position;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public CandidacySource getSource() {
		return source;
	}

	public void setSource(CandidacySource source) {
		this.source = source;
	}
	
	public CandidacySource[] getAllSources() {
		return CandidacySource.values();
	}
	
	private void setPosition(String position) {
		List<IPosition> availablePositions = posBness.getAllPositions();
		if (position.equals("none"))
			this.position = null;
		else {
			for (IPosition p : availablePositions) {
				if (Integer.valueOf(position) == p.getId()) {
					this.position = p;
				}
			}
		}
	}
	

	public void setSelectedPosition(String selectedPosition) {
		this.selectedPosition = selectedPosition;
		setPosition(selectedPosition);
	}

	public String getSelectedPosition() {
		return selectedPosition;
	}
	
	public String newAssignment(String username) {
		this.username = username;
		return "assigncandidacy?face-redirect=true";
	}
	
}
