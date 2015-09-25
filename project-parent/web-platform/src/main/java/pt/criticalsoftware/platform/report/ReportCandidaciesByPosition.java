package pt.criticalsoftware.platform.report;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

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
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.IPosition;

import java.io.File;
import java.io.IOException;
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
	}
}
