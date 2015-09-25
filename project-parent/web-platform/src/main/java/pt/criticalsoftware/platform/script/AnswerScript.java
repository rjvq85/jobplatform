package pt.criticalsoftware.platform.script;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Named
@SessionScoped
public class AnswerScript implements Serializable {

	private static final long serialVersionUID = -1844978726230131253L;
	private final Logger logger = LoggerFactory.getLogger(AnswerScript.class);
	private String answer;

	public AnswerScript() {

	}

	public AnswerScript(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		logger.info("Set the answer");
		this.answer = answer;
	}

}
