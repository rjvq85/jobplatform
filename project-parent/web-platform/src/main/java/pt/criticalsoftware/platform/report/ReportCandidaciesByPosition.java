package pt.criticalsoftware.platform.report;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ReportCandidaciesByPosition implements Serializable {

	private static final long serialVersionUID = -7219175443890456917L;

	@EJB
	private IPositionBusinessService business;
	@EJB
	private ICandidacyBusinessService businessCandidacy;
	private List<ICandidacy> candidacies;
	private List<IPosition> positions;
	private IPosition position;
	private List<String> positionsRef;
	private String  positionRef;
	private Integer positionID;


	public ReportCandidaciesByPosition() {
		positions=new ArrayList<IPosition>();
		positionsRef=new ArrayList<String>();
	}

	@PostConstruct
	private void init(){
		positions=business.getAllPositions();
		for(IPosition p:positions)
			positionsRef.add(p.getReference());
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

	public List<ICandidacy> getCandidacies() {
		return candidacies;
	}

	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}

	public List<IPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<IPosition> positions) {
		this.positions = positions;
	}
	
	public String create(){
		this.candidacies=businessCandidacy.getCandidaciesByPosition(this.positionID);
		return "viewCandidaciesPositions.xhtml?faces-redirect=true";
	}
}
