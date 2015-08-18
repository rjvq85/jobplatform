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
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;

@Named
@RequestScoped
public class CandidacyListView {

	@EJB
	private ICandidacyBusinessService business;

	private ICandidacy candidacy;

	private List<ICandidacy> candidacies;

	private ICandidacy newCandidacy;

	private ICandidate candidate;

	private String searchText;

	public CandidacyListView() {
	}

	public void doSearch() {
		boolean isDate = false;
		if (null != searchText || !searchText.equals("")) {
			LocalDate date;
			try {
				date = LocalDate.parse(searchText, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				candidacies = business.getSearchedDatesCandidaciesAdmin(date);
				isDate = true;
			} catch (DateTimeParseException dtpe) {
				isDate = false;
			}
			if (!isDate) {
				try {
					date = LocalDate.parse(searchText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					candidacies = business.getSearchedDatesCandidaciesAdmin(date);
					isDate = true;
				} catch (DateTimeParseException dtpe) {
					Collection<ICandidacy> searchedCandidacies = new HashSet<ICandidacy>();
					String[] params = searchText.split(" ");
					List<ICandidacy> cands = business.getAllCandidacies();
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

	public String goToInterviews() {
		if (candidacy.getInterviews().size() < 2)
			return "candinterview?faces-redirect=true";
		else
			return "candinterviews?faces-redirect=true";
	}

	public String getCandidacyDate(ICandidacy cand) {
		return cand.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

}
