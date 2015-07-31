package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	private PositionState state;

}
