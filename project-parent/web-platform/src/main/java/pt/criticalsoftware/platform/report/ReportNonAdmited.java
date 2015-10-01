package pt.criticalsoftware.platform.report;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
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

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
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
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.notifications.IMailSender;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class ReportNonAdmited implements Serializable {

	private static final long serialVersionUID = -2611566254018952338L;

	private final Logger logger = LoggerFactory.getLogger(ReportCandidaciesByTime.class);

	@EJB
	private ICandidacyBusinessService business;
	@EJB
	private IPositionBusinessService businessPosition;
	@EJB
	private IUserBusinessService bness;
	@EJB
	private IMailSender email;

	private List<ICandidacy> candidacies;
	private Date initDate, finalDate;
	private String dataType;
	private List<IPosition> positions;
	private IPosition position;
	private List<String> positionsRef;
	private String  positionRef;
	private Integer positionID;
	private String posSelect;

	public ReportNonAdmited() {
		this.candidacies= new ArrayList<ICandidacy>();
		positions=new ArrayList<IPosition>();
		positionsRef=new ArrayList<String>();
	}

	@PostConstruct
	public void init() {

		this.posSelect=null;

	}
	public void onChange(){
		if (dataType.equals("position")){
			posSelect="true";
			pos=true;
			this.positions=businessPosition.getAllPositions();
			for(IPosition p:positions)
				positionsRef.add(p.getReference());
		}else{
			posSelect=null;
			pos=false;
			this.positions=new ArrayList<IPosition>();
		}
			
		
	}
	public String getDataType() {
		return dataType;
	}

	public String getPosSelect() {
		return posSelect;
	}

	public void setPosSelect(String posSelect) {
		this.posSelect = posSelect;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	public String getPositionRef() {
		return positionRef;
	}

	public void setPositionRef(String positionRef) {
		this.positionRef = positionRef;
		for (IPosition p:positions){
			if (p.getReference().equals(positionRef))
				this.positionID=p.getId();
		}
	}

	public IPosition getPosition() {
		return position;
	}

	public void setPosition(IPosition position) {
		this.position = position;
	}

	public List<String> getPositionsRef() {
		return positionsRef;
	}

	public void setPositionsRef(List<String> positionsRef) {
		this.positionsRef = positionsRef;
	}
	private boolean pos=false;;
	public String create(){
		LocalDate dateInit = this.initDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateFinal = this.finalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (dateInit.isAfter(dateFinal)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inicial superior à data final!!Por favor introduzir novas datas.", "");
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, message);		

			return "viewReportNonAdmited.xhtml?faces-redirect=true";
		}
		else {
			if (dataType.equals("position")){
				this.candidacies=business.getNonAdmitedCandidaciesByDatePeriodAndPosition(dateInit,dateFinal, this.positionID);
				return "viewNonAdmitedByPositionData.xhtml?faces-redirect=true";
			}
			else if (dataType.equals("all")){				
				this.candidacies=business.getNonAdmitedCandidaciesByDatePeriod(dateInit,dateFinal);	
				return "viewNonAdmitedAllData.xhtml?faces-redirect=true";
			}
		}
		return "viewReportNonAdmited.xhtml?faces-redirect=true";
	}

	public List<ICandidacy> getCandidacies() {
		return candidacies;
	}

	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}

	//	Chart
	private LineChartModel lineModel;

	public LineChartModel getLineModel() {
		createLineModels();
		return lineModel;
	}

	private void createLineModels() {
		file=false;
		lineModel = initLinearModel();
		file=false;
		if(dataType.equals("all"))
			lineModel.setTitle("Todos os Candidatos Rejeitados");
		else
			lineModel.setTitle("Candidatos Rejeitados para a Posição: " + this.positionRef);


		lineModel.setAnimate(true);
		lineModel.setLegendPosition("se");
		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		int max;
		if(this.candidacies.size()>=1){
			max=this.candidacies.size()+this.candidacies.size()/10+1;

		}
		else max=100;

		yAxis.setMax(max);

		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Motivos"));
		yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Número Candidatos");



	}

	private LineChartModel initLinearModel() {
		file=false;
		LineChartModel model = new LineChartModel();
		LineChartSeries series = new LineChartSeries();
		int aux1=0,aux2=0,aux3=0,aux4=0;
		if (this.candidacies.size()>=1){
			for(ICandidacy c:this.candidacies)
				if(c.getRejectionReason().toString().equals("PROFILE"))
					aux1++;
				else if(c.getRejectionReason().toString().equals("UNAVAILABILITY"))
					aux2++;
				else if(c.getRejectionReason().toString().equals("INTERVIEW"))
					aux3++;
				else if(c.getRejectionReason().toString().equals("OTHER"))
					aux4++;
		}
		else{
			aux1=0;aux2=0;
			aux3=0;aux4=0;
		}
		series.set("Não se enquadra",aux1);
		series.set("Sem disponibilidade",aux2);
		series.set("Não correspondeu",aux3);
		series.set("Outra(s)", aux4);
		model.addSeries(series);
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
				ImageIO.write(renderedImage, "png", new File("nonAdm.png")); 
				file=true;

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
			String chart ="nonAdm.png";
			pdf.add(Image.getInstance(chart));

		}
	}

	@Inject
	private pdfNonAdmitedMail pdfTEST;
	private String documentNumber;
	public void postProcessPDF(Object document){
		documentNumber=document.toString();
		String tt=documentNumber.substring(26);
		documentNumber=tt;
	}

	private String filePath;

	public void proProcessPDF(){
		this.filePath=pdfTEST.generatMain((ArrayList<ICandidacy>) this.candidacies,file, documentNumber,pos );
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
		proProcessPDF();
		IUser user = bness.getUserByID(this.userid);
		email.sendAttachmentEmail(user,this.filePath);
	}

}
