package pt.criticalsoftware.publicplatform.positions;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.states.CandidacyState;
import pt.criticalsoftware.service.sources.CandidacySource;

@Named
@RequestScoped
public class NewCandidacyPublic {
	
	private static final Logger logger = LoggerFactory.getLogger(NewCandidacyPublic.class);

	@Inject
	private SearchedPosition search;
	@EJB
	private ICandidateBusinessService candidateBness;
	@EJB
	private ICandidacyBusinessService candidacyBness;
	@EJB
	private ICandidacyBuilder builder;

	private String letter;

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public void create() {
		IPosition position = search.getSelectedPosition();
		ICandidacy candidacy = builder.candidate(getCandidate()).motivationalLetter(letter).position(position)
				.source(CandidacySource.CRITICAL.name()).state(CandidacyState.SUBMETIDA).build();
		try {
			candidacyBness.assignCandidacy(candidacy);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Candidatura submetida com sucesso",null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			RequestContext.getCurrentInstance().addCallbackParam("submited", true);
		} catch (Exception e) {
			logger.error("Ocorreu um erro.");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao submeter candidatura",null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().addCallbackParam("submited", false);
		}
	}

	private ICandidate getCandidate() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = req.getSession();
		Integer candidateId = (Integer) session.getAttribute("userId");
		return candidateBness.getCandidateById(candidateId);
	}

}
