package pt.criticalsoftware.rest.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entrevistas")
public class InterviewsXml {

	private List<InterviewXml> interviews;

	public InterviewsXml() {
		interviews = new ArrayList<>();
	}

	public InterviewsXml(List<InterviewXml> interviewsList) {
		interviews = interviewsList;
	}

	@XmlElement(name = "entrevista")
	public List<InterviewXml> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<InterviewXml> interviews) {
		this.interviews = interviews;
	}

}
