package pt.criticalsoftware.service.model.dtos;

import java.util.Collection;

import pt.criticalsoftware.service.model.IQuestion;

public class ScriptDTO {
	
	private Integer id;

	private String title;

	private String reference;
	
	private Collection<IQuestion> questions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Collection<IQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<IQuestion> questions) {
		this.questions = questions;
	}
	
	
}
