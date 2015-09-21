package pt.criticalsoftware.platform.report;


import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.model.IInterview;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class ReportInterviews implements Serializable {

	private static final long serialVersionUID = 8200933112738188590L;
	@EJB
	private IInterviewBusinessService business;
	private List<IInterview> interviews;
	private Date initDate, finalDate;
	
	public ReportInterviews() {
		interviews=new ArrayList<IInterview>();
	}

	public List<IInterview> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<IInterview> interviews) {
		this.interviews = interviews;
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
	
	public String create(){
		LocalDate dateInit = this.initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = this.finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (dateInit.isAfter(dateFinal)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial superior à data final!!Por favor introduzir novas datas.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);		
		
			return "viewReportInterviews.xhtml?faces-redirect=true";
		}
		else {
			this.interviews=business.getInterviewsByDatePeriod(dateInit,dateFinal);	
			return "viewInterviews.xhtml?faces-redirect=true";
		}
	}
}

