package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="candidatos")
public class Candidate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="morada",nullable=false)
	private String address;
	
	@Column(name="cidade",nullable=false)
	private String town;
	
	@Column(name="pais",nullable=false)
	private String country;
	
	@Column(name="telefone")
	private Integer phone;
	
	@Column(name="telemovel",nullable=false)
	private Integer mobilePhone;
	
	@Column(name="curso")
	private Collection<String> course;
	
	@Column(name="grau")
	private Collection<String> degree;
	
	@Column(name="universidade")
	private Collection<String> university;
	
	@Column(name="cv")
	private String cv;
	

}
