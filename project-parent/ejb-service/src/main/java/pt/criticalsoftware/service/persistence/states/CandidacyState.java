package pt.criticalsoftware.service.persistence.states;

public enum CandidacyState {

	SUBMETIDA("Submetida"),
	EM_ENTREVISTA("Em entrevista"),
	EM_NEGOCIACAO("Em Negocição"),
	REJEITADA("Rejeitada"),
	FECHADA("Fechada");
	
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
