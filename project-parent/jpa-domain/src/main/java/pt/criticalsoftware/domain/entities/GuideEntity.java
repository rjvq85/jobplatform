package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pt.criticalsoftware.domain.entities.extra.Question;

@Entity
@Table(name="guiao")
public class GuideEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="titulo", nullable=false)
	private String title;
	
	@Column(name="lista_questoes")
	private Collection<Question> questions;
	
	@Column(name="referencia", nullable=false)
	private String reference;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getId() {
		return id;
	}
	
	
	

}
