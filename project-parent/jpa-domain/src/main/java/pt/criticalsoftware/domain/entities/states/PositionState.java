package pt.criticalsoftware.domain.entities.states;

public enum PositionState {
	ABERTA("Aberta"),
	FECHADA("Fechada"),
	EM_ESPERA("Em Espera");
	
	private String name;
	
	private PositionState(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
