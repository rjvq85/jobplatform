package pt.criticalsoftware.platform.position;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.business.IUserBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateReferenceException;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.model.IUser;
import pt.criticalsoftware.service.notifications.IMailSender;
import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.roles.Role;
import pt.criticalsoftware.service.persistence.states.PositionState;

@Named
@RequestScoped
public class NewPosition {

	@EJB
	private IPositionBusinessService positionService;
	@EJB
	private IMailSender mailSender;

	@EJB
	private IUserBusinessService userService;

	private final Logger logger = LoggerFactory.getLogger(NewPosition.class);

	private LocalDate openDate;
	private Date closeDate;
	private String title;
	private String locale;
	private PositionState state;
	private String stateStr;
	private String company;
	private String technicalAreaStr;
	private TechnicalAreaType technicalArea;
	private String sla;
	private Integer vacancies;
	private String responsableName;
	private IUser responsable;
	private String description;
	private Collection<String> adChannels;
	private List<String> allResponsables;
	private List<IUser> responsables;
	private List<String> selectedChannels;
	private List<String> channels;
	private String selectedResponsable;

	@PostConstruct
	public void init() {
		channels = new ArrayList<String>();
		channels.add("Critical Software website");
		channels.add("Linkedin");
		channels.add("Glassdoor");
		channels.add("Facebook");
		channels.add("Outro");
		selectedChannels = new ArrayList<String>();
		adChannels = new ArrayList<String>();
	}

	public NewPosition() {
		super();
	}

	public List<String> getSelectedChannels() {
		return selectedChannels;
	}

	public void setSelectedChannels(List<String> selectedChannels) {
		this.selectedChannels = selectedChannels;
		for (String c : selectedChannels)
			adChannels.add(c);
	}

	public List<String> getAllResponsables() {
		this.responsables = userService.getAllUsersByRole(Role.GESTOR);
		allResponsables = new ArrayList<String>();
		for (IUser r : this.responsables)
			allResponsables.add(r.getFirstName() + " " + r.getLastName());
		return allResponsables;
	}

	public List<String> getChannels() {
		return channels;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate() {
		this.openDate = LocalDate.now();
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		if (stateStr.equals("Aberta"))
			this.state = PositionState.ABERTA;
		else if (stateStr.equals("Fechada"))
			this.state = PositionState.FECHADA;
		else if (stateStr.equals("Em Espera"))
			this.state = PositionState.EM_ESPERA;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTechnicalAreaStr() {
		return technicalAreaStr;
	}

	public void setTechnicalAreaStr(String technicalArea) {
		this.technicalAreaStr = technicalArea;
		if (technicalAreaStr.equals("SSPA"))
			this.technicalArea = TechnicalAreaType.SSPA;
		else if (technicalAreaStr.equals(".Net Development"))
			this.technicalArea = TechnicalAreaType.NET_DEVELOPMENT;
		else if (technicalAreaStr.equals("Java Development"))
			this.technicalArea = TechnicalAreaType.JAVA_DEVELOPMENT;
		else if (technicalAreaStr.equals("Safety Critical"))
			this.technicalArea = TechnicalAreaType.SAFETY_CRITICAL;
		else if (technicalAreaStr.equals("Project Management"))
			this.technicalArea = TechnicalAreaType.PROJECT_MANAGEMENT;
		else if (technicalAreaStr.equals("Integration"))
			this.technicalArea = TechnicalAreaType.INTEGRATION;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}

	public Integer getVacancies() {
		return vacancies;
	}

	public void setVacancies(Integer vacancies) {
		this.vacancies = vacancies;
	}

	public IUser getResponsable() {
		return responsable;
	}

	public void setResponsable(IUser responsable) {
		this.responsable = responsable;
	}

	public void setResponsable(String id) {
		Integer respId = Integer.parseInt(id);
		responsables = (null == responsables) ? getResponsables() : responsables;
		for (IUser u : responsables) {
			if (u.getId() == respId)
				responsable = u;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsableName() {
		return responsableName;
	}

	public void setResponsableName(String responsableName) {
		String[] parts = responsableName.split(" ");
		String fn = parts[0];
		String ln = parts[1];
		for (IUser r : this.responsables) {
			if (r.getFirstName().equals(fn) && r.getLastName().equals(ln))
				this.responsable = r;
		}
	}

	public void createPosition() {
		try {
			setOpenDate();
			logger.info("Id" + this.responsable.getId());
			IPosition newPosition = positionService.createPosition(this.openDate, this.closeDate, this.title,
					this.locale, this.state, this.company, this.technicalArea, this.sla, this.vacancies,
					this.responsable, this.description, this.adChannels);
			mailSender.sendEmail(newPosition, newPosition.getResponsable());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					"A posição " + newPosition.getReference() + " " + this.title + " foi criada com sucesso"));
		} catch (Exception e) {
			logger.error("Erro ao criar posição");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na criação da posição", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void doNothing() {
		//
	}
	
	private void clear() {
		  openDate = null;
		  closeDate = null;
		  title = null;
		  locale = null;
		  state = null;
		  stateStr = null;
		  company = null;
		  technicalAreaStr = null;
		  technicalArea = null;
		  sla = null;
		  vacancies = null;
		  description = null;
		  selectedResponsable = null;
	}
	
	public Date currentDate() {
		try {
			String lDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			return df.parse(lDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<IUser> getResponsables() {
		return (null == responsables) ? userService.getAllUsersByRole(Role.GESTOR) : responsables;
	}

	public void setResponsables(List<IUser> responsables) {
		this.responsables = responsables;
	}

	public String getSelectedResponsable() {
		return selectedResponsable;
	}

	public void setSelectedResponsable(String selectedResponsable) {
		this.selectedResponsable = selectedResponsable;
		setResponsable(selectedResponsable);
	}

	public PositionState getState() {
		return state;
	}

	public void setState(PositionState state) {
		this.state = state;
	}

	public TechnicalAreaType getTechnicalArea() {
		return technicalArea;
	}

	public void setTechnicalArea(TechnicalAreaType technicalArea) {
		this.technicalArea = technicalArea;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	public void setChannels(List<String> channels) {
		this.channels = channels;
	}

	public Collection<String> getAdChannels() {
		return adChannels;
	}

	public void setAdChannels(Collection<String> adChannels) {
		this.adChannels = adChannels;
	}

}