package pt.criticalsoftware.service.persistence.states;

public enum InterviewState {
	
	SCHEDULED("Agendada"), DONE("Realizada");
	
	private String description;
	
	private InterviewState(String d) {
		description = d;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return this.description;
	}

}
