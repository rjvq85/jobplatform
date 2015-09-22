package pt.criticalsoftware.platform.report;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;

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
		if (dataType.equals("position"))
			posSelect="true";
		this.positions=businessPosition.getAllPositions();
		for(IPosition p:positions)
			positionsRef.add(p.getReference());
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
				createLineModels();
				//this.candidacies=business.getNonAdmitedCandidaciesByDatePeriodAndPosition(dateInit,dateFinal, this.positionID);	
				return "viewNonAdmitedByPositionData.xhtml?faces-redirect=true";
			}
			else if (dataType.equals("all")){
				createLineModels();
				//this.candidacies=business.getNonAdmitedCandidaciesByDatePeriod(dateInit,dateFinal);	

				return "viewReportNonAdmitedAllData.xhtml?faces-redirect=true";
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
		return lineModel;
	}

	private void createLineModels() {
		lineModel = initLinearModel();

		if(dataType.equals("all"))
			lineModel.setTitle("Todos os Candidatos Rejeitados");
		else
			lineModel.setTitle("Candidatos Rejeitados para a Posição: " + this.positionRef);

		lineModel.setAnimate(true);
		lineModel.setLegendPosition("e");
		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		//		int max=this.candidacies.size()+this.candidacies.size()/10+1;
		int max=20;
		yAxis.setMax(max);

		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Motivos"));
		yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Número Candidatos");



	}

	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();

		LineChartSeries series = new LineChartSeries();
		series.set("Motivo1",6 );
		series.set("Motivo2", 2);
		series.set("Motivo3",3 );
		series.set("Motivo4", 8);
		series.set("Motivo5", 4);
		int aux1=0,aux2=0,aux3=0,aux4=0,aux5=0;
		//		for(ICandidacy c:this.candidacies)
		//			if(c.getReason.equals("Motivo1"))
		//				aux1++;
		//			else if(c.getReason.equals("Motivo2"))
		//				aux2++;
		//			else if(c.getReason.equals("Motivo3"))
		//				aux3++;
		//			else if(c.getReason.equals("Motivo4"))
		//				aux4++;
		//			else if(c.getReason.equals("Motivo5"))
		//				aux5++;
		//		series.set("Motivo1",aux1 );
		//		series.set("Motivo2", aux2);
		//		series.set("Motivo3",aux3 );
		//		series.set("Motivo4", aux4);
		//		series.set("Motivo5", aux5);
		model.addSeries(series);
		return model;
	}

}
