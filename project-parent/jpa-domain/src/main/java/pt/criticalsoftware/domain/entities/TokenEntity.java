package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pt.criticalsoftware.service.persistence.utils.LocalDatePersistenceConverter;

@Entity
@Table(name = "tokens")
@NamedQueries({
	@NamedQuery(name = "Token.email",query="SELECT t.email FROM TokenEntity t WHERE token like :param"),
	@NamedQuery(name = "Token.date", query = "SELECT t.expiringDate FROM TokenEntity t WHERE token like :param"),
	@NamedQuery(name = "Token.token", query = "SELECT t FROM TokenEntity t WHERE token like :param"),
	@NamedQuery(name = "Token.all", query = "SELECT t FROM TokenEntity t")
})
public class TokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "token", nullable = false)
	private String token;

	@Convert(converter = LocalDatePersistenceConverter.class)
	@Column(name = "data_expiracao", nullable = false)
	private LocalDate expiringDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getExpiringDate() {
		return expiringDate;
	}

	public void setExpiringDate(LocalDate expiringDate) {
		this.expiringDate = expiringDate;
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
		TokenEntity other = (TokenEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
