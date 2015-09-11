package pt.criticalsoftware.domain.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pt.criticalsoftware.service.persistence.roles.Role;

@Entity
@Table(name = "utilizadores")
@NamedQueries({
		@NamedQuery(name = "User.findIdByUsername", query = "SELECT u.id FROM UserEntity u WHERE u.username = :username"),
		@NamedQuery(name = "User.getRoles", query = "SELECT r FROM UserEntity u JOIN u.roles r WHERE u.id = :id"),
		@NamedQuery(name = "User.verifyEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
		@NamedQuery(name = "User.verifyUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
		@NamedQuery(name = "User.findByRole", query = "SELECT u FROM UserEntity u"),
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM UserEntity u")

})
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "cargos", joinColumns = @JoinColumn(name = "nome_utilizador", referencedColumnName = "nome_utilizador") , uniqueConstraints = @UniqueConstraint(columnNames = {
			"cargo", "nome_utilizador" }) )
	@Enumerated(EnumType.STRING)
	@Column(name = "cargo")
	private List<Role> roles;

	@OneToMany(mappedBy = "responsable")
	private Collection<PositionEntity> positions;

	@OneToMany(mappedBy = "receptor")
	private Collection<NotificationEntity> notifications;

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Collection<PositionEntity> getPositions() {
		return positions;
	}

	public void setPositions(Collection<PositionEntity> positions) {
		this.positions = positions;
	}

	public Collection<NotificationEntity> getNotifications() {
		return notifications;
	}

	public void setNotifications(Collection<NotificationEntity> notifications) {
		this.notifications = notifications;
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
		UserEntity other = (UserEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
