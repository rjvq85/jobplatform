package pt.criticalsoftware.service.model.dtos;

import java.time.LocalDate;

public class NotificationDTO {

	private Integer id;

	private LocalDate date;

	private UserDTO receptor;

	private String situation;

	private String text;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UserDTO getReceptor() {
		return receptor;
	}

	public void setReceptor(UserDTO receptor) {
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
	
	

}
