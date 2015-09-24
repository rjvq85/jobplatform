package pt.criticalsoftware.service.model;

public interface ITokenBuilder {

	ITokenBuilder email(String email);

	IToken build();

}
