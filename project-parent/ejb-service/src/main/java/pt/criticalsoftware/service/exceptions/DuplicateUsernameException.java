package pt.criticalsoftware.service.exceptions;

public class DuplicateUsernameException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicateUsernameException() {
	}

	public DuplicateUsernameException(String message) {
		super(message);
	}

}
