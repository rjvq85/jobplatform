package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="notificacoes")
@NamedQueries({
	@NamedQuery(name="Notification.getAll",query="SELECT n FROM NotificationEntity n")
})
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="data_notificacao",nullable=false)
	private LocalDate date;
	

	@ManyToOne
	@JoinColumn(name = "receptor")
	private UserEntity receptor;
	

	@Column(name="situacao",nullable=false)
	private String situation;
	
	@Column(name="conteudo_mensagem",nullable=false)
	private String text;

	public NotificationEntity() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UserEntity getReceptor() {
		return receptor;
	}

	public void setReceptor(UserEntity receptor) {
		this.receptor = receptor;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
		NotificationEntity other = (NotificationEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
