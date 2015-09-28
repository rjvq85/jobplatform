package pt.criticalsoftware.publicplatform.candidate;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Named
@RequestScoped
public class SpontaneousStatus {

	private static final Logger logger = LoggerFactory.getLogger(SpontaneousStatus.class);

	@EJB
	private ICandidacyBusinessService bness;
	@EJB
	private ICandidacyBuilder builder;
	@EJB
	private ICandidateBusinessService candidateBness;

	public Boolean hasSpontCandidacy() {
		Long spont = bness.getSpontaneousCandidacyByCandidate(getUserId());
		if (spont > 0)
			return true;
		else
			return false;
	}

	public void createSpontaneous() {
		ICandidate icandidate = candidateBness.getCandidateById(getUserId());
		ICandidacy icandidacy = builder.state(CandidacyState.SUBMETIDA).candidate(icandidate).build();
		try {
			bness.createSpontaneousCandidacy(icandidacy);
		} catch (Exception e) {
			logger.error("Erro ao criar candidatura espontânea para utilizador " + icandidate.getUsername());
			e.printStackTrace();
		}
	}

	private Integer getUserId() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return (Integer) req.getSession().getAttribute("userId");
	}

}
