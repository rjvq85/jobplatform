package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="entrevistas")
public class InterviewEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="data_entrevista",nullable=false)
	private LocalDate date;
	
	@Column(name="feedback")
	private String feedback;
	
	@ManyToOne
	@JoinColumn(name = "entrevistador")
	private UserEntity interviewer;
	
	@ManyToOne
	@JoinColumn(name = "guiao")
	private InterviewScriptEntity script;
	
	@ManyToOne
	@JoinColumn(name = "posicao da entrevista")
	private PositionEntity position;
	
	@Column(name="candidato")
	@OneToOne
	private CandidacyEntity candidacy;

	public InterviewEntity() {
	
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public UserEntity getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(UserEntity interviewer) {
		this.interviewer = interviewer;
	}

	public InterviewScriptEntity getScript() {
		return script;
	}

	public void setScript(InterviewScriptEntity script) {
		this.script = script;
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
		InterviewEntity other = (InterviewEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
