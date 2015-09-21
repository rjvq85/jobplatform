package pt.criticalsoftware.platform.position;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	private String placeholder="";

	public PositionListView() {
	}


	public String getPlaceholder() {
		return this.placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public void onChange(){
		if (this.positionWord.equals("Data abertura") || this.positionWord.equals("Data fecho"))
			setPlaceholder("AAAA-MM-dd");
	}
	public List<IPosition> showPositions() {
		if (isAdmin()) { 
		return positionService.getAllPositions();
		} else {
			return positionService.getManagerPositions(getUserID());
		}
		
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

	private Date convertStringToDate(String dateString)
	{
		String expectedPattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		try
		{
			String dateInput = dateString;
			Date date = formatter.parse(dateInput);
			return date;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public void search(){
		if (this.positionWord.equals("Data fecho")){	
			Date ld=convertStringToDate(this.searchCode);
			this.positions= positionService.getPositionsByDate(this.positionWord,ld);
		}
		else if (this.positionWord.equals("Data abertura")){	
			LocalDate date = LocalDate.parse(this.searchCode);
			this.positions= positionService.getPositionsByOpenDate(this.positionWord,date);
			}
		else if (this.positionWord.equals("Pesquisa livre")){	
			this.positions= positionService.getPositionsByKeyWords(this.positionWord,this.searchCode);
		}
		else
			this.positions= positionService.getPositionsByWord(this.positionWord,this.searchCode);
		this.searchBoolean=true;
	}
	
	private Boolean isAdmin() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return (Boolean) req.isUserInRole("ADMIN");
	}
	
	private Integer getUserID() {
		FacesContext faces = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) faces.getExternalContext().getRequest();
		return (Integer) request.getSession().getAttribute("userID");
	}

}
