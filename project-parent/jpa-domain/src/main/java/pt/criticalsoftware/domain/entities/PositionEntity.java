package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pt.criticalsoftware.domain.entities.states.PositionState;

@Entity
@Table(name="posicoes")
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
	private String technicalArea;
	
	@Column(name="sla",nullable=false)
	private String sla;
	
	@Column(name="vagas",nullable=false)
	private Integer vacancies;
	
	@Column(name="responsavel",nullable=false)
	@OneToOne
	private UserEntity responsable;
	
	@Column(name="descricao",nullable=false)
	private String description;
	
	@Column(name="canais_publicacao",nullable=false)
	private Collection<String> adChannels;
	
	@OneToMany(mappedBy="position")
	private Collection<InterviewEntity> interviews;
	
	@OneToMany(mappedBy="positionCandidacy")
	private Collection<CandidacyEntity> candidacy;

}
