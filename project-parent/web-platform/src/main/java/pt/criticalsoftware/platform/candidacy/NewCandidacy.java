package pt.criticalsoftware.platform.candidacy;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.persistence.ICandidacyPersistenceService;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Named
@RequestScoped
public class NewCandidacy {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String username;
	private String address;
	private String city;
	private String country;
	private Integer phone;
	private Integer mobile;
	private String course;
	private String degree;
	private String school;
	private String cv;
	private CandidacyState state;

	public NewCandidacy() {
	}

	@Inject
	private ICandidateBuilder candidate;
	@Inject
	private ICandidacyBuilder candidacy;
	@EJB
	private ICandidacyBusinessService business;

	public void create() {
		ICandidate icandidate = candidate.address(address).country(country).course(course).degree(degree).email(email)
				.firstName(firstName).lastName(lastName).mobile(mobile).password(password).phone(phone).school(school)
				.town(city).username(username).build();
		ICandidacy icandidacy = candidacy.state(CandidacyState.SUBMETIDA).candidate(icandidate).build();
		business.createCandidacy(icandidacy);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				"Candidatura submetida com sucesso"));
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public ICandidateBuilder getCandidate() {
		return candidate;
	}

	public void setCandidate(ICandidateBuilder candidate) {
		this.candidate = candidate;
	}

	public ICandidacyBuilder getCandidacy() {
		return candidacy;
	}

	public void setCandidacy(ICandidacyBuilder candidacy) {
		this.candidacy = candidacy;
	}

	public ICandidacyBusinessService getBusiness() {
		return business;
	}

	public void setBusiness(ICandidacyBusinessService business) {
		this.business = business;
	}
	
	

}
