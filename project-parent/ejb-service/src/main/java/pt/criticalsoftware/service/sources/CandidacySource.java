package pt.criticalsoftware.service.sources;

public enum CandidacySource {
	
	CRITICAL("Critical Software's Site"),
	FACEBOOK("Facebook"),
	LINKEDIN("LinkedIn"),
	GOOGLE("Google"),
	GLASSDOR("Glassdoor"),
	OTHER("Outro");
	
	private String description;
	
	private CandidacySource(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
