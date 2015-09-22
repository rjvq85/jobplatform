package pt.criticalsoftware.platform.position;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;

@Named
@RequestScoped
public class ClosedPosition {

	@EJB
	private IPositionBusinessService bness;

	@ManagedProperty("#{param.positionid}")
	private Integer positionId;
	private IPosition position;
	private List<ICandidacy> candidacies;
	private IUser positionManager;

	public void searchPosition() {
		if (null != positionId) {
			position = bness.getPositionById(positionId);
		}
	}

	public IPosition getPosition() {
		return position;
	}

	public void setPosition(IPosition position) {
		this.position = position;
	}

	public IUser getPositionManager() {
		return positionManager;
	}

	public void setPositionManager(IUser positionManager) {
		this.positionManager = positionManager;
	}

	public List<ICandidacy> getCandidacies() {
		candidacies = (null != position) ? position.getAcceptedCandidacies() : null;
		return candidacies;
	}

	public void setCandidacies(List<ICandidacy> candidacies) {
		this.candidacies = candidacies;
	}

	public String closedPosition() {
		return "viewclosedposition";
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

}
