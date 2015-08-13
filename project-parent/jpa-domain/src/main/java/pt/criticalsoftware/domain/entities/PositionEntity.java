package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.criticalsoftware.service.persistence.positions.TechnicalAreaType;
import pt.criticalsoftware.service.persistence.states.PositionState;

@Entity
@Table(name="posicoes")
@NamedQueries({
	@NamedQuery(name = "Position.getAll",query = "SELECT u from PositionEntity u"),
	@NamedQuery(name = "Position.verifyReference", query = "SELECT u FROM PositionEntity u WHERE u.reference = :reference"),
})
public class PositionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="data_abertura",nullable=false)
	private LocalDate openDate;
	
	@Column(name="data_fecho",nullable=false)
	private LocalDate closeDate;
	
	@Column(name="referencia",nullable=false)
	private String reference;
	
	@Column(name="titulo",nullable=false)
	private String title;
	
	@Column(name="localizacao",nullable=false)
	private String locale;
	
	@Column(name="estado",nullable=false)
	@Enumerated(EnumType.STRING)
	private PositionState state;
	
	@Column(name="empresa",nullable=false)
	private String company;
	
	@Column(name="area_tecnica",nullable=false)
	@Enumerated(EnumType.STRING)
	private TechnicalAreaType technicalArea;
	
	@Column(name="sla",nullable=false)
	private String sla;
	
	@Column(name="vagas",nullable=false)
	private Integer vacancies;
	
	@OneToOne
	private UserEntity responsable;
	
	@Column(name="descricao",nullable=false)
	private String description;
	
	@ElementCollection
	@Column(name="canais_publicacao")
	private Collection<String> adChannels;
	
	@OneToMany(mappedBy="position")
	private Collection<InterviewEntity> interviews;
	
	@OneToMany(mappedBy="positionCandidacy")
	private Collection<CandidacyEntity> candidacy;
	
	public PositionEntity() {
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public PositionState getState() {
		return state;
	}

	public void setState(PositionState state) {
		this.state = state;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public TechnicalAreaType getTechnicalArea() {
		return technicalArea;
	}

	public void setTechnicalArea(TechnicalAreaType technicalArea) {
		this.technicalArea = technicalArea;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}

	public Integer getVacancies() {
		return vacancies;
	}

	public void setVacancies(Integer vacancies) {
		this.vacancies = vacancies;
	}

	public UserEntity getResponsable() {
		return responsable;
	}

	public void setResponsable(UserEntity responsable) {
		this.responsable = responsable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<String> getAdChannels() {
		return adChannels;
	}

	public void setAdChannels(Collection<String> adChannels) {
		this.adChannels = adChannels;
	}

	public Collection<InterviewEntity> getInterviews() {
		return interviews;
	}

	public void setInterviews(Collection<InterviewEntity> interviews) {
		this.interviews = interviews;
	}

	public Collection<CandidacyEntity> getCandidacy() {
		return candidacy;
	}

	public void setCandidacy(Collection<CandidacyEntity> candidacy) {
		this.candidacy = candidacy;
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
		PositionEntity other = (PositionEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
