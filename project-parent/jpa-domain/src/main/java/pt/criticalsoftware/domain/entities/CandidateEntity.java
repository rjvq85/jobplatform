package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="candidatos")
public class CandidateEntity {
	
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
	
	@Column(name="telemovel",nullable=false, length=9)
	private Integer mobilePhone;
	
	@Column(name="curso")
	private Collection<String> course;
	
	@Column(name="grau")
	private Collection<String> degree;
	
	@Column(name="universidade")
	private Collection<String> university;
	
	@Column(name="cv")
	private String cv;
	
	@OneToMany(mappedBy="candidate")
	private Collection<CandidacyEntity> candidacies;
	
	public CandidateEntity() {
		
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Integer getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Integer mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Collection<String> getCourse() {
		return course;
	}

	public void setCourse(Collection<String> course) {
		this.course = course;
	}

	public Collection<String> getDegree() {
		return degree;
	}

	public void setDegree(Collection<String> degree) {
		this.degree = degree;
	}

	public Collection<String> getUniversity() {
		return university;
	}

	public void setUniversity(Collection<String> university) {
		this.university = university;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public Collection<CandidacyEntity> getCandidacies() {
		return candidacies;
	}

	public void setCandidacies(Collection<CandidacyEntity> candidacies) {
		this.candidacies = candidacies;
	}

	public Integer getId() {
		return id;
	}

	
}
