package pt.criticalsoftware.publicplatform.jobs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IPosition;

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

	@EJB
	private IPositionBusinessService positionService;

	public Jobs() {
		this.locale="";
		this.technicalAreaStr="";
		jobs=new ArrayList<IPosition>();
		searchParam="";
		jobWord="";
		items= new ArrayList<String>();
		this.items.add("----????----");
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
	public void searchToolbar(){
		logger.info("entrou no searchToolbar com os valores " + this.jobWord +" " +	this.searchWord);
		if (this.jobWord.equals("Área Técnica")){
			this.jobs=positionService.getPositionsByTechnicalArea(this.searchWord);

		} else if (this.jobWord.equals("Localizacao")){
			this.jobs=positionService.getPositionsByLocale(this.searchWord);
		}
		logger.info("O tamanho da lista de pesquisa é " + this.jobs.size());
		this.jobWord="";
		this.searchWord="";
		this.items=new ArrayList<String>();
		this.items.add("----????----");

	}
	public String search(){

		this.searchParam="Resultados por Localização e Área";
		this.jobs=positionService.getPositionsByLocaleAndArea(this.locale,this.technicalAreaStr);
		logger.info("A lista veio com tamanho " + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByLocalePorto(){
		logger.info("A area seleccionada foi pOrto"  );
		this.searchParam="Resultados para 'Porto' ";
		this.jobs=positionService.getPositionsByLocale("Porto");
		logger.info("lista do Porto" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}
	public String findByLocaleLisboa(){
		logger.info("A area seleccionada foi Lisboa"  );
		this.searchParam="Resultados para 'Lisboa' ";
		this.jobs=positionService.getPositionsByLocale("Lisboa");
		logger.info("lista do Lisboa" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}
	public String findByLocaleCoimbra(){
		logger.info("A area seleccionada foi coimbra"  );
		this.searchParam="Resultados para 'Coimbra' ";
		this.jobs=positionService.getPositionsByLocale("Coimbra");
		logger.info("lista do Coimbra" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}
	public String findByTechnicalAreaNetDevelopment(){
		logger.info("A area seleccionada foiNetDevelopment()" );
		this.searchParam="Resultados para '.Net Development' ";
		this.jobs=positionService.getPositionsByTechnicalArea(".Net Development");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}
	public String findByTechnicalAreaSSPA(){
		logger.info("A area seleccionada foiSSPA()" );
		this.searchParam="Resultados para 'SSPA' ";
		this.jobs=positionService.getPositionsByTechnicalArea("SSPA");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}
	public String findByTechnicalAreaJavaDevelopment(){
		logger.info("A area seleccionada foiJava Development()" );
		this.searchParam="Resultados para 'Java Development' ";
		this.jobs=positionService.getPositionsByTechnicalArea("Java Development");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaSafetyCritical(){
		logger.info("A area seleccionada foiSafety Critical()" );
		this.searchParam="Resultados para 'Safety Critical' ";
		this.jobs=positionService.getPositionsByTechnicalArea("Safety Critical");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}
	public String findByTechnicalAreaProjectManagement(){
		logger.info("A area seleccionada foiProject Management()" );
		this.searchParam="Resultados para 'Project Management' ";
		this.jobs=positionService.getPositionsByTechnicalArea("Project Management");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByTechnicalAreaIntegration(){
		logger.info("A area seleccionada foiIntegration()" );
		this.searchParam="Resultados para 'Integration' ";
		this.jobs=positionService.getPositionsByTechnicalArea("Integration");
		logger.info("lista da a´rea" + jobs.size());
		return "jobsResult.html?faces-redirect=true";
	}

	public String findByLast(){
		logger.info("Mais recentes" );
		this.searchParam="Ofertas Mais Recentes";
		this.jobs=positionService.getPositionsByLast();
		return "jobsResult.html?faces-redirect=true";
	}

	public void onRowSelect(SelectEvent event) {
		this.selectedJob=((IPosition) event.getObject());
		logger.info(this.selectedJob.getTitle());
		//		vai verificar se está login
		//		caso não esteja pop-up:necessário registar e efectuar login
	}

	public void registar(){
		logger.info("Registar");
	}
	public void onRowUnselect(UnselectEvent event) {
		this.selectedJob=((IPosition) event.getObject());
		this.selectedJob=null;
	}

	public void onChange(){

		if (this.jobWord.equals("Área Técnica")){
			this.items=new ArrayList<String>();
			this.items.add(".Net Development");
			this.items.add("SSPA");
			this.items.add("Java Development");
			this.items.add("Safety Critical");
			this.items.add("Project Management");
			this.items.add("Integration");

		} else if (this.jobWord.equals("Localizacao")){
			this.items=new ArrayList<String>();
			this.items.add("Porto");
			this.items.add("Coimbra");
			this.items.add("Lisboa");
		}

	}

	public List<String> getItems() {
		logger.info("Get items");
		logger.info("Tamnho dos itmes no get" + this.items.size());
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

	//	metodos para se completarem
	public void logout(){

	}

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
	public void editData(){

	}
	public void editPass(){

	}
	public void deleteAccount(){

	}
}
