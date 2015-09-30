package pt.criticalsoftware.platform.report;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.notifications.IMailSender;

@Named
@SessionScoped
public class ReportInterviewAverage implements Serializable{

	private static final long serialVersionUID = -3892681273582542802L;
	private final Logger logger = LoggerFactory.getLogger(ReportInterviewAverage.class);

	@EJB
	private ICandidacyBusinessService business;
	@EJB
	private IUserBusinessService bness;
	@EJB
	private IMailSender email;
	
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
		if (initDate!=null){
			LocalDate date = initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();	
			try {
				String lDate = date.plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				return df.parse(lDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return initDate;
		}
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getFinalDate() {
		if (finalDate!=null){
			LocalDate date = finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();	
			try {
				String lDate = date.plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				return df.parse(lDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return finalDate;
		}
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
		logger.info("as datas são" + dateInit+ "data final" +dateFinal );
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
			if (this.candidacies.size()>=1)
			this.averageTime=totalDays/this.candidacies.size();
			else 
				this.averageTime=0;
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
					LocalDate date2=c.getHiringDate();
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
			if (this.candidacies.size()>=1)
				this.averageTime=totalDays/this.candidacies.size();
				else 
					this.averageTime=0;
			return "viewHiringAverage.xhtml?faces-redirect=true";
		}
	}
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {

		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
		Font headerFont = new Font(bf_helv, 12);

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String logo = servletContext.getRealPath("") + File.separator + "resources"
				+ ""   + File.separator + "imgs" + File.separator + "criticalIcon.jpg";


		pdf.add(Image.getInstance(logo));
		Phrase phrase = new Phrase(12, "\n", headerFont);
		phrase.add("\n Critical Software Relatórios \n \n");
		pdf.add(phrase);
	}
	
	@Inject
	private pdfInterviewAverageMail pdfTEST;
	@Inject
	private pdfHiringAverage pdfTEST2;
	
	private String documentNumber;
	public void postProcessPDF(Object document){
		documentNumber=document.toString();
		String tt=documentNumber.substring(26);
		documentNumber=tt;
	}

	private String filePath;

	public void proProcessPDF(){
		this.filePath=pdfTEST.generatMain((ArrayList<ICandidacy>) this.candidacies, documentNumber);
	}
	public void proProcessPDF2(){
		this.filePath=pdfTEST2.generatMain((ArrayList<ICandidacy>) this.candidacies, documentNumber);
	}
	private HttpSession getSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getSession();
	}

	private Integer userid = (Integer) getSession().getAttribute("userID");

	public void submitByMail(){
		proProcessPDF();
		IUser user = bness.getUserByID(this.userid);
		email.sendAttachmentEmail(user,this.filePath);
	}
	public void submitByMail2(){
		proProcessPDF2();
		IUser user = bness.getUserByID(this.userid);
		email.sendAttachmentEmail(user,this.filePath);
	}
}
