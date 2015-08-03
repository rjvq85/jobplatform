package pt.criticalsoftware.service.persistence.states;

public enum CandidacyState {

	SUBMTIDA("Submtida"),
	EM_ENTREVISTA("Em entrevista"),
	EM_NEGOCIACAO("Em Negociacao"),
	REJEITADA("Rejeitada"),
	FECHADA("fechada");
	
	private String name;
	
	private CandidacyState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
