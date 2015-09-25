package pt.criticalsoftware.platform.report;


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
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IInterview;
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
			aux1=10;aux2=20;
			aux3=70;aux4=10;
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
			for(IPosition c:this.positions)
				if (c.getTechnicalArea().equals("SSPA"))
					aux1++;
				else if (c.getTechnicalArea().equals("NET_DEVELOPMENT"))
					aux2++;
				else if (c.getTechnicalArea().equals("JAVA_DEVELOPMENT"))
					aux3++;
				else if (c.getTechnicalArea().equals("SAFETY_CRITICAL"))
					aux4++;
				else if (c.getTechnicalArea().equals("PROJECT_MANAGEMENT"))
					aux5++;
				else if (c.getTechnicalArea().equals("Integration"))
					aux6++;
			
			
		}
		else{
			aux1=10;aux2=20;
			aux3=70;aux4=10;aux5=20;
			aux6=70;
		}
		model.set("SSPA",aux1);
		model.set("Integration",aux6);
		model.set("PROJECT_MANAGEMENT",aux5);
		model.set("SAFETY_CRITICAL", aux4);
		model.set("JAVA_DEVELOPMENT", aux3);
		model.set("NET_DEVELOPMENT",aux2);

		return model;
	}
}

