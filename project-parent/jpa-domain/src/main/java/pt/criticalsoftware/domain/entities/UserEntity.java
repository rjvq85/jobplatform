package pt.criticalsoftware.domain.entities;

import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pt.criticalsoftware.domain.entities.roles.Roles;

@Entity
@Table(name="utilizadores")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="nome_utilizador", nullable=false)
	private String username;
	
	@Column(name="palavra_passe",nullable=false)
	private String password;
	
	@Column(name="endereco_email",nullable=false)
	private String email;
	
	@Column(name="primeiro_nome",nullable=false)
	private String firstName;
	
	@Column(name="sobrenome",nullable=false)
	private String lastName;
	
	@ElementCollection
	@CollectionTable(name="cargos",joinColumns=@JoinColumn(name="nome_utilizador",referencedColumnName="nome_utilizador"),uniqueConstraints=@UniqueConstraint(columnNames={"cargo","nome_utilizador"}))
	@Enumerated(EnumType.STRING)
	@Column(name="cargo")
	private Collection<Roles> roles;
	
	@OneToMany(mappedBy="interviewer")
	private Collection<InterviewEntity> interviews;
	
	@OneToMany(mappedBy="responsable")
	private Collection<PositionEntity> positions;

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

	public Collection<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Roles> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	

}
