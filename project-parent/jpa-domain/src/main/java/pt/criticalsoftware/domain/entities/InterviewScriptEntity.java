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

import pt.criticalsoftware.domain.entities.questions.Question;


@Entity
@Table(name="guioes")

@NamedQueries({
@NamedQuery(name="InterviewScript.findAll", query="select p from InterviewScriptEntity p "),
@NamedQuery(name="InterviewScript.findByReference", query="select p from InterviewScriptEntity p where p.reference = :reference "),
@NamedQuery(name="InterviewScript.findByTitle", query="select p from InterviewScriptEntity p where p.title = :title ")
}
)

public class InterviewScriptEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="titulo",nullable=false)
	private String title;

	@Column(name="referencia",nullable=false)
	private String reference;
	
	@Column(name="questoes")
	private Collection<Question> questions;
	
	@OneToMany(mappedBy="script")
	private Collection<InterviewEntity> interviews;
	
	

	public InterviewScriptEntity() {
		
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

	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
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
		InterviewScriptEntity other = (InterviewScriptEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
