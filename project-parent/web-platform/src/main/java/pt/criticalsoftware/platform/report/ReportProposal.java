package pt.criticalsoftware.platform.report;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IPosition;

@Named
@SessionScoped
public class ReportProposal implements Serializable {

	private static final long serialVersionUID = 1141634803535222664L;
	@EJB
	private IPositionBusinessService business;
	private List<IPosition> positions;
	private Date initDate, finalDate;
	
	public ReportProposal() {
		this.positions= new ArrayList<IPosition>();
	}

	public List<IPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<IPosition> positions) {
		this.positions = positions;
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
		
			return "viewReportProposal.xhtml?faces-redirect=true";
		}
		else {
			this.positions=business.getPositionsByOpenDateByPeriod(dateInit,dateFinal);
			
			
			return "viewPositions.xhtml?faces-redirect=true";
		}
	}
	
	
}

