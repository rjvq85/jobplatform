package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import pt.criticalsoftware.service.persistence.states.InterviewState;
import pt.criticalsoftware.service.persistence.utils.LocalDatePersistenceConverter;

@Entity
@Table(name = "entrevistas")

@NamedQueries({ @NamedQuery(name = "Interview.findAll", query = "select p from InterviewEntity p "),
		@NamedQuery(name = "Interview.findByDate", query = "SELECT i from InterviewEntity i WHERE i.date = :param "),
		@NamedQuery(name = "Interview.searchByPeriodDate", query = "SELECT c FROM InterviewEntity c WHERE c.date >= :initDate AND c.date <= :finalDate"),
		@NamedQuery(name = "Interview.findById", query = "select p from InterviewEntity p where p.id = :interviewId "),
		@NamedQuery(name = "Interview.findByInterviewer", query = "SELECT i FROM InterviewEntity i JOIN i.interviewers u WHERE u.id = :param"),
		@NamedQuery(name = "Interview.availableInterviewers", query = "SELECT u FROM UserEntity u WHERE u NOT IN (SELECT DISTINCT elements(i.interviewers) FROM InterviewEntity i WHERE i.id = :param)"),
		@NamedQuery(name = "Interview.availableScripts", query = "SELECT s FROM ScriptEntity s WHERE s NOT IN (SELECT i.script FROM InterviewEntity i WHERE i.id = :param)"),
		@NamedQuery(name = "Interview.byCandidate", query = "SELECT i FROM InterviewEntity i WHERE i.candidacy.candidate.id = :param AND i.feedback IS NOT EMPTY"),
		@NamedQuery(name = "Interview.allScheduled", query = "SELECT i FROM InterviewEntity i WHERE i.interviewState LIKE 'SCHEDULED'"),
		@NamedQuery(name = "Interview.getScheduledScript", query = "SELECT COUNT(i) FROM InterviewEntity i WHERE i.interviewState LIKE 'SCHEDULED' AND i.script.id = :param"),
		@NamedQuery(name = "Interview.getByScript", query = "SELECT i FROM InterviewEntity i WHERE i.interviewState LIKE 'DONE' AND i.script.id = :param"),
		@NamedQuery(name = "Interview.doneInterviews", query = "SELECT i FROM InterviewEntity i WHERE i.interviewState LIKE 'DONE'"),
		@NamedQuery(name = "Interview.doneInterviewsByCandidate", query = "SELECT i FROM InterviewEntity i WHERE i.interviewState LIKE 'DONE' and i.candidacy.candidate.id = :param")})
public class InterviewEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "referencia")
	private String interviewRef;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "data_entrevista", nullable = false)
	private LocalDate date;

	@Column(name = "feedback", columnDefinition = "TEXT")
	private String feedback;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "entrevistadores", uniqueConstraints = {
			@UniqueConstraint(columnNames = { "interview_id", "interviewer_id" }) }, joinColumns = {
					@JoinColumn(name = "interview_id") }, inverseJoinColumns = { @JoinColumn(name = "interviewer_id") })
	private List<UserEntity> interviewers;

	@ManyToOne(optional = true)
	private ScriptEntity script;

	@ManyToOne(optional = false)
	private PositionEntity position;

	@ManyToOne(optional = false)
	private CandidacyEntity candidacy;

	@Column(name = "avaliacao_global")
	private Integer globalRating = 0;

	@Column(name = "estado_entrevista", nullable = false)
	@Enumerated(EnumType.STRING)
	private InterviewState interviewState;

	@Column(name = "entrevistadores_realizaram")
	private Integer doneNumber = 0;

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

	public ScriptEntity getScript() {
		return script;
	}

	public void setScript(ScriptEntity script) {
		this.script = script;
	}

	public Integer getId() {
		return id;
	}

	public List<UserEntity> getInterviewers() {
		return interviewers;
	}

	public void setInterviewers(List<UserEntity> interviewers) {
		this.interviewers = interviewers;
	}

	public PositionEntity getPosition() {
		return position;
	}

	public void setPosition(PositionEntity position) {
		this.position = position;
	}

	public CandidacyEntity getCandidacy() {
		return candidacy;
	}

	public void setCandidacy(CandidacyEntity candidacy) {
		this.candidacy = candidacy;
	}

	public String getInterviewRef() {
		return interviewRef;
	}

	public void setInterviewRef(String interviewRef) {
		this.interviewRef = interviewRef;
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

	public Integer getGlobalRating() {
		return globalRating;
	}

	public void setGlobalRating(Integer globalRating) {
		this.globalRating = globalRating;
	}

	public InterviewState getInterviewState() {
		return interviewState;
	}

	public void setInterviewState(InterviewState interviewState) {
		this.interviewState = interviewState;
	}

	public Integer getDoneNumber() {
		return doneNumber;
	}

	public void setDoneNumber(Integer doneNumber) {
		this.doneNumber = doneNumber;
	}

}
