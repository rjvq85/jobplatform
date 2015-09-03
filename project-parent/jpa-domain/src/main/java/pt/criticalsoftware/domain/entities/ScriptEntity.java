package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "guioes")


@NamedQueries({
@NamedQuery(name="ScriptEntity.getAll", query="select p from ScriptEntity p "),
@NamedQuery(name="ScriptEntity.findByReference", query="select p from ScriptEntity p where p.reference = :reference "),
@NamedQuery(name="ScriptEntity.findByTitle", query="select p from ScriptEntity p where p.title = :title "),
@NamedQuery(name="ScriptEntity.verifyTitle", query="select p from ScriptEntity p where p.title = :title "),
@NamedQuery(name="ScriptEntity.getAllQuestionsById", query="select p from ScriptEntity p where p.id =:id "),
@NamedQuery(name="ScriptEntity.getScriptById", query="select p from ScriptEntity p where p.id =:id "),
@NamedQuery(name = "ScriptEntity.findById", query = "select p from ScriptEntity p where p.id = :param ") })
 


public class ScriptEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "titulo", nullable = false)
	private String title;

	@Column(name = "referencia", nullable = false)
	private String reference;
	
	@OneToMany(mappedBy="script" )
	private Collection<QuestionEntity> questions;
	
	@OneToMany(mappedBy="script")
	private Collection<InterviewEntity> interviews;
	


	public ScriptEntity() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Collection<QuestionEntity> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<QuestionEntity> questions) {
		this.questions = questions;
	}

	public Collection<InterviewEntity> getInterviews() {
		return interviews;
	}

	public void setInterviews(Collection<InterviewEntity> interviews) {
		this.interviews = interviews;
	}

	public Integer getId() {
		return id;
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
		ScriptEntity other = (ScriptEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
