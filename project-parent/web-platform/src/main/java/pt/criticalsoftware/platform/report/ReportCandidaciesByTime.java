package pt.criticalsoftware.platform.report;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.notifications.IMailSender;

import java.io.IOException;
import java.io.Serializable;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.DateFormatter;

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
	@EJB
	private IUserBusinessService bness;
	@EJB
	private IMailSender email;
	
	private List<ICandidacy> candidacies;
	private Date initDate, finalDate;

	public ReportCandidaciesByTime() {
		this.candidacies= new ArrayList<ICandidacy>();

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

	private String documentNumber;
	public void postProcessPDF(Object document){
		documentNumber=document.toString();
		String tt=documentNumber.substring(26);
		documentNumber=tt;
	}

	@Inject
	private pdfCandidaciesTimeMail pdfTEST;

	@Inject
	private pdfSpontaneousMail pdfTEST2;

	private String filePath;

	public void proProcessPDF(){
		this.filePath=pdfTEST.generatMain((ArrayList<ICandidacy>) this.candidacies, documentNumber );
	}
	public void proProcessPDF2(){
		this.filePath=pdfTEST2.generatMain((ArrayList<ICandidacy>) this.candidacies, documentNumber );
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
