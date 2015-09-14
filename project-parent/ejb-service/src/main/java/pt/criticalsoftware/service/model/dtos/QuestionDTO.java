package pt.criticalsoftware.service.model.dtos;


import pt.criticalsoftware.service.model.IScript;
import pt.criticalsoftware.service.persistence.questions.AnswerType;

public class QuestionDTO {

	private Integer id;

	private AnswerType answer;

	private String questionStr;

	private IScript script;
	
	public IScript getScript() {
		return script;
	}

	public void setScript(IScript script) {
		this.script = script;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
