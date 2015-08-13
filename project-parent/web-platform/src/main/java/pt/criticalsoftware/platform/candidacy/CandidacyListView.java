package pt.criticalsoftware.platform.candidacy;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;

@Named
@RequestScoped
public class CandidacyListView {

	@EJB
	private ICandidacyBusinessService business;
	
	private ICandidacy candidacy;
	
	private List<ICandidacy> candidacies;
	
	private ICandidacy newCandidacy;
	
	public CandidacyListView() {
	}

	public ICandidacy getCandidacy() {
		return candidacy;
	}

	public void setCandidacy(ICandidacy candidacy) {
		this.candidacy = candidacy;
	}

	public List<ICandidacy> getCandidacies() {
		if (null == candidacies) {
			candidacies = business.getAllCandidacies();
		}
		return candidacies;
	}

	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}

	public ICandidacy getNewCandidacy() {
		return newCandidacy;
	}

	public void setNewCandidacy(ICandidacy newCandidacy) {
		this.newCandidacy = newCandidacy;
	}
	
	

}
