package pt.criticalsoftware.domain.entities.questions;

public class Question {

	private String question;
	private Integer position;
	private AnswerType answer;
	
	public Question() {
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public AnswerType getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerType answer) {
		this.answer = answer;
	}
	
	
	
}
