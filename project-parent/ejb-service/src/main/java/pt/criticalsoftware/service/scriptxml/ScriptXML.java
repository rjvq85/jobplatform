package pt.criticalsoftware.service.scriptxml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "guiao")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScriptXML {
	
	@XmlElement(name = "questoesRespostas")
	private Map<String, String> questionsAndAnswers;
	
	@XmlElement(name = "avaliacaoFinal")
	private String finalComment;
	
	public ScriptXML() {
		questionsAndAnswers = new HashMap<>();
	}

	public Map<String, String> getQuestionsAndAnswers() {
		return questionsAndAnswers;
	}

	public void setQuestionsAndAnswers(Map<String, String> questionsAndAnswers) {
		this.questionsAndAnswers = questionsAndAnswers;
	}

	public String getFinalComment() {
		return finalComment;
	}

	public void setFinalComment(String finalComment) {
		this.finalComment = finalComment;
	}
	
	

}
