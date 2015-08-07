package pt.criticalsoftware.platform.position;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import pt.criticalsoftware.service.business.IPositionBusinessService;


@Named
@ViewScoped
public class PositionListView implements Serializable{

	private static final long serialVersionUID = -4007871036363495663L;

	@EJB
	private IPositionBusinessService positionservice;

//	private Collection<IPosition> position;
//
//	private IPosition selectedPositionr;


	

	public PositionListView() {
	}


	//Fazer
	public void updatePosition(){

	}

	public void deletePosition(){

	}

	public void newPosition(){

	}
}
