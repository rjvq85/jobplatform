package pt.criticalsoftware.publicplatform.access;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.criticalsoftware.publicplatform.access.utils.FileUploadPublic;
import pt.criticalsoftware.service.business.ICandidacyBusinessService;
import pt.criticalsoftware.service.exceptions.DuplicateCandidateException;
import pt.criticalsoftware.service.model.ICandidacy;
import pt.criticalsoftware.service.model.ICandidacyBuilder;
import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.ICandidateBuilder;
import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Named
@ViewScoped
public class RegisterPublic implements Serializable {

	private static final long serialVersionUID = 4827931867506530824L;

	private static final Logger logger = LoggerFactory.getLogger(RegisterPublic.class);

	@EJB
	private ICandidacyBuilder candidacyBuilder;
	@EJB
	private ICandidateBuilder candidateBuilder;
	@EJB
	private ICandidacyBusinessService candidacyBness;
	@Inject
	private FileUploadPublic fileUpload;
	@Inject
	private LoginPublic login;

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
	private CandidacyState state;
	private Part file;
	
	private boolean readTerms = false;

	public String register() { // Se chegar a esta página vindo de uma posição
								// específica, o id dessa posição terá que estar
								// guardado, e nesse caso este método devolve a
								// página dessa candidatura, caso contrário,
								// volta ao início
		System.out.println("ENTROU PARA O REGISTO\n\n\n\n");
		try {
			fileUpload.setFile(file);
			String filePath = fileUpload.fileUpload(username);
			ICandidate icandidate = candidateBuilder.address(address).country(country).course(course).degree(degree)
					.email(email).firstName(firstName).lastName(lastName).mobile(mobile).password(password).phone(phone)
					.school(school).town(city).username(username).cv(filePath).build();
			ICandidacy icandidacy = candidacyBuilder.state(CandidacyState.SUBMETIDA).candidate(icandidate).build();
			candidacyBness.createCandidacy(icandidacy);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registo efectuado com sucesso!",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			login.setUsername(username);
			login.setPassword(password);
			login.registerLogin();
			reset();
			return "confirmregister?faces-redirect=true";
		} catch (DuplicateCandidateException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			reset();
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fazer o envio do ficheiro",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			reset();
			e.printStackTrace();
			return null;
		} catch (NullPointerException npe) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Todos os campos são obrigatórios!",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			reset();
			npe.printStackTrace();
			return null;
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
		file = null;
	}

	public ICandidacyBuilder getCandidacyBuilder() {
		return candidacyBuilder;
	}

	public void setCandidacyBuilder(ICandidacyBuilder candidacyBuilder) {
		this.candidacyBuilder = candidacyBuilder;
	}

	public ICandidateBuilder getCandidateBuilder() {
		return candidateBuilder;
	}

	public void setCandidateBuilder(ICandidateBuilder candidateBuilder) {
		this.candidateBuilder = candidateBuilder;
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

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public boolean isReadTerms() {
		return readTerms;
	}

	public void setReadTerms(boolean readTerms) {
		this.readTerms = readTerms;
	}

}
