package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pt.criticalsoftware.domain.entities.questions.Question;


@Entity
@Table(name="guioes")
public class InterviewScriptEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="titulo",nullable=false)
	private String title;

	@Column(name="referencia",nullable=false)
	private String reference;
	
	@ElementCollection
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
	
	
}
