package pt.criticalsoftware.platform.report;


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

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;

import java.io.IOException;
import java.io.Serializable;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
@Named
@SessionScoped
public class ReportCandidaciesByTime implements Serializable {

	private static final long serialVersionUID = 8545181949292802114L;
	private final Logger logger = LoggerFactory.getLogger(ReportCandidaciesByTime.class);

	@EJB
	private ICandidacyBusinessService business;
	private List<ICandidacy> candidacies;
	private Date initDate, finalDate;

	public ReportCandidaciesByTime() {
		this.candidacies= new ArrayList<ICandidacy>();

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
	public List<ICandidacy> getCandidacies() {
		return candidacies;
	}

	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}

	public String create(){
		LocalDate dateInit = this.initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = this.finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (dateInit.isAfter(dateFinal)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial superior à data final!!Por favor introduzir novas datas.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);		
		
			return "viewReportCandidaciesByTime.xhtml?faces-redirect=true";
		}
		else {
			this.candidacies=business.getCandidaciesByDatePeriod(dateInit,dateFinal);
			logger.info("A tabela foi criada com tamanho" + candidacies.size());
			
			return "viewCandidacies.xhtml?faces-redirect=true";
		}
	}

	public String createSpontaneous(){
		logger.info("data final no createSpont" + this.finalDate);
		LocalDate dateInit = this.initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = this.finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		logger.info("datas trabalhadas" +dateInit + dateFinal );
		if (dateInit.isAfter(dateFinal)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial superior à data final!!Por favor introduzir novas datas.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);		
			return "viewReportCandidaciesSpontaneousByTime.xhtml?faces-redirect=true";
		}
		else {
			this.candidacies=business.getCandidaciesSpontaneousByDatePeriod(dateInit,dateFinal);
			logger.info("A tabela foi criada com tamanho" + candidacies.size());
			return "viewCandidaciesSpontaneous.xhtml?faces-redirect=true";
		}
	}
	
}