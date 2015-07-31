package pt.criticalsoftware.domain.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pt.criticalsoftware.domain.entities.states.CandidacyState;

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
	private CandidateEntity candidate; 
	
	

}
