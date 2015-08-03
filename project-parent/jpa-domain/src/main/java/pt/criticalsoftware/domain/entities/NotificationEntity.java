package pt.criticalsoftware.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="notificações")
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="data_notificação",nullable=false)
	private LocalDate date;
	

	@ManyToOne
	@JoinColumn(name = "receptor")
	private UserEntity receptor;
	

	@Column(name="situação",nullable=false)
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
	
	
	
}
