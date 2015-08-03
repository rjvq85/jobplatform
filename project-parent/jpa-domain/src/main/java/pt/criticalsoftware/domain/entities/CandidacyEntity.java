package pt.criticalsoftware.domain.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Entity
@Table(name="candidaturas")
public class CandidacyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="carta_motivacao",nullable=false)
	private String motivationLetter;
	
	@Column(name="fonte",nullable=false)
	private String source;
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado_candidatura",nullable=false)
	private CandidacyState state;
	
	@ManyToOne
	@JoinColumn(name = "candidato")
	private CandidateEntity candidate;
	
	@ManyToOne
	@JoinColumn(name = "posicao")
	private PositionEntity positionCandidacy;
	
	@OneToOne
	@JoinColumn(name="entrevista")
	private InterviewEntity interview;

	public CandidacyEntity() {
		
	}

	public String getMotivationLetter() {
		return motivationLetter;
	}

	public void setMotivationLetter(String motivationLetter) {
		this.motivationLetter = motivationLetter;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public CandidacyState getState() {
		return state;
	}

	public void setState(CandidacyState state) {
		this.state = state;
	}

	public CandidateEntity getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateEntity candidate) {
		this.candidate = candidate;
	}

	public Integer getId() {
		return id;
	}

	public PositionEntity getPositionCandidacy() {
		return positionCandidacy;
	}

	public void setPositionCandidacy(PositionEntity positionCandidacy) {
		this.positionCandidacy = positionCandidacy;
	}

	public InterviewEntity getInterview() {
		return interview;
	}

	public void setInterview(InterviewEntity interview) {
		this.interview = interview;
	}

	public void setId(Integer id) {
		this.id = id;
	} 
	
	

}
