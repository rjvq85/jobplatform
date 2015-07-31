package pt.criticalsoftware.domain.entities;

import java.io.Serializable;
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

@Entity(name="utilizador")
@Table(name="utilizadores")
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	@OneToMany(mappedBy="interviewer",targetEntity=InterviewEntity.class)
	private Collection<InterviewEntity> interviews;
	
	
	
	

}
