package pt.criticalsoftware.platform.position;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.model.IPosition;


@Named
@RequestScoped
public class PositionListView implements Serializable{

	private static final long serialVersionUID = -4007871036363495663L;

	private final Logger logger = LoggerFactory.getLogger(PositionListView.class);

	@EJB
	private IPositionBusinessService positionService;

	private List<IPosition> positions;
	private String searchCode;
	private String positionWord;
	private boolean searchBoolean=false;

	public PositionListView() {
	}



	public List<IPosition> showPositions() {
		return positionService.getAllPositions();
	}
	public void searchAll(){
		this.positions=getPositions();
	}
	public List<IPosition> getPositions() {
		if (!this.searchBoolean)
			this.positions=showPositions();
		return this.positions;
	}

	public String getPositionWord() {
		return positionWord;
	}

	public void setPositionWord(String positionWord) {
		this.positionWord = positionWord;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public void search(){
		logger.info(this.positionWord);
		logger.info(this.searchCode);
		this.positions= positionService.getPositionsByWord(this.positionWord,this.searchCode);
		logger.info("tamanho"+this.positions.size());
		this.searchBoolean=true;
	}

}
