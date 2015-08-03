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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Entity
@Table(name="candidaturas")

@NamedQueries({
@NamedQuery(name="Candidacy.findAll", query="select p from CandidacyEntity p ")
}
)

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
		CandidacyEntity other = (CandidacyEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
