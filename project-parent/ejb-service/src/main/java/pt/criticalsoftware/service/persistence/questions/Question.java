package pt.criticalsoftware.service.persistence.questions;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Question implements Serializable {
	
	private final Logger logger = LoggerFactory.getLogger(Question.class);
	private static final long serialVersionUID = 1L;
	private String questionStr;
	private AnswerType answer;
	
	public Question() {
	}

	public String getQuestionStr() {
		return questionStr;
	}

	public void setQuestionStr(String questionStr) {
		this.questionStr = questionStr;
	}

	public AnswerType getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerType answer) {
		this.answer = answer;
	}
	
	
	
}
