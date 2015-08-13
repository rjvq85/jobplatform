package pt.criticalsoftware.service.model;

public interface ICandidateBuilder {

	ICandidate build();

	ICandidateBuilder firstName(String fn);

	ICandidateBuilder lastName(String lastName);

	ICandidateBuilder email(String email);

	ICandidateBuilder password(String password);

	ICandidateBuilder username(String username);

	ICandidateBuilder address(String address);

	ICandidateBuilder town(String town);

	ICandidateBuilder country(String country);

	ICandidateBuilder phone(Integer phone);

	ICandidateBuilder mobile(Integer mobile);

	ICandidateBuilder course(String course);

	ICandidateBuilder degree(String degree);

	ICandidateBuilder school(String school);

	ICandidateBuilder cv(String cvPath);

}
