package pt.criticalsoftware.service.persistence.positions;

public enum TechnicalAreaType {
	
	SSPA("SSPA"),
	NET_DEVELOPMENT(".Net Development"),
	JAVA_DEVELOPMENT("Java Development"),
	SAFETY_CRITICAL("Safety Critical"),
	PROJECT_MANAGEMENT("Project Management"),
	INTEGRATION("Integration");
	
	private String name;
	
	private TechnicalAreaType(String name) {
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
