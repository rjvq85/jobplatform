package pt.criticalsoftware.domain.entities.questions;

public enum AnswerType {

	UM_A_CINCO("1 a 5"),
	TEXTO_LIVRE("texto livre"),
	VERDADEIRO_FALSO("verdadeiro/falso");
	
	private String name;
	
	private AnswerType(String name) {
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
