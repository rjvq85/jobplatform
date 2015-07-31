package pt.criticalsoftware.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(name="estado_candidatura",nullable=false)
	private String state;

}
