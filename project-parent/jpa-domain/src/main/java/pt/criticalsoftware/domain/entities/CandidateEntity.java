package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "candidatos")

@NamedQueries({ @NamedQuery(name = "Candidate.findAll", query = "select p from CandidateEntity p"),
		@NamedQuery(name = "Candidate.findByFirstName", query = "select p from CandidateEntity p where p.firstName = :firstName"),
		@NamedQuery(name = "Candidate.findByLastName", query = "select p from CandidateEntity p where p.lastName = :lastName"),
		@NamedQuery(name = "Candidate.findByTown", query = "select p from CandidateEntity p where p.town = :town"),
		@NamedQuery(name = "Candidate.findByCountry", query = "select p from CandidateEntity p where p.country = :country"),
		@NamedQuery(name = "Candidate.findById", query = "select p from CandidateEntity p where p.id = :candidateId "),
		@NamedQuery(name = "Candidate.findByUsername", query = "SELECT c FROM CandidateEntity c WHERE c.username LIKE :param"),
		@NamedQuery(name = "Candidate.findByEmail", query = "SELECT c FROM CandidateEntity c WHERE c.email LIKE :param"),
		@NamedQuery(name = "Candidate.findDuplicateByUsername", query = "SELECT COUNT(p) FROM CandidateEntity p where UPPER(p.username) LIKE :param"),
		@NamedQuery(name = "Candidate.findDuplicateByEmail", query = "SELECT COUNT(p) FROM CandidateEntity p where UPPER(p.email) LIKE :param") })
public class CandidateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "nome_utilizador", nullable = false, unique = true)
	private String username;

	@Column(name = "palavra_passe", nullable = false)
	private String password;

	@Column(name = "endereco_email", nullable = false, unique = true)
	private String email;

	@Column(name = "primeiro_nome", nullable = false)
	private String firstName;

	@Column(name = "sobrenome", nullable = false)
	private String lastName;

	@Column(name = "morada", nullable = false)
	private String address;

	@Column(name = "cidade", nullable = false)
	private String town;

	@Column(name = "pais", nullable = false)
	private String country;

	@Column(name = "telefone")
	private Integer phone;

	@Column(name = "telemovel", nullable = false, length = 9)
	private Integer mobilePhone;

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "curso")
	private Collection<String> course;

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "grau")
	private Collection<String> degree;

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "universidade")
	private Collection<String> university;

	@Column(name = "cv", unique = true)
	private String cv;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "candidate")
	private Collection<CandidacyEntity> candidacies;

	public CandidateEntity() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		CandidateEntity other = (CandidateEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
