package pt.criticalsoftware.publicplatform.positions;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IPosition;

@Named
@SessionScoped
public class SearchedPosition implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchedPosition.class);
	
	private static final long serialVersionUID = 6210294118749872422L;
	@EJB
	private IPositionBusinessService positionBness;
	private Integer positionId;
	private IPosition selectedPosition;
	
	public void getPosition() {
		System.out.println("\n\n\n ### ID da posição: "+positionId+"\n\n\n");
		try {
			selectedPosition = positionBness.getPositionById(positionId);
		} catch (Exception e) {
			logger.error("Nenhuma posição encontrada.");
		}
	}
	
	public String testingPos(Integer id) {
		positionId = id;
		return "register";
	}
	
	public String goBack() {
		if (null != positionId) return null;
		else return "index";
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public IPosition getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(IPosition selectedPosition) {
		this.selectedPosition = selectedPosition;
	}
	
	public String goOn() {
		if (null == positionId) return "index?faces-redirect=true";
		else return "searchedPosition?faces-redirect=true&ref=" + positionId;
	}
	
	
}
