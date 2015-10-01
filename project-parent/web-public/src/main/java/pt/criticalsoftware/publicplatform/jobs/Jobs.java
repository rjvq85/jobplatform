package pt.criticalsoftware.publicplatform.jobs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.publicplatform.access.LoginPublic;
import pt.criticalsoftware.publicplatform.access.utils.FileUploadPublic;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.ICandidateBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class Jobs implements Serializable {

	private static final long serialVersionUID = -671807913622505751L;
	private final Logger logger = LoggerFactory.getLogger(Jobs.class);
	private IPosition selectedJob;
	private List<IPosition> jobs;
	private String locale, technicalAreaStr, searchParam, jobWord, searchWord;
	private List<String> items;

	@Inject
	LoginPublic log;

	@Inject
	private FileUploadPublic fileUpload;

	@EJB
	private IPositionBusinessService positionService;

	@EJB
	private ICandidateBusinessService business;

	@EJB
	private ICandidacyBusinessService businessCand;

	public Jobs() {
		this.locale = "";
		this.technicalAreaStr = "";
		jobs = new ArrayList<IPosition>();
		searchParam = "";
		jobWord = "";
		items = new ArrayList<String>();
		this.items.add("----????----");
		this.candidacies = new ArrayList<ICandidacy>();
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public IPosition getSelectedJob() {
		return selectedJob;
	}

	public void setSelectedJob(IPosition selectedJob) {
		logger.info("Entrou no set");
		this.selectedJob = selectedJob;
	}

	public String getTechnicalAreaStr() {
		logger.info("get area");
		return technicalAreaStr;
	}

	public void setTechnicalAreaStr(String technicalAreaStr) {
		this.technicalAreaStr = technicalAreaStr;
	}

	public List<IPosition> getJobs() {
		return jobs;
	}

	public void setJobs(List<IPosition> jobs) {
		this.jobs = jobs;
	}

	private List<IPosition> removeClosedPos(List<IPosition> jobs) {

		for (IPosition p : jobs)
			if (p.getState().equals("FECHADA"))
				jobs.remove(p);
		return jobs;
	}

	public void searchToolbar() {
		if (this.jobWord.equals("Área Técnica")) {
			this.jobs = positionService.getPositionsByTechnicalArea(this.searchWord);
			this.jobs = removeClosedPos(this.jobs);

		} else if (this.jobWord.equals("Localizacao")) {
			this.jobs = positionService.getPositionsByLocale(this.searchWord);
			this.jobs = removeClosedPos(this.jobs);
		}
		this.jobWord = "";
		this.searchWord = "";
		this.items = new ArrayList<String>();
		this.items.add("----????----");

	}

	public String search() {

		this.searchParam = "Resultados por Localização e Área";
		this.jobs = positionService.getPositionsByLocaleAndArea(this.locale, this.technicalAreaStr);
		logger.info("A lista veio com tamanho " + jobs.size());
		this.jobs = removeClosedPos(this.jobs);
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByLocalePorto() {
		logger.info("A area seleccionada foi pOrto");
		this.searchParam = "Resultados para 'Porto' ";
		this.jobs = positionService.getPositionsByLocale("Porto");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista do Porto" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByLocaleLisboa() {
		logger.info("A area seleccionada foi Lisboa");
		this.searchParam = "Resultados para 'Lisboa' ";
		this.jobs = positionService.getPositionsByLocale("Lisboa");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista do Lisboa" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByLocaleCoimbra() {
		logger.info("A area seleccionada foi coimbra");
		this.searchParam = "Resultados para 'Coimbra' ";
		this.jobs = positionService.getPositionsByLocale("Coimbra");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista do Coimbra" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaNetDevelopment() {
		logger.info("A area seleccionada foiNetDevelopment()");
		this.searchParam = "Resultados para '.Net Development' ";
		this.jobs = positionService.getPositionsByTechnicalArea(".Net Development");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaSSPA() {
		logger.info("A area seleccionada foiSSPA()");
		this.searchParam = "Resultados para 'SSPA' ";
		this.jobs = positionService.getPositionsByTechnicalArea("SSPA");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaJavaDevelopment() {
		logger.info("A area seleccionada foiJava Development()");
		this.searchParam = "Resultados para 'Java Development' ";
		this.jobs = positionService.getPositionsByTechnicalArea("Java Development");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaSafetyCritical() {
		logger.info("A area seleccionada foiSafety Critical()");
		this.searchParam = "Resultados para 'Safety Critical' ";
		this.jobs = positionService.getPositionsByTechnicalArea("Safety Critical");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaProjectManagement() {
		logger.info("A area seleccionada foiProject Management()");
		this.searchParam = "Resultados para 'Project Management' ";
		this.jobs = positionService.getPositionsByTechnicalArea("Project Management");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaIntegration() {
		logger.info("A area seleccionada foiIntegration()");
		this.searchParam = "Resultados para 'Integration' ";
		this.jobs = positionService.getPositionsByTechnicalArea("Integration");
		this.jobs = removeClosedPos(this.jobs);
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByLast() {
		logger.info("Mais recentes");
		this.searchParam = "Ofertas Mais Recentes";
		this.jobs = positionService.getPositionsByLast();
		this.jobs = removeClosedPos(this.jobs);
		return "jobsResult.html?faces-redirect=true";
	}

	public void onRowSelect(SelectEvent event) {
		this.selectedJob = ((IPosition) event.getObject());
		logger.info(this.selectedJob.getTitle());
		// vai verificar se está login
		// caso não esteja pop-up:necessário registar e efectuar login
	}

	public void registar() {
		logger.info("Registar");
	}

	public void onRowUnselect(UnselectEvent event) {
		this.selectedJob = ((IPosition) event.getObject());
		this.selectedJob = null;
	}

	public void onChange() {

		if (this.jobWord.equals("Área Técnica")) {
			this.items = new ArrayList<String>();
			this.items.add(".Net Development");
			this.items.add("SSPA");
			this.items.add("Java Development");
			this.items.add("Safety Critical");
			this.items.add("Project Management");
			this.items.add("Integration");

		} else if (this.jobWord.equals("Localizacao")) {
			this.items = new ArrayList<String>();
			this.items.add("Porto");
			this.items.add("Coimbra");
			this.items.add("Lisboa");
		}

	}

	public List<String> getItems() {
		return this.items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public String getJobWord() {
		return jobWord;
	}

	public void setJobWord(String jobWord) {
		this.jobWord = jobWord;
	}

	// edit password
	private String userName, userMail, password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	// edit mail
	public void editData() {
		logedCandidate();
		business.updateEmail(userMail, candidate);
	}

	private String countName = "Conta";

	public String getCountName() {

		String username = log.getUsername();
		if (username != null)
			return username;
		else
			return countName;
	}

	public void setCountName(String countName) {
		this.countName = countName;
	}

	private ICandidate candidate;

	public void editPass() {
		logedCandidate();
		business.updatePassword(password, candidate);
		password = "";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password actualizada com sucesso.", "");
		FacesContext.getCurrentInstance().addMessage(null, message);

	}

	// view candidacies
	private List<ICandidacy> candidacies;

	public List<ICandidacy> getCandidacies() {
		candidacies = businessCand.getCandidaciesByCandidate(getCandidateId());
		return candidacies;
	}

	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}

	public String deleteAccount() {
		String username = log.getUsername();
		this.candidate = business.getCandidateByUsername(username);
		business.deleteUser(this.candidate);
		log.logout();
		return "jobsResult.html?faces-redirect=true";
	}

	private void logedCandidate() {
		String username = log.getUsername();
		this.candidate = business.getCandidateByUsername(username);

	}

	// file upload
	private Part file;

	public Part getFile() {
		logger.info("getfile");
		return file;
	}

	public void setFile(Part file) {
		logger.info("setfile");
		this.file = file;
	}

	public void newCV() {

		String username = log.getUsername();
		this.candidate = business.getCandidateByUsername(username);

		try {
			fileUpload.setFile(file);
			String filePath = fileUpload.fileUpload(username);
			business.updateCV(filePath, this.candidate);
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fazer o envio do ficheiro",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
	}

	private Integer getCandidateId() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return (Integer) req.getSession().getAttribute("userId");
	}
}
