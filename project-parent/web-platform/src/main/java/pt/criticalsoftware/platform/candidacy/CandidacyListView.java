package pt.criticalsoftware.platform.candidacy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Named
@RequestScoped
public class CandidacyListView {

	@EJB
	private ICandidacyBusinessService business;

	@Inject
	private ManageCandidacy manage;
	@Inject
	private CandidacyInterviews interview;

	private ICandidacy candidacy;

	private List<ICandidacy> candidacies;

	private ICandidacy newCandidacy;

	private ICandidate candidate;

	private String searchText;

	private CandidacyState newState;
	
	private LocalDate isNewDate;

	public CandidacyListView() {
	}

	public void doSearch() {
		boolean isDate = false;
		if (null != searchText || !searchText.equals("")) {
			LocalDate date;
			try {
				date = LocalDate.parse(searchText, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				if (isAdmin()) candidacies = business.getSearchedDatesCandidaciesAdmin(date);
				if (isManager()) candidacies = business.getSearchedDatesCandidaciesManager(date, getCurrentUserID());
				isDate = true;
			} catch (DateTimeParseException dtpe) {
				isDate = false;
			}
			if (!isDate) {
				try {
					date = LocalDate.parse(searchText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					if (isAdmin()) candidacies = business.getSearchedDatesCandidaciesAdmin(date);
					if (isManager()) candidacies = business.getSearchedDatesCandidaciesManager(date, getCurrentUserID());
					isDate = true;
				} catch (DateTimeParseException dtpe) {
					Collection<ICandidacy> searchedCandidacies = new HashSet<ICandidacy>();
					String[] params = searchText.split(" ");
					List<ICandidacy> cands = new ArrayList<>();
					if (isAdmin()) cands = business.getAllCandidacies();
					if (isManager()) cands = business.getManagerCandidacies(getCurrentUserID());
					for (String s : params) {
						for (ICandidacy cand : cands) {
							if (null != cand.getPositionCandidacy().getTitle()) {
								if (cand.getCandidate().getFirstName().toLowerCase().contains(s.toLowerCase())
										|| cand.getCandidate().getLastName().toLowerCase().contains(s.toLowerCase())
										|| cand.getPositionCandidacy().getTitle().toLowerCase()
												.contains(s.toLowerCase())
										|| cand.getPositionCandidacy().getReference().toLowerCase()
												.contains(s.toLowerCase())) {
									searchedCandidacies.add(cand);
								}
							} else {
								if (cand.getCandidate().getFirstName().toLowerCase().contains(s.toLowerCase())
										|| cand.getCandidate().getLastName().toLowerCase().contains(s.toLowerCase())) {
									searchedCandidacies.add(cand);
								}
							}
						}
					}
					if (searchedCandidacies.size() != 0) {
						List<ICandidacy> finalList = new ArrayList<>(searchedCandidacies);
						candidacies = finalList;
					} else
						candidacies = new ArrayList<>();
				}
			}
		} else
			candidacies = new ArrayList<>();
	}

	public void clearSearch() {
		candidacies = null;
	}

	public ICandidacy getCandidacy() {
		return candidacy;
	}

	public void setCandidacy(ICandidacy candidacy) {
		this.candidacy = candidacy;
	}

	public List<ICandidacy> getCandidacies() {
		if (isAdmin()) {
			if (null == candidacies) {
				candidacies = business.getAllCandidacies();
			}
			return candidacies;
		} else if (isManager()) {
			if (null == candidacies) {
				candidacies = business.getManagerCandidacies((Integer)getSession().getAttribute("userID"));
			}
			return candidacies;
		} else return null;
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

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public ICandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}

	public String goToInterviews(ICandidacy cand) {
		interview.setSelectedCandidacy(cand);
		return "candidacyinterviews?faces-redirect=true";
	}

	public String getCandidacyDate(ICandidacy cand) {
		return cand.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public void updateCandidacyState() {
		manage.updateCandidacy();
	}

	public CandidacyState getNewState() {
		return newState;
	}

	public void setNewState(CandidacyState newState) {
		manage.setNewState(newState);
	}

	public CandidacyState[] getAllStates() {
		return CandidacyState.values();
	}

	public void chooseSelectedCandidacy(ICandidacy candidacy) {
		manage.setCandidacy(candidacy);
	}

	public void delete(ICandidacy candidacy) {
		manage.setCandidacy(candidacy);
		manage.deleteCandidacy();
	}
	
	public boolean isNew(ICandidacy cand) {
		LocalDate minusTwo = LocalDate.now().minusDays(2);
		if (cand.getDate().isBefore(minusTwo)) return false;
		else return true;
	}

	private HttpSession getSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession();
	}
	
	private boolean isAdmin() {
		return getSession().getAttribute("userROLE").equals(Role.ADMIN);
	}
	
	private boolean isManager() {
		return getSession().getAttribute("userROLE").equals(Role.GESTOR);
	}
	
	private Integer getCurrentUserID() {
		return (Integer) getSession().getAttribute("userID");
	}

	public LocalDate getIsNewDate() {
		return isNewDate;
	}

	public void setIsNewDate(LocalDate isNewDate) {
		this.isNewDate = isNewDate;
	}

}
