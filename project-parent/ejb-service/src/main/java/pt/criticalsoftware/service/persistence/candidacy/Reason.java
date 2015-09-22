package pt.criticalsoftware.service.persistence.candidacy;

public enum Reason {
	
	PROFILE("Não se enquadra no perfil desejado para a posição"),
	UNAVAILABILITY("Sem disponibilidade (horário, deslocação, etc...)"),
	INTERVIEW("Não correspondeu às expectativas durante a entrevista"),
	OTHER("Outra(s)");
	
	private String explanation;
	
	private Reason(String explanation) {
		this.explanation = explanation;
	}

	public String getExplanation() {
		return explanation;
	}
	
	@Override
	public String toString() {
		return this.explanation;
	}

}
