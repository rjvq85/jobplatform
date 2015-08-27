package pt.criticalsoftware.platform.interviews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;

@Named
@RequestScoped
public class InterviewListView {

	private List<IInterview> interviews;
	private IInterview selectedInterview;
	private List<IUser> availableInterviewers;
	private List<IScript> availableScripts;
	private List<IUser> existingInterviewers;
	private IUser selectedInterviewer;
	private IUser deletingInterviewer;
	private Date newDate;
	private String searchText;
	private IScript newScript;

	@EJB
	private IInterviewBusinessService business;
	@EJB
	private IUserBusinessService userBness;
	@Inject
	private EditInterview edit;

	public void doSearch() {
		boolean isDate = false;
		if (null != searchText || !searchText.equals("")) {
			LocalDate date;
			try {
				date = LocalDate.parse(searchText, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				if (isAdmin())
					interviews = business.getInterviewsByDate(date);
				else
					interviews = business.getInterviewsByDateAndInterviewer(date, getCurrentUserID());
				isDate = true;
			} catch (DateTimeParseException dtpe) {
				isDate = false;
			}
			if (!isDate) {
				try {
					date = LocalDate.parse(searchText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					if (isAdmin())
						interviews = business.getInterviewsByDate(date);
					else
						interviews = business.getInterviewsByDateAndInterviewer(date, getCurrentUserID());
					isDate = true;
				} catch (DateTimeParseException dtpe) {
					Collection<IInterview> searchedInterviews = new HashSet<IInterview>();
					String[] params = searchText.split(" ");
					List<IInterview> intervs = new ArrayList<>();
					if (isAdmin())
						intervs = business.getAllInterviews();
					else
						intervs = business.getInterviewsByInterviewer((Integer) getCurrentUserID());
					for (String s : params) {
						for (IInterview interv : intervs) {
							String reference = "I" + interv.getId();
							if (null != interv.getPosition().getTitle()) {
								if (reference.equalsIgnoreCase(s)
										|| interv.getCandidacy().getCandidate().getFirstName().toLowerCase()
												.contains(s.toLowerCase())
										|| interv.getCandidacy().getCandidate().getLastName().toLowerCase()
												.contains(s.toLowerCase())
										|| interv.getPosition().getTitle().toLowerCase().contains(s.toLowerCase())
										|| interv.getPosition().getReference().toLowerCase()
												.contains(s.toLowerCase())) {
									searchedInterviews.add(interv);
								}
							} else {
								if (reference.equalsIgnoreCase(s)
										|| interv.getCandidacy().getCandidate().getFirstName().toLowerCase()
												.contains(s.toLowerCase())
										|| interv.getCandidacy().getCandidate().getLastName().toLowerCase()
												.contains(s.toLowerCase())) {
									searchedInterviews.add(interv);
								}
							}
						}
					}
					if (searchedInterviews.size() != 0) {
						List<IInterview> finalList = new ArrayList<>(searchedInterviews);
						interviews = finalList;
					} else
						interviews = new ArrayList<>();
				}
			}
		} else
			interviews = new ArrayList<>();
	}

	public List<IInterview> getInterviews() {
		if (isAdmin()) {
			return (null == interviews) ? business.getAllInterviews() : interviews;
		} else {
			return (null == interviews)
					? business.getInterviewsByInterviewer((Integer) getSession().getAttribute("userID")) : interviews;
		}
	}

	public void setInterviews(List<IInterview> interviews) {
		this.interviews = interviews;
	}

	public IInterview getSelectedInterview() {
		selectedInterview = edit.getSelectedInterview();
		return selectedInterview;
	}

	public void setSelectedInterview(IInterview selectedInterview) {
		edit.setSelectedInterview(selectedInterview);
	}

	public String getInterviewDate(IInterview interv) {
		return interv.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public void clearSearch() {
		interviews = null;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List<IUser> getAvailableInterviewers() {
		if (null == edit.getSelectedInterview()) {
			return availableInterviewers = userBness.getAllUsers();
		} else {
			if (null == availableInterviewers) {
				return availableInterviewers = business.getAvailableInterviewers(edit.getSelectedInterview().getId());
			} else
				return availableInterviewers = business.getAvailableInterviewers(edit.getSelectedInterview().getId());
		}
	}

	public void setAvailableInterviewers(List<IUser> availableInterviewers) {
		this.availableInterviewers = availableInterviewers;
	}

	public IUser getSelectedInterviewer() {
		return selectedInterviewer;
	}

	public void setSelectedInterviewer(IUser selectedInterviewer) {
		this.selectedInterviewer = selectedInterviewer;
	}

	public void updateInterview() {
		try {
			selectedInterview = business.updateInterview(edit.getSelectedInterview());
			edit.setSelectedInterview(selectedInterview);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrevista editada com sucesso", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao editar entrevista", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void deleteInterview() {
		try {
			business.deleteInterview(edit.getSelectedInterview());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrevista cancelada.", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cancelar entrevista", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String editInterview(IInterview i) {
		edit.setSelectedInterview(i);
		return "editinterview?faces-redirect=true";
	}

	//

	public void updateDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formatedDate = df.format(newDate);
		LocalDate localDate = LocalDate.parse(formatedDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		edit.getSelectedInterview().setDate(localDate);
		updateInterview();
	}

	public void addInterviewer() {
		edit.getSelectedInterview().addInterviewer(selectedInterviewer);
		updateInterview();
		selectedInterview = null;
	}
	
	public void addScript() {
		edit.getSelectedInterview().setScript(newScript);
		updateInterview();
		selectedInterview = null;
	}

	public void removeInterviewer() {
		edit.getSelectedInterview().deleteInterviewer(deletingInterviewer);
		updateInterview();
		deletingInterviewer = null;
	}

	public List<IUser> getExistingInterviewers() {
		return edit.getSelectedInterview().getInterviewers();
	}

	public void setExistingInterviewers(List<IUser> existingInterviewers) {
		this.existingInterviewers = existingInterviewers;
	}

	public IUser getDeletingInterviewer() {
		return deletingInterviewer;
	}

	public void setDeletingInterviewer(IUser deletingInterviewer) {
		this.deletingInterviewer = deletingInterviewer;
		selectedInterview = getSelectedInterview();
		selectedInterview.deleteInterviewer(selectedInterviewer);
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
	
	public String currentDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public IScript getNewScript() {
		return newScript;
	}

	public void setNewScript(IScript newScript) {
		this.newScript = newScript;
	}

	public List<IScript> getAvailableScripts() {
		return business.getAvailableScripts(edit.getSelectedInterview().getId());
	}

	public void setAvailableScripts(List<IScript> availableScripts) {
		this.availableScripts = availableScripts;
	}
	
	/*
	 * Private methods used to access params like Session's attributes
	 */

	private HttpServletRequest getRequest() {
		FacesContext faces = FacesContext.getCurrentInstance();
		return (HttpServletRequest) faces.getExternalContext().getRequest();
	}

	private HttpSession getSession() {
		return getRequest().getSession();
	}

	public boolean isAdmin() {
		return getRequest().isUserInRole("ADMIN");
	}

	private Object getCurrentUserID() {
		return getSession().getAttribute("userID");
	}

}
