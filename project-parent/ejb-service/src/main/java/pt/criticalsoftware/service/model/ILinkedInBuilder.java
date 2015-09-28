package pt.criticalsoftware.service.model;

public interface ILinkedInBuilder {

	ILinkedInBuilder connections(Integer connections);

	ILinkedInBuilder profile(String profile);

	ILinkedInBuilder picture(String picture);

	ILinkedInBuilder summary(String summary);

	ILinkedInBuilder headline(String headline);

	ILinkedInBuilder candidate(ICandidate candidate);

	ICandidate build();

}
