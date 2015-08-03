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
	@JoinColumn(name = "guião")
	private InterviewScriptEntity script;
	
	@ManyToOne
	@JoinColumn(name = "posiçao da entrevista")
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
	
	
	

}
