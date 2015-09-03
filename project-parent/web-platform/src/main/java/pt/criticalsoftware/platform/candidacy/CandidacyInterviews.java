package pt.criticalsoftware.platform.candidacy;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pt.criticalsoftware.platform.interviews.ManageInterview;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named
@SessionScoped
public class CandidacyInterviews implements Serializable {

	private static final long serialVersionUID = 2611532777130810723L;

	@EJB
	private ICandidacyBusinessService candBness;
	@Inject
	private ManageInterview manage;

	private Date newDate;
	private ICandidacy selectedCandidacy;
	private IInterview selectedInterview;
	private List<IInterview> existingInterviews;

	public ICandidacy getSelectedCandidacy() {
		return selectedCandidacy;
	}

	public void setSelectedCandidacy(ICandidacy selectedCandidacy) {
		this.selectedCandidacy = selectedCandidacy;
	}

	public List<IInterview> getExistingInterviews() {
		return existingInterviews = candBness.getCandidacyInterviews(selectedCandidacy.getId());
	}

	public void setExistingInterviews(List<IInterview> existingInterviews) {
		this.existingInterviews = existingInterviews;
	}

	public String getInterviewDate(IInterview interv) {
		return interv.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public void deleteInterview(IInterview interv) {
		manage.deleteInterview(interv);
	}

	public void updateDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String nDate = df.format(newDate);
		selectedInterview.setDate(LocalDate.parse(nDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		manage.updateInterview(selectedInterview);
	}

	public IPosition getSelectedPosition() {
		return selectedCandidacy.getPositionCandidacy();
	}

	public void addNewInterview(IInterview interv) {
		existingInterviews.add(interv);
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

	public IInterview getSelectedInterview() {
		return selectedInterview;
	}

	public void setSelectedInterview(IInterview selectedInterview) {
		this.selectedInterview = selectedInterview;
	}

}
