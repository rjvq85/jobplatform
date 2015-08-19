package pt.criticalsoftware.platform.candidacy;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.platform.candidacy.utils.FileUpload;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.business.IPositionBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.model.IPosition;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Named
@RequestScoped
public class NewCandidacy {

	private static final Logger logger = LoggerFactory.getLogger(NewCandidacy.class);

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String username = "";
	private String address;
	private String city;
	private String country;
	private Integer phone;
	private Integer mobile;
	private String course;
	private String degree;
	private String school;
	private String cv;
	private IPosition position;
	private CandidacyState state;
	private List<IPosition> availablePositions;
	private String selectedPosition;
	private Part file;
	public NewCandidacy() {
	}

	@Inject
	private ICandidateBuilder candidate;
	@Inject
	private ICandidacyBuilder candidacy;
	@EJB
	private ICandidacyBusinessService business;
	@EJB
	private IPositionBusinessService positionBusiness;
	@Inject
	private FileUpload upload;

	public void create() {
		
		try {
			upload.setFile(file);
			String filePath = upload.fileUpload(username);
			ICandidate icandidate = candidate.address(address).country(country).course(course).degree(degree).email(email)
					.firstName(firstName).lastName(lastName).mobile(mobile).password(password).phone(phone).school(school)
					.town(city).username(username).cv(filePath).build();
			ICandidacy icandidacy = candidacy.state(CandidacyState.SUBMETIDA).candidate(icandidate).position(position)
					.build();
			business.createCandidacy(icandidacy);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Candidatura submetida com sucesso!",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			reset();
		} catch (DuplicateCandidateException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			logger.error(e.getMessage());
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fazer o envio do ficheiro", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void reset() {
		firstName = null;
		lastName = null;
		email = null;
		password = null;
		username = null;
		address = null;
		city = null;
		country = null;
		phone = null;
		mobile = null;
		course = null;
		degree = null;
		school = null;
		cv = null;
		state = null;
		position = null;
		selectedPosition = null;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public CandidacyState getState() {
		return state;
	}

	public void setState(CandidacyState state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IPosition getPosition() {
		return position;
	}

	public void setPosition(String position) {
		if (position.equals("none"))
			this.position = null;
		else {
			for (IPosition p : availablePositions) {
				if (Integer.valueOf(position) == p.getId()) {
					this.position = p;
				}
			}
		}
	}

	public List<IPosition> getAvailablePositions() {
		if (null == availablePositions) {
			availablePositions = positionBusiness.getAllPositions();
			return availablePositions;
		}
		return null;
	}

	public void setAvailablePositions(List<IPosition> availablePositions) {
		this.availablePositions = availablePositions;
	}

	public String getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(String selectedPosition) {
		this.selectedPosition = selectedPosition;
		setPosition(selectedPosition);
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
