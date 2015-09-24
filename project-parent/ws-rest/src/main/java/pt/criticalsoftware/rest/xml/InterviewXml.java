package pt.criticalsoftware.rest.xml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pt.criticalsoftware.service.model.IInterview;

@XmlRootElement(name = "entrevista")
public class InterviewXml {

	private IInterview interview;
	private String reference;
	private LocalDate date;
	private String position;

	public InterviewXml() {
		this(null);
	}

	public InterviewXml(IInterview intrv) {
		this.interview = intrv;
		this.reference = interview.getReference();
		this.date = interview.getDate();
		this.position = interview.getPosition().getReference();
	}

	@XmlTransient
	public IInterview getInterview() {
		return interview;
	}

	public void setInterview(IInterview interview) {
		this.interview = interview;
	}

	@XmlElement(name = "dataEntrevista")
	public String getDate() {
		return interview.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	@XmlElement(name = "posicao")
	public String getPosition() {
		return interview.getPosition().getReference();
	}

	@XmlElement(name = "referencia")
	public String getReference() {
		return interview.getReference();
	}

}
