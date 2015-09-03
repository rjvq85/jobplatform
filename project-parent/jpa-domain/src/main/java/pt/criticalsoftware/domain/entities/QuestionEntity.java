package pt.criticalsoftware.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pt.criticalsoftware.service.persistence.questions.AnswerType;


@Entity
@Table(name="questões")
@NamedQueries({

	@NamedQuery(name="QuestionEntity.findById", query="select p from QuestionEntity p where p.id = :id "),
	@NamedQuery(name="QuestionEntity.getAllQuestionsByScript", query="select p from QuestionEntity p where p.script =:script "),}
		)
public class QuestionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name="pergunta",nullable=false)
	private String questionStr;

	@Column(name="número")
	private Integer number;

	@Column(name="resposta",nullable=false)
	@Enumerated(EnumType.STRING)
	private AnswerType answer;

	@ManyToOne(fetch = FetchType.EAGER)
	private ScriptEntity script;

	public ScriptEntity getScript() {
		return script;
	}

	public void setScript(ScriptEntity script) {
		this.script = script;
	}

	public QuestionEntity() {

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
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionEntity other = (QuestionEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
