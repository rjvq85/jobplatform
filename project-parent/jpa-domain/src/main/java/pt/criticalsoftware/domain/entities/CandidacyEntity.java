package pt.criticalsoftware.domain.entities;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pt.criticalsoftware.service.persistence.states.CandidacyState;

@Entity
@Table(name = "candidaturas")

@NamedQueries({ 
	@NamedQuery(name = "Candidacy.findAll", query = "SELECT c FROM CandidacyEntity c "), 
	@NamedQuery(name = "Candidacy.search", query = "SELECT c FROM CandidacyEntity as c INNER JOIN c.candidate as a WHERE UPPER(a.firstName) LIKE :param OR UPPER(a.lastName) LIKE :param")
})

public class CandidacyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "carta_motivacao")
	private String motivationLetter;

	@Column(name = "fonte")
	private String source;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_candidatura", nullable = false)
	private CandidacyState state;

	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name = "candidato")
	private CandidateEntity candidate;

	@ManyToOne
//	@JoinColumn(name = "posicao")
	private PositionEntity positionCandidacy;

	@OneToMany(mappedBy = "candidacy")
//	@JoinColumn(name = "entrevistas")

	private CandidateEntity candidate;

	@ManyToOne
	private PositionEntity positionCandidacy;

	@OneToMany(mappedBy = "candidacy")
	private List<InterviewEntity> interviews;

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

	public List<InterviewEntity> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<InterviewEntity> interviews) {
		this.interviews = interviews;
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
