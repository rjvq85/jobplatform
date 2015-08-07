package pt.criticalsoftware.service.model.dtos;


import pt.criticalsoftware.service.persistence.states.CandidacyState;

public class CandidacyDTO {
	
	private Integer id;

	private String motivationLetter;

	private String source;

	private CandidacyState state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMotivationLetter() {
		return motivationLetter;
	}

	public void setMotivationLetter(String motivationLetter) {
		this.motivationLetter = motivationLetter;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public CandidacyState getState() {
		return state;
	}

	public void setState(CandidacyState state) {
		this.state = state;
	}
	
	


}
