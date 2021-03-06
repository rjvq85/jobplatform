package pt.criticalsoftware.platform.report;


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

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IInterview;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.notifications.IMailSender;

@Named
@SessionScoped
public class ReportProposal implements Serializable {

	private static final long serialVersionUID = 1141634803535222664L;
	
	private final Logger logger = LoggerFactory.getLogger(ReportProposal.class);
	@EJB
	private IPositionBusinessService business;
	@EJB
	private IUserBusinessService bness;
	@EJB
	private IMailSender email;
	
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

			return "viewReportProposal.xhtml?faces-redirect=true";
		}
		else {
			this.positions=business.getPositionsByOpenDateByPeriod(dateInit,dateFinal);


			return "viewPositions.xhtml?faces-redirect=true";
		}
	}

	//	Chart
	private PieChartModel lineModel2;

	public PieChartModel getLineModel2() {
		createLineModels2();
		return lineModel2;
	}

	private void createLineModels2() {
		file=false;
		lineModel2 = initLinearModel2();
		lineModel2.setTitle("Propostas Apresentadas Por Localização ");
		lineModel2.setLegendPosition("se");
		lineModel2.setFill(false);
		lineModel2.setShowDataLabels(true);
		lineModel2.setDiameter(150);
	}

	private PieChartModel initLinearModel2() {
		PieChartModel model = new PieChartModel();
		int aux1=0,aux2=0,aux3=0, aux4=0;
		if (this.positions.size()>=1){
			for(IPosition c:this.positions)
				if (c.getLocale().equals("Porto"))
					aux1++;
				else if (c.getLocale().equals("Coimbra"))
					aux2++;
				else if (c.getLocale().equals("Lisboa"))
					aux3++;
				else if (c.getLocale().equals("Outra"))
					aux4++;
		}
		else{
			aux1=0;aux2=0;
			aux3=0;aux4=0;
		}
		model.set("Porto",aux1);
		model.set("Coimbra",aux2);
		model.set("Lisboa",aux3);
		model.set("Outra", aux4);
		return model;
	}


	//	Chart
	private PieChartModel lineModel;

	public PieChartModel getLineModel() {
		createLineModels();
		return lineModel;
	}

	private void createLineModels() {
		file1=false;
		lineModel = initLinearModel();
		lineModel.setTitle("Propostas Apresentadas Por Área Técnica ");
		lineModel.setLegendPosition("se");
		lineModel.setFill(false);
		lineModel.setShowDataLabels(true);
		lineModel.setDiameter(150);
	}

	private PieChartModel initLinearModel() {
		PieChartModel model = new PieChartModel();
		int aux1=0,aux2=0,aux3=0, aux4=0,aux5=0,aux6=0;
		if (this.positions.size()>=1){
			for(IPosition c:this.positions){
				if (c.getTechnicalArea().toString().equals("SSPA"))
					aux1++;
				else if (c.getTechnicalArea().toString().equals("Net Development"))
					aux2++;
				else if (c.getTechnicalArea().toString().equals("Java Development"))
					aux3++;
				else if (c.getTechnicalArea().toString().equals("Safety Critical"))
					aux4++;
				else if (c.getTechnicalArea().toString().equals("Project Management"))
					aux5++;
				else if (c.getTechnicalArea().toString().equals("Integration"))
					aux6++;
				logger.info("area tecnica" +c.getTechnicalArea()+ " au3" +aux3);
			}

		}
		else{
			aux1=0;aux2=0;
			aux3=0;aux4=0;aux5=0;
			aux6=0;
		}
		model.set("SSPA",aux1);
		model.set("Integration",aux6);
		model.set("Project Management",aux5);
		model.set("Safety Critica", aux4);
		model.set("JAVA_DEVELOPMENT", aux3);
		model.set("NET_DEVELOPMENT",aux2);

		return model;
	}

	private String base64Str ;

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

			byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
			try {
				RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
				ImageIO.write(renderedImage, "png", new File("prop.png")); 
				file=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String base64Str1 ;

	public String getBase64Str1() {
		return base64Str1;
	}

	public void setBase64Str1(String base64Str1) {
		this.base64Str1 = base64Str1;
	}
	private boolean file1=false;
	public void submittedBase64Str1(){

		if(base64Str1.split(",").length > 1){
			String encoded = base64Str1.split(",")[1];

			byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
			try {
				RenderedImage renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
				ImageIO.write(renderedImage, "png", new File("prop1.png")); 
				file1=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		if (file){
			String chart ="prop.png";
			pdf.add(Image.getInstance(chart));
			file=false;
		}
		if (file1){
			String chart1="prop1.png";
			pdf.add(Image.getInstance(chart1));
			file1=false;
		}
	}
	

	@Inject
	private pdfProposalMail pdfTEST;
	private String documentNumber;
	public void postProcessPDF(Object document){
		documentNumber=document.toString();
		String tt=documentNumber.substring(26);
		documentNumber=tt;
	}

	private String filePath;

	public void proProcessPDF(){
		this.filePath=pdfTEST.generatMain((ArrayList<IPosition>) this.positions,file, file1, documentNumber);
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
	

}

