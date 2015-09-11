package pt.criticalsoftware.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "email")
@NamedQueries({ @NamedQuery(name = "Email.getActive", query = "SELECT e FROM EmailEntity e WHERE e.active is TRUE"),
		@NamedQuery(name = "Email.areActive", query = "SELECT COUNT(e) FROM EmailEntity e WHERE e.active is TRUE") })
public class EmailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "hostname")
	private String hostName;

	@Column(name = "smtpport")
	private Integer smtpPort;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "sslOnConnect")
	private Boolean sllOnConnect;

	@Column(name = "startTLS")
	private Boolean startTLS;

	@Column(name = "activo")
	private Boolean active;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public Boolean getSllOnConnect() {
		return sllOnConnect;
	}

	public void setSllOnConnect(Boolean sllOnConnect) {
		this.sllOnConnect = sllOnConnect;
	}

	public Boolean getStartTLS() {
		return startTLS;
	}

	public void setStartTLS(Boolean startTLS) {
		this.startTLS = startTLS;
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
		EmailEntity other = (EmailEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

}
