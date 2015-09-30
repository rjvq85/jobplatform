package pt.criticalsoftware.platform.report;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.chart.PieChartModel;
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

import pt.criticalsoftware.service.business.IInterviewBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.notifications.IMailSender;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class ReportInterviews implements Serializable {

	private static final long serialVersionUID = 8200933112738188590L;
	private final Logger logger = LoggerFactory.getLogger(ReportInterviews.class);

	@EJB
	private IInterviewBusinessService business;
	@EJB
	private IUserBusinessService bness;
	@EJB
	private IMailSender email;
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

	//	Chart
	private PieChartModel lineModel;

	public PieChartModel getLineModel() {
		file=false;
		createLineModels();
		return lineModel;
	}

	private void createLineModels() {
		lineModel = initLinearModel();
		lineModel.setTitle("Resultados das Entrevistas");
		lineModel.setLegendPosition("se");
		lineModel.setFill(false);
		lineModel.setShowDataLabels(true);
		lineModel.setDiameter(150);
	}

	private PieChartModel initLinearModel() {
		PieChartModel model = new PieChartModel();
		int auxP=0,auxN=0,auxI=0;
		if (this.interviews.size()>=1){
			for(IInterview c:this.interviews)
				if (c.getGlobalRating()>0)
					auxP++;
				else if (c.getGlobalRating()<0)
					auxN++;
				else
					auxI++;
		}
		else{
			auxP=10;auxN=20;
			auxI=70;
		}
		model.set("Positivo",auxP);
		model.set("Sem disponibilidade",auxN);
		model.set("Não correspondeu",auxI);

		return model;
	}
	String base64Str ;

	public String getBase64Str() {
		return base64Str;
	}

	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}
	private boolean file=false;
	public void submittedBase64Str(){

		if(base64Str.split(",").length > 1){
			String encoded = base64Str.split(",")[1];
			logger.info(encoded);
			byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
			try {
				RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
				ImageIO.write(renderedImage, "png", new File("out.png")); 
				file=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf;
		pdf = (Document) document;
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
		if (file){
			String chart = "out.png";
			pdf.add(Image.getInstance(chart));
		}
	}
	private String documentNumber;
	public void postProcessPDF(Object document){
		documentNumber=document.toString();
		String tt=documentNumber.substring(26);
		documentNumber=tt;
	}

	@Inject
	private pdfInterviewsMail pdfTEST;
	
	private String filePath;

	public void proProcessPDF(){
		this.filePath=pdfTEST.generatMain((ArrayList<IInterview>) this.interviews,file, documentNumber );
	}
	private HttpSession getSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getSession();
	}

	private Integer userid = (Integer) getSession().getAttribute("userID");

	public void submitByMail(){

				proProcessPDF();
				IUser user = bness.getUserByID(userid);
				email.sendAttachmentEmail(user,this.filePath);
	}


}

