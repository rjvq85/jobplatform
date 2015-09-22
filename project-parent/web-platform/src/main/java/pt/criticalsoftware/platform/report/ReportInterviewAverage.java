package pt.criticalsoftware.platform.report;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;

@Named
@SessionScoped
public class ReportInterviewAverage implements Serializable{

	private static final long serialVersionUID = -3892681273582542802L;
	private final Logger logger = LoggerFactory.getLogger(ReportInterviewAverage.class);

	@EJB
	private ICandidacyBusinessService business;
	private List<ICandidacy> candidacies;
	private Date initDate, finalDate;
	private Integer averageTime;
	


	public ReportInterviewAverage() {
		this.candidacies= new ArrayList<ICandidacy>();
	}

	public List<ICandidacy> getCandidacies() {
		return candidacies;
	}
	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	public Date getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
	public Integer getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(Integer averageTime) {
		this.averageTime = averageTime;
	}
	public String create(){
		LocalDate dateInit = this.initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = this.finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (dateInit.isAfter(dateFinal)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial superior à data final!!Por favor introduzir novas datas.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);		

			return "viewReportFirstInterview.xhtml?faces-redirect=true";
		}
		else {
			this.candidacies=business.getCandidaciesByDatePeriod(dateInit,dateFinal);	
			int totalDays=0;
			//remove candidacies with no interviews
			for (ICandidacy c:this.candidacies)
				if(c.getInterviews().size()<1)
					this.candidacies.remove(c);
				else{
					//date1 is the date of the candidacy
					LocalDate date1=c.getDate();
					//date2 is the date of the first interview
					LocalDate date2=c.getInterviews().get(0).getDate();
					Period betweenDates = Period.between(date1, date2);
					int diffInDays = betweenDates.getDays();
					int diffInMonths = betweenDates.getMonths();
					int diffInYears = betweenDates.getYears();
					int numdI=dateInit.getDayOfYear();
					int numdF=dateFinal.getDayOfYear();
					int dif;
					//both of dates belongs to the same year
					if (date1.getYear()==date2.getYear())
						dif=numdF-numdI;
					//the difference between the dates is one year
					else if (-date1.getYear()+date2.getYear()==1){
						int year1=dateInit.lengthOfYear();
						dif=(numdF)+(year1-numdI);
						//the difference between the dates is more then one year
					}else {
						//						calcular todos os dias no intervalo entre os anos que estao entre eles
						int year1=dateInit.lengthOfYear();
						dif=(numdF)+(year1-numdI);

						while(date1.getYear()<date2.getYear()){
							date1=date1.plusYears(1);
							dif+=date1.lengthOfYear();
						}

					}
					totalDays+=dif;
				}
			this.averageTime=totalDays/this.candidacies.size();
			return "viewInterviewsAverage.xhtml?faces-redirect=true";
		}
	}
	public String createHiring(){
		LocalDate dateInit = this.initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = this.finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (dateInit.isAfter(dateFinal)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial superior à data final!!Por favor introduzir novas datas.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);		

			return "viewReportFirstHiring.xhtml?faces-redirect=true";
		}
		else {
			this.candidacies=business.getCandidaciesByDatePeriod(dateInit,dateFinal);	
			int totalDays=0;
			//remove candidacies with no interviews
			for (ICandidacy c:this.candidacies)
				if(!c.getState().name().equals("FECHADA"))
					this.candidacies.remove(c);
				else{
					//date1 is the date of the candidacy
					LocalDate date1=c.getDate();
					//date2 is the hiring date  
					
					//---------------------ALTERAR AQUI A DATA DE CONTRATAÇÃO QUE É A SEGUNDA DATA------------------------
					LocalDate date2=c.getInterviews().get(0).getDate();
					Period betweenDates = Period.between(date1, date2);
					int diffInDays = betweenDates.getDays();
					int diffInMonths = betweenDates.getMonths();
					int diffInYears = betweenDates.getYears();
					int numdI=dateInit.getDayOfYear();
					int numdF=dateFinal.getDayOfYear();
					int dif;
					//both of dates belongs to the same year
					if (date1.getYear()==date2.getYear())
						dif=numdF-numdI;
					//the difference between the dates is one year
					else if (-date1.getYear()+date2.getYear()==1){
						int year1=dateInit.lengthOfYear();
						dif=(numdF)+(year1-numdI);
						//the difference between the dates is more then one year
					}else {
						//						calcular todos os dias no intervalo entre os anos que estao entre eles
						int year1=dateInit.lengthOfYear();
						dif=(numdF)+(year1-numdI);

						while(date1.getYear()<date2.getYear()){
							date1=date1.plusYears(1);
							dif+=date1.lengthOfYear();
						}

					}
					totalDays+=dif;
				}
			this.averageTime=totalDays/this.candidacies.size();
			return "viewInterviewsAverage.xhtml?faces-redirect=true";
		}
	}
}
