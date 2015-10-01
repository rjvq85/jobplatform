package pt.criticalsoftware.rest.xml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pt.criticalsoftware.service.model.ICandidate;
import pt.criticalsoftware.service.model.IInterview;

@XmlRootElement(name = "candidato")
public class CandidateXml {

	private ICandidate candidate;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String address;
	private Integer mobile;
	private Integer cellphone;

	public CandidateXml() {
		this(null);
	}

	public CandidateXml(ICandidate candidate) {
		this.candidate = candidate;
		this.firstname = candidate.getFirstName();
		this.lastname = candidate.getLastName();
		this.username = candidate.getUsername();
		this.email = candidate.getEmail();
		this.address = candidate.getAddress();
		this.mobile = candidate.getMobilePhone();
		this.cellphone = candidate.getPhone();
	}

	@XmlTransient
	public ICandidate getCandidate() {
		return candidate;
	}

	public void setCandidate(ICandidate candidate) {
		this.candidate = candidate;
	}

	@XmlElement(name = "primeiroNome")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@XmlElement(name = "ultimoNome")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@XmlElement(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name = "morada")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@XmlElement(name = "telemovel")
	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	@XmlElement(name = "telefone")
	public Integer getCellphone() {
		return cellphone;
	}

	public void setCellphone(Integer cellphone) {
		this.cellphone = cellphone;
	}

}
