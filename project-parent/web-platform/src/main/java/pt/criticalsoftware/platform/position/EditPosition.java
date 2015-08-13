package pt.criticalsoftware.platform.position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.PositionState;

import java.io.Serializable;

@Named
@SessionScoped
public class EditPosition implements Serializable {
	
	private static final long serialVersionUID = 4802990286390608105L;
	
	private final Logger logger = LoggerFactory.getLogger(EditPosition.class);

	@EJB
	private IPositionBusinessService positionService;
	@Inject
	PositionListView posList;
	@Inject 
	NewPosition newPos;
	@EJB
	private IUserBusinessService userService;

	private IPosition editPosition;
	private String reference;
	private IUser responsable;
	private String responsableName;
	private PositionState state;
	private String stateStr;
	private String title;
	private String locale;
	private Integer vacancies;
	private String company;
	private String description;
	private List<String> allResponsables;
	private List<IUser> responsables;


	public EditPosition() {

	}

	
	public void init(){
		this.reference=editPosition.getReference();
		this.stateStr=editPosition.getState().getName();
		this.title=editPosition.getTitle();
		this.locale=editPosition.getLocale();
		this.vacancies=editPosition.getVacancies();
		this.company=editPosition.getCompany();
		this.description=editPosition.getDescription();

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.editPosition.setTitle(title);
		this.title = title;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.editPosition.setLocale(locale);
		this.locale = locale;
	}

	public Integer getVacancies() {
		return vacancies;
	}

	public void setVacancies(Integer vacancies) {
		this.editPosition.setVacancies(vacancies);
		this.vacancies = vacancies;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.editPosition.setCompany(company);
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.editPosition.setDescription(description);
		this.description = description;
	}

	public IPosition getEditPosition() {
		return editPosition;
	}

	public void setEditPosition(IPosition editPosition) {
		this.editPosition = editPosition;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.editPosition.setReference(reference);
		this.reference = reference;
	}


	public String getStateStr() {
		return stateStr;
	}
	
	public String getResponsableName() {
		return responsableName;
	}
		
	public IUser getResponsable() {
		return responsable;
	}


	public void setResponsable(IUser responsable) {
		this.responsable = responsable;
	}


	public void setResponsableName(String responsableName) {
		String[] parts = responsableName.split(" ");
		String fn = parts[0]; 
		String ln = parts[1];
		for (IUser r:responsables){
			if (r.getFirstName().equals(fn)&& r.getLastName().equals(ln))
				this.responsable=r;
		}

		this.editPosition.setResponsable(this.responsable);
	}

	public void setStateStr(String stateStr) {
		if (stateStr.equals("Aberta"))
			this.state=PositionState.ABERTA;
		else if (stateStr.equals("Fechada"))
			this.state=PositionState.FECHADA;
		else if (stateStr.equals("Em Espera"))
			this.state=PositionState.EM_ESPERA;
		this.editPosition.setState(this.state);
	}
	
	public List<String> getAllResponsables() {
		Role role=Role.GESTOR;
		this.responsables=userService.getAllUsersByRole(role);
		allResponsables= new ArrayList<String>();
		for (IUser r:this.responsables)
			allResponsables.add(r.getFirstName()+" "+r.getLastName());
		return allResponsables;
	}
	public void updatePosition(){
		positionService.update(this.editPosition);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"A posição " + this.reference  + " foi editada com sucesso"));
	}
	
	public void deletePosition(){
		positionService.delete(this.editPosition);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"A posição " + this.reference  + " foi eliminada com sucesso"));
	}
	
	public void onRowSelect(SelectEvent event) {
		this.editPosition=((IPosition) event.getObject());
		init();

	}

}
