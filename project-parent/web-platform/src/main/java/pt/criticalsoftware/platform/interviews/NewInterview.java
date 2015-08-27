package pt.criticalsoftware.platform.interviews;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.platform.candidacy.CandidacyInterviews;
import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.business.IScriptBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IInterviewBuilder;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.model.IUser;

@Named
@RequestScoped
public class NewInterview {

	private static final Logger logger = LoggerFactory.getLogger(NewInterview.class);

	private Date date;
	private String feedback;
	private IUser interviewer;
	private List<IUser> interviewers;
	private List<IUser> newInterviewers;
	private IScript script;
	private List<IScript> availableScripts;
	private IInterview createdInterview;

	@EJB
	private IInterviewBusinessService business;
	@EJB
	private IInterviewBuilder builder;
	@EJB
	private IUserBusinessService userBness;
	@EJB
	private IScriptBusinessService scriptBness;
	@Inject
	private CandidacyInterviews candInterview;

	public void schedule() {
		try {
			IInterview interview = builder.candidacy(candInterview.getSelectedCandidacy()).date(convertDate())
					.feedback(feedback).interviewers(newInterviewers).position(candInterview.getSelectedPosition())
					.script(script).build();
			createdInterview = business.newInterview(interview);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entrevista agendada com sucesso", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			candInterview.addNewInterview(interview);
			clear();
		} catch (Exception e) {
			logger.error("Erro ao agendar entrevista\n" + e.getStackTrace().toString());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao agendar entrevista", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			clear();
		}
	}
	
	private void clear() {
		date = null;
		feedback = null;
		interviewer = null;
		interviewers = null;
		newInterviewers = null;
		script = null;
		availableScripts = null;
		createdInterview = null;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public List<IUser> getInterviewers() {
		if (null == interviewers)
			interviewers = userBness.getAllUsers();
		return interviewers;
	}

	public void setInterviewers(List<IUser> interviewers) {
		this.interviewers = interviewers;
	}

	public IScript getScript() {
		return script;
	}

	public void setScript(IScript script) {
		this.script = script;
	}

	public IInterview getCreatedInterview() {
		return createdInterview;
	}

	private LocalDate convertDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String formatedDate = df.format(date);
		return LocalDate.parse(formatedDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	public Date currentDate() {
		try {
			String lDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			return df.parse(lDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public IUser getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(IUser interviewer) {
		this.interviewer = interviewer;
	}

	public List<IScript> getAvailableScripts() {
		if (null == availableScripts)
			setAvailableScripts(scriptBness.getAll());
		return availableScripts;
	}

	public void setAvailableScripts(List<IScript> availableScripts) {
		this.availableScripts = availableScripts;
	}

	public List<IUser> getNewInterviewers() {
		return newInterviewers;
	}

	public void setNewInterviewers(List<IUser> newInterviewers) {
		this.newInterviewers = newInterviewers;
	}

}
